package fr.upec.episen.sirius.fimafeng.search.model;

import jakarta.persistence.*;
import lombok.Data; // Nécessite Lombok, sinon génère Getters/Setters
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

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}
