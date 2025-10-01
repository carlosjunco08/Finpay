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
@Table(name = "tipo_transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTransaccion.findAll", query = "SELECT t FROM TipoTransaccion t"),
    @NamedQuery(name = "TipoTransaccion.findByIdtipoTransaccion", query = "SELECT t FROM TipoTransaccion t WHERE t.idtipoTransaccion = :idtipoTransaccion"),
    @NamedQuery(name = "TipoTransaccion.findByEstadoTransaccion", query = "SELECT t FROM TipoTransaccion t WHERE t.estadoTransaccion = :estadoTransaccion")})
public class TipoTransaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipo_transaccion")
    private Integer idtipoTransaccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "estado_transaccion")
    private String estadoTransaccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoTransaccionId")
    private Collection<Transaccion> transaccionCollection;

    public TipoTransaccion() {
    }

    public TipoTransaccion(Integer idtipoTransaccion) {
        this.idtipoTransaccion = idtipoTransaccion;
    }

    public TipoTransaccion(Integer idtipoTransaccion, String estadoTransaccion) {
        this.idtipoTransaccion = idtipoTransaccion;
        this.estadoTransaccion = estadoTransaccion;
    }

    public Integer getIdtipoTransaccion() {
        return idtipoTransaccion;
    }

    public void setIdtipoTransaccion(Integer idtipoTransaccion) {
        this.idtipoTransaccion = idtipoTransaccion;
    }

    public String getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(String estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
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
        hash += (idtipoTransaccion != null ? idtipoTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTransaccion)) {
            return false;
        }
        TipoTransaccion other = (TipoTransaccion) object;
        if ((this.idtipoTransaccion == null && other.idtipoTransaccion != null) || (this.idtipoTransaccion != null && !this.idtipoTransaccion.equals(other.idtipoTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TipoTransaccion[ idtipoTransaccion=" + idtipoTransaccion + " ]";
    }
    
}
