package services;

import entities.Transaccion;
import entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless // Importante: EJB sin estado
public class TransaccionServiceImpl implements TransaccionService {

    @PersistenceContext(unitName = "com.Dev-finpay_war_1.0-SNAPSHOTPU") 
    private EntityManager em;

    @Override
    public void guardar(Transaccion transaccion) {
        if (transaccion.getId() == null) {
            em.persist(transaccion); // Nuevo registro
        } else {
            em.merge(transaccion); // Actualización
        }
    }

    @Override
    public List<Transaccion> obtenerPorUsuario(Usuario usuario) {
        TypedQuery<Transaccion> query = em.createQuery(
            // JPQL: Consulta las transacciones asociadas a un usuario
            "SELECT t FROM Transaccion t WHERE t.usuario = :usuario ORDER BY t.fecha DESC", Transaccion.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    @Override
    public void eliminar(Transaccion transaccion) {
        em.remove(em.merge(transaccion)); // Merge previo para asegurar la gestión de la entidad
    }
}