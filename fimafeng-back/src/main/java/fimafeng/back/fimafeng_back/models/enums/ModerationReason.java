package fimafeng.back.fimafeng_back.models.enums;

public enum ModerationReason {
    NOT_MODERATED_YET,
    INTENTION_OK,
    UNDEFINED,
    SPAM, // done
    HATE, // done
    HARASSMENT, // canceled
    DISINFORMATION,
    VIOLENCE, // detectable
    PORNOGRAPHY, // canceled
    INTIMIDATION, // canceled
    IDENTITY_STEALTH, // canceled
    PRIVACY_VIOLATION, // canceled
    VOCABULARY // done
}
