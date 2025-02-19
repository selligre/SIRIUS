package fimafeng.back.fimafeng_back.models;

import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;

public class ModerationAnalysis {

    private String title;
    private ModerationReason titleStatus;
    private String titleReason;
    private String titleRejectedWord;

    private String description;
    private ModerationReason descriptionStatus;
    private String descriptionReason;
    private String descriptionRejectedWord;

    private ModerationReason intention;
    private AnnounceStatus moderationStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ModerationReason getTitleStatus() {
        return titleStatus;
    }

    public void setTitleStatus(ModerationReason titleStatus) {
        this.titleStatus = titleStatus;
    }

    public String getTitleReason() {
        return titleReason;
    }

    public void setTitleReason(String titleReason) {
        this.titleReason = titleReason;
    }

    public String getTitleRejectedWord() {
        return titleRejectedWord;
    }

    public void setTitleRejectedWord(String titleRejectedWord) {
        this.titleRejectedWord = titleRejectedWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModerationReason getDescriptionStatus() {
        return descriptionStatus;
    }

    public void setDescriptionStatus(ModerationReason descriptionStatus) {
        this.descriptionStatus = descriptionStatus;
    }

    public String getDescriptionReason() {
        return descriptionReason;
    }

    public void setDescriptionReason(String descriptionReason) {
        this.descriptionReason = descriptionReason;
    }

    public String getDescriptionRejectedWord() {
        return descriptionRejectedWord;
    }

    public void setDescriptionRejectedWord(String descriptionRejectedWord) {
        this.descriptionRejectedWord = descriptionRejectedWord;
    }

    public ModerationReason getIntention() {
        return intention;
    }

    public void setIntention(ModerationReason intention) {
        this.intention = intention;
    }

    public AnnounceStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(AnnounceStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    @Override
    public String toString() {
        return "ModerationAnalysis{" +
                "title='" + title + '\'' +
                ", titleStatus='" + titleStatus + '\'' +
                ", titleReason='" + titleReason + '\'' +
                ", titleRejectedWord='" + titleRejectedWord + '\'' +
                ", description='" + description + '\'' +
                ", descriptionStatus='" + descriptionStatus + '\'' +
                ", descriptionReason='" + descriptionReason + '\'' +
                ", descriptionRejectedWord='" + descriptionRejectedWord + '\'' +
                ", intention=" + intention + '\'' +
                ", moderationStatus=" + moderationStatus +
                '}';
    }

    public ModerationAnalysis(Moderation moderation) {
        this.title = moderation.getAnnounceTitle();
        this.titleStatus = ModerationReason.NOT_MODERATED_YET;
        this.description = moderation.getAnnounceDescription();
        this.descriptionStatus = ModerationReason.NOT_MODERATED_YET;
        this.intention = ModerationReason.UNDEFINED;
        this.moderationStatus = AnnounceStatus.TO_ANALYSE;
    }
}
