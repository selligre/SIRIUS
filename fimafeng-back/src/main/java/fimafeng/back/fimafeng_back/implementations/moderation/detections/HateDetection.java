package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import fimafeng.back.fimafeng_back.implementations.moderation.ModerationConfiguration;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import fimafeng.back.fimafeng_back.models.enums.ModerationReason;

import java.util.logging.Logger;

public class HateDetection extends BadWordDetection {

    private final Logger LOGGER = Logger.getLogger(HateDetection.class.getName());

    private int wordCount;

    public HateDetection() {
        super();
    }

    /**
     *  We want to detect two things :
     *  - the amount of capital letters (percentage and consecutive one)
     *  - the amount of bad words (percentage)
     *  Then depending on the score obtained, we'll take the action on announce
     */
    @Override
    public void run(Moderation moderation) {
        LOGGER.info("Analysing hate");

        ModerationAnalysis analysis = moderation.getAnalysis();
        int consecutiveCapitalAmount = 0;
        int averageUpperCasePerWord = 0;
        int badWordAmount = 0;

        // Managing title
        badWordAmount = countBadWords(moderation.getAnnounceTitle());
        consecutiveCapitalAmount = countConsecutiveCapitals(moderation.getAnnounceTitle());
        averageUpperCasePerWord = calculateUpperLettersRatio(consecutiveCapitalAmount, wordCount); // /!\ word Count = number of words WITH > 1 upper char

        LOGGER.info("[Title] badWordAmount="+badWordAmount+", consecutiveCapitalsAmount="+consecutiveCapitalAmount+", wordCount="+wordCount+", averageUpperCasePerWord="+averageUpperCasePerWord);
        if (badWordAmount > ModerationConfiguration.MAX_ALLOWED_BAD_WORD_AMOUNT) {
            analysis.addCustomTitleInformations(ModerationConfiguration.MODERATION_TOO_MANY_SUSPICIOUS_WORDS_MESSAGE, ModerationReason.HATE);
        }
        if (averageUpperCasePerWord > ModerationConfiguration.MAX_ALLOWED_AVERAGE_UPPER_LETTER) {
            analysis.addCustomTitleInformations(ModerationConfiguration.MODERATION_TOO_MANY_UPPER_LETTER_MESSAGE, ModerationReason.HATE);
        }

        // Managing description
        badWordAmount = countBadWords(moderation.getAnnounceDescription());
        consecutiveCapitalAmount = countConsecutiveCapitals(moderation.getAnnounceDescription());
        averageUpperCasePerWord = calculateUpperLettersRatio(consecutiveCapitalAmount, wordCount); // /!\ word Count = number of words WITH > 1 upper char

        LOGGER.info("[Description] badWordAmount="+badWordAmount+", consecutiveCapitalsAmount="+consecutiveCapitalAmount+", wordCount="+wordCount+", averageUpperCasePerWord="+averageUpperCasePerWord);
        if (badWordAmount > ModerationConfiguration.MAX_ALLOWED_BAD_WORD_AMOUNT) {
            analysis.addCustomDescriptionInformations(ModerationConfiguration.MODERATION_TOO_MANY_SUSPICIOUS_WORDS_MESSAGE, ModerationReason.HATE);
        }
        if (averageUpperCasePerWord > ModerationConfiguration.MAX_ALLOWED_AVERAGE_UPPER_LETTER) {
            analysis.addCustomDescriptionInformations(ModerationConfiguration.MODERATION_TOO_MANY_UPPER_LETTER_MESSAGE, ModerationReason.HATE);
        }
    }

    /**
     * Analyse message amount of capital letter.
     * First we remove all non-capital and non-white space characters (lower case char and ponctuation)
     * Then for each remaining word, if the amount of capital characters is greater than 1, we sum it
     * @param message the message we want to analyse
     * @return the sum of upper char in words of more than 1 upper char inside message
     */
    protected int countConsecutiveCapitals(String message) {
        // ponctuation removal found at: https://stackoverflow.com/questions/18830813/how-can-i-remove-punctuation-from-input-text-in-java
        String[] words = message.replaceAll("[^A-Z ]","").split("\\s+");
        wordCount = words.length;

        int consecutiveCapitals = 0;
        for (String word : words) {
            if(word.length() > 1) {
                consecutiveCapitals += word.length();
            }
        }
        LOGGER.finer(""+consecutiveCapitals);
        return consecutiveCapitals;
    }

    /**
     * Counting the amount of bad words in message
     * @param message the text we want to count bad words from
     * @return the amount of bad words found
     */
    protected int countBadWords(String message) {
        String[] words = message.toLowerCase().split("\\s+");
        int badWordsAmount = 0;
        for (String word : words) {
            if(listBanWords.contains(word)) {
                badWordsAmount++;
            }
        }
        return badWordsAmount;
    }

    /**
     * Return the average ratio of word with upper letter per word
     * @param consecutiveCapitals number of consecutive upper letter
     * @param numberOfWords number of words
     * @return consecutiveCapitals/numberOfWords if numberOfWords > 0; consecutiveCapitals otherwise
     */
    protected int calculateUpperLettersRatio(int consecutiveCapitals, int numberOfWords) {
        if (numberOfWords > 0) {
            return (consecutiveCapitals / numberOfWords);
        } else {
            return consecutiveCapitals;
        }
    }

}
