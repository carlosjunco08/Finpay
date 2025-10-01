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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByIdrol", query = "SELECT r FROM Rol r WHERE r.idrol = :idrol"),
    @NamedQuery(name = "Rol.findByNombreRol", query = "SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrol")
    private Integer idrol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_rol")
    private String nombreRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private Collection<RolHasUsuario> rolHasUsuarioCollection;
    @JoinColumn(name = "estado_rol_id", referencedColumnName = "idestado_rol")
    @ManyToOne(optional = false)
    private EstadoRol estadoRolId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private Collection<Usuario> usuarioCollection;

    public Rol() {
    }

    public Rol(Integer idrol) {
        this.idrol = idrol;
    }

    public Rol(Integer idrol, String nombreRol) {
        this.idrol = idrol;
        this.nombreRol = nombreRol;
    }

    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @XmlTransient
    public Collection<RolHasUsuario> getRolHasUsuarioCollection() {
        return rolHasUsuarioCollection;
    }

    public void setRolHasUsuarioCollection(Collection<RolHasUsuario> rolHasUsuarioCollection) {
        this.rolHasUsuarioCollection = rolHasUsuarioCollection;
    }

    public EstadoRol getEstadoRolId() {
        return estadoRolId;
    }

    public void setEstadoRolId(EstadoRol estadoRolId) {
        this.estadoRolId = estadoRolId;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrol != null ? idrol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.idrol == null && other.idrol != null) || (this.idrol != null && !this.idrol.equals(other.idrol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rol[ idrol=" + idrol + " ]";
    }
    
}
