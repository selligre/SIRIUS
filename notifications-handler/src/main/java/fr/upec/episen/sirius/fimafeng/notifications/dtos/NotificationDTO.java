package fr.upec.episen.sirius.fimafeng.notifications.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO pour transférer les données de notification
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private long id;
    private int userId;
    private int announceId;
    private Date creationDate;
    private boolean hasBeenRed;
    private String title;
    private String message;
}
