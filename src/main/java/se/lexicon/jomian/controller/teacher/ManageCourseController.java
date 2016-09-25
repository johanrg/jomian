package se.lexicon.jomian.controller.teacher;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.spi.CalendarNameProvider;

import static org.eclipse.persistence.expressions.ExpressionOperator.today;

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
    private ScheduleModel schedule;

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
            schedule = new DefaultScheduleModel();
  /*          course.getCourseSessions().forEach(cs -> {
                        schedule.addEvent(new DefaultScheduleEvent(cs.getTitle(), cs.getStartDate(), cs.getEndDate()));
                    }
            ); */
        } else {
            CurrentContext.redirect404();
        }
    }

    private Date nextDay9Am() {
        Calendar t = Calendar.getInstance();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR_OF_DAY, 16);
        t.set(Calendar.MINUTE, 0);

        return t.getTime();
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

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }
}


