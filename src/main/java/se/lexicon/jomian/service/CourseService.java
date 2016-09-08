package se.lexicon.jomian.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
@Stateless
public class CourseService {
    @PersistenceContext
    private EntityManager em;
}
