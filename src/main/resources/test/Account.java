package test;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Johan Gustafsson
 * @since 2016-09-19.
 */
@Entity
public class Account {
    private long id;
    private boolean admin;
    private Timestamp createdat;
    private String email;
    private String name;
    private String password;
    private boolean student;
    private boolean teacher;
    private Boolean verified;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "admin")
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Basic
    @Column(name = "createdat")
    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "student")
    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    @Basic
    @Column(name = "teacher")
    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "verified")
    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (admin != account.admin) return false;
        if (student != account.student) return false;
        if (teacher != account.teacher) return false;
        if (createdat != null ? !createdat.equals(account.createdat) : account.createdat != null) return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (name != null ? !name.equals(account.name) : account.name != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (verified != null ? !verified.equals(account.verified) : account.verified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (student ? 1 : 0);
        result = 31 * result + (teacher ? 1 : 0);
        result = 31 * result + (verified != null ? verified.hashCode() : 0);
        return result;
    }
}
