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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Maryem
 */
@Entity
public class DemandeConge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private EntiteAdministrative entiteToValidate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebut;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFin;
    @ManyToOne
    private TypeConge typeConge;
    private int etat;//refusé Accepter en cour de traitement
    private int duree;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEtat;//refusé Accepter
    @ManyToOne
    private Employee employee;
    @OneToMany(mappedBy = "demandeConge")
    private List<PieceJustificative> pieceJustificatives;

    public EntiteAdministrative getEntiteToValidate() {
        if(entiteToValidate==null){
           entiteToValidate = new EntiteAdministrative();
            
        }
        return entiteToValidate;
    }

    public DemandeConge() {
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setEntiteToValidate(EntiteAdministrative entiteToValidate) {
        this.entiteToValidate = entiteToValidate;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDateEtat() {
        return dateEtat;
    }

    public void setDateEtat(Date dateEtat) {
        this.dateEtat = dateEtat;
    }

    public Employee getEmployee() {
       if (employee==null) {
       employee=new Employee();
       }
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<PieceJustificative> getPieceJustificatives() {
        return pieceJustificatives;
    }

    public void setPieceJustificatives(List<PieceJustificative> pieceJustificatives) {
        this.pieceJustificatives = pieceJustificatives;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public TypeConge getTypeConge() {
        return typeConge;
    }

    public void setTypeConge(TypeConge typeConge) {
        this.typeConge = typeConge;
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
        if (!(object instanceof DemandeConge)) {
            return false;
        }
        DemandeConge other = (DemandeConge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Conge[ id=" + id + " ]";
    }

}
