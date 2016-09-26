package se.lexicon.jomian.service;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.entity.Attendance;
import se.lexicon.jomian.entity.CourseSession;
import se.lexicon.jomian.resultclass.TotalAttendanceForDay;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Stateless
public class AttendanceService implements Serializable {
    @Inject
    private AttendanceDAO attendanceDAO;

    public ScheduleModel populateScheduleModel(Long accountId) {
        ScheduleModel eventModel = new DefaultScheduleModel();
        List<Attendance> attendances = attendanceDAO.findAttendanceUntilToday(accountId);
        attendances.forEach(a -> {
            CourseSession courseSession = a.getCourseSession();
            DefaultScheduleEvent s = new DefaultScheduleEvent(courseSession.getTitle(), courseSession.getStartDate(), courseSession.getEndDate(), courseSession.isAllDay());
            if (!a.isPresent()) {
                s.setStyleClass("not-present");
            }
            eventModel.addEvent(s);
        });
        return eventModel;
    }
}
