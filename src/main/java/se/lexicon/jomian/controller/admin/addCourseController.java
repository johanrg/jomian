package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-15.
 */
@Named
@RequestScoped
public class addCourseController {
    @Inject
    private CourseService courseService;
    @Inject
    private AccountDAO accountDAO;
    private Course course = new Course();
    private List<Account> selectedTeachers;

    public String createCourse() {
        try {
            courseService.create(course, selectedTeachers);
        } catch (ServiceException e) {
            CurrentContext.message("addCourseForm:name", e.getMessage());
            return null;
        }
        return "/admin/manageCourses.xhtml?faces-redirect=true";
    }

    public List<Account> getAllTeachers() {
        return accountDAO.findAllTeachers();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Account> getSelectedTeachers() {
        return selectedTeachers;
    }

    public void setSelectedTeachers(List<Account> selectedTeachers) {
        this.selectedTeachers = selectedTeachers;
    }
}
