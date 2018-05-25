package controller;

import bean.DemandeConge;
import bean.Employee;
import bean.EntiteAdministrative;
import bean.TypeConge;
import bean.User;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import service.DemandeCongeFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.EmployeeFacade;
import service.EntiteAdministrativeFacade;
import service.ValidationDemandeCongeFacade;

@Named("demandeCongeController")
@SessionScoped
public class DemandeCongeController implements Serializable {

    @EJB
    private service.DemandeCongeFacade ejbFacade;
    private List<DemandeConge> items = null;
    private DemandeConge selected;

    @EJB
    private ValidationDemandeCongeFacade validationDemande;
    @EJB
    private EmployeeFacade employeeFacade;
    @EJB
    private EntiteAdministrativeFacade entiteAdministrativeFacade;

    private Employee chef;
    private Employee emp;
    private Employee employee;
    private Employee empl;
    private TypeConge typeCongee;
    private EntiteAdministrative EntiteAdChef;

    private List<DemandeConge> demandeByEntiteDetail;
    private List<DemandeConge> demandeByChefDetail;
    private List<DemandeConge> demandeByEmployeeDetail;
    private List<DemandeConge> demandes;
    private Date date;
    private Date dateMin;
    private Date dateMax;
    private Date dateMin1;
    private Date dateMax1;
    private String login;
    private Integer etat;
    
    
    
    public void generate1Pdf( DemandeConge demandeConge) {
        ejbFacade.generate1Pdf(demandeConge);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void simuler() {
        ejbFacade.simuler(selected);

    }

    public void findDemandeByCriteria() {
        System.out.println(empl + " " + typeCongee + " " + etat + " " + " " + dateMin + " " + dateMax);
        items = ejbFacade.findDemandeByCriteria1(empl, typeCongee, etat, dateMin, dateMax);
    }

//    public void findDemandeByCriteria() {
//        items = ejbFacade.findDemandeByCriteria(etat1, dateMin1, dateMax1);
//        demandes = items;
//    }

    public void findDemandeByLogin() {
        User user = (User) SessionUtil.getAttribute("login");
        employee = employeeFacade.findByLogin(user.getLogin());
        demandes = ejbFacade.findDemandeByEmployee(employee.getId());

    }

    public void accepter(DemandeConge demandeConge) {
        int res = ejbFacade.accepter(demandeConge);
        System.out.println("haaa res==> " + res);
    }

    public void refuser(DemandeConge demandeConge) {
        int res = ejbFacade.refuser(demandeConge);
        System.out.println("haaa res==> " + res);

    }

    public void findDemandeByEntite() {
        User user = (User) SessionUtil.getAttribute("login");
        chef = employeeFacade.findByLogin(user.getLogin());
        EntiteAdChef = entiteAdministrativeFacade.findByLogin(chef.getId());
        demandeByEntiteDetail = ejbFacade.findByEntiteToValidate(EntiteAdChef);
    }

    public void findDemandeBychef() {
        User user = (User) SessionUtil.getAttribute("login");
        chef = employeeFacade.findByLogin(user.getLogin());
        System.out.println("ha==>" + chef);
        demandeByChefDetail = ejbFacade.findDemandeByChef(chef);

    }

    public Date getDateMin1() {
        return dateMin1;
    }

    public void setDateMin1(Date dateMin1) {
        this.dateMin1 = dateMin1;
    }

    public Date getDateMax1() {
        return dateMax1;
    }

    public void setDateMax1(Date dateMax1) {
        this.dateMax1 = dateMax1;
    }

   

    public DemandeCongeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(DemandeCongeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public ValidationDemandeCongeFacade getValidationDemande() {
        return validationDemande;
    }

    public void setValidationDemande(ValidationDemandeCongeFacade validationDemande) {
        this.validationDemande = validationDemande;
    }

    public EmployeeFacade getEmployeeFacade() {
        return employeeFacade;
    }

    public void setEmployeeFacade(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    public EntiteAdministrativeFacade getEntiteAdministrativeFacade() {
        return entiteAdministrativeFacade;
    }

    public void setEntiteAdministrativeFacade(EntiteAdministrativeFacade entiteAdministrativeFacade) {
        this.entiteAdministrativeFacade = entiteAdministrativeFacade;
    }

    public Employee getChef() {
        return chef;
    }

    public void setChef(Employee chef) {
        this.chef = chef;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmpl() {
        return empl;
    }

    public void setEmpl(Employee empl) {
        this.empl = empl;
    }

    public TypeConge getTypeCongee() {
        return typeCongee;
    }

    public void setTypeCongee(TypeConge typeCongee) {
        this.typeCongee = typeCongee;
    }

    public EntiteAdministrative getEntiteAdChef() {
        return EntiteAdChef;
    }

    public void setEntiteAdChef(EntiteAdministrative EntiteAdChef) {
        this.EntiteAdChef = EntiteAdChef;
    }

    public List<DemandeConge> getDemandeByEntiteDetail() {
        return demandeByEntiteDetail;
    }

    public void setDemandeByEntiteDetail(List<DemandeConge> demandeByEntiteDetail) {
        this.demandeByEntiteDetail = demandeByEntiteDetail;
    }

    public List<DemandeConge> getDemandeByChefDetail() {
        return demandeByChefDetail;
    }

    public void setDemandeByChefDetail(List<DemandeConge> demandeByChefDetail) {
        this.demandeByChefDetail = demandeByChefDetail;
    }

    public List<DemandeConge> getDemandeByEmployeeDetail() {
        return demandeByEmployeeDetail;
    }

    public void setDemandeByEmployeeDetail(List<DemandeConge> demandeByEmployeeDetail) {
        this.demandeByEmployeeDetail = demandeByEmployeeDetail;
    }

    public List<DemandeConge> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeConge> demandes) {
        this.demandes = demandes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateMin() {
        return dateMin;
    }

    public void setDateMin(Date dateMin) {
        this.dateMin = dateMin;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getEtat() {

        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public DemandeCongeController() {
    }

    public DemandeConge getSelected() {
        if (selected == null) {
            selected = new DemandeConge();
        }
        return selected;
    }

    public void setSelected(DemandeConge selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DemandeCongeFacade getFacade() {
        return ejbFacade;
    }

    public DemandeConge prepareCreate() {
        selected = new DemandeConge();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DemandeCongeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DemandeCongeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DemandeCongeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DemandeConge> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DemandeConge getDemandeConge(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<DemandeConge> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DemandeConge> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DemandeConge.class)
    public static class DemandeCongeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DemandeCongeController controller = (DemandeCongeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "demandeCongeController");
            return controller.getDemandeConge(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DemandeConge) {
                DemandeConge o = (DemandeConge) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DemandeConge.class.getName()});
                return null;
            }
        }

    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    
}
