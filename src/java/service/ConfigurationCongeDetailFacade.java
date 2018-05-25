/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ConfigurationCongeDetail;
import bean.TypeConge;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Maryem
 */
@Stateless
public class ConfigurationCongeDetailFacade extends AbstractFacade<ConfigurationCongeDetail> {

    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigurationCongeDetailFacade() {
        super(ConfigurationCongeDetail.class);
    }
     public ConfigurationCongeDetail definnbj(TypeConge typeConge) {
        return (ConfigurationCongeDetail) em.createQuery("SELECT cd from ConfigurationCongeDetail cd WHERE cd.typeConge.id='" + typeConge.getId() + "'").getSingleResult();

    }
    
}
