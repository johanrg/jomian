package se.lexicon.jomian.service;

import org.mindrot.jbcrypt.BCrypt;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.util.Language;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class AccountService implements Serializable {
    @Inject
    private AccountDAO accountDAO;

    public void create(Account account) throws ServiceException {
        if (accountDAO.findByEmail(account.getEmail()) != null) {
            throw new ServiceException(Language.getMessage("account.emailInUse"));
        }

        account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12)));

        if (accountDAO.count() == 0) {
            account.setVerified(true);
            account.setAdmin(true);
        } else {
            account.setVerified(false);
        }
        accountDAO.persist(account);
    }

    public Account returnAccountWithMatchingCredentials(String email, String password) throws ServiceException {
        Account dbAccount = accountDAO.findByEmail(email);
        if (dbAccount == null) {
            throw new ServiceException(Language.getMessage("account.invalidAccount"));
        }
        if (!dbAccount.isVerified()) {
            throw new ServiceException(Language.getMessage("account.notVerified"));
        } else if (BCrypt.checkpw(password, dbAccount.getPassword())) {
            return dbAccount;
        }
        return null;
    }

    public List<String> getAccountNamesLike(String query) {
        List<Account> accounts = accountDAO.findLikeName(query);
        return accounts.stream().map(Account::getName).collect(Collectors.toList());
    }

    public List<Account> getAccountsLike(String query) {
        if (query == null || query.equals("")) {
            return accountDAO.getAll();
        } else {
            return accountDAO.findLikeName(query);
        }

    }
}
