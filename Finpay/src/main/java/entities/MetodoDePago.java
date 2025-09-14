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
@Table(name = "metodo_de_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetodoDePago.findAll", query = "SELECT m FROM MetodoDePago m"),
    @NamedQuery(name = "MetodoDePago.findByIdmetodoDePago", query = "SELECT m FROM MetodoDePago m WHERE m.idmetodoDePago = :idmetodoDePago"),
    @NamedQuery(name = "MetodoDePago.findByNombrePago", query = "SELECT m FROM MetodoDePago m WHERE m.nombrePago = :nombrePago")})
public class MetodoDePago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmetodo_de_pago")
    private Integer idmetodoDePago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre_pago")
    private String nombrePago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metodoDePagoId")
    private Collection<Transaccion> transaccionCollection;

    public MetodoDePago() {
    }

    public MetodoDePago(Integer idmetodoDePago) {
        this.idmetodoDePago = idmetodoDePago;
    }

    public MetodoDePago(Integer idmetodoDePago, String nombrePago) {
        this.idmetodoDePago = idmetodoDePago;
        this.nombrePago = nombrePago;
    }

    public Integer getIdmetodoDePago() {
        return idmetodoDePago;
    }

    public void setIdmetodoDePago(Integer idmetodoDePago) {
        this.idmetodoDePago = idmetodoDePago;
    }

    public String getNombrePago() {
        return nombrePago;
    }

    public void setNombrePago(String nombrePago) {
        this.nombrePago = nombrePago;
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
        hash += (idmetodoDePago != null ? idmetodoDePago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetodoDePago)) {
            return false;
        }
        MetodoDePago other = (MetodoDePago) object;
        if ((this.idmetodoDePago == null && other.idmetodoDePago != null) || (this.idmetodoDePago != null && !this.idmetodoDePago.equals(other.idmetodoDePago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MetodoDePago[ idmetodoDePago=" + idmetodoDePago + " ]";
    }
    
}
