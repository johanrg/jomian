package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-19.
 */
@Stateless
public class CourseDAO implements Serializable {
    @PersistenceContext
    EntityManager em;

    public void persist(Course course) {
        em.persist(course);
    }

    public void merge(Course course) {
        em.merge(course);
    }

    public void remove(Course course) {
        em.remove(em.merge(course));
    }

    public List<Account> findTeachersForCourse(Long courseId) {
        try {
            Course course = findById(courseId);
            List<Account> teachers = new ArrayList<>();
            for (AccountCourse accountCourse : course.getAccountCourses()) {
                if (accountCourse.getAccount().isTeacher()) {
                    teachers.add(accountCourse.getAccount());
                }
            }
            return teachers;
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public List<Course> findComingCourses() {
        return em.createNamedQuery("Course.FindComingCourses", Course.class)
                .getResultList();
    }

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    public long getNumberOfStudentApplicationsForCourse(Course course) {
        return course.getAccountCourses().stream().map(a -> a.getRole() == AccountCourse.Role.APPLICATION).count();
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

    public List<Course> findLikeName(String name) {
        return em.createNamedQuery("Course.FindLikeName", Course.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public List<Course> getAll() {
        CriteriaQuery<Course> criteriaQuery = em.getCriteriaBuilder().createQuery(Course.class);
        criteriaQuery.select(criteriaQuery.from(Course.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
