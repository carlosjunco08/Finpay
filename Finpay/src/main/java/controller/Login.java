package controller;

import entities.Usuario;
import javax.inject.Named;
// import javax.enterprise.context.SessionScoped; // COMENTAR O ELIMINAR EL AMBITO ANTERIOR
import javax.faces.view.ViewScoped; // ÁMBITO RECOMENDADO
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.UsuarioFacadeLocal;

/**
 *
 * @author Jhon Deibys Torres
 */
@Named(value = "login")
@ViewScoped // Mantenemos ViewScoped
public class Login implements Serializable {

    private String usuario; // Campo para el Correo electrónico
    private String contrasena; // 🛑 CORRECCIÓN: Variable estandarizada a 'contrasena'
    private String nombre_usuario; 

    
    private Usuario user = new Usuario();
    
    @EJB
    UsuarioFacadeLocal ufl; 

    // Constructor por defecto
    public Login() {
    }
    
    // --- Getters y Setters ---

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() { // 🛑 CORRECCIÓN: Getter para 'contrasena'
        return contrasena;
    }

    public void setContrasena(String contrasena) { // 🛑 CORRECCIÓN: Setter para 'contrasena'
        this.contrasena = contrasena;
    }
    
    // --- LÓGICA DE INICIO DE SESIÓN CORREGIDA ---
    
    public String iniciarSesion(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        // PRUEBA DE NULIDAD DEL EJB
        if (this.ufl == null) {
             context.addMessage(null, 
                 new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de Configuración", "El servicio de login (EJB) no pudo ser cargado. Contacte a soporte."));
             return null;
        }

        // Limpiar los datos de entrada
        String correoLimpio = this.usuario != null ? this.usuario.trim() : null;
        // 🔑 Usamos la variable CORREGIDA 'this.contrasena'
        String contrasenaLimpia = this.contrasena != null ? this.contrasena.trim() : null; 
        
        // El FACADE busca por CORREO Y CONTRASEÑA.
        Usuario usuarioLogeado = this.ufl.iniciarSesion(correoLimpio, contrasenaLimpia);
        
        // Solo verificamos si el Facade encontró el usuario (lo que implica credenciales válidas).
        if (usuarioLogeado != null) {
            
            // 1. Establecer Sesión
            ExternalContext externalContext = context.getExternalContext();
            HttpSession sesion = (HttpSession) externalContext.getSession(true);
            sesion.setAttribute("usuario", usuarioLogeado); 

            // 2. Redireccionar según el Rol
            if (usuarioLogeado.getRolId().getIdrol() == 1) {
                return "views/inicio_Admin.xhtml?faces-redirect=true"; 
            } else if(usuarioLogeado.getRolId().getIdrol() == 2){
                return "views/inicio_Template.xhtml?faces-redirect=true"; 
            } else {
                 FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso Denegado", "Rol no mapeado para la aplicación.");
                 context.addMessage(null, fm);
                 return null;
            }
        }
        // 3. Login fallido: El Facade devolvió null.
        else{
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo y/o contraseña inválidos", "MSG_ERROR");
            context.addMessage(null, fm);
            return null;
        }
    }
    
    // --- Método de Cierre de Sesión ---
    
    public String cerrarSesion(){
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/newLogin.xhtml?faces-redirect=true";
    }
}