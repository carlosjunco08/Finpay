package services;

import entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jhon Deibys Torres
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "com.Dev-Jhon_LoginJSF_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    // 🛑 MÉTODO CORREGIDO
    /**
     * Busca un usuario por correo electrónico y contraseña. 
     * Usa LOWER() en el correo para mayor robustez, y consulta directa 
     * en la contraseña (asumiendo que Login.java ya aplicó trim()).
     */
    @Override
    public Usuario iniciarSesion(String correo, String contrasena) {
        
        // 🔑 CORRECCIÓN FINAL: Usamos LOWER() en el correo para insensibilidad a mayúsculas/minúsculas.
        // Quitamos TRIM() para evitar el Error 1064 de sintaxis de JPQL.
        Query query = em.createQuery(
            "SELECT u FROM Usuario u WHERE LOWER(u.correo) = LOWER(:correo_user) AND u.contrasena = :pass"
        );
        
        query.setParameter("correo_user", correo);
        query.setParameter("pass", contrasena); // Se pasa la contraseña en texto plano
        
        try {
            // Si se encuentra un resultado, se devuelve el objeto Usuario.
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            // Si no hay resultado, la combinación de correo/contraseña es inválida.
            return null; 
        } catch (Exception e) {
            // Error de BD (imprimimos el error para diagnóstico)
            e.printStackTrace(); 
            return null; 
        }
    }
    
    // --- El resto de métodos NO requieren modificación ---
    
    @Override
    public Usuario findByIdentificacion(String identificacion) {
        try {
        return (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.identificacion = :ident")
                        .setParameter("ident", identificacion)
                        .getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
    }

    @Override
    public Usuario findByCorreo(String correo) {
        try {
        return (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo")
                        .setParameter("correo", correo)
                        .getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
    }

    @Override
    public Usuario findByUsername(String user_name) {
          try {
        return (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :user_name")
                        .setParameter("user_name", user_name)
                        .getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
    }
}