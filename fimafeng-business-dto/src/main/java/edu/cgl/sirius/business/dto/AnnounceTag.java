package edu.cgl.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnounceTag {

    private String announce_tag_id;
    private String ref_announce_id;
    private String ref_tag_id;

    public AnnounceTag() {
    }

    public final AnnounceTag build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "announce_tags_id", "ref_announce_id", "ref_tag_id");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, announce_tag_id, ref_announce_id, ref_tag_id);
    }

    public AnnounceTag(String announce_tag_id, String ref_announce_id, String ref_tag_id) {
        this.announce_tag_id = announce_tag_id;
        this.ref_announce_id = ref_announce_id;
        this.ref_tag_id = ref_tag_id;
    }

    public String getAnnounce_tag_id() {
        return this.announce_tag_id;
    }

    @JsonProperty("announce_tag_id")
    public void setAnnounce_tag_id(String announce_tag_id) {
        this.announce_tag_id = announce_tag_id;
    }

    public String getRef_announce_id() {
        return this.ref_announce_id;
    }

    @JsonProperty("ref_announce_id")
    public void setRef_announce_id(String ref_announce_id) {
        this.ref_announce_id = ref_announce_id;
    }

    @JsonProperty("ref_tag_id")
    public String getRef_tag_id() {
        return this.ref_tag_id;
    }

    public void setRef_tag_id(String ref_tag_id) {
        this.ref_tag_id = ref_tag_id;
    }

}
