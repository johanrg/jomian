package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.dao.NewsDAO;
import se.lexicon.jomian.entity.News;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Named
@RequestScoped
public class addNewsController {
    @Inject
    private NewsDAO newsDAO;
    private News news = new News();

    public String createNews() {
        news.setDate(new Date());
        newsDAO.persist(news);
        return "/admin/manageNews";
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
