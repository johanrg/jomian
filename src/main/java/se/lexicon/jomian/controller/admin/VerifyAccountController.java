package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-09.
 */
@Named
@RequestScoped
public class VerifyAccountController {
    @Inject
    private AccountService accountService;
    private List<Account> selectedAccounts = new ArrayList<>();

    public List<Account> getUnverifiedAccounts() {
        return accountService.getUnverifiedAccounts();
    }

    public void verify() {
        for(Account account : selectedAccounts) {
            accountService.verifyAccount(account);
        }
    }

    public void delete(Account account) throws IOException {
        accountService.deleteAccount(account);
        CurrentContext.redirect("/admin/batchVerifyAccount.xhtml");
    }

    public List<Account> getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<Account> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }
}
