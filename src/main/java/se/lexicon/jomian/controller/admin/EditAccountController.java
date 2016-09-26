package se.lexicon.jomian.controller.admin;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleModel;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Attendance;
import se.lexicon.jomian.entity.CourseSession;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.AttendanceService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-13.
 */
@Named
@ConversationScoped
public class EditAccountController implements Serializable {
    @Inject
    Conversation conversation;
    @Inject
    private AccountService accountService;
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private AttendanceDAO attendanceDAO;
    @Inject
    private AttendanceService attendanceService;
    private Account account = new Account();
    private Long accountId;
    private String from;
    private ScheduleModel eventModel;

    public void initView() {
        if (accountId == null) {
            CurrentContext.redirect404();
            return;
        }

        account = accountDAO.find(accountId);
        if (account == null) {
            CurrentContext.redirect404();
            return;
        }

        if (from == null || from.equals("")) {
            from = "/restricted";
        }

        eventModel = attendanceService.populateScheduleModel(accountId);
        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String editAccount() {
        accountDAO.merge(account);
        conversation.end();
        return from + "?faces-redirect=true";
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
}
