package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {

    private String tag_id;
    private String name;
    private String category;

    public Tag() {
    }

    public Tag(String name, String cat) {
        this.name = name;
        this.category = cat;
    }

    public Tag(String id, String name, String cat) {
        this.tag_id = id;
        this.name = name;
        this.category = cat;
    }

    public String getTag_id() {
        return this.tag_id;
    }

    @JsonProperty("tag_id")
    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return this.name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    @JsonProperty("category")
    public void setCategory(String categorie) {
        this.category = categorie;
    }

    @Override
    public String toString() {
        return "N: " + this.name + ", c: " + this.category;
    }

    public final Tag build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "tag_id", "name", "category");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, tag_id, name, category);
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
