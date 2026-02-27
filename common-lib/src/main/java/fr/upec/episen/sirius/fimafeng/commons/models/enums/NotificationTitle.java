package fr.upec.episen.sirius.fimafeng.commons.models.enums;

public enum NotificationTitle {
    ANNOUNCE_SAVED("Annonce sauvegardée"),
    ANNOUNCE_MODERATED("Annonce modérée"),
    ANNOUNCE_PUBLISHED("Annonce publiée"),
    ANNOUNCE_DELETED("Annonce supprimée"),
    ANNOUNCE_REMINDER("Rappel d'annonce"),
    EVENT_CANCELED("Événement annulé"),
    EVENT_SUBSCRIPTION("Inscription à un événement"),
    CORRESPONDENCE_FOUND("Correspondance trouvée");

    private final String title;

    NotificationTitle(String s) {
        this.title = s;
    }

    public String getValue() {
        return this.title;
    }

    public static NotificationTitle getNotificationTitle(String s) {
        try {
            return NotificationTitle.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No enum constant class " + NotificationTitle.class + " with value " + s);
        }
    }
}
