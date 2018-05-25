package controller;

import bean.ConfigurationCongeDetail;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.ConfigurationCongeDetailFacade;

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
import javax.xml.soap.Detail;

@Named("configurationCongeDetailController")
@SessionScoped
public class ConfigurationCongeDetailController implements Serializable {

    @EJB
    private service.ConfigurationCongeDetailFacade ejbFacade;
    private List<ConfigurationCongeDetail> items = null;
    private ConfigurationCongeDetail selected;

    public ConfigurationCongeDetailController() {
    }

    public ConfigurationCongeDetail getSelected() {
        
        if(selected==null){
        selected = new ConfigurationCongeDetail() ;
        }
     
        return selected;
    }

    public void setSelected(ConfigurationCongeDetail selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ConfigurationCongeDetailFacade getFacade() {
        return ejbFacade;
    }

    public ConfigurationCongeDetail prepareCreate() {
        selected = new ConfigurationCongeDetail();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ConfigurationCongeDetailCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ConfigurationCongeDetailUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ConfigurationCongeDetailDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ConfigurationCongeDetail> getItems() {
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

    public ConfigurationCongeDetail getConfigurationCongeDetail(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ConfigurationCongeDetail> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ConfigurationCongeDetail> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ConfigurationCongeDetail.class)
    public static class ConfigurationCongeDetailControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConfigurationCongeDetailController controller = (ConfigurationCongeDetailController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "configurationCongeDetailController");
            return controller.getConfigurationCongeDetail(getKey(value));
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
            if (object instanceof ConfigurationCongeDetail) {
                ConfigurationCongeDetail o = (ConfigurationCongeDetail) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ConfigurationCongeDetail.class.getName()});
                return null;
            }
        }

    }

}
