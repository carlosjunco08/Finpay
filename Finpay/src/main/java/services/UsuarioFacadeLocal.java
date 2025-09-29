/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    // CORRECCIÓN CLAVE: Ahora solo se esperan el correo y la contraseña
    Usuario iniciarSesion(String correo, String contrasena); 
    
    Usuario findByIdentificacion(String identificacion);
    Usuario findByCorreo(String correo);
     Usuario findByUsername(String user_name);
    
}