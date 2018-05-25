/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ConfigurationCongeDetail;
import bean.DemandeConge;
import bean.Employee;
import bean.EntiteAdministrative;
import bean.JourFerie;
import bean.TypeConge;
import controller.util.DateUtil;
import controller.util.PdfUtil;
import controller.util.SearchUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Maryem
 */
@Stateless
public class DemandeCongeFacade extends AbstractFacade<DemandeConge> {

    private final List< Calendar> joursCongees = new ArrayList<>();
    private Calendar date;
    private int i;
    private JourFerie jf;
    private ConfigurationCongeDetail configurationCongeDetail;
    @EJB
    private ConfigurationCongeDetailFacade configurationCongeDetailFacade;

    private long durée1heure = 1000 * 60 * 60;////1000 millisecondes * 60 secondes * 60 minutes = 1 heure
    private long durée1jour = durée1heure * 24;
    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandeCongeFacade() {
        super(DemandeConge.class);
    }

   

    
    public int findByTypeAndEntite(TypeConge typeConge, EntiteAdministrative entiteAdministrative) {
        String requete = "SELECT d FROM DemandeConge d WHERE d.typeConge.id='" + typeConge.getId() + "' AND d.employee.entiteAdministrative='"+entiteAdministrative.getId()+"' AND d.etat='"+1+"'";
        return em.createQuery(requete).getResultList().size();

    }
    
   
    
   
    public void generate1Pdf(DemandeConge demandeConge) {
         List<DemandeConge> demandeConges = new ArrayList<>();
        try {
            //        AutorisationAbsence autorisationAbsence1  = find(autorisationAbsence.getId());
            Map<String, Object> params = new HashMap();
            params.put("etat", demandeConge.getEtat());
            params.put("employee.nom", demandeConge.getEmployee().getNom());
            params.put("employee.prenom", demandeConge.getEmployee().getPrenom());
            params.put("entiteToValidate.nom", demandeConge.getEntiteToValidate().getNom());
            params.put("entiteToValidate.chef.nom", demandeConge.getEntiteToValidate().getChef().getNom());
            params.put("typeConge.nom", demandeConge.getTypeConge().getNom());
            params.put("duree", demandeConge.getDuree());
            params.put("dateDebut", demandeConge.getDateDebut());
            params.put("dateFin", demandeConge.getDateFin());
            params.put("dateSystem", new Date());
            try {
                PdfUtil.generatePdf(null, params, "DemandeCongeValider" + System.currentTimeMillis(), "/jasper/DemandeCongeeValider.jasper");
            } catch (JRException ex) {
                Logger.getLogger(DemandeCongeFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(DemandeCongeFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public List<JourFerie> findByCriteria(Date dateMin, Date dateMax) {
        String query = "SELECT jf FROM JourFerie  jf WHERE 1=1";
        if (dateMin != null || dateMax != null) {
            query += SearchUtil.addConstraintMinMaxDate("jf", "dateFerier", dateMin, dateMax);
        }
        List<JourFerie> res = em.createQuery(query).getResultList();
        System.out.println("haaa res ==> " + res);
        return res;
    }

    private int calculerNbrJour(List<JourFerie> res) {
        int NJF = 0;
        for (int j = 0; j < res.size(); j++) {
            jf = res.get(i);
            NJF += jf.getNbrJourFerier();
            System.out.println("haaa NJF ==> " + NJF);
        }
        return NJF;
    }

    public void simuler(DemandeConge demandeConge) {
        save(demandeConge, true);

    }

    @Override
    public void create(DemandeConge demandeConge) {
        save(demandeConge, false);
    }

    private void save(DemandeConge demandeConge, boolean simuler) {
        demandeConge.setEntiteToValidate(demandeConge.getEmployee().getEntiteAdministrative());
        configurationCongeDetail = configurationCongeDetailFacade.definnbj(demandeConge.getTypeConge());
        Date dateMax = DateUtil.addJourToDate(demandeConge.getDateDebut(), configurationCongeDetail.getNbJour());
        demandeConge.setDateDebut(demandeConge.getDateDebut());
        List<JourFerie> res = findByCriteria(demandeConge.getDateDebut(), dateMax);
        int NJF = calculerNbrJour(res);
        demandeConge.setDuree(configurationCongeDetail.getNbJour() + NJF);
        demandeConge.setDateFin(DateUtil.addJourToDate(demandeConge.getDateDebut(), demandeConge.getDuree()));
        if (simuler == false) {
            super.create(demandeConge);
        }
    }

    public int accepter(DemandeConge demandeConge) {
        int res = -2;
        if (demandeConge.getEtat() == 1) {  //1refuser ,2accepter
            res = -1;
        } else if (demandeConge.getEntiteToValidate().getEntiteSuperieur().getId() == null) {
            demandeConge.setEtat(1);
            demandeConge.setDateEtat(new Date());
            res = 1;
        } else {
            demandeConge.setEntiteToValidate(demandeConge.getEntiteToValidate().getEntiteSuperieur());
            res = 2;
        }
        edit(demandeConge);
        return res;
    }

    public int refuser(DemandeConge demandeConge) {
        if (demandeConge.getEtat() == 2) {
            return -1;
        }
        demandeConge.setEtat(2);
        demandeConge.setDateEtat(new Date());
        edit(demandeConge);
        return 1;
    }

    public List<DemandeConge> findByEntiteToValidate(EntiteAdministrative entiteAdministrative) {
        return em.createQuery("SELECT de from DemandeConge de WHERE de.entiteToValidate.id=" + entiteAdministrative.getId()).getResultList();
    }
//    public List<DemandeConge> findDemandeByChef(Employee chef) {
//        return em.createQuery("SELECT de from DemandeConge de WHERE de.employee.entiteAdministrative.chef.id='" + chef.getId()+"'").getResultList();
//    }

    public List<DemandeConge> findDemandeByChef(Employee chef) {
        return em.createQuery("SELECT de from DemandeConge de WHERE de.entiteToValidate.chef.id='" + chef.getId() + "'").getResultList();

    }

    public List<DemandeConge> findDemandeByEmployee(String id) {
        return em.createQuery("SELECT de from DemandeConge de WHERE de.employee.id=" + id).getResultList();
    }

    public List<DemandeConge> findDemandeByCriteria(int etat, Date dateMin, Date dateMax) {
        String query = "SELECT de FROM DemandeConge de WHERE 1=1";
        if (dateMin != null || dateMax != null) {
            query += SearchUtil.addConstraintMinMaxDate("de", "dateEtat", dateMin, dateMax);
        }

        query += SearchUtil.addConstraint("de", "etat", "=", etat);

        return em.createQuery(query).getResultList();

    }

    public List<DemandeConge> findDemandeByCriteria1(Employee employee, TypeConge typeConge, Integer etat, Date dateMin, Date dateMax) {
        String query = "SELECT de FROM DemandeConge de WHERE 1=1";
        if (employee != null) {
            query += SearchUtil.addConstraint("de", "employee.id", "=", employee.getId());
        }
        if (typeConge != null) {
            query += SearchUtil.addConstraint("de", "typeConge.id", "=", typeConge.getId());
        }

        query += SearchUtil.addConstraint("de", "etat", "=", etat);
        query += SearchUtil.addConstraintMinMaxDate("de", "dateEtat", dateMin, dateMax);
        System.out.println("I got here !!!");
        return em.createQuery(query).getResultList();

    }

    public void calculerDifference(Date dateDebut, Date dateFin) {
        long différence = Math.abs(dateDebut.getTime() - dateFin.getTime()); //calcul différence entre les deux dates
        //System.out.println(différence); //affiche la différence en nombre de millisecondes
        System.out.println("Nombrheures entre " + dateDebut + " et " + dateFin + ": " + différence / durée1jour); //affiche la différence en nombre de jours

    }

//    public void ajouterNbrJour(Date dateDebut, int nbrjour) {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//        calendar.add(Calendar.DATE, nbrjour);
//        System.out.println(" nous serons le: " + sdf.format(calendar.getTime()));
//    }
}
