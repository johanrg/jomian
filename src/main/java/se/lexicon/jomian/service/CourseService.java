package se.lexicon.jomian.service;

import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.Language;

import javax.ejb.Stateless;
import javax.naming.ServiceUnavailableException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class CourseService implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public void createCourse(Course course) throws ServiceException {
        if (course == null) {
            throw new ServiceException(Language.getMessage("error.unexpectedError"));
        }

        if (findByCourseName(course.getName()) == null) {
            em.persist(course);
        } else {
            throw new ServiceException(Language.getMessage("addCourse.courseAlreadyExist"));
        }
    }

    public void editCourse(Course course) throws ServiceException {
        if (course == null) {
            throw new ServiceException(Language.getMessage("error.unexpectedError"));
        }
        em.merge(course);
    }

    /**
     * Finds and returns the account holding the specified id.
     *
     * @param id the account id.
     * @return Account entity.
     */
    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    public Course findByCourseName(String name) {
        try {
            return em.createNamedQuery("Course.FindByCourseName", Course.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Course> getAll() {
        CriteriaQuery<Course> criteriaQuery = em.getCriteriaBuilder().createQuery(Course.class);
        criteriaQuery.select(criteriaQuery.from(Course.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
