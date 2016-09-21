package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.resultclass.AccountAndCourse;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-19.
 */
@Stateless
public class AccountDAO extends AbstractDAO<Account> implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public AccountDAO() {
        super(Account.class);
    }

    @Override
    EntityManager getEntityManager() {
        return em;
    }

    public Account findByIdAndPass(Long id, String password) throws NoResultException {
        try {
            return em.createNamedQuery("Account.FindByIdAndPass", Account.class)
                    .setParameter("id", id)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Account findByEmail(String email) throws NoResultException {
        try {
            return em.createNamedQuery("Account.FindByEmail", Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Account> findLikeName(String name) {
        return em.createNamedQuery("Account.FindLikeName", Account.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public List<Account> findAllTeachers() {
        return em.createNamedQuery("Account.FindAllTeachers", Account.class)
                .getResultList();
    }

    public List<Account> findUnverifiedAccounts() {
        return em.createNamedQuery("Account.FindUnverifiedAccounts", Account.class)
                .getResultList();
    }
}
