package services;

import com.finpay.integracion.DianRequest;
import com.finpay.integracion.DianResponse;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class DianIntegrationService implements DianIntegrationServiceLocal {

    // ** ⚠️ IMPORTANTE: CONFIGURACIÓN CRÍTICA ⚠️ **
    // 1. URL de la API de la DIAN (DEBE ser la URL de producción/sandbox REAL)
    private static final String DIAN_BASE_URL = "https://api.dian.gov.co/v1/"; 
    
    // 2. TOKEN de Autenticación - NUNCA HARDCODEAR EN PRODUCCIÓN.
    // Usar JNDI o Variables de Entorno para almacenar este valor de forma segura.
    private static final String DIAN_AUTH_TOKEN = "Bearer [SU_TOKEN_JWT_O_CERTIFICADO_AQUI]"; // Reemplazar

    @Override
    public String checkDianStatus() {
        // En una implementación real:
        // 1. Crear el cliente JAX-RS y apuntar al endpoint de 'health check'.
        // 2. Retornar "Disponible" o "Error" basado en el código de respuesta HTTP.
        return "DISPONIBLE (Lógica de validación de servicio pendiente)";
    }

    @Override
    public DianResponse enviarReporte(DianRequest request) {
        
        Client client = ClientBuilder.newClient();
        Response response = null;
        DianResponse dianResponse = new DianResponse();

        try {
            // Endpoint específico para el reporte fiscal
            String targetUrl = DIAN_BASE_URL + "reportes/fiscales"; 

            response = client.target(targetUrl)
                    .request(MediaType.APPLICATION_JSON) // Pide JSON como respuesta
                    .header(HttpHeaders.AUTHORIZATION, DIAN_AUTH_TOKEN) // Autenticación
                    .post(Entity.json(request)); // Envía el JSON con la solicitud

            // Procesar la respuesta
            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                // Éxito (ej. 200 OK, 201 Created)
                dianResponse = response.readEntity(DianResponse.class);
                dianResponse.setCodigoEstado(String.valueOf(response.getStatus()));
                
            } else {
                // Errores de la DIAN (ej. 400, 401, 500)
                dianResponse.setCodigoEstado(String.valueOf(response.getStatus()));
                dianResponse.setMensajeResultado("Error de la DIAN: " + response.readEntity(String.class));
            }
            
        } catch (Exception e) {
            // Error de conexión, timeout o parseo
            dianResponse.setCodigoEstado("503");
            dianResponse.setMensajeResultado("Error de conexión o configuración: " + e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
            client.close(); // Siempre cerrar el cliente para liberar recursos
        }
        return dianResponse;
    }
}