package se.lexicon.jomian.controller.admin;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.resultclass.TotalAttendanceForDay;
import se.lexicon.jomian.util.Language;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
@Named
@ViewScoped
public class AttendanceStatisticsController implements Serializable {
    @Inject
    private AttendanceDAO attendanceDAO;
    private LineChartModel dateModel = new LineChartModel();
    private Date startDate = new Date();
    private Date endDate = new Date();

    @PostConstruct
    public void init() {
        createDateModel();
    }

    private void createDateModel() {
        dateModel = new LineChartModel();

        List<TotalAttendanceForDay> potentialList = attendanceDAO.findPotentialAttendanceForAllCoursesBetween(startDate, endDate);
        LineChartSeries potential = new LineChartSeries();
        potential.setLabel("Potential");
        for (TotalAttendanceForDay p : potentialList) {
            potential.set(new SimpleDateFormat("yyyy-MM-dd").format(p.getDate()), p.getAttending());
        }

        List<TotalAttendanceForDay> attendanceList = attendanceDAO.findAttendanceForAllCoursesBetween(startDate, endDate);
        LineChartSeries attending = new LineChartSeries();
        attending.setLabel("Attending");
        for (TotalAttendanceForDay a : attendanceList) {
            attending.set(new SimpleDateFormat("yyyy-MM-dd").format(a.getDate()), a.getAttending());
        }

        dateModel.addSeries(potential);
        dateModel.addSeries(attending);

        dateModel.setTitle(Language.getMessage("statistics.header"));
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickFormat("%b %#d, %y");

        dateModel.getAxes().put(AxisType.X, axis);
    }

    public void update() {
        createDateModel();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(LineChartModel dateModel) {
        this.dateModel = dateModel;
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
}
