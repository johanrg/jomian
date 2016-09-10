package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.service.ServiceException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Johan Gustafsson
 * @since 2016-09-10.
 */
@Named
@RequestScoped
public class AddCourseController {
    @Inject
    private CourseService courseService;
    private Course course = new Course();

    public String submit() {
        try {
            courseService.createCourse(course);
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance()
                    .addMessage("addCourseForm:name", new FacesMessage(e.getMessage()));

        }
        return null;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
