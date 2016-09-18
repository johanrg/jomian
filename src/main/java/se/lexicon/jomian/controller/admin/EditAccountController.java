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
        if (accountId == null) {
            CurrentContext.redirect404();
            return;
        }

        account = accountService.findById(accountId);
        if (account == null) {
            CurrentContext.redirect404();
            return;
        }

        if (from == null || from.equals("")) {
            from = "/restricted";
        }

        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String editAccount() {
        accountService.update(account);
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
