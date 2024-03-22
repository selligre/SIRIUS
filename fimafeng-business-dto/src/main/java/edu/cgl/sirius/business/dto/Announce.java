package edu.cgl.sirius.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Announce {

    protected Date publicationDate;
    protected String status;
    protected String type;
    protected String title;
    protected String description;
    protected Date dateTimeStart;
    protected float duration;
    protected Date dateTimeEnd;
    protected Boolean isRecurrent;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Announce(String status, String type, String title, String desc, Date start, float duration, Date end,
            Boolean recc) {
        this.publicationDate = new Date();
        this.status = status;
        this.type = type;
        this.title = title;
        this.description = desc;
        this.dateTimeStart = start;
        this.duration = duration;
        this.dateTimeEnd = end;
        this.isRecurrent = recc;
    }

    @Override
    public String toString() {
        return "pD: " + dateFormat.format(this.publicationDate) + ", st: " + this.status + ", tp: " + this.type
                + ", tt: " + this.title + ", d: "
                + this.description + ", sD: " + dateFormat.format(this.dateTimeStart) + ", d: " + this.duration
                + ", eD: "
                + dateFormat.format(dateTimeEnd) + ", r:" + this.isRecurrent;
    }

    protected String getPublicationDateString() {
        return dateFormat.format(this.publicationDate);
    }

    protected String getDateTimeStartString() {
        return dateFormat.format(dateTimeStart);
    }

    protected String getDateTimeEndString() {
        return dateFormat.format(dateTimeEnd);
    }
}
