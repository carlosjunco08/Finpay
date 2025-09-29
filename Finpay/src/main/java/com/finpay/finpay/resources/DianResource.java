package com.finpay.finpay.resources;

import com.finpay.integracion.DianRequest;
import com.finpay.integracion.DianResponse;
import services.DianIntegrationServiceLocal;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Ruta Final: /resources/dian
@Path("dian")
public class DianResource {

    // Inyección de la dependencia a la capa de servicios EJB
    @EJB
    private DianIntegrationServiceLocal dianService;

    /**
     * Endpoint: GET /resources/dian/status
     * Utilidad para verificar la salud del servicio DIAN.
     */
    @GET
    @Path("status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {
        String estado = dianService.checkDianStatus();
        return Response.ok("{\"status_dian_integracion\": \"" + estado + "\"}").build();
    }
    
    /**
     * Endpoint: POST /resources/dian/reportes
     * Procesa la solicitud y la envía al EJB de integración.
     */
    @POST
    @Path("reportes")
    @Consumes(MediaType.APPLICATION_JSON) // Espera un JSON en el cuerpo
    @Produces(MediaType.APPLICATION_JSON) // Devuelve un JSON
    public Response postReporte(DianRequest request) {
        
        // 1. Validación básica del cuerpo
        if (request == null || request.getNitUsuario() == null || request.getPeriodoFiscal() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"Faltan campos obligatorios para el reporte DIAN.\"}").build();
        }

        // 2. Llamada al servicio EJB
        DianResponse dianResponse = dianService.enviarReporte(request);
        
        // 3. Respuesta al cliente de Finpay
        if (dianResponse.getCodigoEstado().startsWith("2")) {
             // Retorna 200/201 si el reporte fue exitoso para la DIAN
             return Response.ok(dianResponse).build();
        } else {
             // Retorna un error 500 o el error que el EJB haya capturado
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(dianResponse).build();
        }
    }
}