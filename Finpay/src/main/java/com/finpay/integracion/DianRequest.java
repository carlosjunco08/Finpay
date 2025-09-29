package com.finpay.integracion;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
// Asegúrate de que este POJO tenga todos los campos que la API de la DIAN necesita para un reporte.

@XmlRootElement
public class DianRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String nitUsuario;        // Identificación del usuario/empresa
    private String periodoFiscal;     // Ejemplo: "2024-Q3"
    private String tipoDocumento;     // Tipo de reporte requerido por DIAN
    private String datosComprobantes; // Datos serializados de las transacciones
    
    // ---------------------- CONSTRUCTORES Y MÉTODOS ----------------------
    
    public DianRequest() {}

    public String getNitUsuario() {
        return nitUsuario;
    }

    public void setNitUsuario(String nitUsuario) {
        this.nitUsuario = nitUsuario;
    }

    public String getPeriodoFiscal() {
        return periodoFiscal;
    }

    public void setPeriodoFiscal(String periodoFiscal) {
        this.periodoFiscal = periodoFiscal;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDatosComprobantes() {
        return datosComprobantes;
    }

    public void setDatosComprobantes(String datosComprobantes) {
        this.datosComprobantes = datosComprobantes;
    }
}