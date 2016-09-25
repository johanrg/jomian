package se.lexicon.jomian.service;

import org.primefaces.model.ScheduleEvent;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.dao.CourseSessionDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Attendance;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.entity.CourseSession;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-25.
 */
@Stateless
public class CourseSessionService {
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private CourseSessionDAO courseSessionDAO;
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private AttendanceDAO attendanceDAO;

    public CourseSession create(Long courseId, ScheduleEvent event, List<Account> selectedAccounts) {
        Course course = courseDAO.find(courseId);
        List<Account> students = courseDAO.findStudentsForCourse(courseId);

        CourseSession courseSession = new CourseSession();
        courseSession.setTitle(event.getTitle());
        courseSession.setStartDate(event.getStartDate());
        courseSession.setEndDate(event.getEndDate());
        courseSession.setAllDay(event.isAllDay());
        courseSession.setCourse(course);
        for (Account student : students) {
            Attendance attendance = new Attendance();
            attendance.setCourseSession(courseSession);
            attendance.setAccount(student);
            attendance.setPresent(selectedAccounts.contains(student));
            courseSession.getAttendances().add(attendance);
            student.getAttendances().add(attendance);
        }

        courseSessionDAO.persist(courseSession);
        course.getCourseSessions().add(courseSession);
        courseDAO.merge(course);
        return courseSession;
    }

    public void update(CourseSession courseSession, ScheduleEvent event, List<Account> selectedAccounts) {
        try {
            List<Account> students = courseDAO.findStudentsForCourse(courseSession.getCourse().getId());

            courseSession.setTitle(event.getTitle());
            courseSession.setStartDate(event.getStartDate());
            courseSession.setEndDate(event.getEndDate());
            courseSession.setAllDay(event.isAllDay());
            removeAttendances(courseSession);
            addAttendances(courseSession, selectedAccounts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void remove(CourseSession courseSession) {
        try {
            Course course = courseSession.getCourse();
            course.getCourseSessions().remove(courseSession);
            courseDAO.merge(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    private void removeAttendances(CourseSession courseSession) {
        for (Attendance attendance : courseSession.getAttendances()) {
            attendance.getAccount().getAttendances().remove(attendance);
            attendanceDAO.remove(attendance);
            accountDAO.merge(attendance.getAccount());
        }
        courseSession.getAttendances().clear();
        courseSessionDAO.merge(courseSession);
    }

    private void addAttendances(CourseSession courseSession, List<Account> selectedAccounts) {
        List<Account> students = courseDAO.findStudentsForCourse(courseSession.getCourse().getId());
        for (Account student : students) {
            Attendance attendance = new Attendance();
            attendance.setCourseSession(courseSession);
            attendance.setAccount(student);
            attendance.setPresent(selectedAccounts.contains(student));
            attendanceDAO.persist(attendance);
            courseSession.getAttendances().add(attendance);
            student.getAttendances().add(attendance);
            accountDAO.merge(student);
        }
        courseSessionDAO.merge(courseSession);
    }
}

