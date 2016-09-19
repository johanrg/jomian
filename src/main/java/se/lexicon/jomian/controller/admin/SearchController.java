package se.lexicon.jomian.controller.admin;

import org.primefaces.event.SelectEvent;
import se.lexicon.jomian.dao.AccountDAO;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.util.CurrentContext;
import se.lexicon.jomian.util.Language;
import se.lexicon.jomian.util.SearchOption;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * @author Johan Gustafsson
 * @since 2016-09-14.
 */
@Named
@ConversationScoped
public class SearchController implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private AccountService accountService;
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private CourseService courseService;
    private String searchFor;
    private Map<String, SearchOption> searchOptions = new HashMap<>();
    private SearchOption searchOption = SearchOption.ACCOUNT;
    private List<Account> accounts = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private Account selectedAccount;

    @PostConstruct
    public void init() {
        searchOptions.put(Language.getMessage("search.optionAccount"), SearchOption.ACCOUNT);
        searchOptions.put(Language.getMessage("search.optionCourse"), SearchOption.COURSE);
    }

    public void initConversation() {
        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void onAccountRowSelect(SelectEvent event) {
        CurrentContext.redirect("/admin/editAccount.xhtml?from=/admin/search&accountId="
                + ((Account) event.getObject()).getId());
    }

    public void onCourseRowSelect(SelectEvent event) {
        CurrentContext.redirect("/admin/editCourse.xhtml?from=/admin/search&courseId="
                + ((Course) event.getObject()).getId());
    }

    public String search() {
        if (searchOption == SearchOption.ACCOUNT) {
            accounts = accountService.getAccountsLike(searchFor);
            courses.clear();
        } else {
            courses = courseService.getCoursesLike(searchFor);
            accounts.clear();
        }
        return "/admin/search.xhtml?faces-redirect=true";
    }

    public String deleteAccount(Account account) {
        accountDAO.remove(account);
        search();
        return "/admin/search.xhtml?faces-redirect=true";
    }

    public String deleteCourse(Course course) {
        courseDAO.remove(course);
        search();
        return "/admin/search.xhtml?faces-redirect=true";
    }

    public List<String> getAutocompleteList(String query) {
        List<String> result = new ArrayList<>();
        if (searchOption == SearchOption.ACCOUNT) {
            accountService.getAccountNamesLike(query);
        } else if (searchOption == SearchOption.COURSE) {
            courseService.getCourseNamesLike(query);
        }
        return result;
    }

    public void changeListener(ValueChangeEvent event) {
        searchOption = (SearchOption) event.getNewValue();
    }

    public void onSearchOptionChange() {
        System.out.println(searchOption);
    }

    public Map<String, SearchOption> getSearchOptions() {
        return searchOptions;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public SearchOption getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(SearchOption searchOption) {
        this.searchOption = searchOption;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
}
