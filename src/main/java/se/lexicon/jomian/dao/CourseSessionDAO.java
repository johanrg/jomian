package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.CourseSession;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-25.
 */
@Stateless
public class CourseSessionDAO extends AbstractDAO<CourseSession> implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public CourseSessionDAO() {
        super(CourseSession.class);
    }

    @Override
    EntityManager getEntityManager() {
        return em;
    }

    public List<CourseSession> findScheduleForCourse(Long courseId) {
        return em.createNamedQuery("CourseSession.FindScheduleForCourse", CourseSession.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}
