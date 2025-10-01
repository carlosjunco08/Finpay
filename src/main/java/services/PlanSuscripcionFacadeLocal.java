/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.PlanSuscripcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface PlanSuscripcionFacadeLocal {

    void create(PlanSuscripcion planSuscripcion);

    void edit(PlanSuscripcion planSuscripcion);

    void remove(PlanSuscripcion planSuscripcion);

    PlanSuscripcion find(Object id);

    List<PlanSuscripcion> findAll();

    List<PlanSuscripcion> findRange(int[] range);

    int count();
    
}
