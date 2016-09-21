package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.AccountCourse;
import se.lexicon.jomian.resultclass.AccountAndCourse;
import se.lexicon.jomian.service.AccountCourseService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-21.
 */
@Named
@RequestScoped
public class ApproveStudentsToCoursesController {
    @Inject
    private AccountCourseService accountCourseService;
    @Inject
    private CourseDAO courseDAO;
    private List<AccountAndCourse> selectedAccounts;

    public String approveStudents() {
        selectedAccounts.forEach(a -> {
            accountCourseService.changeRole(a.getAccountCourseId(), AccountCourse.Role.STUDENT);
        });
        return "/admin/approveStudentsToCourses";
    }

    public void denyCourse(AccountAndCourse accountAndCourse) {
        accountCourseService.removeAccountCourse(accountAndCourse.getAccountCourseId());
        CurrentContext.redirect("/admin/approveStudentsToCourses.xhtml");
    }

    public List<AccountAndCourse> getAllStudentApplications() {
        return courseDAO.findAllStudentApplications();
    }

    public List<AccountAndCourse> getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<AccountAndCourse> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }
}
