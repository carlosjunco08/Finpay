/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import entities.Usuario; // Asegúrate de que esta entidad exista en tu proyecto
import services.GeminiServiceLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ejb.EJB;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@Named(value = "iaBean")
@SessionScoped
public class AsesorFinancieroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private GeminiServiceLocal geminiService;

    private String consultaUsuario;
    private String respuestaIA = "Escribe tu pregunta para el Asesor AI y recibe un consejo personalizado.";
    private boolean cargando = false; 

    public void enviarConsulta() {
        if (consultaUsuario == null || consultaUsuario.trim().isEmpty()) {
             FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor escribe tu consulta."));
            return;
        }

        try {
            cargando = true;
            
            Usuario usuario = getUsuarioLogueado();
            String contexto = buildContexto(usuario);
            
            respuestaIA = geminiService.consultarIA(consultaUsuario, contexto);
            
            consultaUsuario = "";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al contactar al AI: " + e.getMessage()));
            respuestaIA = "No fue posible contactar al Asesor AI.";
            e.printStackTrace();
        } finally {
            cargando = false;
        }
    }
    
    private String buildContexto(Usuario u) {
        if (u == null) {
            return "El usuario no está logueado o es anónimo. Ofrece consejos generales de finanzas personales.";
        }
        
        // Generación de contexto basado en tu entidad Usuario
        return String.format("El usuario está en Finpay. Su nombre es %s, su edad es %d. Proporcione un consejo financiero accionable.", 
                u.getNombres() + " " + u.getApellidos(), u.getEdad());
    }
    
    // Asume que la clase Login.java guarda el usuario en la sesión como "usuarioLogueado"
    private Usuario getUsuarioLogueado() {
        return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogueado");
    }

    // ===== Getters y Setters =====
    public String getConsultaUsuario() { return consultaUsuario; }
    public void setConsultaUsuario(String consultaUsuario) { this.consultaUsuario = consultaUsuario; }
    public String getRespuestaIA() { return respuestaIA; }
    public void setRespuestaIA(String respuestaIA) { this.respuestaIA = respuestaIA; }
    public boolean isCargando() { return cargando; }
}
