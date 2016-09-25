package se.lexicon.jomian.controller.restricted;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Attendance;
import se.lexicon.jomian.entity.CourseSession;
import se.lexicon.jomian.util.CurrentContext;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-25.
 */
@Named
@ViewScoped
public class MyAttendanceController implements Serializable {
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private AttendanceDAO attendanceDAO;
    private ScheduleModel eventModel;
    private Long accountId;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

    public void initView() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (accountId == null || accountId == 0) {
            CurrentContext.redirect404();
            return;
        }

        Account account = accountDAO.find(accountId);
        if (account == null) {
            CurrentContext.redirect404();
            return;
        }
        eventModel.clear();
        List<Attendance> attendances = attendanceDAO.findAttendanceUntilToday(accountId);
        attendances.forEach(a -> {
            CourseSession courseSession = a.getCourseSession();
            DefaultScheduleEvent s = new DefaultScheduleEvent(courseSession.getTitle(), courseSession.getStartDate(), courseSession.getEndDate(), courseSession.isAllDay());
            if (!a.isPresent()) {
                s.setStyleClass("not-present");
            }
            eventModel.addEvent(s);
        });
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
}
