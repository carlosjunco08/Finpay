/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jhon Deibys Torres
 */
@Entity
@Table(name = "transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByIdtransaccion", query = "SELECT t FROM Transaccion t WHERE t.idtransaccion = :idtransaccion"),
    @NamedQuery(name = "Transaccion.findByMonto", query = "SELECT t FROM Transaccion t WHERE t.monto = :monto"),
    @NamedQuery(name = "Transaccion.findByFecha", query = "SELECT t FROM Transaccion t WHERE t.fecha = :fecha")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransaccion")
    private Integer idtransaccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "suscripcion_id", referencedColumnName = "idsuscripcion")
    @ManyToOne(optional = false)
    private Suscripcion suscripcionId;
    @JoinColumn(name = "tipo_transaccion_id", referencedColumnName = "idtipo_transaccion")
    @ManyToOne(optional = false)
    private TipoTransaccion tipoTransaccionId;
    @JoinColumn(name = "metodo_de_pago_id", referencedColumnName = "idmetodo_de_pago")
    @ManyToOne(optional = false)
    private MetodoDePago metodoDePagoId;

    public Transaccion() {
    }

    public Transaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Transaccion(Integer idtransaccion, BigDecimal monto, Date fecha) {
        this.idtransaccion = idtransaccion;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Suscripcion getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(Suscripcion suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public TipoTransaccion getTipoTransaccionId() {
        return tipoTransaccionId;
    }

    public void setTipoTransaccionId(TipoTransaccion tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public MetodoDePago getMetodoDePagoId() {
        return metodoDePagoId;
    }

    public void setMetodoDePagoId(MetodoDePago metodoDePagoId) {
        this.metodoDePagoId = metodoDePagoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccion != null ? idtransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.idtransaccion == null && other.idtransaccion != null) || (this.idtransaccion != null && !this.idtransaccion.equals(other.idtransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transaccion[ idtransaccion=" + idtransaccion + " ]";
    }
    
}
