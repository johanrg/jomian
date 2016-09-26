package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.News;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Stateless
public class NewsDAO extends AbstractDAO<News> {
    @PersistenceContext
    private EntityManager em;

    public NewsDAO() {
        super(News.class);
    }

    @Override
    EntityManager getEntityManager() {
        return em;
    }

    public List<News> getAllNews() {
        return em.createNamedQuery("News.GetAllNews", News.class)
                .getResultList();
    }
}
