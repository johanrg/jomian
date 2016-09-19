package se.lexicon.jomian.controller;

import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.ServiceException;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    @Inject
    private AccountDAO accountDAO;
    private Account account = new Account();
    private Account loggedInAccount;
    private final String USER_SESSION_ID = "user.session.id";
    private final String USER_SESSION_HASH = "user.session.hash";
    private boolean isLoggedIn = false;

    public String login() {
        try {
            loggedInAccount = accountService.returnAccountWithMatchingCredentials(account.getEmail(), account.getPassword());
            isLoggedIn = true;
            CurrentContext.getSessionMap().put(USER_SESSION_ID, loggedInAccount.getId());
            CurrentContext.getSessionMap().put(USER_SESSION_HASH, loggedInAccount.getPassword());
            return "/restricted/index.xhtml?faces-redirect=true";
        } catch (ServiceException e) {
            CurrentContext.message("loginForm", e.getMessage());
        }
        return null;
    }

    public void logout() {
        isLoggedIn = false;
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
        if (isLoggedIn) {
            return true;
        } else {
            tryToLogIn();
        }
        return false;
    }

    private void tryToLogIn() {
        Long id = (Long) CurrentContext.getSessionMap().get(USER_SESSION_ID);
        String password = (String) CurrentContext.getSessionMap().get(USER_SESSION_HASH);
        if (id == null || password == null) {
            return;
        }

        loggedInAccount = accountDAO.findByIdAndPass(id, password);
        isLoggedIn = loggedInAccount != null;
    }
}
