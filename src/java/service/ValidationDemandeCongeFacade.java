/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ValidationDemandeConge;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Maryem
 */
@Stateless
public class ValidationDemandeCongeFacade extends AbstractFacade<ValidationDemandeConge> {

    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValidationDemandeCongeFacade() {
        super(ValidationDemandeConge.class);
    }
    
}
