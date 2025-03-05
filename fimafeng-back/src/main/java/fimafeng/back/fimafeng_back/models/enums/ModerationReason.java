package fimafeng.back.fimafeng_back.models.enums;

public enum ModerationReason {
    NOT_MODERATED_YET,
    INTENTION_OK,
    UNDEFINED,
    SPAM, // need algorithm
    HATE, // detectable
    HARASSMENT, // complicated
    DISINFORMATION,
    VIOLENCE, // detectable
    PORNOGRAPHY, // detectable
    INTIMIDATION, // complicated
    IDENTITY_STEALTH, // impossible
    PRIVACY_VIOLATION // impossible
}
