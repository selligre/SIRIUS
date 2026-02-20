package fr.upec.episen.sirius.fimafeng.models.enums;
public enum AnnounceStatus {
    DRAFT(0),
    TO_ANALYSE(1),
    PUBLISHED(2),
    MODERATED(3);

    private final int value;

    AnnounceStatus(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public static AnnounceStatus fromValue(int i) {
        for (AnnounceStatus status : AnnounceStatus.values()) {
            if (status.getValue() == i) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant " + AnnounceStatus.class + " with value " + i);
    }
}
