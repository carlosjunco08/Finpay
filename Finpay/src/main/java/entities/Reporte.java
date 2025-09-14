/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
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
@Table(name = "reporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reporte.findAll", query = "SELECT r FROM Reporte r"),
    @NamedQuery(name = "Reporte.findByIdreporte", query = "SELECT r FROM Reporte r WHERE r.idreporte = :idreporte"),
    @NamedQuery(name = "Reporte.findByMonto", query = "SELECT r FROM Reporte r WHERE r.monto = :monto"),
    @NamedQuery(name = "Reporte.findByFecha", query = "SELECT r FROM Reporte r WHERE r.fecha = :fecha")})
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreporte")
    private Integer idreporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private long monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "tipo_reporte_id", referencedColumnName = "idtipo_reporte")
    @ManyToOne(optional = false)
    private TipoReporte tipoReporteId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @JoinColumn(name = "periodo_id", referencedColumnName = "idperiodo")
    @ManyToOne(optional = false)
    private Periodo periodoId;

    public Reporte() {
    }

    public Reporte(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public Reporte(Integer idreporte, long monto, Date fecha, String descripcion) {
        this.idreporte = idreporte;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Integer getIdreporte() {
        return idreporte;
    }

    public void setIdreporte(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
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

    public TipoReporte getTipoReporteId() {
        return tipoReporteId;
    }

    public void setTipoReporteId(TipoReporte tipoReporteId) {
        this.tipoReporteId = tipoReporteId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Periodo getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Periodo periodoId) {
        this.periodoId = periodoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreporte != null ? idreporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.idreporte == null && other.idreporte != null) || (this.idreporte != null && !this.idreporte.equals(other.idreporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reporte[ idreporte=" + idreporte + " ]";
    }
    
}
