package fimafeng.back.fimafeng_back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "client_tag")
public class ClientTag {

    // Columns
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_tag_id")
    private int refTagId;

    @Column(name = "ref_client_id")
    private int refClientId;

    public ClientTag() {}

    public ClientTag(int refTagId, int refClientId) {
        this.refTagId = refTagId;
        this.refClientId = refClientId;
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

    public int getRefClientId() {
        return refClientId;
    }

    public void setRefClientId(int refClientId) {
        this.refClientId = refClientId;
    }

    // Functions
    @Override
    public String toString() {
        String res = "ClientTag[";
        res += "ID: " + id + ", ";
        res += "ref_tag_id: " + refTagId + ", ";
        res += "ref_client_id: " + refClientId + "]";
        return res;
    }
}
