package se.lexicon.jomian.entity;

import javax.annotation.Generated;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Entity
@NamedQuery(name = "News.GetAllNews", query = "SELECT n FROM News n ORDER BY n.date DESC")
public class News {
    private Long id;
    private Date date;
    private String title;
    private String body;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(length = 120, nullable = false)
    @Size(min = 1, max = 120)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "TEXT", nullable = false)
    @Size(min = 1, max = 4096)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
