package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public List<Account> getUnverifiedAccounts() {
        return accountService.getUnverifiedAccounts();
    }

    public void verify(Account account) {
        accountService.verifyAccount(account);
    }

    public void delete(Account account) {
        accountService.deleteAccount(account);
    }

    public String formatTime(Timestamp timestamp) {
        LocalDateTime time = timestamp.toLocalDateTime();
        return String.format("%d-%02d-%02d", time.getYear(), time.getMonthValue(), time.getDayOfMonth());
    }
}
