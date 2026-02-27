package fr.upec.episen.sirius.fimafeng.search.models;

import lombok.Data;
import java.util.Date;

@Data
public class AnnounceDTO {

    private int id;

    private Date publicationDate;

    private int status;

    private int type;

    private String title;

    private String description;

    private Date dateTimeStart;

    private Float duration;

    private Date dateTimeEnd;

    private int authorId;

    private String authorUsername;

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
        res += "authorId: " + authorId + ", ";
        res += "authorUsername: " + authorUsername + "]";
        return res;
    }

}
