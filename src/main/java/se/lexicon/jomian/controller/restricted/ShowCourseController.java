package se.lexicon.jomian.controller.restricted;

import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-17.
 */
@Named
@RequestScoped
public class ShowCourseController {
    @Inject
    CourseService courseService;
    @Inject
    private CourseDAO courseDAO;
    private Long courseId;
    private Course course;
    private List<Account> teachers = new ArrayList<>();

    public void initView() {
        if (courseId == null || courseId == 0) {
            CurrentContext.redirect404();
            return;
        }

        try {
            course = courseDAO.findById(courseId);
            populateListOfTeachers();
        } catch (NoResultException e) {
            CurrentContext.redirect404();
        }
    }

    private void populateListOfTeachers() {
        course.getAccountCourses().forEach(accountCourse -> {
            if (accountCourse.getRole() == AccountCourse.Role.TEACHER) {
                Account teacher = accountCourse.getAccount();
                teachers.add(teacher);
            }
        });
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Account> getTeachers() {
        return teachers;
    }
}

