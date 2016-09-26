package se.lexicon.jomian.controller.admin;

import org.primefaces.event.SelectEvent;
import se.lexicon.jomian.dao.NewsDAO;
import se.lexicon.jomian.entity.News;
import se.lexicon.jomian.util.CurrentContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Named
@RequestScoped
public class ManageNewsController {
    @Inject
    private NewsDAO newsDAO;

    public List<News> getNews() {
        return newsDAO.getAllNews();
    }

    public void deleteNews(News news) {
        newsDAO.remove(news);
        CurrentContext.redirect("/admin/manageNews.xhtml");
    }

    public void onRowSelect(SelectEvent event) {
        CurrentContext.redirect("/admin/editNews.xhtml?newsId="
                + ((News) event.getObject()).getId());
    }
}
