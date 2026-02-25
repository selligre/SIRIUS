package fr.upec.episen.sirius.fimafeng.models;

import fr.upec.episen.sirius.fimafeng.models.enums.NotificationTitle;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int userId;

    private int announceId;

    private Date creationDate;

    private boolean hasBeenRed;

    private NotificationTitle title;

    private String message;



}
