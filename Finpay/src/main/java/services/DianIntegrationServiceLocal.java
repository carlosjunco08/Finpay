package services;

import com.finpay.integracion.DianRequest;
import com.finpay.integracion.DianResponse;
import javax.ejb.Local;

@Local
public interface DianIntegrationServiceLocal {
    
    /**
     * Revisa si el servicio de la DIAN está disponible.
     */
    String checkDianStatus();
    
    /**
     * Envía un reporte estructurado a la API externa de la DIAN.
     * @param request Datos del reporte Finpay mapeados al formato DIAN.
     * @return La respuesta de la DIAN.
     */
    DianResponse enviarReporte(DianRequest request);
}