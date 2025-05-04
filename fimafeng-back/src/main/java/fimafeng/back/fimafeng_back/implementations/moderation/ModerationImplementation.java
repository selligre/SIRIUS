package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.implementations.moderation.detections.BadWordDetection;
import fimafeng.back.fimafeng_back.implementations.moderation.detections.HateDetection;
import fimafeng.back.fimafeng_back.implementations.moderation.detections.IntentionDetection;
import fimafeng.back.fimafeng_back.implementations.moderation.detections.SpamDetection;
import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

@Service
public class ModerationImplementation extends ModerationService {

    Logger LOGGER = Logger.getLogger(ModerationImplementation.class.getName());

    @Autowired
    @Lazy
    // Lazy (dependencies cycling)
    // Source: https://medium.com/@tuananhbk1996/how-to-handle-cyclic-dependency-between-beans-in-spring-754d1a56e297
    private AnnounceService announceService;


    IntentionDetection intentionDetection = new IntentionDetection();
    BadWordDetection badWordDetection = new BadWordDetection();
    SpamDetection spamDetection = new SpamDetection();
    HateDetection hateDetection = new HateDetection();


    public Moderation createModeration(Announce announce) {
        Moderation moderation = new Moderation();
        moderation.setModeratorName(ModerationConfiguration.SYSTEM_MODERATOR_NAME);
        moderation.setModerationDate(new Date());
        moderation.setAnnounceId(announce.getId());
        moderation.setAuthorId(announce.getAuthorId());
        moderation.setReason(ModerationReason.NOT_MODERATED_YET);
        moderation.setAnnounceTitle(announce.getTitle());
        moderation.setAnnounceDescription(announce.getDescription());
        moderation.setAnnounceType(announce.getType());
        moderation.setAnnouncePublicationDate(announce.getPublicationDate());
        moderation.setLatestAction(true);
        moderation.setAnalysis(new ModerationAnalysis(moderation));
        return moderation;
    }

    private void markAndUpdateAnnounceAs(Announce announce, AnnounceStatus status) {
        LOGGER.info("Updating announce as " + status);
        announce.setStatus(status);
        announceService.update(announce, true, true);
    }

    private void analyseAnnounce(Moderation moderation) {
        LOGGER.info("Analysing announce");
        badWordDetection.run(moderation);
        intentionDetection.run(moderation);
        spamDetection.run(moderation);
        hateDetection.run(moderation);

        ModerationAnalysis analysis = moderation.getAnalysis();
        LOGGER.info("[Analysis] Title status: " + analysis.getTitleStatus());
        LOGGER.info("[Analysis] Description status : " + analysis.getDescriptionStatus());
        updateModerationStatus(moderation);
        LOGGER.info("Moderation reason: " + moderation.getReason());
    }

    private String generateModerationDescription(ModerationAnalysis analysis) {
        StringBuilder sb = new StringBuilder();
        sb.append(ModerationConfiguration.MODERATION_DEFAULT_MESSAGE);
        if(analysis.getTitleStatus() != ModerationReason.NOT_MODERATED_YET) {
            sb.append(" ");
            sb.append(analysis.getTitleReason());
            sb.append(analysis.getTitleRejectedWord() != null ? analysis.getTitleRejectedWord() : "");
        }
        if(analysis.getDescriptionStatus() != ModerationReason.NOT_MODERATED_YET) {
            sb.append(" ");
            sb.append(analysis.getDescriptionReason());
            sb.append(analysis.getDescriptionRejectedWord() != null ? analysis.getDescriptionRejectedWord() : "");
        }
        if(analysis.getIntention() != ModerationReason.INTENTION_OK
                && analysis.getIntention() != ModerationReason.UNDEFINED) {
            sb.append(" ");
            sb.append("Intention détectée : ");
            sb.append(analysis.getIntention());
        }
        return sb.toString();
    }

    private void updateModerationStatus(Moderation moderation) {
        ModerationAnalysis analysis = moderation.getAnalysis();
        if (analysis.getTitleStatus() != ModerationReason.INTENTION_OK
                && analysis.getTitleStatus() != ModerationReason.NOT_MODERATED_YET) {
            moderation.setReason(analysis.getTitleStatus());
        }
        if (analysis.getDescriptionStatus() != ModerationReason.INTENTION_OK
                && analysis.getDescriptionStatus() != ModerationReason.NOT_MODERATED_YET) {
            moderation.setReason(analysis.getDescriptionStatus());
        }
    }

    public void moderate(Announce announceToAnalyse) {
        // Assuring data isn't null or empty
        if (announceToAnalyse.getDescription() != null && !announceToAnalyse.getDescription().isEmpty()) {

            Moderation moderation = createModeration(announceToAnalyse);
            analyseAnnounce(moderation);

            // Update announce according to case
            if (moderation.getAnalysis().getModerationStatus() == AnnounceStatus.MODERATED) {
                markAndUpdateAnnounceAs(announceToAnalyse, AnnounceStatus.MODERATED);
                moderation.setDescription(generateModerationDescription(moderation.getAnalysis()));
                save(moderation);
            } else {
                markAndUpdateAnnounceAs(announceToAnalyse, AnnounceStatus.PUBLISHED);
            }
        }
    }
}
