package fimafeng.back.fimafeng_back.models;

import fimafeng.back.fimafeng_back.models.enums.AnnounceType;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
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

    @Column(name = "announce_publication_date")
    private Date announcePublicationDate;

    @Column(name = "announce_type")
    @Enumerated(EnumType.STRING)
    private AnnounceType announceType;

    @Column(name = "announce_title")
    private String announceTitle;

    @Column(name = "announce_description", length = 500)
    private String announceDescription;

    @Column(name = "moderation_date")
    private Date moderationDate;

    @Column(name = "moderator_name")
    private String moderatorName;

    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    private ModerationReason reason;

    @Column(name = "description")
    private String description;

    @Column(name = "latest_action")
    private boolean latestAction;

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

    public Date getModerationDate() {
        return moderationDate;
    }

    public void setModerationDate(Date actionDate) {
        this.moderationDate = actionDate;
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

    public Date getAnnouncePublicationDate() {
        return announcePublicationDate;
    }

    public void setAnnouncePublicationDate(Date publicationDate) {
        this.announcePublicationDate = publicationDate;
    }

    public AnnounceType getAnnounceType() {
        return announceType;
    }

    public void setAnnounceType(AnnounceType announceType) {
        this.announceType = announceType;
    }

    public String getAnnounceTitle() {
        return announceTitle;
    }

    public void setAnnounceTitle(String annonceTitle) {
        this.announceTitle = annonceTitle;
    }

    public String getAnnounceDescription() {
        return announceDescription;
    }

    public void setAnnounceDescription(String annonceDescription) {
        this.announceDescription = annonceDescription;
    }

    public boolean getLatestAction() {
        return latestAction;
    }

    public void setLatestAction(boolean isLatest) {
        latestAction = isLatest;
    }

    @Override
    public String toString() {
        return "Moderation{" +
                "id=" + id +
                ", announceId=" + announceId +
                ", authorId=" + authorId +
                ", announcePublicationDate=" + announcePublicationDate +
                ", announceType=" + announceType +
                ", announceTitle='" + announceTitle + '\'' +
                ", announceDescription='" + announceDescription + '\'' +
                ", moderationDate=" + moderationDate +
                ", moderatorName='" + moderatorName + '\'' +
                ", reason=" + reason +
                ", description='" + description + '\'' +
                ", latestAction=" + latestAction +
                '}';
    }
}
