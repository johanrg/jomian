package se.lexicon.jomian.resultclass;

/**
 * @author Johan Gustafsson
 * @since 2016-09-21.
 */
public class AccountAndCourse {
    private Long accountCourseId;
    private String accountName;
    private String accountEmail;
    private String courseName;

    public AccountAndCourse(Long accountCourseId, String accountName, String accountEmail, String courseName) {
        this.accountCourseId = accountCourseId;
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.courseName = courseName;
    }

    public Long getAccountCourseId() {
        return accountCourseId;
    }

    public void setAccountCourseId(Long accountCourseId) {
        this.accountCourseId = accountCourseId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
