package fr.upec.episen.sirius.fimafeng.search.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "announce")
public class Announce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private BigDecimal price;
    
    private String author;
    
    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;
    
    private Integer type;      // 0=service, 1=pret, 2=evenement
    
    private Integer status;      // 0=brouillon, 1=a_analyser, 2=publiée, 3=moderée
    
    @Column(name = "date_time_start")
    private LocalDateTime dateTimeStart;
    
    @Column(name = "date_time_end")
    private LocalDateTime dateTimeEnd;
    
    private Float duration;    // durée en heures
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
