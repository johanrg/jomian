package se.lexicon.jomian.service;

import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.Language;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class CourseService implements Serializable {
    @Inject
    CourseDAO courseDAO;
    @Inject
    AccountDAO accountDAO;

    public void create(Course course, List<Account> teachers) throws ServiceException {
        if (courseDAO.findByCourseName(course.getName()) != null) {
            throw new ServiceException(Language.getMessage("course.courseAlreadyExist"));
        }

        for (Account teacher : teachers) {
            addAccountToCourse(course, teacher, AccountCourse.Role.TEACHER);
        }
        courseDAO.persist(course);
    }

    public void edit(Course course, List<Account> updatedTeacherList) throws ServiceException {
        if (teachersHaveChanged(course, updatedTeacherList)) {
            removeTeachersNotInUpdatedList(course, updatedTeacherList);
            addTeachersFromUpdatedList(course, updatedTeacherList);
            courseDAO.merge(course);
        }
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
                accountDAO.merge(account);
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

    public boolean hasStudentAlreadyApplied(Course course, Account account) {
        for (AccountCourse accountCourse : course.getAccountCourses()) {
            if (account.getId().equals(accountCourse.getAccount().getId())
                    && accountCourse.getRole() == AccountCourse.Role.APPLICATION) {
                return true;
            }
        }
        return false;
    }

    public boolean isTeacherOfCourse(Course course, Account account) {
        for (AccountCourse accountCourse : course.getAccountCourses()) {
            if (account.getId().equals(accountCourse.getAccount().getId())
                   && accountCourse.getRole() == AccountCourse.Role.TEACHER) {
                return true;
            }
        }
        return false;
    }

    public void applyStudentToCourse(Course course, Account account) {
        addAccountToCourse(course, account, AccountCourse.Role.APPLICATION);
        courseDAO.merge(course);
    }

    public void addAccountToCourse(Course course, Account account, AccountCourse.Role role) {
        AccountCourse accountCourse = new AccountCourse();
        accountCourse.setAccount(account);
        accountCourse.setCourse(course);
        accountCourse.setRole(role);
        account.getAccountCourses().add(accountCourse);
        course.getAccountCourses().add(accountCourse);
        accountDAO.merge(account);
    }

    public void removeAccountFromCourse(Course course, Account account) {
        Iterator<AccountCourse> accountCourses = course.getAccountCourses().iterator();
        while (accountCourses.hasNext()) {
            AccountCourse accountCourse = accountCourses.next();
            Account dbAccount = accountCourse.getAccount();
            if (dbAccount.getId().equals(account.getId())) {
                dbAccount.getAccountCourses().remove(accountCourse);
                accountCourses.remove();
                accountDAO.merge(account);
                courseDAO.merge(course);
                break;
            }
        }
    }

    public List<String> getCourseNamesLike(String query) {
        List<Course> courses = courseDAO.findLikeName(query);
        return courses.stream().map(Course::getName).collect(Collectors.toList());
    }

    public List<Course> getCoursesLike(String query) {
        if (query == null || query.equals("")) {
            return courseDAO.getAll();
        } else {
            return courseDAO.findLikeName(query);
        }
    }

    public long getNumberOfStudentApplicationsForCourse(Course course) {
        return course.getAccountCourses().stream().filter(a -> a.getRole() == AccountCourse.Role.APPLICATION).count();
    }

    public List<Course> getCoursesAppliedTo(Account account) {
        return account.getAccountCourses().stream()
                .filter(ac -> ac.getRole() == AccountCourse.Role.APPLICATION)
                .map(AccountCourse::getCourse)
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesAcceptedTo(Account account) {
        return account.getAccountCourses().stream()
                .filter(ac -> ac.getRole() == AccountCourse.Role.STUDENT)
                .map(AccountCourse::getCourse)
                .collect(Collectors.toList());
    }
}
