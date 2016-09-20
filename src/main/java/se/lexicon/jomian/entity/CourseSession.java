package se.lexicon.jomian.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Every unique lesson gets a new CourseSession entry in this table.
 *
 * @author Johan Gustafsson
 * @since 2016-09-04.
 */
@Entity
public class CourseSession {
    private Long id;
    private Date createdAt;
    private Course course;
    private List<Attendance> attendances = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "courseSession", orphanRemoval = true)
    private List<Attendance> getAttendances() {
        return attendances;
    }

    private void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
