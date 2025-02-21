package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ModerationImplementation extends ModerationService {

    Logger LOGGER = Logger.getLogger(ModerationImplementation.class.getName());

    @Autowired
    @Lazy
    // Lazy (dependencies cycling)
    // Source: https://medium.com/@tuananhbk1996/how-to-handle-cyclic-dependency-between-beans-in-spring-754d1a56e297
    private AnnounceService announceService;

    private static List<String> listBanWords = null;

    public ModerationImplementation() {
        // Load data from files if not already existing
        if (listBanWords == null) {
            LOGGER.info("Initializing Moderation Service");
            try {
                listBanWords = new ArrayList<>();
                ClassPathResource resource = new ClassPathResource(ModerationConfiguration.CLEAR_BAD_WORDS_FILE);
                File file = resource.getFile();
                listBanWords = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }

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
        announceService.update(announce, true);
    }

    private String isThereBanWord(String[] text) {
        for(String word : text) {
            if (listBanWords.contains(word)) {
                return word;
            }
        }
        return null;
    }

    private void analyseAnnounce(Moderation moderation) {
        // Setting up data to do less interaction later
        LOGGER.info("Analysing announce data");
        String[] title = moderation.getAnnounceTitle().split(" ");
        String[] desc = moderation.getAnnounceDescription().split(" ");

        // Checking bad word presence
        String titleModeratedWord = isThereBanWord(title);
        String descModeratedWord = isThereBanWord(desc);

        ModerationAnalysis analysis = moderation.getAnalysis();
        if(titleModeratedWord != null) {
            analysis.setTitleStatus(ModerationReason.UNDEFINED);
            analysis.setTitleReason("Le titre a été détecté comme inacceptable : ");
            analysis.setTitleRejectedWord(titleModeratedWord);
            analysis.setModerationStatus(AnnounceStatus.MODERATED);
        }
        if(descModeratedWord != null) {
            analysis.setDescriptionStatus(ModerationReason.UNDEFINED);
            analysis.setDescriptionReason("La description a été détectée comme inacceptable : ");
            analysis.setDescriptionRejectedWord(titleModeratedWord);
            analysis.setModerationStatus(AnnounceStatus.MODERATED);
        }

        LOGGER.info("Moderation analyse: title=" + analysis.getTitleStatus()+ ", desc=" + analysis.getDescriptionStatus());
        moderation.setAnalysis(analysis);
    }

    private String generateModerationDescription(ModerationAnalysis analysis) {
        StringBuilder sb = new StringBuilder();
        sb.append(ModerationConfiguration.MODERATION_DEFAULT_MESSAGE);
        if(analysis.getTitleStatus() != ModerationReason.NOT_MODERATED_YET) {
            sb.append(analysis.getTitleReason());
            sb.append(analysis.getTitleRejectedWord());
        }
        if(analysis.getDescriptionStatus() != ModerationReason.NOT_MODERATED_YET) {
            sb.append(analysis.getDescriptionReason());
            sb.append(analysis.getDescriptionRejectedWord());
        }
        sb.append("Intention détectée : ");
        sb.append(analysis.getIntention());
        return sb.toString();
    }

    public void run(Announce announceToAnalyse) {
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
