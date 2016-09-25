package se.lexicon.jomian.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Every unique lesson gets a new CourseSession entry in this table.
 *
 * @author Johan Gustafsson
 * @since 2016-09-04.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "CourseSession.FindScheduleForCourse",
                query = "SELECT c FROM CourseSession c WHERE c.course.id = :courseId")
})
public class CourseSession {
    private Long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private boolean isAllDay;
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

    @Column(length = 120, nullable = false)
    @Size(min = 1, max = 120)
    public String getTitle() {
        return title;
    }

   public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
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
    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof CourseSession && getId().equals(((CourseSession)o).getId());
    }
}
