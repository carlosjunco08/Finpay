/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import entities.Usuario;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import services.UsuarioFacadeLocal;

/**
 *
 * @author Jhon Deibys Torres
 */
@Named(value = "graficasController")
@ViewScoped
public class GraficasController implements Serializable {
    
    
    @EJB
    private UsuarioFacadeLocal ufl;

    public GraficasController() {
    }

    /**
     * Devuelve un JSON con la cantidad de usuarios agrupados por rol.
     * Ejemplo: [{"rol":"Cliente", "cantidad":3}, ...]
     */
    public String getJsonUsuariosPorRol() {
        Map<String, Integer> conteoPorRol = new HashMap<>();

        for (Usuario u : ufl.findAll()) {
            String nombreRol = u.getRolId().getNombreRol();
            conteoPorRol.put(nombreRol, conteoPorRol.getOrDefault(nombreRol, 0) + 1);
        }

        StringBuilder json = new StringBuilder("[");
        for (Map.Entry<String, Integer> entry : conteoPorRol.entrySet()) {
            json.append("{\"rol\":\"")
                .append(entry.getKey())
                .append("\",\"cantidad\":")
                .append(entry.getValue())
                .append("},");
        }
        if (json.length() > 1) json.setLength(json.length() - 1); // quitar última coma
        json.append("]");
        return json.toString();
    }

    /**
     * Devuelve un JSON con la cantidad de usuarios agrupados por país.
     * Ejemplo: [{"pais":"Colombia", "cantidad":5}, ...]
     */
    public String getJsonUsuariosPorPais() {
        try {
            List<Usuario> usuarios = ufl.findAll();

            Map<String, Long> usuariosPorPais = usuarios.stream()
                .filter(u -> u.getPaisId() != null && u.getPaisId().getNombrePais() != null)
                .collect(Collectors.groupingBy(u -> u.getPaisId().getNombrePais(), Collectors.counting()));

            List<Map<String, Object>> listaDatos = usuariosPorPais.entrySet().stream()
                .map(e -> {
                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("pais", e.getKey());
                    mapa.put("cantidad", e.getValue());
                    return mapa;
                })
                .collect(Collectors.toList());

            Gson gson = new Gson();
            return gson.toJson(listaDatos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[]";
    }
    
}
