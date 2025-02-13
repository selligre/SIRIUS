package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

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
    // Lazy (dependencies cycling) Source: https://medium.com/@tuananhbk1996/how-to-handle-cyclic-dependency-between-beans-in-spring-754d1a56e297
    private AnnounceService announceService;

    // Local variables
    private static final String SYSTEM_MODERATOR_NAME = "SYSTEM";
    private static final String MODERATION_DEFAULT_MESSAGE = "Votre annonce a été modérée car des mots ne relevant pas d'un bon comportement ont été repérés dans son contenu.";

    // from https://github.com/darwiin/french-badwords-list
    private static final String DICTIONARY_CLEAR_FILE_NAME = "french-bad-words-list-clear.txt";

    // from https://fr.wiktionary.org/w/index.php?title=Cat%C3%A9gorie:Insultes_en_fran%C3%A7ais
    private static final String DICTIONARY_CRYPTED_FILE_NAME = "french-bad-words-list.txt";

    private static List<String> listBanWords = null;


    public ModerationImplementation() {
        // Load data from files if not already existing
        if (listBanWords == null) {
            LOGGER.info("Initializing Moderation Service");
            try {
                listBanWords = new ArrayList<>();
                File file = ResourceUtils.getFile("classpath:"+ DICTIONARY_CLEAR_FILE_NAME);
                listBanWords = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }

    public Moderation createModeration(Announce announce) {
        Moderation moderation = new Moderation();
        moderation.setModeratorName(SYSTEM_MODERATOR_NAME);
        moderation.setModerationDate(new Date());
        moderation.setAnnounceId(announce.getId());
        moderation.setAuthorId(announce.getAuthorId());
        moderation.setReason(ModerationReason.UNDEFINED);
        moderation.setDescription(MODERATION_DEFAULT_MESSAGE);
        moderation.setAnnounceTitle(announce.getTitle());
        moderation.setAnnounceDescription(announce.getDescription());
        moderation.setAnnounceType(announce.getType());
        moderation.setAnnouncePublicationDate(announce.getPublicationDate());
        moderation.setLatestAction(true);
        return moderation;
    }

    private void markAndUpdateAnnounceAs(Announce announce, AnnounceStatus status) {
        LOGGER.info("Updating announce as " + status);
        announce.setStatus(status);
        announceService.update(announce, true);
    }

    private boolean isThereBanWord(String[] text) {
        for(String word : text) {
            if (listBanWords.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public void analyse(Announce announceToAnalyse) {
        // Assuring data isn't null or empty
        if (announceToAnalyse.getDescription() != null && !announceToAnalyse.getDescription().isEmpty()) {

            // Setting up data to do less interaction later
            LOGGER.info("Analysing announce data");
            String[] title = announceToAnalyse.getTitle().split(" ");
            String[] desc = announceToAnalyse.getDescription().split(" ");

            // Checking bad word presence
            boolean titleModerated = isThereBanWord(title);
            boolean descModerated = isThereBanWord(desc);
            LOGGER.info("Moderation analyse: title=" + (titleModerated ? "KO" : "OK")+ ", desc=" + (descModerated ? "KO" : "OK"));

            // Update announce according to case
            if (titleModerated || descModerated) {
                Moderation moderation = createModeration(announceToAnalyse);
                markAndUpdateAnnounceAs(announceToAnalyse, AnnounceStatus.MODERATED);
                save(moderation);
            } else {
                markAndUpdateAnnounceAs(announceToAnalyse, AnnounceStatus.PUBLISHED);
            }
        }
    }
}
