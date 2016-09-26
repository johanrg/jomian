package se.lexicon.jomian.controller.admin;

import se.lexicon.jomian.dao.NewsDAO;
import se.lexicon.jomian.entity.News;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Named
@ViewScoped
public class EditNewsController implements Serializable {
    @Inject
    private NewsDAO newsDAO;
    private Long newsId;
    private News news = new News();

    public String editNews() {
        newsDAO.merge(news);
        return "/admin/manageNews";
    }

    public void initView() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (newsId == null || newsId == 0) {
            CurrentContext.redirect404();
            return;
        }

        news = newsDAO.find(newsId);
        if (news == null) {
            CurrentContext.redirect404();
        }
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
