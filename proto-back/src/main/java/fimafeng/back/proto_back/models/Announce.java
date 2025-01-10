package fimafeng.back.proto_back.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "announce")
public class Announce {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnnounce;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date_time_start")
    private Date dateTimeStart;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "date_time_end")
    private Date dateTimeEnd;

    @Column(name = "is_recurrent")
    private Boolean isRecurrent;

}