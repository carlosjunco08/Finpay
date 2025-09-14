/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.TipoReporte;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jhon Deibys Torres
 */
@Local
public interface TipoReporteFacadeLocal {

    void create(TipoReporte tipoReporte);

    void edit(TipoReporte tipoReporte);

    void remove(TipoReporte tipoReporte);

    TipoReporte find(Object id);

    List<TipoReporte> findAll();

    List<TipoReporte> findRange(int[] range);

    int count();
    
}
