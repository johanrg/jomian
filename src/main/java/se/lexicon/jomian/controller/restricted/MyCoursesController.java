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

    public void onTeacherForCourseRowSelect(SelectEvent event) {
        CurrentContext.redirect("/teacher/courseSchedule.xhtml?courseId="
                + ((Course) event.getObject()).getId());
    }

    public void onCourseRowSelect(SelectEvent event) {
        CurrentContext.redirect("/restricted/applyToCourse.xhtml?courseId="
                + ((Course) event.getObject()).getId() + "&from=/restricted/myCourses");
    }

    public List<Course> getCoursesAsTeacher() {
        return courseService.getCoursesAsTeacher(loginController.getLoggedInAccount());
    }

    public List<Course> getCoursesAppliedTo() {
        return courseService.getCoursesStudentAppliedTo(loginController.getLoggedInAccount());
    }

    public List<Course> getCoursesAcceptedTo() {
        return courseService.getAcceptedCoursesForStudent(loginController.getLoggedInAccount());
    }
}
