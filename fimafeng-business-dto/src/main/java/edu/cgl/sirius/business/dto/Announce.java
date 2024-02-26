package edu.cgl.sirius.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Announce {

    private Date publicationDate;
    private String status;
    private String type;
    private String title;
    private String description;
    private Date dateTimeStart;
    private float duration;
    private Date dateTimeEnd;
    private Boolean isRecurrent;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");

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

}
