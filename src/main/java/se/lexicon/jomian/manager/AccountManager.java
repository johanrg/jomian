package se.lexicon.jomian.manager;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.util.Language;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * @author johan
 * @since 2016-09-01.
 */
@Stateless
public class AccountManager {
    @Inject
    private EntityManager em;

    public void createAccount(Account account) throws ManagerException {
        if (findByEmail(account.getEmail()) == null) {
            em.persist(account);
        } else {
            throw new ManagerException(Language.getMessage("emailInUse"));
        }
    }

    public Account findById(Long id) {
        return em.find(Account.class, id);
    }

    public Account findByEmail(String email) {
        try {
            return em.createNamedQuery("Account.FindByEmail", Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Added for now as an example.
      * @return List of accounts
     */
    public List<Account> getAll() {
        CriteriaQuery<Account> criteriaQuery = em.getCriteriaBuilder().createQuery(Account.class);
        criteriaQuery.select(criteriaQuery.from(Account.class));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Added for now as an example.
     * @return Total count of table
     */
    public Long count() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(Account.class)));
        return em.createQuery(query).getSingleResult();
    }
}
