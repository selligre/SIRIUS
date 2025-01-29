package fimafeng.back.fimafeng_back.models;

import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.AnnounceType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "announce")
public class Announce {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AnnounceStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AnnounceType type;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "date_time_start")
    private Date dateTimeStart;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "date_time_end")
    private Date dateTimeEnd;

    @Column(name = "is_recurrent")
    private Boolean isRecurrent;

    @Column(name = "ref_author_id")
    private int authorId;

    @Column(name = "ref_district_id")
    private int refDistrictId;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public AnnounceStatus getStatus() {
        return status;
    }

    public void setStatus(AnnounceStatus status) {
        this.status = status;
    }

    public AnnounceType getType() {
        return type;
    }

    public void setType(AnnounceType type) {
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

    public void setIsRecurrent(Boolean recurrent) {
        isRecurrent = recurrent;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getRefDistrictId() {
        return refDistrictId;
    }

    public void setRefDistrictId(int refDistrictId) {
        this.refDistrictId = refDistrictId;
    }

    @Override
    public String toString() {
        String res = "Announce[";
        res += "id: " + id + ", ";
        res += "publicationDate: " + publicationDate + ", ";
        res += "status: " + status + ", ";
        res += "type: " + type + ", ";
        res += "title: " + title + ", ";
        res += "description: " + description + ", ";
        res += "dateTimeStart: " + dateTimeStart + ", ";
        res += "duration: " + duration + ", ";
        res += "dateTimeEnd: " + dateTimeEnd + ", ";
        res += "isRecurrent: " + isRecurrent + ", ";
        res += "authorId: " + authorId + ", ";
        res += "refDistrictId: " + refDistrictId + "]";
        return res;
    }
}