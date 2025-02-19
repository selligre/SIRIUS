package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class IntentionImplemention {

    Logger LOGGER = Logger.getLogger(IntentionImplemention.class.getName());

    /**
     * We want to implement an algorithm that detect intention in text section
     * For that, we'll segment the algorithm into few steps
     *
     * 1. Text cleaning
     * A simple step that resume as removing any irrelevant words such as "le", "la", "les", etc.
     *
     * 2. Text transforming
     * A more complex step to transform words to their root, in order to reduce cases into more common ones.
     * For example, we want to transform "couru", "courront" into "courir"
     *
     * 3. Intention detection
     * Then, we'll 'simply' concatenate remaining words into intention:
     * "acheter" + "billet" will be recognized as "acheter un billet"
     *
     * 4. Moderation action
     * Finally, if the previous detected intention is 'negative',
     * we'll call our moderation service to moderate announce.
     * Otherwise (i.e. text section isn't recognized as 'negative'), we call our MS to authorized announce.
     */

    private static List<String> listIrrelevantWords = null;

    public IntentionImplemention() {
        // Load data from files if not already existing
        if (listIrrelevantWords == null) {
            LOGGER.info("Initializing Moderation Service");
            try {
                listIrrelevantWords = new ArrayList<>();
                ClassPathResource resource = new ClassPathResource(ModerationConfiguration.CLEAR_IRRELEVANT_WORDS_FILE_NAME);
                File file = resource.getFile();
                listIrrelevantWords = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }

    private String cleanText(String message) {
        List<String> words = new ArrayList<>(Arrays.stream(message.split(" ")).toList());
        for (String word : words) {
            if(listIrrelevantWords.contains(word)) {
                words.remove(word);
            }
        }
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word).append(" ");
        }
        return result.toString();
    }

    public ModerationAnalysis run(ModerationAnalysis analysis) {
        analysis.setTitle(cleanText(analysis.getTitle()));
        analysis.setDescription(cleanText(analysis.getDescription()));
        return analysis;
    }



}
