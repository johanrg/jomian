package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.resultclass.AccountAndCourse;

import javax.faces.bean.RequestScoped;
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
    private CourseDAO courseDAO;
    private List<AccountAndCourse> selectedAccounts;

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
