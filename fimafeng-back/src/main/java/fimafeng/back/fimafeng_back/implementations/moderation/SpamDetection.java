package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.logging.Logger;

@Service
public class SpamDetection {
    /**
     * fonction detectSpam(msg: str): str
     *  Pour taillePattern allant de 1 à taille(msg)/4:
     *      Pour indexPattern allant de 0 à taille(msg)-taillePattern:
     *          Expr = msg[indexPattern, taillePattern]
     *          Repete = 0
     *          Pour verif allant de indexPattern à taille(msg):
     *              Si msg[verif, verif+taillePattern] == Expr:
     *                  Repete += 1
     *              Sinon : skip
     *              Si Repete > 2:
     *                  Retourner SPAM
     */


    Logger LOGGER = Logger.getLogger(SpamDetection.class.getName());

    private String concatenatedTitle;
    private int titlePatternRepetitionIndex;
    private String titlePatternRepetition;

    private String concatenatedDesc;
    private int descPatternRepetitionIndex;
    private String descPatternRepetition;

    private int repetitionFirstIndex;
    private int repetitionLastIndex;

    // https://stackoverflow.com/questions/38116385/how-can-i-round-up-to-the-nearest-multiple-of-the-specified-number
    private int upperRound(int num, int base) {
        int temp = num%base;
        if (temp < 0 )
            temp = base + temp;
        if (temp == 0)
            return num;
        return num + base - temp;
    }


    /**
     *  Analyse the message by first clearing and simplifying it
     *  Then compare each following substring with increasing length
     *  If a substring is identified 4 times consecutively, then detected as SPAM
     * @param message: the text to analyse
     * @return ModerationReason.SPAM if detected as spam, ModerationReason.IntentionOK otherwise
     */
    private ModerationReason detect(String message) {
        message = Normalizer.normalize(message, Normalizer.Form.NFD)
                .replaceAll(" ", "")
                .replaceAll("[^\\p{ASCII}]", "").toLowerCase();

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


    public void run(Moderation moderation) {

        // Title
        ModerationReason titleStatus = detect(moderation.getAnnounceTitle());
        if (titleStatus == ModerationReason.SPAM) {
            // Do something
            LOGGER.info("Spam Detected in title at position "+repetitionFirstIndex+": ");
            String subTitle = concatenatedTitle.substring(repetitionFirstIndex, repetitionLastIndex);
            LOGGER.info(subTitle);
        }



    }

    // code for debug
    public static void main(String[] args) {
        SpamDetection spamDetection = new SpamDetection();
        spamDetection.detect("");
    }
}
