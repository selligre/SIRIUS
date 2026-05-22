package fr.upec.episen.sirius.fimafeng.commons.models;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Evénement de notification produit vers Kafka.
 */
public class NotificationEvent implements Serializable {

    private String uuid;

    private String id;
    private int userId;
    private int announceId;
    private String status;
    private String createdAt;
    private String message;

    public NotificationEvent() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = Instant.now().toString();
    }

    public NotificationEvent(String uuid, int userId, int announceId, String status, String message) {
        this();
        this.uuid = uuid;
        this.userId = userId;
        this.announceId = announceId;
        this.status = status;
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(int announceId) {
        this.announceId = announceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "id='" + id + '\'' +
                ", uuid=" + uuid +
                ", userId=" + userId +
                ", announceId=" + announceId +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
