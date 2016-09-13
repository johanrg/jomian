package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Johan Gustafsson
 * @since 2016-09-13.
 */
@Named
@RequestScoped
public class EditAccountController implements Serializable {
    @Inject
    private AccountService accountService;
    private Long accountId;
    private Account account = new Account();

    public String editAccount() {
        accountService.editAccount(account);
        return "/admin/batchVerifyAccount?faces-redirect=true";
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        account = accountService.findById(accountId);
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
