package se.lexicon.jomian.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * @author johan
 * @since 2016-09-01.
 */
@Entity
@NamedQuery(name = "Account.FindByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
public class Account {
    private Long id;
    private String name;
    private String password;
    private String email;
    private AccountType accountType;
    private char testOfChar;
    private List<AccountCourse> accountCourses = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 80, nullable = false)
    @Size(min = 1, max = 80, message = "{name.size}")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 80, nullable = false)
    @Size(min = 6, max = 80, message = "{password.size}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(length = 255, nullable = false)
    @Size(min = 3, max = 255, message = "{email.size}")
    @Pattern(regexp = "^.+@.+$", message = "{email.invalid}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated(EnumType.ORDINAL)
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public char getTestOfChar() {
        return testOfChar;
    }

    public void setTestOfChar(char testOfChar) {
        this.testOfChar = testOfChar;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true)
    public List<AccountCourse> getAccountCourses() {
        return accountCourses;
    }

    public void setAccountCourses(List<AccountCourse> accountCourses) {
        this.accountCourses = accountCourses;
    }
}
