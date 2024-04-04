package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnounceLocation {

    private String announce_id; 
    private String title;
    private String location_id;
    private String name;


    public AnnounceLocation() {
    }
    public final AnnounceLocation build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "announce_id", "title", "location_id", "name");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, announce_id, title, location_id, name);
    }
    public AnnounceLocation(String announce_id, String title, String location_id, String name) {
        this.announce_id = announce_id;
        this.title = title;
        this.location_id = location_id;
        this.name = name;
    }

    public String getAnnounce_id() {
        return announce_id;
    }

    public String getTitle() {
        return title;
    }
    
    public String getLocation_id() {
        return location_id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("announce_id")
    public void setAnnounce_id(String announce_id) {
        this.announce_id = announce_id;
    }

    @JsonProperty("title")
    public void setTitle (String title) {
        this.title = title;
    }

    @JsonProperty("location_id")
    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
        return "AnnounceLocation{" +
                "announce_id='" + announce_id + '\'' +
                "title='" + title + '\'' +
                "location_id='" + location_id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
