package services;

import entities.Transaccion;
import entities.Usuario;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TransaccionService {
    
    void guardar(Transaccion transaccion);
    
    List<Transaccion> obtenerPorUsuario(Usuario usuario);
    
    void eliminar(Transaccion transaccion);
}