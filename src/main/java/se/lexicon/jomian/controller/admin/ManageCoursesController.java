package se.lexicon.jomian.controller.admin;

import org.primefaces.event.SelectEvent;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Course;
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
public class ManageCoursesController {
    @Inject
    private CourseDAO courseDAO;

    public void onCourseRowSelect(SelectEvent event) {
        CurrentContext.redirect("/admin/editCourse.xhtml?from=/admin/manageCourses&courseId="
                + ((Course) event.getObject()).getId());
    }

    public String deleteCourse(Course course) {
        courseDAO.remove(course);
        return "/admin/manageCourses.xhtml";
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAll();
    }
}
