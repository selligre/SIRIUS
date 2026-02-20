package fr.upec.episen.sirius.fimafeng.models;

import fr.upec.episen.sirius.fimafeng.models.enums.AnnounceStatus;
import fr.upec.episen.sirius.fimafeng.models.enums.AnnounceType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "announce")
public class Announce {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private AnnounceStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AnnounceType type;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "date_time_start")
    private Date dateTimeStart;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "date_time_end")
    private Date dateTimeEnd;

    @Column(name = "ref_author_id")
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