package services;

import entities.EstadoRol;
import java.util.List;
import javax.ejb.Local;

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
