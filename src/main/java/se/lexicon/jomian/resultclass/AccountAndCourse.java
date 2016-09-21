package se.lexicon.jomian.resultclass;

/**
 * @author Johan Gustafsson
 * @since 2016-09-21.
 */
public class AccountAndCourse {
    private Long accountAndCourseId;
    private String accountName;
    private String courseName;

    public AccountAndCourse(Long accountAndCourseId, String accountName, String courseName) {
        this.accountAndCourseId = accountAndCourseId;
        this.accountName = accountName;
        this.courseName = courseName;
    }

    public Long getAccountAndCourseId() {
        return accountAndCourseId;
    }

    public void setAccountAndCourseId(Long accountAndCourseId) {
        this.accountAndCourseId = accountAndCourseId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
