package se.lexicon.jomian.entity;

import javax.persistence.*;

/**
 * This is the glue between the account and the CourseSession table. At every CourseSession we set a timestamp,
 * here we set the present flag for an account so we can keep track of the attendance of the student.
 *
 * @author Johan Gustafsson
 * @since 2016-09-04.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "coursesession_id"})})
public class Attendance {
    private Long id;
    private boolean present;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(nullable = false)
    public CourseSession getCourseSession() {
        return courseSession;
    }

    public void setCourseSession(CourseSession courseSession) {
        this.courseSession = courseSession;
    }
}
