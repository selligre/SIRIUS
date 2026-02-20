package fr.upec.episen.sirius.fimafeng.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

}