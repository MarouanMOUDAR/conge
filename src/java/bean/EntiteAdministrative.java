/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable; 
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Maryem
 */
@Entity
public class EntiteAdministrative implements Serializable {

    
   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    private Employee chef;   
    @OneToMany(mappedBy = "entiteAdministrative")
    private List<Employee> employees;
    @ManyToOne
    private EntiteAdministrative entiteSuperieur;

   
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Employee getChef() {
        return chef;
    }

    public void setChef(Employee chef) {
        this.chef = chef;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public EntiteAdministrative getEntiteSuperieur() {
        if(entiteSuperieur==null){
            entiteSuperieur= new EntiteAdministrative();
            
        }
        return entiteSuperieur;
    }

    public void setEntiteSuperieur(EntiteAdministrative entiteSuperieur) {
        this.entiteSuperieur = entiteSuperieur;
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
        if (!(object instanceof EntiteAdministrative)) {
            return false;
        }
        EntiteAdministrative other = (EntiteAdministrative) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.EntiteAdministrative[ id=" + id + " ]";
    }
    
}
