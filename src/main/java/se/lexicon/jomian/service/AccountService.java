package se.lexicon.jomian.service;

import org.mindrot.jbcrypt.BCrypt;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.util.Language;

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
 * @since 2016-09-01.
 */
@Stateless
public class AccountService implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public void create(Account account) throws ServiceException {
        try {
            findByEmail(account.getEmail());
            throw new ServiceException(Language.getMessage("account.emailInUse"));
        } catch (NoResultException e) {
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12)));

            if (count() == 0) {
                account.setVerified(true);
                account.setAdmin(true);
            } else {
                account.setVerified(false);
            }
            em.persist(account);
        }
    }

    public void update(Account account) {
        em.merge(account);
    }

    public void delete(Account account) {
        em.remove(em.merge(account));
    }

    public Account returnAccountWithMatchingCredentials(String email, String password) throws ServiceException {
        try {
            Account dbAccount = findByEmail(email);
            if (!dbAccount.isVerified()) {
                throw new ServiceException(Language.getMessage("account.notVerified"));
            } else if (BCrypt.checkpw(password, dbAccount.getPassword())) {
                return dbAccount;
            }
        } catch (NoResultException e) {
            throw new ServiceException(Language.getMessage("account.invalidAccount"));
        }
        return null;
    }

    public Account findById(Long id) {
        return em.find(Account.class, id);
    }

    public Account findByIdAndPass(Long id, String password) throws NoResultException {
        return em.createNamedQuery("Account.FindByIdAndPass", Account.class)
                .setParameter("id", id)
                .setParameter("password", password)
                .getSingleResult();
    }

    public Account findByEmail(String email) throws NoResultException {
        return em.createNamedQuery("Account.FindByEmail", Account.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<Account> findLikeName(String name) {
        return em.createNamedQuery("Account.FindLikeName", Account.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public List<Account> getAllTeachers() {
        return em.createNamedQuery("Account.GetAllTeachers", Account.class)
                .getResultList();
    }

    public List<Account> getUnverifiedAccounts() {
        return em.createNamedQuery("Account.NotVerified", Account.class)
                .getResultList();
    }

    public List<Account> getAll() {
        CriteriaQuery<Account> criteriaQuery = em.getCriteriaBuilder().createQuery(Account.class);
        criteriaQuery.select(criteriaQuery.from(Account.class));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public Long count() throws ServiceException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(Account.class)));
        return em.createQuery(query).getSingleResult();
    }

}
