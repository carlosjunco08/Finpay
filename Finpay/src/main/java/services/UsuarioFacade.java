/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    @Override
    public Usuario iniciarSesion(String nombre_user, String nombres, String contrasenna) {
       Query query = em.createQuery("SELECT U FROM Usuario U WHERE U.nombreUsuario=:nombre_user AND U.nombres=:nombres AND U.contraseña=:password");
       query.setParameter("nombre_user", nombre_user);
        query.setParameter("nombres", nombres);
        query.setParameter("password", contrasenna);
        
        
        try {
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
        }
        
        Usuario userIn = new Usuario();
        return  userIn;
    }

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
