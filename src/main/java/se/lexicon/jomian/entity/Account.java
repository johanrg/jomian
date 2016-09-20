package se.lexicon.jomian.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Account table holds all students, teachers and admins in one single table. Differentiated by 3 boolean values.
 * We use boolean here so we can at least combine the admin and teacher roll to one person if that makes sense in
 * the school.
 *
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Account.FindByIdAndPass", query = "SELECT a FROM Account a WHERE a.id = :id AND a.password = :password"),
        @NamedQuery(name = "Account.FindByName", query = "SELECT a FROM Account a WHERE a.name = :name"),
        @NamedQuery(name = "Account.FindLikeName", query = "SELECT a FROM Account a WHERE UPPER(a.name) LIKE UPPER(:name)"),
        @NamedQuery(name = "Account.FindByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
        @NamedQuery(name = "Account.FindAllTeachers", query = "SELECT a FROM Account a WHERE a.teacher = true"),
        @NamedQuery(name = "Account.FindUnverifiedAccounts", query = "SELECT a FrOM Account a WHERE a.verified = false"),
})
public class Account {
    private Long id;
    private String name;
    private String password;
    private String email;
    private boolean verified;
    private boolean student;
    private boolean teacher;
    private boolean admin;
    public Date createdAt;
    private List<AccountCourse> accountCourses = new ArrayList<>();
    private List<Attendance> attendances = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 80, nullable = false)
    @Size(min = 1, max = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 60, nullable = false)
    @Size(min = 6, max = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(length = 255, nullable = false, unique = true)
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^.+@.+$", message = "{email.invalid}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Column(nullable = false)
    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    @Column(nullable = false)
    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    @Column(nullable = false)
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true)
    public List<AccountCourse> getAccountCourses() {
        return accountCourses;
    }

    public void setAccountCourses(List<AccountCourse> accountCourses) {
        this.accountCourses = accountCourses;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true)
    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
