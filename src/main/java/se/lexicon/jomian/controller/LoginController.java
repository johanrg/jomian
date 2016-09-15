package se.lexicon.jomian.controller;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Johan Gustafsson
 * @since 2016-09-08.
 */
@Named
@SessionScoped
public class LoginController implements Serializable {
    @Inject
    private AccountService accountService;
    private Account account = new Account();
    private Account loggedInAccount;
    private final String USER_SESSION_ID = "user.session.id";
    private final String USER_SESSION_HASH = "user.session.hash";

    public String login() {
        try {
            loggedInAccount = accountService.loginAccount(account);
            CurrentContext.getSessionMap().put(USER_SESSION_ID, loggedInAccount.getId());
            CurrentContext.getSessionMap().put(USER_SESSION_HASH, loggedInAccount.getPassword());
            return "/restricted/index.xhtml?faces-redirect=true";
        } catch (ServiceException e) {
            CurrentContext.message("loginForm", e.getMessage());
        }
        return null;
    }

    public void logout() {
        loggedInAccount = null;
        CurrentContext.getSessionMap().remove(USER_SESSION_ID);
        CurrentContext.getSessionMap().remove(USER_SESSION_HASH);
        CurrentContext.redirect("/login.xhtml");
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public boolean isLoggedIn() {
        if (loggedInAccount != null) {
            return true;
        } else {
            Long id = (Long) CurrentContext.getSessionMap().get(USER_SESSION_ID);
            String password = (String) CurrentContext.getSessionMap().get(USER_SESSION_HASH);
            if (id == null || password == null) {
                return false;
            } else {
                loggedInAccount = accountService.findByIdAndPass(id, password);
                if (loggedInAccount != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
