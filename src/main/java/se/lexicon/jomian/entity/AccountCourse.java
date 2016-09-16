package se.lexicon.jomian.entity;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;

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
public class AccountCourse {
    public enum Status {
        REGISTER(1),
        DROPOUT(2),
        STUDENT(3),
        TEACHER(4);

        private int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }

    }

    private Long id;
    private Status status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
