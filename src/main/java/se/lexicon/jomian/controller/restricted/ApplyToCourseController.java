package se.lexicon.jomian.controller.restricted;

import se.lexicon.jomian.controller.LoginController;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-17.
 */
@Named
@ConversationScoped
public class ApplyToCourseController implements Serializable {
    @Inject
    Conversation conversation;
    @Inject
    CourseService courseService;
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private LoginController loginController;
    private Long courseId;
    private Course course;
    private List<Account> teachers = new ArrayList<>();
    private long studentsAppliedCount;
    private String from;

    public void initView() {
        if (courseId == null || courseId == 0) {
            CurrentContext.redirect404();
            return;
        }

        course = courseDAO.findById(courseId);
        if (course == null) {
            CurrentContext.redirect404();
            return;
        }

        if (from == null) {
            from = "/restricted/index";
        }
        studentsAppliedCount = courseService.getNumberOfStudentApplicationsForCourse(course);
        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String applyToCourse() {
        courseService.applyStudentToCourse(course, loginController.getLoggedInAccount());
        conversation.end();
        return from;
    }

    public String removeFromCourse() {
        courseService.removeAccountFromCourse(course, loginController.getLoggedInAccount());
        conversation.end();
        return from;
    }

    public boolean getHasStudentAlreadyApplied() {
        return courseService.hasStudentApplied(course, loginController.getLoggedInAccount());
    }

    public boolean getHasStudentBeenApproved() {
       return courseService.hasStudentBeenApproved(course, loginController.getLoggedInAccount());
    }

    public boolean isTeacherOfCourse() {
        return courseService.isTeacherOfCourse(course, loginController.getLoggedInAccount());
    }

    public boolean isApplicationStillOpen() {
        return studentsAppliedCount < course.getMaxStudents();
    }

    public Long getPlacesLeft() {
        return course.getMaxStudents() - studentsAppliedCount;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Account> getTeachers() {
        return courseDAO.findTeachersForCourse(courseId);
    }
}

