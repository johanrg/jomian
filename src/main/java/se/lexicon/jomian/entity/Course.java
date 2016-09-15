package se.lexicon.jomian.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Course table hold all the courses available at the school.
 *
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Course.FindByCourseName", query = "SELECT c FROM Course c WHERE c.name = :name"),
        @NamedQuery(name = "Course.FindLikeName", query = "SELECT c FROM Course c WHERE UPPER(c.name) LIKE UPPER(:name)")
})
public class Course {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<AccountCourse> accountCourses = new ArrayList<>();
    private List<CourseSession> courseSessions = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 80, nullable = false, unique = true)
    @Size(min = 1, max = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "TEXT")
    @Size(max = 4096)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true)
    public List<AccountCourse> getAccountCourses() {
        return accountCourses;
    }

    public void setAccountCourses(List<AccountCourse> accountCourses) {
        this.accountCourses = accountCourses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true)
    public List<CourseSession> getCourseSessions() {
        return courseSessions;
    }

    public void setCourseSessions(List<CourseSession> courseSessions) {
        this.courseSessions = courseSessions;
    }
}
