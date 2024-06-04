package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLocation {

    private String user_location_id;
    private String ref_user_id;
    private String ref_location_id;

    public UserLocation() {
    }

    public UserLocation(String ref_user_id, String ref_location_id) {
        this.ref_user_id = ref_user_id;
        this.ref_location_id = ref_location_id;
    }

    public UserLocation(String user_location_id, String ref_user_id, String ref_location_id) {
        this.user_location_id = user_location_id;
        this.ref_user_id = ref_user_id;
        this.ref_location_id = ref_location_id;
    }

    public String getUser_location_id() {
        return this.user_location_id;
    }

    @JsonProperty("user_location_id")
    public void setUser_location_id(String location_id) {
        this.user_location_id = location_id;
    }

    public String getRef_user_id() {
        return this.ref_user_id;
    }

    @JsonProperty("ref_user_id")
    public void setRef_user_id(String name) {
        this.ref_user_id = name;
    }

    public String getRef_location_id() {
        return this.ref_location_id;
    }

    @JsonProperty("ref_location_id")
    public void setRef_location_id(String categorie) {
        this.ref_location_id = categorie;
    }

    @Override
    public String toString() {
        return "N: " + this.ref_user_id + ", c: " + this.ref_location_id;
    }

    public final UserLocation build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "user_location_id", "ref_user_id", "ref_location_id");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, user_location_id, ref_user_id, ref_location_id);
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement,
            final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final String fieldName : fieldNames) {
            preparedStatement.setString(++ix, fieldName);
        }
        return preparedStatement;
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            if (resultSet.getObject(fieldName) == null) {
                field.set(this, " ");
            } else {
                field.set(this, resultSet.getObject(fieldName).toString());
            }
        }
    }

}
