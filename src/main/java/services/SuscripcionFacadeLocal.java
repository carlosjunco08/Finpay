/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.Suscripcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface SuscripcionFacadeLocal {

    void create(Suscripcion suscripcion);

    void edit(Suscripcion suscripcion);

    void remove(Suscripcion suscripcion);

    Suscripcion find(Object id);

    List<Suscripcion> findAll();

    List<Suscripcion> findRange(int[] range);

    int count();
    
}
