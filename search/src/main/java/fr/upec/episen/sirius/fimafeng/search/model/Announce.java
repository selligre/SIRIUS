package fr.upec.episen.sirius.fimafeng.search.model;

import jakarta.persistence.*;
import lombok.Data; // Nécessite Lombok, sinon génère Getters/Setters
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "announces")
public class Announce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private BigDecimal prix;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}
