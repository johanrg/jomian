package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.AccountCourse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Johan Gustafsson
 * @since 2016-09-20.
 */
public class AccountCourseDAO {
    @PersistenceContext
    private EntityManager em;

    public void persist(AccountCourse accountCourse) {
        em.persist(accountCourse);
    }

    public void merge(AccountCourse accountCourse) {
        em.merge(accountCourse);
    }

    public void remove(AccountCourse accountCourse) {
        em.remove(em.merge(accountCourse));
    }
}
