package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTag {

    private String user_tag_id;
    private String ref_user_id;
    private String ref_tag_id;

    public UserTag() {
    }

    public UserTag(String ref_user_id, String ref_tag_id) {
        this.ref_user_id = ref_user_id;
        this.ref_tag_id = ref_tag_id;
    }

    public UserTag(String user_tag_id, String ref_user_id, String ref_tag_id) {
        this.user_tag_id = user_tag_id;
        this.ref_user_id = ref_user_id;
        this.ref_tag_id = ref_tag_id;
    }

    public String getUser_tag_id() {
        return this.user_tag_id;
    }

    @JsonProperty("user_tag_id")
    public void setUser_tag_id(String tag_id) {
        this.user_tag_id = tag_id;
    }

    public String getRef_user_id() {
        return this.ref_user_id;
    }

    @JsonProperty("ref_user_id")
    public void setRef_user_id(String name) {
        this.ref_user_id = name;
    }

    public String getRef_tag_id() {
        return this.ref_tag_id;
    }

    @JsonProperty("ref_tag_id")
    public void setRef_tag_id(String categorie) {
        this.ref_tag_id = categorie;
    }

    @Override
    public String toString() {
        return "N: " + this.ref_user_id + ", c: " + this.ref_tag_id;
    }

    public final UserTag build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "user_tag_id", "ref_user_id", "ref_tag_id");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, user_tag_id, ref_user_id, ref_tag_id);
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
