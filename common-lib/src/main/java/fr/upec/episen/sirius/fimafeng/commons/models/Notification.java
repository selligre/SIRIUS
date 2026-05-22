package fr.upec.episen.sirius.fimafeng.commons.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Notification {

    @Id
    private UUID uuid;

    private int userId;

    private int announceId;

    private Date creationDate;

    private boolean hasBeenRed;

    private String title;

}
