package se.lexicon.jomian.entity;

import javax.jws.soap.InitParam;
import javax.persistence.*;
import java.util.Date;

/**
 * Many to many glue between an Account and a Course, the reason for doing it this way instead of a manytomany is so
 * we can define the account as either student or teacher no matter what the account says. Of course an account
 * can not be the teacher for a course unless he has the teacher flag set in account and in here. But this opens up
 * the possibility for a teacher being a student in a certain course.
 *
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "account_id"})})
@NamedQueries({
        @NamedQuery(name = "AccountCourse.FindByAccountAndCourseId", query = "SELECT a FROM AccountCourse a WHERE a.account.id = :accountId AND a.course.id = :courseId")
})
public class AccountCourse {
    public enum Role {
        APPLICATION(1),
        DROPOUT(2),
        STUDENT(3),
        TEACHER(4);

        private int value;

        public int getValue() {
            return value;
        }

        Role(int value) {
            this.value = value;
        }
    }

    private Long id;
    private Role role;
    private Date createdAt;
    private Course course;
    private Account account;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
       return "" + getId();
    }

    @Override
    public boolean equals(Object o) {
       return o != null && o instanceof AccountCourse && getId().equals(((AccountCourse)o).getId());
    }
}
