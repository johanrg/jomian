package se.lexicon.jomian.service;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.Language;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class CourseService implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public void create(Course course, List<Account> teachers) throws ServiceException {
        try {
            findByCourseName(course.getName());
            throw new ServiceException(Language.getMessage("course.courseAlreadyExist"));
        } catch (NoResultException e) {
            for (Account teacher : teachers) {
                addAccountToCourse(course, teacher, AccountCourse.Role.TEACHER);
            }
            em.persist(course);
        }
    }

    public void edit(Course course, List<Account> updatedTeacherList) throws ServiceException {
        if (teachersHaveChanged(course, updatedTeacherList)) {
            removeTeachersNotInUpdatedList(course, updatedTeacherList);
            addTeachersFromUpdatedList(course, updatedTeacherList);
            em.merge(course);
        }
    }

    public void delete(Course course) throws ServiceException {
        em.remove(em.merge(course));
    }

    private boolean teachersHaveChanged(Course course, List<Account> updatedTeacherList) {
        if (course.getAccountCourses().size() == updatedTeacherList.size()) {
            for (AccountCourse accountCourse : course.getAccountCourses()) {
                if (updatedTeacherList.indexOf(accountCourse.getAccount()) == -1) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void removeTeachersNotInUpdatedList(Course course, List<Account> updatedTeacherList) {
        Iterator<AccountCourse> accountCourses = course.getAccountCourses().iterator();
        while (accountCourses.hasNext()) {
            AccountCourse accountCourse = accountCourses.next();
            Account account = accountCourse.getAccount();
            if (account.isTeacher() && updatedTeacherList.indexOf(account) == -1) {
                account.getAccountCourses().remove(accountCourse);
                accountCourses.remove();
                em.merge(account);
            }
        }
    }

    private void addTeachersFromUpdatedList(Course course, List<Account> updatedTeacherList) {
        for (Account teacher : updatedTeacherList) {
            boolean found = false;
            for (AccountCourse accountCourse : course.getAccountCourses()) {
                if (accountCourse.getAccount().equals(teacher)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                addAccountToCourse(course, teacher, AccountCourse.Role.TEACHER);
            }
        }
    }

    public void addAccountToCourse(Course course, Account account, AccountCourse.Role role) {
        AccountCourse accountCourse = new AccountCourse();
        accountCourse.setAccount(account);
        accountCourse.setCourse(course);
        accountCourse.setRole(role);
        account.getAccountCourses().add(accountCourse);
        course.getAccountCourses().add(accountCourse);
        em.merge(account);
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
        } catch (ServiceException e) {
            return Collections.emptyList();
        }
    }

    public List<Course> getComingCourses() {
        return em.createNamedQuery("Course.FindComingCourses", Course.class)
                .getResultList();
    }

    public Course findById(Long id) throws ServiceException {
        Course result = em.find(Course.class, id);
        if (result == null) {
            throw new NoResultException("findById");
        }
        return result;
    }

    public Course findByCourseName(String name) throws ServiceException {
        try {
            return em.createNamedQuery("Course.FindByCourseName", Course.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("findByCourseName");
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
