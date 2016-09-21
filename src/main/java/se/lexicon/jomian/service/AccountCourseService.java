package se.lexicon.jomian.service;

import se.lexicon.jomian.dao.AccountCourseDAO;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;

/**
 * @author Johan Gustafsson
 * @since 2016-09-21.
 */
@Stateless
public class AccountCourseService {
    @Inject
    private AccountCourseDAO accountCourseDAO;
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private CourseDAO courseDAO;

    public void changeRole(Long id, AccountCourse.Role role) {
        AccountCourse accountCourse = accountCourseDAO.findById(id);
        accountCourse.setRole(role);
        accountCourseDAO.merge(accountCourse);
    }

    public void removeAccountCourse(Long id) {
        AccountCourse accountCourse = accountCourseDAO.findById(id);
        Account account = accountCourse.getAccount();
        Course course = accountCourse.getCourse();
        account.getAccountCourses().remove(accountCourse);
        course.getAccountCourses().remove(accountCourse);
        accountDAO.merge(account);
        courseDAO.merge(course);
    }
}
