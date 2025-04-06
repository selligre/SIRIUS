package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import fimafeng.back.fimafeng_back.implementations.moderation.ModerationConfiguration;
import fimafeng.back.fimafeng_back.implementations.moderation.iDetection;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BadWordDetection implements iDetection {


    private final Logger LOGGER = Logger.getLogger(BadWordDetection.class.getName());

    protected static List<String> listBanWords = null;

    public BadWordDetection() {
        // Load data from files if not already existing
        if (listBanWords == null) {
            LOGGER.info("Initializing Moderation Service");
            try {
                listBanWords = new ArrayList<>();
                InputStream file = ModerationConfiguration.loadFile(ModerationConfiguration.CLEAR_BAD_WORDS_FILE);
                BufferedReader br = new BufferedReader(new InputStreamReader(file));
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    listBanWords.add(line);
                }
                br.close();
                file.close();

                file = ModerationConfiguration.loadFile(ModerationConfiguration.CRYPTED_BAD_WORDS_FILE_NAME);
                br = new BufferedReader(new InputStreamReader(file));
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    listBanWords.add(line);
                }
                br.close();
                file.close();
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }

    public void run(Moderation moderation) {
        LOGGER.info("Analysing bad words presence");

        // Setting up data to do less interaction later
        String[] title = moderation.getAnnounceTitle().split(" ");
        String[] desc = moderation.getAnnounceDescription().split(" ");

        // Checking bad word presence
        String titleModeratedWord = isThereBanWord(title);
        String descModeratedWord = isThereBanWord(desc);

        // Storing moderation actions
        ModerationAnalysis analysis = moderation.getAnalysis();
        if (titleModeratedWord != null) {
            LOGGER.info("Title: " + titleModeratedWord);
            analysis.addTitleInformations(titleModeratedWord, ModerationReason.VOCABULARY);
        }
        if (descModeratedWord != null) {
            LOGGER.info("Desc: " + descModeratedWord);
            analysis.addDescriptionInformations(descModeratedWord, ModerationReason.VOCABULARY);
        }
    }

    private String isThereBanWord(String[] text) {
        for(String word : text) {
            if (listBanWords.contains(word)) {
                return word;
            }
        }
        return null;
    }
}
