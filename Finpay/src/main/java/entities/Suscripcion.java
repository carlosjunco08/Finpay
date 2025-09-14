/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jhon Deibys Torres
 */
@Entity
@Table(name = "suscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suscripcion.findAll", query = "SELECT s FROM Suscripcion s"),
    @NamedQuery(name = "Suscripcion.findByIdsuscripcion", query = "SELECT s FROM Suscripcion s WHERE s.idsuscripcion = :idsuscripcion"),
    @NamedQuery(name = "Suscripcion.findByFechaInicio", query = "SELECT s FROM Suscripcion s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Suscripcion.findByFechaFin", query = "SELECT s FROM Suscripcion s WHERE s.fechaFin = :fechaFin")})
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsuscripcion")
    private Integer idsuscripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "usuario_id", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @JoinColumn(name = "plan_suscripcion_id", referencedColumnName = "idplan_suscripcion")
    @ManyToOne(optional = false)
    private PlanSuscripcion planSuscripcionId;
    @JoinColumn(name = "estado_suscripcion_id", referencedColumnName = "idestado_suscripcion")
    @ManyToOne(optional = false)
    private EstadoSuscripcion estadoSuscripcionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suscripcionId")
    private Collection<Transaccion> transaccionCollection;

    public Suscripcion() {
    }

    public Suscripcion(Integer idsuscripcion) {
        this.idsuscripcion = idsuscripcion;
    }

    public Suscripcion(Integer idsuscripcion, Date fechaInicio, Date fechaFin) {
        this.idsuscripcion = idsuscripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getIdsuscripcion() {
        return idsuscripcion;
    }

    public void setIdsuscripcion(Integer idsuscripcion) {
        this.idsuscripcion = idsuscripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public PlanSuscripcion getPlanSuscripcionId() {
        return planSuscripcionId;
    }

    public void setPlanSuscripcionId(PlanSuscripcion planSuscripcionId) {
        this.planSuscripcionId = planSuscripcionId;
    }

    public EstadoSuscripcion getEstadoSuscripcionId() {
        return estadoSuscripcionId;
    }

    public void setEstadoSuscripcionId(EstadoSuscripcion estadoSuscripcionId) {
        this.estadoSuscripcionId = estadoSuscripcionId;
    }

    @XmlTransient
    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsuscripcion != null ? idsuscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suscripcion)) {
            return false;
        }
        Suscripcion other = (Suscripcion) object;
        if ((this.idsuscripcion == null && other.idsuscripcion != null) || (this.idsuscripcion != null && !this.idsuscripcion.equals(other.idsuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Suscripcion[ idsuscripcion=" + idsuscripcion + " ]";
    }
    
}
