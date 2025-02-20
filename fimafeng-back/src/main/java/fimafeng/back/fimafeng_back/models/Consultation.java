package fimafeng.back.fimafeng_back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "consultation")
public class Consultation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_user_id")
    private int refUserId;

    @Column(name = "ref_announce_id")
    private int refAnnounceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(int refUserId) {
        this.refUserId = refUserId;
    }

    public int getRefAnnounceId() {
        return refAnnounceId;
    }

    public void setRefAnnounceId(int refAnnounceId) {
        this.refAnnounceId = refAnnounceId;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", refUserId=" + refUserId +
                ", refAnnounceId=" + refAnnounceId +
                '}';
    }
}
