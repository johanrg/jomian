package se.lexicon.jomian.dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-21.
 */
public abstract class AbstractDAO<T> {
    private final Class<T> entityClass;

    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    abstract EntityManager getEntityManager();

    public void persist(T entity) {
        getEntityManager().persist(entity);
    }

    public void merge(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void detach(T entity) {
        getEntityManager().detach(entity);
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public void refresh(Object id) {
        T entity = getEntityManager().find(entityClass, id);
        getEntityManager().refresh(entity);
    }

    public List<T> getAll() {
        CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public long count() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(entityClass)));
        return getEntityManager().createQuery(query).getSingleResult();
    }
}
