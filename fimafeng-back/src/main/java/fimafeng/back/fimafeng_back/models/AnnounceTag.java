package fimafeng.back.fimafeng_back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "announce_tag")
public class AnnounceTag {

    // Columns
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_announce_id")
    private int refAnnounceId;

    @Column(name = "ref_tag_id")
    private int refTagId;

    public AnnounceTag() {
    }

    public AnnounceTag(int refAnnounceId, int refTagId) {
        this.refAnnounceId = refAnnounceId;
        this.refTagId = refTagId;
    }

    // Getters and Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public int getRefTagId() {
        return refTagId;
    }

    public void setRefTagId(int refTagId) {
        this.refTagId = refTagId;
    }

    public int getRefAnnounceId() {
        return refAnnounceId;
    }

    public void setRefAnnounceId(int refAnnounceId) {
        this.refAnnounceId = refAnnounceId;
    }

    // Functions
    @Override
    public String toString() {
        String res = "AnnounceTag[";
        res += "id: " + id + ", ";
        res += "ref_announce_id: " + refAnnounceId + ", ";
        res += "ref_tag_id: " + refTagId + "]";
        return res;
    }
}
