/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Employee;
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
public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
     public List<Employee> findChefEntiteAdministrative() {
        return em.createQuery("SELECT en.chef FROM EntiteAdministrative en").getResultList();
    }
    public List<Employee> findByEntiteAdministrative(EntiteAdministrative entiteAdministrative) {
        return em.createQuery("SELECT em from Employee em WHERE em.entiteAdministrative.id="+entiteAdministrative.getId()).getResultList();
    }
    public Employee findByLogin(String login){
        return (Employee) em.createQuery("SELECT em from Employee em WHERE em.id="+login).getSingleResult();
    }
    
}
