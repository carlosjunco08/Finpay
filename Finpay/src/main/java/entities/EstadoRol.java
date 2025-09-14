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
@Table(name = "estado_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoRol.findAll", query = "SELECT e FROM EstadoRol e"),
    @NamedQuery(name = "EstadoRol.findByIdestadoRol", query = "SELECT e FROM EstadoRol e WHERE e.idestadoRol = :idestadoRol"),
    @NamedQuery(name = "EstadoRol.findByEstadoRol", query = "SELECT e FROM EstadoRol e WHERE e.estadoRol = :estadoRol")})
public class EstadoRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestado_rol")
    private Integer idestadoRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estado_rol")
    private String estadoRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoRolId")
    private Collection<Rol> rolCollection;

    public EstadoRol() {
    }

    public EstadoRol(Integer idestadoRol) {
        this.idestadoRol = idestadoRol;
    }

    public EstadoRol(Integer idestadoRol, String estadoRol) {
        this.idestadoRol = idestadoRol;
        this.estadoRol = estadoRol;
    }

    public Integer getIdestadoRol() {
        return idestadoRol;
    }

    public void setIdestadoRol(Integer idestadoRol) {
        this.idestadoRol = idestadoRol;
    }

    public String getEstadoRol() {
        return estadoRol;
    }

    public void setEstadoRol(String estadoRol) {
        this.estadoRol = estadoRol;
    }

    @XmlTransient
    public Collection<Rol> getRolCollection() {
        return rolCollection;
    }

    public void setRolCollection(Collection<Rol> rolCollection) {
        this.rolCollection = rolCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadoRol != null ? idestadoRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoRol)) {
            return false;
        }
        EstadoRol other = (EstadoRol) object;
        if ((this.idestadoRol == null && other.idestadoRol != null) || (this.idestadoRol != null && !this.idestadoRol.equals(other.idestadoRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadoRol[ idestadoRol=" + idestadoRol + " ]";
    }
    
}
