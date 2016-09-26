package se.lexicon.jomian.resultclass;

import java.util.Date;

/**
 * @author Johan Gustafsson
 * @since 2016-09-26.
 */
public class TotalAttendanceForDay {
    private Date date;
    private Long attending;

    public TotalAttendanceForDay(Date date, Long attending) {
        this.date = date;
        this.attending = attending;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAttending() {
        return attending;
    }

    public void setAttending(Long attending) {
        this.attending = attending;
    }
}
