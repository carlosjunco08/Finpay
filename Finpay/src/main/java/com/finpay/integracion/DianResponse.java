package com.finpay.integracion;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DianResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Estos campos reflejarán la respuesta de la DIAN:
    private String codigoEstado;     // Código HTTP o de negocio (ej: "200", "401")
    private String mensajeResultado; // Mensaje de éxito/error de la DIAN
    private String idTransaccionDian; // ID de seguimiento para la DIAN (si aplica)
    
    // ---------------------- CONSTRUCTORES Y MÉTODOS ----------------------

    public DianResponse() {}

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }

    public String getIdTransaccionDian() {
        return idTransaccionDian;
    }

    public void setIdTransaccionDian(String idTransaccionDian) {
        this.idTransaccionDian = idTransaccionDian;
    }
}