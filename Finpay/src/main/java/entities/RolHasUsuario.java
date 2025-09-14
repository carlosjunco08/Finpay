/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jhon Deibys Torres
 */
@Entity
@Table(name = "rol_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolHasUsuario.findAll", query = "SELECT r FROM RolHasUsuario r"),
    @NamedQuery(name = "RolHasUsuario.findById", query = "SELECT r FROM RolHasUsuario r WHERE r.id = :id")})
public class RolHasUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "usuario_id", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @JoinColumn(name = "rol_id", referencedColumnName = "idrol")
    @ManyToOne(optional = false)
    private Rol rolId;

    public RolHasUsuario() {
    }

    public RolHasUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolHasUsuario)) {
            return false;
        }
        RolHasUsuario other = (RolHasUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.RolHasUsuario[ id=" + id + " ]";
    }
    
}
