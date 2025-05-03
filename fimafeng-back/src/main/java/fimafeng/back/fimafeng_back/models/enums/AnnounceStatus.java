package fimafeng.back.fimafeng_back.models.enums;

public enum AnnounceStatus {
    // The user create announce but do not click on 'publish'
    DRAFT,
    // User create announce and click on 'publish'
    TO_ANALYSE,
    // Announce get validated by the moderation algorithm
    PUBLISHED,
    // Announce get removed by the USER
    REMOVED,
    // Announce do not pass the moderation algorithm
    MODERATED,
    // Announce get refused by a moderator
    REFUSED,
    // Announce get approved by a moderator
    APPROVED
}
