/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entities.EstadoRol;
import entities.Pais;
import entities.Rol;
import entities.Usuario;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import services.EstadoRolFacadeLocal;
import services.PaisFacadeLocal;
import services.RolFacadeLocal;
import services.UsuarioFacadeLocal;
import com.google.gson.Gson;
import entities.Suscripcion;
import java.util.stream.Collectors;
import services.SuscripcionFacadeLocal;
/**
 *
 * @author Jhon Deibys Torres
 */
@Named(value = "usuariosController")
@SessionScoped
public class UsuariosController implements Serializable{

    /**
     * Creates a new instance of UsuariosController
     */
    Usuario user = new Usuario();
    Rol rol = new Rol();
    Pais pais = new Pais();
    EstadoRol est = new EstadoRol();
    Suscripcion  sub = new Suscripcion();
    
    @EJB
    PaisFacadeLocal pfl;
    @EJB
    UsuarioFacadeLocal ufl;
    @EJB
    RolFacadeLocal rfl;
    @EJB
    EstadoRolFacadeLocal erfl;
    @EJB
     SuscripcionFacadeLocal subs;

    List<SelectItem> listaPaises;
    List<SelectItem> listarEstados;
    List<SelectItem> listarRoles;

    public Suscripcion getSub() {
        return sub;
    }

    public void setSub(Suscripcion sub) {
        this.sub = sub;
    }

    
    public EstadoRol getEst() {
        return est;
    }

    public void setEst(EstadoRol est) {
        this.est = est;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Usuario> listarUsuarios() {

        try {
            return this.ufl.findAll();
        } catch (Exception e) {
        }

        return null;
    }

    public UsuariosController() {
    }

    public List<SelectItem> listarPaises() {

        listaPaises = new ArrayList<>();
        try {
            for (Pais pais : this.pfl.findAll()) {

                SelectItem item = new SelectItem(pais.getIdpais(), pais.getNombrePais());
                listaPaises.add(item);

            }

            return listaPaises;
        } catch (Exception e) {
        }
        return null;
    }

    public List<SelectItem> listarEstados() {

        listarEstados = new ArrayList<>();
        try {
            for (EstadoRol erol : this.erfl.findAll()) {
                if ("Activo".equalsIgnoreCase(erol.getEstadoRol())) {
                    SelectItem item = new SelectItem(erol.getIdestadoRol(), erol.getEstadoRol());
                    listarEstados.add(item);
                }
            }

            return listarEstados;
        } catch (Exception e) {
        }
        return null;
    }

    @PostConstruct
    public void init() {
        user = new Usuario();
        user.setRolId(new Rol());    // ← Esto es obligatorio
        user.setPaisId(new Pais());  // ← Esto también
    }

    public void crearUsuarioPartOne() {

        this.user = new Usuario();
        this.user.setPaisId(new Pais());
        this.user.setRolId(new Rol());
        this.rol.setEstadoRolId(new EstadoRol());

    }

    public String crearUsuario() {

        try {
            Usuario existente = ufl.findByIdentificacion(user.getIdentificacion());
            if (existente != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este Documento Ya Existe", "Ya existe un usuario con este documento."));
                return null;
            }
            Usuario usermaneexistente = ufl.findByUsername(user.getNombreUsuario());
            if (usermaneexistente != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este nombre de usuario ya Existe", "Ya existe un usuario con este nombre de usuario."));
                return null;
            }   

            Usuario correoexistente = ufl.findByCorreo(user.getCorreo());
            if (correoexistente != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este Correo Ya Existe", "Ya existe un usuario con este correo."));
                return null;
            }
            Rol rolSeleccionado = rfl.find(user.getRolId().getIdrol());
            user.setPaisId(pfl.find(pais.getIdpais())); // pais es el objeto seleccionado en el menú
            user.setRolId(rolSeleccionado);
            this.ufl.create(user);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario registrado correctamente", null));

            if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("Admin")) {
                return "inicio_Admin.xhtml?faces-redirect=true";
            }

        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        } catch (ConstraintViolationException ex) {
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                System.out.println("Error en propiedad: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace(); // revisa bien esta parte en consola
        }
        return null;
    }

    public List<SelectItem> listarRol() {

        listarRoles = new ArrayList<>();
        try {
            for (Rol rol : this.rfl.findAll()) {
                if ("Cliente".equalsIgnoreCase(rol.getNombreRol())) {
                    SelectItem item = new SelectItem(rol.getIdrol(), rol.getNombreRol());
                    listarRoles.add(item);
                }
            }

            return listarRoles;
        } catch (Exception e) {
        }
        return null;
    }

    public List<SelectItem> listarRolAdmin() {

        listarRoles = new ArrayList<>();
        try {
            for (Rol rol : this.rfl.findAll()) {

                SelectItem item = new SelectItem(rol.getIdrol(), rol.getNombreRol());
                listarRoles.add(item);

            }

            return listarRoles;
        } catch (Exception e) {
        }
        return null;
    }

    public void editarUserOne(Usuario u) {

        this.user = u;

    }

    public void editarStepTwo() {

        try {
            Rol rolSeleccionado = rfl.find(user.getRolId().getIdrol());
            user.setPaisId(pfl.find(user.getPaisId().getIdpais())); // pais es el objeto seleccionado en el menú
            user.setRolId(rolSeleccionado);
            this.ufl.edit(user);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Editado Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);
        } catch (Exception e) {
        }

    }

    public void Eliminar(Usuario user) {
        try {
            this.ufl.remove(user);
            listarUsuarios(); // recarga la lista en memoria
            
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Eliminado Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar usuario: " + e.getMessage(), "MSG_ERROR");
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
       
    }

    public String redirigir() {

        crearUsuario();
        return "inicio_Admin.xhtml";
    }

    public String getJsonUsuariosPorRol() {
    Map<String, Integer> conteoPorRol = new HashMap<>();

    for (Usuario u : ufl.findAll()) {
        String nombreRol = u.getRolId().getNombreRol();
        conteoPorRol.put(nombreRol, conteoPorRol.getOrDefault(nombreRol, 0) + 1);
    }

    // Convertimos a formato JSON plano: ej. [{"rol":"Cliente", "cantidad":3}, ...]
    StringBuilder json = new StringBuilder("[");
    for (Map.Entry<String, Integer> entry : conteoPorRol.entrySet()) {
        json.append("{\"rol\":\"")
            .append(entry.getKey())
            .append("\",\"cantidad\":")
            .append(entry.getValue())
            .append("},");
    }

    if (json.length() > 1) json.setLength(json.length() - 1); // quitar última coma
    json.append("]");
    return json.toString();
}
    
 public String getJsonUsuariosPorPais() {
    try {
        // Obtiene todos los usuarios
        List<Usuario> usuarios = ufl.findAll();

        // Agrupa usuarios por nombre de país, asegurando que no sea null
        Map<String, Long> usuariosPorPais = usuarios.stream()
            .filter(u -> u.getPaisId() != null && u.getPaisId().getNombrePais() != null)
            .collect(Collectors.groupingBy(u -> u.getPaisId().getNombrePais(), Collectors.counting()));

        // Convierte el Map a una lista de mapas para JSON
        List<Map<String, Object>> listaDatos = usuariosPorPais.entrySet().stream()
            .map(e -> {
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("pais", e.getKey());
                mapa.put("cantidad", e.getValue());
                return mapa;
            })
            .collect(Collectors.toList());

        // Convierte la lista a JSON
        Gson gson = new Gson();
        return gson.toJson(listaDatos);

    } catch (Exception e) {
        e.printStackTrace();
    }
    // Retorna lista vacía JSON en caso de error
    return "[]";
}

}
