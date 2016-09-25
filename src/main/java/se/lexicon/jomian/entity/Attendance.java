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
                name = "Attendance.findAttendanceUntilToday",
                query = "SELECT a FROM Attendance a WHERE a.account.id = :accountId AND a.courseSession.startDate <= CURRENT_DATE")
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
