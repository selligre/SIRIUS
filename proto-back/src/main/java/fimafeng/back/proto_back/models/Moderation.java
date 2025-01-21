package fimafeng.back.proto_back.models;

import fimafeng.back.proto_back.models.enums.ModerationReason;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "moderation")
public class Moderation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_announce_id")
    private int announceId;

    @Column(name = "ref_author_id")
    private int authorId;

    @Column(name = "action_date")
    private Date actionDate;

    @Column(name = "moderator_name")
    private String moderatorName;

    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    private ModerationReason reason;

    @Column(name = "description")
    private String description;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(int announceId) {
        this.announceId = announceId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getModeratorName() {
        return moderatorName;
    }

    public void setModeratorName(String moderatorName) {
        this.moderatorName = moderatorName;
    }

    public ModerationReason getReason() {
        return reason;
    }

    public void setReason(ModerationReason reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
