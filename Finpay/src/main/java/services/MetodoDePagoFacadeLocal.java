/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.MetodoDePago;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface MetodoDePagoFacadeLocal {

    void create(MetodoDePago metodoDePago);

    void edit(MetodoDePago metodoDePago);

    void remove(MetodoDePago metodoDePago);

    MetodoDePago find(Object id);

    List<MetodoDePago> findAll();

    List<MetodoDePago> findRange(int[] range);

    int count();
    
}
