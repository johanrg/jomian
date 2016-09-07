package se.lexicon.jomian.controller;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.manager.AccountManager;
import se.lexicon.jomian.manager.ManagerException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author johan
 * @since 2016-09-01.
 */
@Named
@RequestScoped
public class RegisterController {
    @Inject
    private AccountManager accountManager;
    private Account account = new Account();

    /**
     * Bound to the form commandButton on the registration page, adds a new account to the system.
     */
    public void submit() {
        try {
            accountManager.createAccount(account);
        } catch (ManagerException e) {
            FacesContext.getCurrentInstance()
                    .addMessage("registerForm:email", new FacesMessage(e.getMessage()));
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
