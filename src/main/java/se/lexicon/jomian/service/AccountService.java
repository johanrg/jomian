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
 * Handles all the logic concerning accounts.
 *
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class AccountService implements Serializable {
    @PersistenceContext
    private EntityManager em;

    /**
     * Creates a new account if the email is not present in the table already.
     *
     * @param account The account to create.
     * @throws ServiceException if the e-mail is already used by another account.
     */
    public void createAccount(Account account) throws ServiceException {
        if (account == null) {
            throw new ServiceException(Language.getMessage("error.unexpectedError"));
        }

        if (findByEmail(account.getEmail()) == null) {
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12)));

            if (count() == 0) {
                // This is the first user in the system so make the account an admin.
                account.setVerified(true);
                account.setAdmin(true);
            } else {
                account.setVerified(false);
            }
            em.persist(account);
        } else {
            throw new ServiceException(Language.getMessage("register.emailInUse"));
        }
    }

    public void editAccount(Account account) {
        em.merge(account);
    }

    public void verifyAccount(Account account) {
        account.setVerified(true);
        em.merge(account);
    }

    public void deleteAccount(Account account) {
        em.remove(em.merge(account));
    }

    public Account loginAccount(Account account) throws ServiceException {
        Account dbAccount = findByEmail(account.getEmail());
        if (dbAccount != null) {
            if (!dbAccount.isVerified()) {
                throw new ServiceException(Language.getMessage("login.notVerified"));
            } else if (BCrypt.checkpw(account.getPassword(), dbAccount.getPassword())) {
                return dbAccount;
            }
        }
        throw new ServiceException(Language.getMessage("login.invalidAccount"));
    }

    /**
     * Finds and returns the account holding the specified id.
     *
     * @param id the account id.
     * @return Account entity.
     */
    public Account findById(Long id) {
        return em.find(Account.class, id);
    }

    public Account findByIdAndPass(Long id, String password) {
        try {
            return em.createNamedQuery("Account.FindByIdAndPass", Account.class)
                    .setParameter("id", id)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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

    public List<Account> getUnverifiedAccounts() {
        return em.createNamedQuery("Account.NotVerified", Account.class)
                .getResultList();
    }

    /**
     * Added for now as an example.
     *
     * @return List of accounts
     */
    public List<Account> getAll() {
        CriteriaQuery<Account> criteriaQuery = em.getCriteriaBuilder().createQuery(Account.class);
        criteriaQuery.select(criteriaQuery.from(Account.class));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Returns the total number of rows in the table
     *
     * @return Total row count in the table
     */
    public Long count() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(Account.class)));
        return em.createQuery(query).getSingleResult();
    }

}
