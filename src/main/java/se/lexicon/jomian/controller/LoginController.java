package se.lexicon.jomian.controller;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.ServiceException;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private Account account = new Account();
    private boolean isLoggedIn = false;

    public String submit() {
        try {
            account = accountService.loginAccount(account);
            isLoggedIn = true;
            return "faces/restricted/index.xhtml?faces-redirect=true";
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(e.getMessage()));
        }
        return null;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
