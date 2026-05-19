package fr.upec.episen.sirius.fimafeng.notifications.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour créer une nouvelle notification
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNotificationDTO {
    private int userId;
    private int announceId;
    private String title;
    private String message;
}
