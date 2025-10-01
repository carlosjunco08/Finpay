package services;

import entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "com.Dev-finpay_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Método para iniciar sesión con contrasena cifrada
     */
    @Override
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        try {
            // 🔹 Buscar el usuario por nombre de usuario
            Usuario user = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getSingleResult();

            // 🔹 Validar la contrasena cifrada con BCrypt
            if (user != null && BCrypt.checkpw(contrasena, user.getContrasena())) {
                return user; // Login correcto
            }
        } catch (NoResultException e) {
            return null; // Usuario no encontrado
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Si algo falla, retorna null
    }

    @Override
    public Usuario findByIdentificacion(String identificacion) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.identificacion = :ident", Usuario.class)
                    .setParameter("ident", identificacion)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario findByCorreo(String correo) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class)
                    .setParameter("correo", correo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario findByUsername(String user_name) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :user_name", Usuario.class)
                    .setParameter("user_name", user_name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
