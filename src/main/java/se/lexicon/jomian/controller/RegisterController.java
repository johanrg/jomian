package se.lexicon.jomian.controller;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Named
@RequestScoped
public class RegisterController {
    @Inject
    private AccountService accountService;
    private Account account = new Account();

    /**
     * Bound to the form commandButton on the registration page, adds a new account to the system.
     */
    public String submit() {
        try {
            accountService.createAccount(account);

            if (account.isAdmin()) {
                return String.format("welcomeAdmin?faces-redirect=true&username=%s", account.getName());
            }
            return String.format("thankyou?faces-redirect=true&username=%s", account.getName());
        } catch (ServiceException e) {
            CurrentContext.message("registerForm:email", e.getMessage());
        }
        return null;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
