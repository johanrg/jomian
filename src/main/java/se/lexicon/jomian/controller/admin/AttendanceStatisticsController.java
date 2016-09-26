package se.lexicon.jomian.controller.admin;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import se.lexicon.jomian.dao.AttendanceDAO;
import se.lexicon.jomian.resultclass.TotalAttendanceForDay;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
        createDateModel1();
    }

    public void initView() {
        //createDateModel();
    }

    private String createDateModel() {
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set("2014-01-01", 51);
        series1.set("2014-01-06", 22);
        series1.set("2014-01-12", 65);
        series1.set("2014-01-18", 74);
        series1.set("2014-01-24", 24);
        series1.set("2014-01-30", 51);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");

        series2.set("2014-01-01", 32);
        series2.set("2014-01-06", 73);
        series2.set("2014-01-12", 24);
        series2.set("2014-01-18", 12);
        series2.set("2014-01-24", 74);
        series2.set("2014-01-30", 62);

        dateModel.addSeries(series1);
        dateModel.addSeries(series2);

        dateModel.setTitle("Zoom for Details");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");

        dateModel.getAxes().put(AxisType.X, axis);
        return "/admin/attendanceStatistics";
    }

    private void createDateModel1() {
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

        dateModel.setTitle("Zoom for Details");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
//        axis.setTickAngle(-10);
        //axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");

        dateModel.getAxes().put(AxisType.X, axis);
    }

    public void update() {
        createDateModel1();
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
