package se.lexicon.jomian.controller.restricted;

import se.lexicon.jomian.controller.LoginController;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        populateListOfTeachers();

        if (from == null) {
            from = "/restricted/index";
        }
        studentsAppliedCount = courseService.getNumberOfStudentApplicationsForCourse(course);
        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public boolean getHasStudentAlreadyApplied() {
        Account account = loginController.getLoggedInAccount();
        return account != null && courseService.hasStudentAlreadyApplied(course, account);
    }

    public boolean isTeacherOfCourse() {
        Account account = loginController.getLoggedInAccount();
        return account != null && courseService.isTeacherOfCourse(course, account);
    }

    public String applyToCourse() {
        Account account = loginController.getLoggedInAccount();
        courseService.applyStudentToCourse(course, account);
        conversation.end();
        return from;
    }

    public String cancelApplication() {
        Account account = loginController.getLoggedInAccount();
        courseService.removeAccountFromCourse(course, account);
        conversation.end();
        return from;
    }

    private void populateListOfTeachers() {
        teachers.clear();
        course.getAccountCourses().forEach(a -> {
            if (a.getRole() == AccountCourse.Role.TEACHER) {
                Account teacher = a.getAccount();
                teachers.add(teacher);
            }
        });
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

