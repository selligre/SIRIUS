package fr.upec.episen.sirius.fimafeng.announce_manager.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnnounceDTO {
    private String title;
    private String description;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private LocalDateTime publicationDate;
    private int status;
    private int type;
    private int authorId;

    @Override
    public String toString() {
        return "AnnounceDTO [title=" + title 
            + ", description=" + description
            + ", dateTimeStart=" + dateTimeStart
            + ", dateTimeEnd=" + dateTimeEnd
            + ", publicationDate=" + publicationDate
            + ", status=" + status
            + ", type=" + type
            + ", authorId=" + authorId + "]";
    }

    
}
