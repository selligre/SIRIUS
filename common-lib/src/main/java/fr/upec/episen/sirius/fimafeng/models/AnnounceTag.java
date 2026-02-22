package fr.upec.episen.sirius.fimafeng.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AnnounceTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int refAnnounceId;

    private int refTagId;

    public AnnounceTag() {
    }

    public AnnounceTag(int refAnnounceId, int refTagId) {
        this.refAnnounceId = refAnnounceId;
        this.refTagId = refTagId;
    }

}