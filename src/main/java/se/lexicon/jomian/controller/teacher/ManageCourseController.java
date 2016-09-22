package se.lexicon.jomian.controller.teacher;

import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Johan Gustafsson
 * @since 2016-09-22.
 */
@Named
@ConversationScoped
public class ManageCourseController implements Serializable {
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private Conversation conversation;
    private Long courseId;
    private Course course;

    public void initView() {
        if (courseId == null || courseId == 0) {
            CurrentContext.redirect404();
            return;
        }

        course = courseDAO.find(courseId);
        if (course != null) {
            if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
                conversation.begin();
            }
        } else {
            CurrentContext.redirect404();
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
