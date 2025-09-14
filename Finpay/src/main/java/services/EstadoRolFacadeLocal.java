/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.EstadoRol;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface EstadoRolFacadeLocal {

    void create(EstadoRol estadoRol);

    void edit(EstadoRol estadoRol);

    void remove(EstadoRol estadoRol);

    EstadoRol find(Object id);

    List<EstadoRol> findAll();

    List<EstadoRol> findRange(int[] range);

    int count();
    
}
