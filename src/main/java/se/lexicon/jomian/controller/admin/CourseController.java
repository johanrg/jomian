package se.lexicon.jomian.controller.admin;

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
 * @since 2016-09-10.
 */
@Named
@RequestScoped
public class CourseController {
    @Inject
    private CourseService courseService;
    private Course course = new Course();

    public String addCourse() {
        try {
            courseService.createCourse(course);
        } catch (ServiceException e) {
            CurrentContext.message("addCourseForm:name", e.getMessage());
        }
        return null;
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
}
