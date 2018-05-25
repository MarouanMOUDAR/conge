/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.EntiteAdministrative;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import service.DemandeCongeFacade;
import service.EntiteAdministrativeFacade;
import service.TypeCongeFacade;

/**
 *
 * @author larhlimi
 */
@Named(value = "statistiqueController")
@SessionScoped
public class StatistiqueController implements Serializable {

    /**
     * Creates a new instance of StatistiqueController
     */
    public StatistiqueController() {
    }
   private BarChartModel model;
    @EJB
    private TypeCongeFacade typeCongeFacade;
    @EJB
    private EntiteAdministrativeFacade entiteAdministrativeFacade;
    @EJB
   private DemandeCongeFacade demandeCongeFacade;
   private EntiteAdministrative  entiteAdministrative;
    
//    @PostConstruct
//    public void init() {
//        createAnimatedModels();
//    }
     public void createAnimatedModels() {
        model = chartsDemendeByTypeAndEntite();
        model.setTitle("Demandes By Type And Entite");
        model.setAnimate(true);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(60);
    }
     public BarChartModel chartsDemendeByTypeAndEntite() {
        BarChartModel barChartModel = new BarChartModel();
        ChartSeries demandes = new ChartSeries();
        demandes.setLabel("Nombre de demandes");
        for (int i = 0; i < typeCongeFacade.findAll().size(); i++) {
            for (int j = 0; j < entiteAdministrativeFacade.findAll().size(); j++){
                entiteAdministrative=entiteAdministrativeFacade.findAll().get(j);
                 demandes.set(typeCongeFacade.findAll().get(i).getNom(), demandeCongeFacade.findByTypeAndEntite(typeCongeFacade.findAll().get(i),entiteAdministrativeFacade.findAll().get(j)));
        }
        }
        barChartModel.addSeries(demandes);
        return barChartModel;
    }

    public EntiteAdministrativeFacade getEntiteAdministrativeFacade() {
        return entiteAdministrativeFacade;
    }

    public void setEntiteAdministrativeFacade(EntiteAdministrativeFacade entiteAdministrativeFacade) {
        this.entiteAdministrativeFacade = entiteAdministrativeFacade;
    }

    public EntiteAdministrative getEntiteAdministrative() {
        return entiteAdministrative;
    }

    public void setEntiteAdministrative(EntiteAdministrative entiteAdministrative) {
        this.entiteAdministrative = entiteAdministrative;
    }

   

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }

    public TypeCongeFacade getTypeCongeFacade() {
        return typeCongeFacade;
    }

    public void setTypeCongeFacade(TypeCongeFacade typeCongeFacade) {
        this.typeCongeFacade = typeCongeFacade;
    }

    public DemandeCongeFacade getDemandeCongeFacade() {
        return demandeCongeFacade;
    }

    public void setDemandeCongeFacade(DemandeCongeFacade demandeCongeFacade) {
        this.demandeCongeFacade = demandeCongeFacade;
    }
    
}
