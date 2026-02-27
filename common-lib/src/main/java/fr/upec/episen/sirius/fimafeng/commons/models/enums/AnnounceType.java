package fr.upec.episen.sirius.fimafeng.commons.models.enums;

public enum AnnounceType {
    SERVICE(0),
    LOAN(1),
    EVENT(2);

    private final int id;

    AnnounceType(int i) {
        this.id = i;
    }

    public int getValue() {
        return this.id;
    }

    public static AnnounceType fromValue(int i) {
        for (AnnounceType type : AnnounceType.values()) {
            if (type.getValue() == i) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + AnnounceType.class + " with value " + i);
    }
}
