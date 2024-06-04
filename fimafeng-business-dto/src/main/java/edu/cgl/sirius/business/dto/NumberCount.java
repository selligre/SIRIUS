package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberCount {

    private String count;

    public NumberCount() {
    }

    public final NumberCount build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "count");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, count);
    }

    public NumberCount(String count) {
        this.count = count;
    }

    public String getCount(){
        return count;
    }
    
    @JsonProperty("count")
    public void setCount(String count) {
        this.count = count;
    }


    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            if (resultSet.getObject(fieldName) instanceof String) {
                field.set(this, resultSet.getObject(fieldName));
            } else {
                field.set(this, resultSet.getObject(fieldName).toString());
            }
        }
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

    @Override
    public String toString() {
        return "NumberCount{" +
                "count='" + count + '\'' +
                '}';
    }
}
