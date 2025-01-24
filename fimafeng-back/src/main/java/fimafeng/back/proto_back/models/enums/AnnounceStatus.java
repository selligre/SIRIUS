package fimafeng.back.proto_back.models.enums;

public enum AnnounceStatus {
    // The user create announce but do not click on 'publish'
    DRAFT,
    // User create announce and click on 'publish'
    TO_ANALYSE,
    // Announce get validated by the moderation algorithm
    PUBLISHED,
    // Announce do not pass the moderation algorithm
    MODERATED
}
