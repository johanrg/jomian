package se.lexicon.jomian.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * This is the glue between the account and the CourseSession table. At every CourseSession we set a timestamp,
 * here we set the present flag for an account so we can keep track of the attendance of the student.
 *
 * @author Johan Gustafsson
 * @since 2016-09-04.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Attendance.FindAttendanceUntilToday",
                query = "SELECT a FROM Attendance a WHERE a.account.id = :accountId AND a.courseSession.startDate <= CURRENT_DATE"),
        @NamedQuery(
                name = "Attendance.FindAttendanceForAllCoursesBetween",
                query = "SELECT new se.lexicon.jomian.resultclass.TotalAttendanceForDay(c.startDate, count(a.id)) FROM CourseSession c, Attendance a WHERE a MEMBER OF c.attendances AND a.present=TRUE AND c.startDate >= :startDate AND c.endDate <= :endDate GROUP BY c.startDate ORDER BY c.startDate ASC"),
        @NamedQuery(
                name = "Attendance.FindPotentialAttendanceForAllCoursesBetween",
                query = "SELECT new se.lexicon.jomian.resultclass.TotalAttendanceForDay(c.startDate, count(a.id)) FROM CourseSession c, Attendance a WHERE a MEMBER OF c.attendances AND c.startDate >= :startDate AND c.endDate <= :endDate GROUP BY c.startDate ORDER BY c.startDate ASC"),
        @NamedQuery(
                name = "Attendance.FindAttendanceForCourseBetween",
                query = "SELECT count(c.id) FROM CourseSession c, Attendance a WHERE a MEMBER OF c.attendances AND a.present=:present AND c.course.id=:courseId AND c.startDate>=:startDate AND c.endDate<=:endDate"),
})
public class Attendance {
    private Long id;
    private boolean present;
    private Date createdAt;
    private Account account;
    private CourseSession courseSession;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "courseSession_id")
    public CourseSession getCourseSession() {
        return courseSession;
    }

    public void setCourseSession(CourseSession courseSession) {
        this.courseSession = courseSession;
    }
}
