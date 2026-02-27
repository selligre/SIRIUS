package fr.upec.episen.sirius.fimafeng.announce_manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DAO (Data Access Object) pour les annonces enrichies avec les informations de l'auteur.
 * Utilisé pour les réponses du frontend avec le username et les labels type/status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnounceDAO {
    private int id;
    private String title;
    private String description;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private LocalDateTime publicationDate;
    private String statusLabel;
    private String typeLabel;
    private int status;
    private int type;
    private int authorId;
    private String username;
    private Float duration;

    @Override
    public String toString() {
        return "AnnounceDAO [" +
                "id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", dateTimeStart=" + dateTimeStart +
                ", dateTimeEnd=" + dateTimeEnd +
                ", publicationDate=" + publicationDate +
                ", statusLabel=" + statusLabel +
                ", typeLabel=" + typeLabel +
                ", status=" + status +
                ", type=" + type +
                ", authorId=" + authorId +
                ", username=" + username +
                ", duration=" + duration +
                "]";
    }
}
