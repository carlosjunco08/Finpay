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
import javax.persistence.Lob;
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
@Table(name = "plan_suscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanSuscripcion.findAll", query = "SELECT p FROM PlanSuscripcion p"),
    @NamedQuery(name = "PlanSuscripcion.findByIdplanSuscripcion", query = "SELECT p FROM PlanSuscripcion p WHERE p.idplanSuscripcion = :idplanSuscripcion"),
    @NamedQuery(name = "PlanSuscripcion.findByNombre", query = "SELECT p FROM PlanSuscripcion p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PlanSuscripcion.findByPrecio", query = "SELECT p FROM PlanSuscripcion p WHERE p.precio = :precio"),
    @NamedQuery(name = "PlanSuscripcion.findByDuracion", query = "SELECT p FROM PlanSuscripcion p WHERE p.duracion = :duracion")})
public class PlanSuscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplan_suscripcion")
    private Integer idplanSuscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private long precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duracion")
    private int duracion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planSuscripcionId")
    private Collection<Suscripcion> suscripcionCollection;

    public PlanSuscripcion() {
    }

    public PlanSuscripcion(Integer idplanSuscripcion) {
        this.idplanSuscripcion = idplanSuscripcion;
    }

    public PlanSuscripcion(Integer idplanSuscripcion, String nombre, long precio, int duracion, String descripcion) {
        this.idplanSuscripcion = idplanSuscripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
        this.descripcion = descripcion;
    }

    public Integer getIdplanSuscripcion() {
        return idplanSuscripcion;
    }

    public void setIdplanSuscripcion(Integer idplanSuscripcion) {
        this.idplanSuscripcion = idplanSuscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idplanSuscripcion != null ? idplanSuscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSuscripcion)) {
            return false;
        }
        PlanSuscripcion other = (PlanSuscripcion) object;
        if ((this.idplanSuscripcion == null && other.idplanSuscripcion != null) || (this.idplanSuscripcion != null && !this.idplanSuscripcion.equals(other.idplanSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PlanSuscripcion[ idplanSuscripcion=" + idplanSuscripcion + " ]";
    }
    
}
