package fimafeng.back.proto_back.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "announce")
public class Announce {

    @Id
    @Column(name="announce_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnnounce;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date_time_start")
    private Date dateTimeStart;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "date_time_end")
    private Date dateTimeEnd;

    @Column(name = "is_recurrent")
    private Boolean isRecurrent;


    // Getters and setters
    public Long getIdAnnounce() {
        return idAnnounce;
    }

    public void setIdAnnounce(Long idAnnounce) {
        this.idAnnounce = idAnnounce;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(Date dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Date getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(Date dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public Boolean getIsRecurrent() {
        return isRecurrent;
    }

    public void setIsRecurrent(Boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
    }

    

}