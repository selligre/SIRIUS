package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Announce {

    private String announce_id; 
    private String ref_author_id;
    private String publication_date;
    private String status;
    private String type;
    private String title;
    private String description;
    private String date_time_start;
    private String duration;
    private String date_time_end ;
    private String is_recurrent;

    public Announce() {
    }
    public final Announce build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "announce_id", "ref_author_id", "publication_date", "status", "type", "title",
                            "description", "date_time_start", "duration", "date_time_end", "is_recurrent");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, announce_id, ref_author_id, publication_date, status, type, title, description, date_time_start, duration, date_time_end, is_recurrent);
    }
    public Announce(String announce_id, String ref_author_id, String publication_date, String status, String type, String title, String description, String date_time_start, String duration, String date_time_end, String is_recurrent) {
        this.announce_id = announce_id;
        this.ref_author_id = ref_author_id;
        this.publication_date = publication_date;
        this.status = status;
        this.type = type;
        this.title = title;
        this.description = description;
        this.date_time_start = date_time_start;
        this.duration = duration;
        this.date_time_end = date_time_end;
        this.is_recurrent = is_recurrent;
    }

    public String getAnnounce_id() {
        return announce_id;
    }

    public String getRef_author_id() {
        return ref_author_id;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_time_start() {
        return date_time_start;
    }

    public String getDuration() {
        return duration;
    }

    public String getDate_time_end() {
        return date_time_end;
    }

    public String getIs_recurrent() {
        return is_recurrent;
    }

    @JsonProperty("announce_id")
    public void setAnnounce_id(String announce_id) {
        this.announce_id = announce_id;
    }

    @JsonProperty("ref_author_id")
    public void setRef_author_id(String ref_author_id) {
        this.ref_author_id = ref_author_id;
    }

    @JsonProperty("publication_date")
    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("date_time_start")
    public void setDate_time_start(String date_time_start) {
        this.date_time_start = date_time_start;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("date_time_end")
    public void setDate_time_end(String date_time_end) {
        this.date_time_end = date_time_end;
    }

    @JsonProperty("is_recurrent")
    public void setIs_recurrent(String is_recurrent) {
        this.is_recurrent = is_recurrent;
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName : fieldNames ) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            if (resultSet.getObject(fieldName) instanceof String){
                field.set(this, resultSet.getObject(fieldName));
            }
            else{
                field.set(this, resultSet.getObject(fieldName).toString());
            }
        }
    }
    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for(final String fieldName : fieldNames ) {
            preparedStatement.setString(++ix, fieldName);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Announce{" +
                "announce_id='" + announce_id + '\'' +
                ", ref_author_id='" + ref_author_id + '\'' +
                ", publication_date='" + publication_date + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_time_start='" + date_time_start + '\'' +
                ", duration='" + duration + '\'' +
                ", date_time_end='" + date_time_end + '\'' +
                ", is_recurrent='" + is_recurrent + '\'' +
                '}';
    }
}
