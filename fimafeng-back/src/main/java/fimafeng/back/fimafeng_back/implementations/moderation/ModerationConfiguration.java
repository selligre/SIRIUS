package fimafeng.back.fimafeng_back.implementations.moderation;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.logging.Logger;

public class ModerationConfiguration {
    public static final String BASE_RESOURCES_DIRECTORY = "moderation-files"+ FileSystems.getDefault().getSeparator();
    // from https://github.com/darwiin/french-badwords-list
    public static final String CLEAR_BAD_WORDS_FILE = BASE_RESOURCES_DIRECTORY+"french-bad-words-list-clear.txt";
    // from https://fr.wiktionary.org/w/index.php?title=Cat%C3%A9gorie:Insultes_en_fran%C3%A7ais
    public static final String CRYPTED_BAD_WORDS_FILE_NAME = BASE_RESOURCES_DIRECTORY+"french-bad-words-list-crypted.txt";
    // from https://www.talkinfrench.com/common-french-adverbs/
    public static final String CLEAR_IRRELEVANT_WORDS_FILE = BASE_RESOURCES_DIRECTORY+"french-irrelevant-words-list.txt";
    // from https://github.com/bnare/fr.french.verbs.lib/tree/master/src/xml
    public static final String CONJUGAISONS_FILE = BASE_RESOURCES_DIRECTORY+"conjugaison.xml";
    public static final String VERBS_FILE = BASE_RESOURCES_DIRECTORY+"verbes.xml";

    // Local variables
    public static final String SYSTEM_MODERATOR_NAME = "SYSTEM";
    public static final String MODERATION_DEFAULT_MESSAGE = "Votre annonce a été modérée car elle a été détectée comme inacceptable.";
    public static final String MODERATION_TITLE_DEFAULT_MESSAGE = "[Titre] ";
    public static final String MODERATION_DESCRIPTION_DEFAULT_MESSAGE = "[Description] ";
    public static final String MODERATION_INCORRECT_WORD_MESSAGE = "Le mot ou les mots suivants ont levé des alertes : ";
    public static final String MODERATION_TOO_MANY_SUSPICIOUS_WORDS_MESSAGE = "Le texte contient trop de mots suspicieux/inappropriés. ";
    public static final String MODERATION_TOO_MANY_UPPER_LETTER_MESSAGE = "Le texte contient des mots avec trop de majuscules. ";

    public static final int MAX_REPETITION_PATTERN = 3;
    public static final int MAX_ALLOWED_BAD_WORD_AMOUNT = 2;
    public static final int MAX_ALLOWED_AVERAGE_UPPER_LETTER = 2;

    static Logger LOGGER = Logger.getLogger(ModerationConfiguration.class.getName());

    public static InputStream loadFile(String fileName) {
        InputStream result = null;
        try {
            result = new ClassPathResource(fileName).getInputStream();
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }
}
