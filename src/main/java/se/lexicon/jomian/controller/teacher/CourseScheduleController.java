package se.lexicon.jomian.controller.teacher;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Attendance;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.entity.CourseSession;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.service.CourseSessionService;
import se.lexicon.jomian.util.CurrentContext;
import se.lexicon.jomian.util.Language;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Johan Gustafsson
 * @since 2016-09-24.
 */
@Named
@ViewScoped
public class CourseScheduleController implements Serializable {
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private CourseSessionService courseSessionService;
    @Inject
    private CourseService courseService;
    private Long courseId;
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private List<Account> selectedAccounts = new ArrayList<>();
    private Course course;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

    public void initView() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (courseId == null || courseId == 0) {
            CurrentContext.redirect404();
            return;
        }

        course = courseDAO.find(courseId);
        if (course == null) {
            CurrentContext.redirect404();
            return;
        }

        eventModel.clear();
        for (CourseSession courseSession : course.getCourseSessions()) {
            DefaultScheduleEvent s = new DefaultScheduleEvent(courseSession.getTitle(), courseSession.getStartDate(), courseSession.getEndDate(), courseSession);
            s.setAllDay(courseSession.isAllDay());
            eventModel.addEvent(s);
        }
    }

    public String getHeader() {
        return Language.getFormatedMessage("courseSchedule.header", course.getName());
    }

    public List<Account> getAllStudents() {
        return courseDAO.findStudentsForCourse(courseId);
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            CourseSession courseSession = courseSessionService.create(courseId, event, selectedAccounts);
            ((DefaultScheduleEvent) event).setData(courseSession);
            eventModel.addEvent(event);

        } else {
            courseSessionService.update(((CourseSession) event.getData()), event, selectedAccounts);
            eventModel.updateEvent(event);
        }
        event = new DefaultScheduleEvent();
    }

    public void removeEvent(ActionEvent actionEvent) {
        courseSessionService.remove((CourseSession) event.getData());
        eventModel.deleteEvent(event);
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        CourseSession courseSession = ((CourseSession) event.getData());
        selectedAccounts.clear();
        selectedAccounts = courseSession.getAttendances().stream()
                .filter(Attendance::isPresent)
                .map(Attendance::getAccount)
                .collect(Collectors.toList());
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        selectedAccounts.clear();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Account> getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<Account> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
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

    public List<Account> getStudentsInCourse() {
        return courseService.getStudentsAcceptedToCourse(course);
    }

    public Long getPlacesLeft() {
        return courseService.getOpenSpots(course);

    }
}
