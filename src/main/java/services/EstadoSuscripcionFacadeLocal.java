/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.EstadoSuscripcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface EstadoSuscripcionFacadeLocal {

    void create(EstadoSuscripcion estadoSuscripcion);

    void edit(EstadoSuscripcion estadoSuscripcion);

    void remove(EstadoSuscripcion estadoSuscripcion);

    EstadoSuscripcion find(Object id);

    List<EstadoSuscripcion> findAll();

    List<EstadoSuscripcion> findRange(int[] range);

    int count();
    
}
