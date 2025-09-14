/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entities.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.UsuarioFacadeLocal;



/**
 *
 * @author Jhon Deibys Torres
 * 
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String usuario;
    private String contrasenna;
    private String nombre_usuario;

   
    private Usuario user = new Usuario();
    
    @EJB
    UsuarioFacadeLocal ufl;

  

    public Login(String usuario, String contrasenna, String nombre_usuario) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
        this.nombre_usuario = nombre_usuario;
    }

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

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
    
    public String iniciarSesion(){
       user = this.ufl.iniciarSesion(nombre_usuario,usuario, contrasenna);
        if (user.getIdentificacion() != null && user.getRolId().getIdrol() == 1) {
            HttpSession sesion = ( HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sesion.setAttribute("usuario", usuario);
            return "/resources/views/inicio_Admin.xhtml?faces-redirect=true";
        }else if(user.getIdentificacion() != null && user.getRolId().getIdrol() == 2){
             HttpSession sesion = ( HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sesion.setAttribute("usuario", usuario);
            return "/resources/views/inicio_Template.xhtml?faces-redirect=true";
        }
        else{
            FacesContext context =  FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario, nombre de usuario y/o contraseña invalidos", "MSG_ERROR");
            context.addMessage(null, fm);
            return null;
        }
    }
    
    public Login() {
    }
    
   public String cerrarSesion(){
       
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       return "/newLogin.xhtml?faces-redirect=true";
   }
}
