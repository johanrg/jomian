package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    private Course course = new Course();
    private Long courseId;

    public String addCourse() {
        try {
            courseService.createCourse(course);
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
        return "/admin/manageCourses.xhtml";
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
        this.courseId = courseId;
    }
}
