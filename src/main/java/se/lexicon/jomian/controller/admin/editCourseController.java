package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-15.
 */
@Named
@ConversationScoped
public class editCourseController implements Serializable {
    @Inject
    Conversation conversation;
    @Inject
    private CourseService courseService;
    @Inject
    private AccountService accountService;
    private Course course = new Course();
    private List<Account> selectedTeachers;
    private Long courseId;
    private String from;

    public void initView() {
        if (courseId == null || courseId == 0) {
            CurrentContext.redirect404();
            return;
        }

        if (from == null || from.equals("")) {
            from = "/restricted/index.xhtml?faces-redirect=true";
        }

        try {
            course = courseService.findById(courseId);
            selectedTeachers = courseService.findTeachersForCourse(courseId);
            if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
                conversation.begin();
            }
        } catch (ServiceException e) {
            CurrentContext.redirect404();
        }
    }

    public String editCourse() {
        try {
            courseService.edit(course, selectedTeachers);
        } catch (ServiceException e) {
            CurrentContext.message("edit:messages", e.getMessage());
            return null;
        }
        conversation.end();
        return from + "?faces-redirect=true";
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Account> getAllTeachers() {
        return accountService.getAllTeachers();
    }

    public List<Account> getSelectedTeachers() {
        return selectedTeachers;
    }

    public void setSelectedTeachers(List<Account> selectedTeachers) {
        this.selectedTeachers = selectedTeachers;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
