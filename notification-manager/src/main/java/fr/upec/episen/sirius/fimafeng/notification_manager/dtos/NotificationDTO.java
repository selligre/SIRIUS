package fr.upec.episen.sirius.fimafeng.notification_manager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private String uuid;

    private int userId;

    @JsonProperty("announcementId")
    private int announceId;

    private Date creationDate;

    @JsonProperty("isRead")
    private boolean hasBeenRed;

    private String title;
}
