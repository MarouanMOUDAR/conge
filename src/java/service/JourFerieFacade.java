/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeConge;
import bean.JourFerie;
import controller.util.JsfUtil;
import controller.util.PdfUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Maryem
 */
@Stateless
public class JourFerieFacade extends AbstractFacade<JourFerie> {

    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JourFerieFacade() {
        super(JourFerie.class);
    }
    public void PrintMultiJour() throws JRException, IOException{
        List<JourFerie> jourFeries = new ArrayList<>();
        List<JourFerie> cm = new ArrayList<>();
        jourFeries =findAll();
        Map map = new HashMap();
        List<JasperPrint> jasperPrints = new ArrayList();
        for (int i = 0; i < jourFeries.size(); i++) {
            JourFerie j = jourFeries.get(i);
            cm.add(j);
            jasperPrints.add(PdfUtil.createJasperPrint(cm, null,"/jasper/AutorisationAbsence.jasper"));
            cm.clear();
        }
        if (jasperPrints != null && !jasperPrints.isEmpty()) {
            PdfUtil.generatePdfs(jasperPrints,"MultiAutorisationAbsence Report");
        } else {
            JsfUtil.addErrorMessage("Une erreur s'est produit lors de l'impression");
        }
        
    }
    
    public static boolean isJourTravaille(Calendar p_date)
    {
        // On constitue la liste des jours fériés
        final List < Calendar > joursFeries = new ArrayList <  > ();
        // On recherche les jours fériés de l'année de la date en paramètre
        final Calendar jourFerie = (Calendar) p_date.clone();
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.JANUARY, 1);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.MAY, 1);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.MAY, 8);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.JULY, 14);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.AUGUST, 15);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.NOVEMBER, 1);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.NOVEMBER, 11);
        joursFeries.add((Calendar) jourFerie.clone());
        jourFerie.set(jourFerie.get(Calendar.YEAR), Calendar.DECEMBER, 25);
        joursFeries.add((Calendar) jourFerie.clone());
        
        
        
         if (joursFeries.contains(p_date)
                || p_date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || p_date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }
        return true;
    }
    
}
