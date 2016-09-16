package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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
    private Account account = new Account();
    private Long accountId;
    private String from;

    public void initView() {
        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
        if (accountId == null || accountId == 0) {
            CurrentContext.redirect("/WEB-INF/errorpage/404.xhtml");
        } else {
            account = accountService.findById(accountId);
            if (from == null || from.equals("")) {
                from = "/restricted";
            }
        }
    }

    public String editAccount() {
        try {
            accountService.editAccount(account);
        } catch (ServiceException e) {
            CurrentContext.message("editAccountForm:messages", e.getMessage());
        }
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
}
