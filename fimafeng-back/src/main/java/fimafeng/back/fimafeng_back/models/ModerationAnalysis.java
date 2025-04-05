package fimafeng.back.fimafeng_back.models;

import fimafeng.back.fimafeng_back.implementations.moderation.ModerationConfiguration;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class ModerationAnalysis {

    private ArrayList<String> title;
    private ModerationReason titleStatus;
    private String titleReason;
    private String titleRejectedWord;

    private ArrayList<String> description;
    private ModerationReason descriptionStatus;
    private String descriptionReason;
    private String descriptionRejectedWord;

    private ModerationReason intention;
    private AnnounceStatus moderationStatus;

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
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

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
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


    Logger LOGGER = Logger.getLogger(ModerationAnalysis.class.getName());

    public ModerationAnalysis(Moderation moderation) {
        // Text normalization, i.e. convert accent letters to non-accent letters
        // source : https://stackoverflow.com/questions/4122170/java-change-%c3%a1%c3%a9%c5%91%c5%b1%c3%ba-to-aeouu?noredirect=1&lq=1
        String normalizedTitle = Normalizer
                .normalize(moderation.getAnnounceTitle(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").replaceAll(",","");
        LOGGER.fine("Title: " + normalizedTitle);
        this.title = new ArrayList<String>(Arrays.asList(normalizedTitle.split(" ")));
        LOGGER.info("Title: " +this.title.toString());
        this.titleStatus = ModerationReason.NOT_MODERATED_YET;
        String normalizedDescription = Normalizer
                .normalize(moderation.getAnnounceDescription(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").replaceAll(",","");
        LOGGER.fine("Description: " + normalizedDescription);
        this.description = new ArrayList<String>(Arrays.asList(normalizedDescription.split(" ")));
        LOGGER.info("Description: "+this.description.toString());
        this.descriptionStatus = ModerationReason.NOT_MODERATED_YET;
        this.intention = ModerationReason.UNDEFINED;
        this.moderationStatus = AnnounceStatus.TO_ANALYSE;
    }

    private String generateModerationInformation(String words, ModerationReason reason) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ModerationConfiguration.MODERATION_INCORRECT_WORD_MESSAGE);
        stringBuilder.append("\"");
        stringBuilder.append(words);
        stringBuilder.append("\"");
        if (reason != null) {
            stringBuilder.append(" (");
            stringBuilder.append(reason);
            stringBuilder.append(") ");
        }
        return stringBuilder.toString();
    }

    public void addTitleInformations(String words, ModerationReason reason) {
        if(this.titleReason == null) {
            this.titleReason = ModerationConfiguration.MODERATION_TITLE_DEFAULT_MESSAGE;
        }
        this.titleReason += generateModerationInformation(words, reason);
        this.titleStatus = reason;
        this.moderationStatus = AnnounceStatus.MODERATED;
    }

    public void addDescriptionInformations(String words, ModerationReason reason) {
        if(this.descriptionReason == null) {
            this.descriptionReason = ModerationConfiguration.MODERATION_DESCRIPTION_DEFAULT_MESSAGE;
        }
        this.descriptionReason += generateModerationInformation(words, reason);
        this.descriptionStatus = reason;
        this.moderationStatus = AnnounceStatus.MODERATED;
    }
}
