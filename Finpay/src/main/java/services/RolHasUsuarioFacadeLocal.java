/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.RolHasUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface RolHasUsuarioFacadeLocal {

    void create(RolHasUsuario rolHasUsuario);

    void edit(RolHasUsuario rolHasUsuario);

    void remove(RolHasUsuario rolHasUsuario);

    RolHasUsuario find(Object id);

    List<RolHasUsuario> findAll();

    List<RolHasUsuario> findRange(int[] range);

    int count();
    
}
