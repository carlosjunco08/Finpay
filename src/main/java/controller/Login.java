package controller;

import entities.Usuario;
import services.UsuarioFacadeLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ejb.EJB;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Bean de login compatible con bindings antiguos (nombreUsuario) y con correo.
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioFacadeLocal ufl;

    // Compatibilidad: soportamos nombreUsuario (vista antigua) y correo (opcional)
    private String nombreUsuario;
    private String correo;
    private String contrasena;

    private Usuario usuarioLogueado;

    public Login() {
    }

    // ===== Getters / Setters =====
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    // ✅ Alias para compatibilidad en vistas (#{login.usuario})
    public Usuario getUsuario() {
        return usuarioLogueado;
    }

    // ===== Acción: iniciar sesión =====
    public String iniciarSesion() {
        try {
            // Validar input
            if ((nombreUsuario == null || nombreUsuario.trim().isEmpty())
                    && (correo == null || correo.trim().isEmpty())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese usuario o correo", null));
                return null;
            }
            if (contrasena == null || contrasena.trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese la contrasena", null));
                return null;
            }

            Usuario user = null;

            // 1) intentar por nombre de usuario (compatibilidad)
            if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
                user = ufl.findByUsername(nombreUsuario.trim());
            }

            // 2) si no encontró por usuario, intentar por correo
            if (user == null && correo != null && !correo.trim().isEmpty()) {
                user = ufl.findByCorreo(correo.trim().toLowerCase());
            }

            // 3) validar existencia y contrasena (BCrypt)
            if (user != null && user.getContrasena() != null && BCrypt.checkpw(contrasena, user.getContrasena())) {
                this.usuarioLogueado = user;
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioLogueado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido " + usuarioLogueado.getNombres(), null));

                // Redirigir según rol (usa rolId, nombreRol en tu entidad Rol)
                String rolNombre = null;
                if (usuarioLogueado.getRolId() != null) {
                    rolNombre = usuarioLogueado.getRolId().getNombreRol();
                }
                if (rolNombre != null && rolNombre.equalsIgnoreCase("Administrador")) {
                    return "views/inicio_Admin.xhtml?faces-redirect=true";
                } else if (rolNombre != null && rolNombre.equalsIgnoreCase("Cliente")) {
                    return "views/inicio_Template.xhtml?faces-redirect=true";
                } else {
                    return "index.xhtml?faces-redirect=true";
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contrasena incorrectos", null));
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al iniciar sesión", null));
            return null;
        }
    }

    // ===== Cerrar sesión =====
    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/newLogin.xhtml?faces-redirect=true"; // ajusta al nombre de tu página de login
    }
}
