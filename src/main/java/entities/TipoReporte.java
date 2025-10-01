/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jhon Deibys Torres
 */
@Entity
@Table(name = "tipo_reporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoReporte.findAll", query = "SELECT t FROM TipoReporte t"),
    @NamedQuery(name = "TipoReporte.findByIdtipoReporte", query = "SELECT t FROM TipoReporte t WHERE t.idtipoReporte = :idtipoReporte"),
    @NamedQuery(name = "TipoReporte.findByNameTipoReporte", query = "SELECT t FROM TipoReporte t WHERE t.nameTipoReporte = :nameTipoReporte")})
public class TipoReporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipo_reporte")
    private Integer idtipoReporte;
    @Size(max = 50)
    @Column(name = "name_tipo_reporte")
    private String nameTipoReporte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoReporteId")
    private Collection<Reporte> reporteCollection;

    public TipoReporte() {
    }

    public TipoReporte(Integer idtipoReporte) {
        this.idtipoReporte = idtipoReporte;
    }

    public Integer getIdtipoReporte() {
        return idtipoReporte;
    }

    public void setIdtipoReporte(Integer idtipoReporte) {
        this.idtipoReporte = idtipoReporte;
    }

    public String getNameTipoReporte() {
        return nameTipoReporte;
    }

    public void setNameTipoReporte(String nameTipoReporte) {
        this.nameTipoReporte = nameTipoReporte;
    }

    @XmlTransient
    public Collection<Reporte> getReporteCollection() {
        return reporteCollection;
    }

    public void setReporteCollection(Collection<Reporte> reporteCollection) {
        this.reporteCollection = reporteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoReporte != null ? idtipoReporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoReporte)) {
            return false;
        }
        TipoReporte other = (TipoReporte) object;
        if ((this.idtipoReporte == null && other.idtipoReporte != null) || (this.idtipoReporte != null && !this.idtipoReporte.equals(other.idtipoReporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TipoReporte[ idtipoReporte=" + idtipoReporte + " ]";
    }
    
}
