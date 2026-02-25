package fr.upec.episen.sirius.fimafeng.commons.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int refTagId;

    private int refClientId;

    public UserTag() {}

    public UserTag(int refTagId, int refClientId) {
        this.refTagId = refTagId;
        this.refClientId = refClientId;
    }
}
