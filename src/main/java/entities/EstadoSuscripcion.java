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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jhon Deibys Torres
 */
@Entity
@Table(name = "estado_suscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoSuscripcion.findAll", query = "SELECT e FROM EstadoSuscripcion e"),
    @NamedQuery(name = "EstadoSuscripcion.findByIdestadoSuscripcion", query = "SELECT e FROM EstadoSuscripcion e WHERE e.idestadoSuscripcion = :idestadoSuscripcion"),
    @NamedQuery(name = "EstadoSuscripcion.findByNombreEstado", query = "SELECT e FROM EstadoSuscripcion e WHERE e.nombreEstado = :nombreEstado")})
public class EstadoSuscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestado_suscripcion")
    private Integer idestadoSuscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_estado")
    private String nombreEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoSuscripcionId")
    private Collection<Suscripcion> suscripcionCollection;

    public EstadoSuscripcion() {
    }

    public EstadoSuscripcion(Integer idestadoSuscripcion) {
        this.idestadoSuscripcion = idestadoSuscripcion;
    }

    public EstadoSuscripcion(Integer idestadoSuscripcion, String nombreEstado) {
        this.idestadoSuscripcion = idestadoSuscripcion;
        this.nombreEstado = nombreEstado;
    }

    public Integer getIdestadoSuscripcion() {
        return idestadoSuscripcion;
    }

    public void setIdestadoSuscripcion(Integer idestadoSuscripcion) {
        this.idestadoSuscripcion = idestadoSuscripcion;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @XmlTransient
    public Collection<Suscripcion> getSuscripcionCollection() {
        return suscripcionCollection;
    }

    public void setSuscripcionCollection(Collection<Suscripcion> suscripcionCollection) {
        this.suscripcionCollection = suscripcionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadoSuscripcion != null ? idestadoSuscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSuscripcion)) {
            return false;
        }
        EstadoSuscripcion other = (EstadoSuscripcion) object;
        if ((this.idestadoSuscripcion == null && other.idestadoSuscripcion != null) || (this.idestadoSuscripcion != null && !this.idestadoSuscripcion.equals(other.idestadoSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadoSuscripcion[ idestadoSuscripcion=" + idestadoSuscripcion + " ]";
    }
    
}
