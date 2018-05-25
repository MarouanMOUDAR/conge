/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import controller.util.SessionUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Maryem
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "NewGestionCongePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
         public User findByLogin(User user) {
        return (User) em.createQuery("SELECT ur from User ur where ur.login='" + user.getLogin() + "'").getSingleResult();
    }

    public int seConnecter(User user) {
        if (user == null) {
            return -1;
        } else {
            User loadedUser = findByLogin(user);
            if (loadedUser == null) {
                return -2;
            } else if (!loadedUser.getPassword().equals(user.getPassword())) {
                return -3;
            }
            return 1;
        }
    }
     public void seDeConnnecter() {
        SessionUtil.getSession().invalidate();
    }
    
    
}
