/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Maryem
 */
@Entity
public class JourFerie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFerier;
    private int NbrJourFerier;
    
    @ManyToOne
    private ConfigurationJourFerier configurationJourFerier;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ConfigurationJourFerier getConfigurationJourFerier() {
        return configurationJourFerier;
    }

    public void setConfigurationJourFerier(ConfigurationJourFerier configurationJourFerier) {
        this.configurationJourFerier = configurationJourFerier;
    }

    public int getNbrJourFerier() {
        return NbrJourFerier;
    }

    public void setNbrJourFerier(int NbrJourFerier) {
        this.NbrJourFerier = NbrJourFerier;
    }

    public Date getDateFerier() {
        return dateFerier;
    }

    public void setDateFerier(Date dateFerier) {
        this.dateFerier = dateFerier;
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
        if (!(object instanceof JourFerie)) {
            return false;
        }
        JourFerie other = (JourFerie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.JourFerie[ id=" + id + " ]";
    }
    
}
