package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.AccountNotFoundException;
import javax.xml.ws.Service;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-10.
 */
@Named
@RequestScoped
public class CourseController {
    @Inject
    private CourseService courseService;
    @Inject
    private AccountService accountService;
    private Course course = new Course();
    private Long courseId;
    private String from;
    private List<Account> selectedTeachers;

    public String addCourse() {
        try {
            courseService.createCourse(course, selectedTeachers);
        } catch (ServiceException e) {
            CurrentContext.message("addCourseForm:name", e.getMessage());
            return null;
        }
        return "/admin/manageCourses.xhtml";
    }

    public String editCourse() {
        try {
            courseService.editCourse(course);
        } catch (ServiceException e) {
            CurrentContext.message("editCourse", e.getMessage());
            return null;
        }
        return from + "?faces-redirect=true";
    }

    public String deleteCourse(Course course) {
        try {
            courseService.deleteCourse(course);
        } catch (ServiceException e) {
            CurrentContext.message("manageCoursesForm:messages", e.getMessage());
        }
        return "/admin/manageCourses.xhtml?faces-redirect=true";
    }

    public List<Account> getAllTeachers() {
        return accountService.getAllTeachers();
    }

    public List<Course> getAllCourses() {
        return courseService.getAll();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        course = courseService.findById(courseId);
        selectedTeachers = courseService.findTeachers(courseId);
        this.courseId = courseId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Account> getSelectedTeachers() {
        return selectedTeachers;
    }

    public void setSelectedTeachers(List<Account> selectedTeachers) {
        this.selectedTeachers = selectedTeachers;
    }
}
