/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.EntiteAdministrative;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Maryem
 */
@Stateless
public class EntiteAdministrativeFacade extends AbstractFacade<EntiteAdministrative> {

    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntiteAdministrativeFacade() {
        super(EntiteAdministrative.class);
    }
     public List<EntiteAdministrative> findEntiteTop() {
        return em.createQuery("SELECT en from EntiteAdministrative en WHERE en.entiteSuperieur.id=NULL").getResultList();
    }

    public List<EntiteAdministrative> findByEntiteSup(EntiteAdministrative entiteAdministrative) {
    return em.createQuery("SELECT en from EntiteAdministrative en WHERE en.entiteSuperieur.id="+entiteAdministrative.getId()).getResultList();
    }
    public EntiteAdministrative findByLogin(String login) {
        return (EntiteAdministrative) em.createQuery("SELECT en from EntiteAdministrative en WHERE en.chef.id="+login).getSingleResult();
    }
    
}
