package controller;

import bean.ValidationDemandeConge;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.ValidationDemandeCongeFacade;

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

@Named("validationDemandeCongeController")
@SessionScoped
public class ValidationDemandeCongeController implements Serializable {

    @EJB
    private service.ValidationDemandeCongeFacade ejbFacade;
    private List<ValidationDemandeConge> items = null;
    private ValidationDemandeConge selected;

    public ValidationDemandeCongeController() {
    }

    public ValidationDemandeConge getSelected() {
         if(selected==null){
        selected = new ValidationDemandeConge() ;
        }
        return selected;
    }

    public void setSelected(ValidationDemandeConge selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ValidationDemandeCongeFacade getFacade() {
        return ejbFacade;
    }

    public ValidationDemandeConge prepareCreate() {
        selected = new ValidationDemandeConge();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ValidationDemandeCongeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ValidationDemandeCongeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ValidationDemandeCongeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ValidationDemandeConge> getItems() {
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

    public ValidationDemandeConge getValidationDemandeConge(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ValidationDemandeConge> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ValidationDemandeConge> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ValidationDemandeConge.class)
    public static class ValidationDemandeCongeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ValidationDemandeCongeController controller = (ValidationDemandeCongeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "validationDemandeCongeController");
            return controller.getValidationDemandeConge(getKey(value));
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
            if (object instanceof ValidationDemandeConge) {
                ValidationDemandeConge o = (ValidationDemandeConge) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ValidationDemandeConge.class.getName()});
                return null;
            }
        }

    }

}
