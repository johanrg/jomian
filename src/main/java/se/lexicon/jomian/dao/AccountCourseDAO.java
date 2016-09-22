package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.AccountCourse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * @author Johan Gustafsson
 * @since 2016-09-20.
 */
public class AccountCourseDAO extends AbstractDAO<AccountCourse> implements Serializable {
    @PersistenceContext
    private EntityManager em;

    AccountCourseDAO() {
        super(AccountCourse.class);
    }

    @Override
    EntityManager getEntityManager() {
        return em;
    }

    public AccountCourse findByAccountAndCourseId(Long accountId, Long courseId) {
        return em.createNamedQuery("AccountCourse.FindByAccountAndCourseId", AccountCourse.class)
                .setParameter("accountId", accountId)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }
}
