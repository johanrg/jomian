package se.lexicon.jomian.controller.restricted;

import org.primefaces.event.SelectEvent;
import se.lexicon.jomian.controller.LoginController;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-16.
 */
@Named
@RequestScoped
public class MyCoursesController {
    @Inject
    LoginController loginController;
    @Inject
    CourseService courseService;

    public void onCourseRowSelect(SelectEvent event) {
        CurrentContext.redirect("/restricted/applyToCourse.xhtml?courseId="
                + ((Course) event.getObject()).getId());
    }

    public List<Course> getCoursesAppliedTo() {
        return courseService.getCoursesAppliedTo(loginController.getLoggedInAccount());
    }

    public List<Course> getCoursesAcceptedTo() {
        return courseService.getCoursesAcceptedTo(loginController.getLoggedInAccount());
    }
}
