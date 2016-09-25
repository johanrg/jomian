package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.resultclass.AccountAndCourse;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-19.
 */
@Stateless
public class CourseDAO extends AbstractDAO<Course> implements Serializable {
    @PersistenceContext
    EntityManager em;

    public CourseDAO() {
        super(Course.class);
    }

    @Override
    EntityManager getEntityManager() {
        return em;
    }

    public List<Account> findTeachersForCourse(Long courseId) {
        try {
            Course course = find(courseId);
            List<Account> teachers = new ArrayList<>();
            for (AccountCourse accountCourse : course.getAccountCourses()) {
                if (accountCourse.getRole() == AccountCourse.Role.TEACHER) {
                    teachers.add(accountCourse.getAccount());
                }
            }
            return teachers;
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public List<Account> findStudentsForCourse(Long courseId) {
        try {
            Course course = find(courseId);
            List<Account> students = new ArrayList<>();
            for (AccountCourse accountCourse : course.getAccountCourses()) {
                if (accountCourse.getRole() == AccountCourse.Role.STUDENT) {
                    students.add(accountCourse.getAccount());
                }
            }
            return students;
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public List<Course> findComingCourses() {
        return em.createNamedQuery("Course.FindComingCourses", Course.class)
                .getResultList();
    }

    public List<Course> findStartedButNotFinishedCourses() {
        return em.createNamedQuery("Course.FindStartedButNotFinishedCourses", Course.class)
                .getResultList();
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

    public List<AccountAndCourse> findAllStudentApplications() {
        return em.createNamedQuery("Course.FindAllOfRoleType", AccountAndCourse.class)
                .setParameter("role", AccountCourse.Role.APPLICATION)
                .getResultList();
    }

    public List<Course> findLikeName(String name) {
        return em.createNamedQuery("Course.FindLikeName", Course.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
