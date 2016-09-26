package se.lexicon.jomian.dao;

import se.lexicon.jomian.entity.Attendance;
import se.lexicon.jomian.resultclass.TotalAttendanceForDay;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-25.
 */
@Stateless
public class AttendanceDAO extends AbstractDAO<Attendance> implements Serializable{
    @PersistenceContext
    private EntityManager em;

    public AttendanceDAO() {
        super(Attendance.class);
    }

    @Override
    EntityManager getEntityManager() {
        return em;
    }

    public List<Attendance> findAttendanceUntilToday(Long accountId) {
        return em.createNamedQuery("Attendance.FindAttendanceUntilToday", Attendance.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    public List<TotalAttendanceForDay> findAttendanceForAllCoursesBetween(Date startDate, Date endDate) {
        return em.createNamedQuery("Attendance.FindAttendanceForAllCoursesBetween", TotalAttendanceForDay.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<TotalAttendanceForDay> findPotentialAttendanceForAllCoursesBetween(Date startDate, Date endDate) {
        return em.createNamedQuery("Attendance.FindPotentialAttendanceForAllCoursesBetween", TotalAttendanceForDay.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
