package controller;

import entities.Transaccion;
import entities.Usuario;
import services.TransaccionService;
import controller.Login;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
// Usaremos la anotación CDI si tienes CDI configurado, sino JSF (ManagedBean)

@ManagedBean // Nombre del bean en EL: finanzasBean
@ViewScoped
public class FinanzasBean implements Serializable {

    // Inyecta el EJB creado en el Paso 2
    @EJB
    private TransaccionService transaccionService;

    private List<Transaccion> listaTransacciones;
    private Transaccion nuevaTransaccion = new Transaccion();

    // Método de utilidad para obtener el usuario de la sesión
    private Usuario getUsuarioActual() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // 1. Obtener la instancia del bean de login
        Login loginBean = (Login) context.getApplication().evaluateExpressionGet(
            context, "#{login}", Login.class);
        
        // 2. LLAMADA CORREGIDA: Usamos el getter real del bean de Login
        return loginBean != null ? loginBean.getUsuarioLogueado() : null;
    }

    @PostConstruct
    public void init() {
        Usuario usuarioActual = getUsuarioActual();
        if (usuarioActual != null) {
            listaTransacciones = transaccionService.obtenerPorUsuario(usuarioActual);
        }
    }

    public void guardarTransaccion() {
        Usuario usuarioActual = getUsuarioActual();
        if (usuarioActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe iniciar sesión para continuar."));
            return;
        }

        try {
            nuevaTransaccion.setUsuario(usuarioActual); 
            transaccionService.guardar(nuevaTransaccion);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transacción guardada con éxito."));
            
            init(); 
            nuevaTransaccion = new Transaccion();
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar: " + e.getMessage()));
        }
    }
    
    public void eliminarTransaccion(Transaccion t) {
        try {
            transaccionService.eliminar(t);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transacción eliminada con éxito."));
            init(); 
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la transacción."));
        }
    }

    // --- Getters y Setters ---

    public List<Transaccion> getListaTransacciones() { return listaTransacciones; }
    public Transaccion getNuevaTransaccion() { return nuevaTransaccion; }
    public void setNuevaTransaccion(Transaccion nuevaTransaccion) { this.nuevaTransaccion = nuevaTransaccion; }
}