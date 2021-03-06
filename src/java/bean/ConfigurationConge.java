/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Maryem
 */
@Entity
public class ConfigurationConge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateApplication;
    @OneToMany(mappedBy = "configurationConge")
    private List<ConfigurationCongeDetail> configurationCongeDetails;

    public Date getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(Date dateApplication) {
        this.dateApplication = dateApplication;
    }

    public List<ConfigurationCongeDetail> getConfigurationCongeDetails() {
        return configurationCongeDetails;
    }

    public void setConfigurationCongeDetails(List<ConfigurationCongeDetail> configurationCongeDetails) {
        this.configurationCongeDetails = configurationCongeDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ConfigurationConge)) {
            return false;
        }
        ConfigurationConge other = (ConfigurationConge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.ConfigurationConge[ id=" + id + " ]";
    }

}
