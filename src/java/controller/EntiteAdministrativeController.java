package controller;

import bean.Employee;
import bean.EntiteAdministrative;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.EntiteAdministrativeFacade;

import java.io.Serializable;
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
import service.DemandeCongeFacade;
import service.EmployeeFacade;

@Named("entiteAdministrativeController")
@SessionScoped
public class EntiteAdministrativeController implements Serializable {

    @EJB
    private service.EntiteAdministrativeFacade ejbFacade;
    private List<EntiteAdministrative> items = null;
    private EntiteAdministrative selected;
 private EntiteAdministrative EntiteAdministrative;
    private EntiteAdministrativeFacade entiteAdministrativeFacade;
    private List<EntiteAdministrative> entiteAdministrativeDetail;
    
    private List<Employee>  empByEntite;
    
     @EJB
   private DemandeCongeFacade demandeFacade;
  
     @EJB
   private EmployeeFacade employeeFacade;
  

    
     public String EntiteSup() {
        entiteAdministrativeFacade.findEntiteTop();
        selected = null;
        return "List";
    }
      public  void findByEntiteAdministrative() {
        empByEntite = employeeFacade.findByEntiteAdministrative(EntiteAdministrative);
         
    }

    public void findByEntiteSup(EntiteAdministrative entiteAdministrative) {
        entiteAdministrativeDetail = ejbFacade.findByEntiteSup(entiteAdministrative);
    }

    public EntiteAdministrativeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(EntiteAdministrativeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public EntiteAdministrative getEntiteAdministrative() {
        return EntiteAdministrative;
    }

    public void setEntiteAdministrative(EntiteAdministrative EntiteAdministrative) {
        this.EntiteAdministrative = EntiteAdministrative;
    }

    public EntiteAdministrativeFacade getEntiteAdministrativeFacade() {
        return entiteAdministrativeFacade;
    }

    public void setEntiteAdministrativeFacade(EntiteAdministrativeFacade entiteAdministrativeFacade) {
        this.entiteAdministrativeFacade = entiteAdministrativeFacade;
    }

    public List<EntiteAdministrative> getEntiteAdministrativeDetail() {
        return entiteAdministrativeDetail;
    }

    public void setEntiteAdministrativeDetail(List<EntiteAdministrative> entiteAdministrativeDetail) {
        this.entiteAdministrativeDetail = entiteAdministrativeDetail;
    }

    public List<Employee> getEmpByEntite() {
        return empByEntite;
    }

    public void setEmpByEntite(List<Employee> empByEntite) {
        this.empByEntite = empByEntite;
    }

    public DemandeCongeFacade getDemandeFacade() {
        return demandeFacade;
    }

    public void setDemandeFacade(DemandeCongeFacade demandeFacade) {
        this.demandeFacade = demandeFacade;
    }

    public EmployeeFacade getEmployeeFacade() {
        return employeeFacade;
    }

    public void setEmployeeFacade(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }
    
    public EntiteAdministrativeController() {
    }

    public EntiteAdministrative getSelected() {
         if(selected==null){
        selected = new EntiteAdministrative();
        }
        return selected;
    }

    public void setSelected(EntiteAdministrative selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EntiteAdministrativeFacade getFacade() {
        return ejbFacade;
    }

    public EntiteAdministrative prepareCreate() {
        selected = new EntiteAdministrative();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EntiteAdministrativeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EntiteAdministrativeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EntiteAdministrativeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EntiteAdministrative> getItems() {
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

    public EntiteAdministrative getEntiteAdministrative(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EntiteAdministrative> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EntiteAdministrative> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EntiteAdministrative.class)
    public static class EntiteAdministrativeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EntiteAdministrativeController controller = (EntiteAdministrativeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "entiteAdministrativeController");
            return controller.getEntiteAdministrative(getKey(value));
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
            if (object instanceof EntiteAdministrative) {
                EntiteAdministrative o = (EntiteAdministrative) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EntiteAdministrative.class.getName()});
                return null;
            }
        }

    }

}
