package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import fimafeng.back.fimafeng_back.implementations.moderation.ModerationConfiguration;
import fimafeng.back.fimafeng_back.implementations.moderation.iDetection;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.logging.Logger;

@Service
public class SpamDetection implements iDetection {

    private final Logger LOGGER = Logger.getLogger(SpamDetection.class.getName());

    private int repetitionFirstIndex;
    private int repetitionLastIndex;

    public void run(Moderation moderation) {
        LOGGER.info("Analysing spam");
        ModerationAnalysis analysis = moderation.getAnalysis();

        // Title
        String concatenatedTitle = concatenateText(moderation.getAnnounceTitle());
        ModerationReason titleStatus = detect(concatenatedTitle);
        if (titleStatus == ModerationReason.SPAM) {
            String subTitle = concatenatedTitle.substring(repetitionFirstIndex, repetitionLastIndex);
            LOGGER.info("Title: repetition near "+repetitionFirstIndex+": "+subTitle);
            analysis.addTitleInformations(subTitle, ModerationReason.SPAM);
        }

        // Description
        String concatenatedDesc = concatenateText(moderation.getAnnounceDescription());
        ModerationReason descStatus = detect(concatenatedDesc);
        if (descStatus == ModerationReason.SPAM) {
            String subDesc = concatenatedDesc.substring(repetitionFirstIndex, repetitionLastIndex);
            LOGGER.info("Description: repetition near "+repetitionFirstIndex+": "+subDesc);
            analysis.addDescriptionInformations(subDesc, ModerationReason.SPAM);
        }
    }


    // https://stackoverflow.com/questions/38116385/how-can-i-round-up-to-the-nearest-multiple-of-the-specified-number
    private int upperRound(int num, int base) {
        int temp = num%base;
        if (temp < 0 )
            temp = base + temp;
        if (temp == 0)
            return num;
        return num + base - temp;
    }

    private String concatenateText(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll(" ", "")
                .replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }


    /**
     *  Analyse the message by first clearing and simplifying it
     *  Then compare each following substring with increasing length
     *  If a substring is identified 4 times consecutively, then detected as SPAM
     * @param message the text to analyse
     * @return ModerationReason.SPAM if detected as spam, ModerationReason.IntentionOK otherwise
     */
    protected ModerationReason detect(String message) {
        int messageLength = message.length();
        int maxPatternSize = upperRound(messageLength, ModerationConfiguration.MAX_REPETITION_PATTERN)/ModerationConfiguration.MAX_REPETITION_PATTERN;
        // Loop varying pattern size
        for (int patternSize = 1; patternSize <= maxPatternSize; patternSize++) {

            int messageTempSizeLimit = messageLength-patternSize*ModerationConfiguration.MAX_REPETITION_PATTERN;
            // Loop varying pattern start index
            for (int patternIndex = 0; patternIndex <= messageTempSizeLimit ; patternIndex++) {

                // While loop variables
                String currentPattern = message.substring(patternIndex, patternIndex+patternSize);
                int repetition = 1; // We already 'detected' the pattern once (from its definition)
                int currentPatternIndex = patternIndex+patternSize; // Since we've already detected first repetition, we must go to the next position

                // Loop checking patterns equality
                while(currentPatternIndex < messageLength-1) {

                    if (currentPatternIndex+patternSize > messageLength-1) {
                        // If future observed pattern include out of range substring
                        break;
                    }

                    String observedPattern = message.substring(currentPatternIndex, currentPatternIndex+patternSize);
                    // If observed pattern is different from the one we're looking for
                    if (!observedPattern.equals(currentPattern)) {
                        break; // stop the current verification (while loop), and process next one
                    } else {
                        // Observed pattern is currently the same
                        repetition++;
                        if (repetition > ModerationConfiguration.MAX_REPETITION_PATTERN) {
                            repetitionFirstIndex = patternIndex;
                            repetitionLastIndex = currentPatternIndex+patternSize;
                            return ModerationReason.SPAM;
                        }
                        // Else
                        currentPatternIndex += patternSize;
                    }
                }

            }

        }
        return ModerationReason.INTENTION_OK;
    }



}
