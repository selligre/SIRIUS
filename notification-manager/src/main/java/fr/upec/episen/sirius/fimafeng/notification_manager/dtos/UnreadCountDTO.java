package fr.upec.episen.sirius.fimafeng.notification_manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour transférer le nombre de notifications non-lues
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadCountDTO {
    private int userId;
    private long unreadCount;
}
