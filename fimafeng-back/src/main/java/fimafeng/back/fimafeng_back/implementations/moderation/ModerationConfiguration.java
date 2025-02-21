package fimafeng.back.fimafeng_back.implementations.moderation;

import java.nio.file.FileSystems;

public class ModerationConfiguration {
    public static final String BASE_RESOURCES_DIRECTORY = "moderation-files"+ FileSystems.getDefault().getSeparator();
    // from https://github.com/darwiin/french-badwords-list
    public static final String CLEAR_BAD_WORDS_FILE = BASE_RESOURCES_DIRECTORY+"french-bad-words-list-clear.txt";
    // from https://fr.wiktionary.org/w/index.php?title=Cat%C3%A9gorie:Insultes_en_fran%C3%A7ais
    public static final String CRYPTED_BAD_WORDS_FILE_NAME = BASE_RESOURCES_DIRECTORY+"french-bad-words-list.txt";
    // from https://www.talkinfrench.com/common-french-adverbs/
    public static final String CLEAR_IRRELEVANT_WORDS_FILE = BASE_RESOURCES_DIRECTORY+"french-irrelevant-words-list.txt";
    // from https://github.com/bnare/fr.french.verbs.lib/tree/master/src/xml
    public static final String CONJUGAISONS_FILE = BASE_RESOURCES_DIRECTORY+"conjugaison.xml";
    public static final String VERBS_FILE = BASE_RESOURCES_DIRECTORY+"verbes.xml";

    // Local variables
    public static final String SYSTEM_MODERATOR_NAME = "SYSTEM";
    public static final String MODERATION_DEFAULT_MESSAGE = "Votre annonce a été modérée car elle a été détectée ne relevant pas d'un bon comportement.";

}
