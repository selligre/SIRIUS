package fimafeng.back.fimafeng_back.implementations.moderation;

public class ModerationConfiguration {
    public static final String BASE_RESOURCES_DIRECTORY = "moderation-files/";
    // from https://github.com/darwiin/french-badwords-list
    public static final String CLEAR_BAD_WORDS_FILE_NAME = BASE_RESOURCES_DIRECTORY+"french-bad-words-list-clear.txt";
    // from https://fr.wiktionary.org/w/index.php?title=Cat%C3%A9gorie:Insultes_en_fran%C3%A7ais
    public static final String CRYPTED__BAD_WORDS_FILE_NAME = BASE_RESOURCES_DIRECTORY+"french-bad-words-list.txt";
    // from https://www.talkinfrench.com/common-french-adverbs/
    public static final String CLEAR_IRRELEVANT_WORDS_FILE_NAME = "french_irrelevant_words_list.txt";

    // Local variables
    public static final String SYSTEM_MODERATOR_NAME = "SYSTEM";
    public static final String MODERATION_DEFAULT_MESSAGE = "Votre annonce a été modérée car elle a été détectée ne relevant pas d'un bon comportement.";

}
