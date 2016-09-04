package se.lexicon.jomian.manager;

import se.lexicon.jomian.entity.Course;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author johan
 * @since 2016-09-01.
 */
@Stateless
public class CourseManager {
    @Inject
    private EntityManager em;
}
