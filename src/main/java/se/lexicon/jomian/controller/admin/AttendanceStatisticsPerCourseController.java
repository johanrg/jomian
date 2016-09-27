package se.lexicon.jomian.controller.admin;

import org.primefaces.model.chart.PieChartModel;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.dao.CourseDAO;
import se.lexicon.jomian.entity.Course;
import se.lexicon.jomian.util.Language;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Johan Gustafsson
 * @since 2016-09-27.
 */
@Named
@ViewScoped
public class AttendanceStatisticsPerCourseController implements Serializable {
    @Inject
    private AttendanceDAO attendanceDAO;
    @Inject
    private CourseDAO courseDAO;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private PieChartModel statisticsModel;
    private Map<String, Long> mapOfCourses;
    private Long courseId;

    @PostConstruct
    public void init() {
        createMapOfCourses();
        createStatisticsModel();
    }

    private void createMapOfCourses() {
        mapOfCourses = new HashMap<>();
        courseDAO.getAll().forEach(c -> {
           mapOfCourses.put(c.getName(), c.getId());
        });
    }

    private void createStatisticsModel() {
        statisticsModel = new PieChartModel();
        Long present = attendanceDAO.findAttendanceForCourseBetween(courseId, true, startDate, endDate);
        Long absent = attendanceDAO.findAttendanceForCourseBetween(courseId, false, startDate, endDate);
        statisticsModel.set(Language.getFormatedMessage("statistics.course.present"), present);
        statisticsModel.set(Language.getFormatedMessage("statistics.course.absent"), absent);
        statisticsModel.setTitle(Language.getMessage("statistics.course.header"));
        statisticsModel.setLegendPosition("w");
    }

    public void update() {
        createStatisticsModel();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PieChartModel getStatisticsModel() {
        return statisticsModel;
    }

    public void setStatisticsModel(PieChartModel statisticsModel) {
        this.statisticsModel = statisticsModel;
    }

    public Map<String, Long> getMapOfCourses() {
        return mapOfCourses;
    }

    public void setMapOfCourses(Map<String, Long> mapOfCourses) {
        this.mapOfCourses = mapOfCourses;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
