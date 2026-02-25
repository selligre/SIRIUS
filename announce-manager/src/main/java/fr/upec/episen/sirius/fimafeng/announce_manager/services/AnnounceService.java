package fr.upec.episen.sirius.fimafeng.announce_manager.services;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.commons.models.enums.AnnounceStatus;
import fr.upec.episen.sirius.fimafeng.commons.models.enums.AnnounceType;
import fr.upec.episen.sirius.fimafeng.announce_manager.dtos.AnnounceDTO;
import fr.upec.episen.sirius.fimafeng.announce_manager.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class AnnounceService {

    @Autowired
    private AnnounceRepository announceRepository;

    /**
     * Crée une nouvelle annonce à partir du DTO provenant du formulaire web
     * @param announceDTO Le DTO contenant les données de l'annonce
     * @return L'annonce créée et sauvegardée
     */
    public Announce createAnnounce(AnnounceDTO announceDTO) {
        Announce announce = new Announce();

        // Mapper les champs texte
        announce.setTitle(announceDTO.getTitle());
        announce.setDescription(announceDTO.getDescription());

        // Convertir les LocalDateTime en java.util.Date
        announce.setDateTimeStart(Timestamp.valueOf(announceDTO.getDateTimeStart()));
        announce.setDateTimeEnd(Timestamp.valueOf(announceDTO.getDateTimeEnd()));
        announce.setPublicationDate(Timestamp.valueOf(announceDTO.getPublicationDate()));

        // Mapper le statut depuis l'entier vers l'enum
        AnnounceStatus status = AnnounceStatus.fromValue(announceDTO.getStatus());
        announce.setStatus(status);

        // Mapper le type depuis l'entier vers l'enum
        AnnounceType type = AnnounceType.fromValue(announceDTO.getType());
        announce.setType(type);

        // Assigner l'author ID de l'utilisateur connecté
        announce.setAuthorId(announceDTO.getAuthorId());

        // Calculer la durée en heures (optionnel)
        long durationMinutes = java.time.temporal.ChronoUnit.MINUTES.between(
            announceDTO.getDateTimeStart(),
            announceDTO.getDateTimeEnd()
        );
        float durationHours = durationMinutes / 60.0f;
        announce.setDuration(durationHours);

        // Sauvegarder dans la base de données
        return announceRepository.save(announce);
    }
}
