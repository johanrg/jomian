package se.lexicon.jomian.controller.admin;

import org.primefaces.event.SelectEvent;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

    public void onRowSelect(SelectEvent event) {
        CurrentContext.redirect("/admin/editAccount.xhtml?accountId="
                + ((Account) event.getObject()).getId()
                + "&from=/admin/batchVerifyAccount");
    }

    public void verifyAccount() {
        for (Account account : selectedAccounts) {
            account.setVerified(true);
            accountService.update(account);
        }
    }

    public void deleteAccount(Account account) {
        accountService.delete(account);
        CurrentContext.redirect("/admin/batchVerifyAccount.xhtml");
    }

    public List<Account> getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<Account> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }
}
