package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.entity.Account;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.service.AccountService;
import se.lexicon.jomian.service.CourseService;
import se.lexicon.jomian.util.SearchOption;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Johan Gustafsson
 * @since 2016-09-14.
 */
@Named
@SessionScoped
public class SearchController implements Serializable {
    @Inject
    AccountService accountService;
    @Inject
    CourseService courseService;
    private String searchFor;
    private Map<String, SearchOption> searchOptions = new HashMap<>();
    private SearchOption searchOption = SearchOption.ACCOUNT;
    private List<Account> accounts = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private boolean keepSearch;

    @PostConstruct
    public void init() {
        searchOptions.put("Account", SearchOption.ACCOUNT);
        searchOptions.put("Course", SearchOption.COURSE);
    }

    public void clear() {
        if (!keepSearch) {
            searchOption = SearchOption.ACCOUNT;
            searchFor = "";
            accounts.clear();
            courses.clear();
        }
        keepSearch = false;
    }

    public String search() {
        if (searchOption == SearchOption.ACCOUNT) {
            accounts = accountService.findLikeName(searchFor);
            courses.clear();
        } else {
            courses = courseService.findLikeName(searchFor);
            accounts.clear();
        }
        return "/admin/search.xhtml?faces-redirect=true&keepSearch=true";
    }

    public List<String> getAutocompleteList(String query) {
        List<String> result = new ArrayList<>();
        if (searchOption == SearchOption.ACCOUNT) {
            List<Account> accounts = accountService.findLikeName(query);
            for (Account account : accounts) {
                result.add(account.getName());
            }
        } else if (searchOption == SearchOption.COURSE) {
            List<Course> courses = courseService.findLikeName(query);
            for (Course course : courses) {
                result.add(course.getName());
            }
        } else {
            result.add("Select search option");
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

    public boolean isKeepSearch() {
        return keepSearch;
    }

    public void setKeepSearch(boolean keepSearch) {
        this.keepSearch = keepSearch;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
