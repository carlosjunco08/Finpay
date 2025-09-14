/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.EstadoRol;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jhon Deibys Torres
 */
@Stateless
public class EstadoRolFacade extends AbstractFacade<EstadoRol> implements EstadoRolFacadeLocal {

    @PersistenceContext(unitName = "com.Dev-Jhon_LoginJSF_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoRolFacade() {
        super(EstadoRol.class);
    }
    
}
