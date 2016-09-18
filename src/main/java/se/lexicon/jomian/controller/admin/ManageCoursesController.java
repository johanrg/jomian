package se.lexicon.jomian.controller.admin;

import org.primefaces.event.SelectEvent;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.AccountService;
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
public class ManageCoursesController {
    @Inject
    private CourseService courseService;
    @Inject
    private AccountService accountService;

    public void onCourseRowSelect(SelectEvent event) {
        CurrentContext.redirect("/admin/editCourse.xhtml?from=/admin/manageCourses&courseId="
                + ((Course) event.getObject()).getId());
    }

    public String deleteCourse(Course course) {
        try {
            courseService.delete(course);
        } catch (ServiceException e) {
            CurrentContext.message("manageCoursesForm:messages", e.getMessage());
        }
        return "/admin/manageCourses.xhtml";
    }

    public List<Course> getAllCourses() {
        return courseService.getAll();
    }
}
