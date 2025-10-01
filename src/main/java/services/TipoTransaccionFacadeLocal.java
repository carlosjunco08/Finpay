/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.TipoTransaccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface TipoTransaccionFacadeLocal {

    void create(TipoTransaccion tipoTransaccion);

    void edit(TipoTransaccion tipoTransaccion);

    void remove(TipoTransaccion tipoTransaccion);

    TipoTransaccion find(Object id);

    List<TipoTransaccion> findAll();

    List<TipoTransaccion> findRange(int[] range);

    int count();
    
}
