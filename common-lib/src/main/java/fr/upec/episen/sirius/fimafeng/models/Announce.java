package fr.upec.episen.sirius.fimafeng.models;

import fr.upec.episen.sirius.fimafeng.models.enums.AnnounceStatus;
import fr.upec.episen.sirius.fimafeng.models.enums.AnnounceType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date publicationDate;

    private AnnounceStatus status;

    private AnnounceType type;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date dateTimeStart;

    private Float duration;

    private Date dateTimeEnd;

    private int authorId;

    @Override
    public String toString() {
        String res = "Announce[";
        res += "id: " + id + ", ";
        res += "publicationDate: " + publicationDate + ", ";
        res += "status: " + status + ", ";
        res += "type: " + type + ", ";
        res += "title: " + title + ", ";
        res += "description: " + description + ", ";
        res += "dateTimeStart: " + dateTimeStart + ", ";
        res += "duration: " + duration + ", ";
        res += "dateTimeEnd: " + dateTimeEnd + ", ";
        res += "authorId: " + authorId + "]";
        return res;
    }
}