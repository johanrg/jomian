package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-09.
 */
@Named
@RequestScoped
public class VerifyAccount {
    @Inject
    private AccountService accountService;

    public List<Account> getUnverifiedAccounts() {
        return accountService.getUnverifiedAccounts();
    }
}
