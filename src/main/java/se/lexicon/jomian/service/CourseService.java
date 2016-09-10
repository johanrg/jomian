package se.lexicon.jomian.service;

import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.Language;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class CourseService {
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

    public Course findByCourseName(String name) {
        try {
            return em.createNamedQuery("Course.FindByCourseName", Course.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
