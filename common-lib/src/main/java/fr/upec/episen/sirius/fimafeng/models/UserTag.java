package fr.upec.episen.sirius.fimafeng.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_tag")
public class UserTag {

    // Columns
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_tag_id")
    private int refTagId;

    @Column(name = "ref_client_id")
    private int refClientId;

    public UserTag() {}

    public UserTag(int refTagId, int refClientId) {
        this.refTagId = refTagId;
        this.refClientId = refClientId;
    }
}
