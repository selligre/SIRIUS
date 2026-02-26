package fr.upec.episen.sirius.fimafeng.announce_manager.services;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.commons.models.enums.AnnounceStatus;
import fr.upec.episen.sirius.fimafeng.commons.models.enums.AnnounceType;
import fr.upec.episen.sirius.fimafeng.announce_manager.dtos.AnnounceDTO;
import fr.upec.episen.sirius.fimafeng.announce_manager.dtos.AnnounceDAO;
import fr.upec.episen.sirius.fimafeng.announce_manager.repositories.AnnounceRepository;
import fr.upec.episen.sirius.fimafeng.announce_manager.utils.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnounceService {

    @Autowired
    private AnnounceRepository announceRepository;

    @Autowired
    private NotificationClient notificationClient;

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
        Announce savedAnnounce = announceRepository.save(announce);

        // Créer une notification pour l'utilisateur
        try {
            notificationClient.notifyAnnounceCreated(
                announceDTO.getAuthorId(),
                (int) savedAnnounce.getId(),
                "ANNOUNCE_SAVED",
                "Votre annonce '" + announceDTO.getTitle() + "' a été créée avec succès."
            );
        } catch (Exception e) {
            // On log l'erreur mais on ne lève pas d'exception pour ne pas bloquer
            System.err.println("Erreur lors de la création de la notification: " + e.getMessage());
        }

        return savedAnnounce;
    }

    /**
     * Récupère toutes les annonces d'un utilisateur
     * @param authorId L'ID de l'utilisateur
     * @return Une liste des annonces de cet utilisateur
     */
    public List<Announce> getAnnouncesByAuthorId(int authorId) {
        return announceRepository.findByAuthorId(authorId);
    }

    /**
     * Récupère une annonce par son ID
     * @param id L'ID de l'annonce
     * @return L'annonce si elle existe
     */
    public Optional<Announce> getAnnounceById(int id) {
        return announceRepository.findById(id);
    }

    /**
     * Met à jour une annonce existante
     * @param id L'ID de l'annonce
     * @param announceDTO Les nouvelles données
     * @param authorId L'ID de l'utilisateur effectuant la modification
     * @return L'annonce mise à jour
     * @throws IllegalAccessException Si l'utilisateur n'est pas l'auteur
     */
    public Announce updateAnnounce(int id, AnnounceDTO announceDTO, int authorId) throws IllegalAccessException {
        Optional<Announce> optional = announceRepository.findById(id);
        
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Announcement not found");
        }
        
        Announce announce = optional.get();
        
        // Vérifier que l'utilisateur est bien l'auteur
        if (announce.getAuthorId() != authorId) {
            throw new IllegalAccessException("You are not authorized to modify this announcement");
        }
        
        // Mettre à jour les champs
        announce.setTitle(announceDTO.getTitle());
        announce.setDescription(announceDTO.getDescription());
        announce.setDateTimeStart(Timestamp.valueOf(announceDTO.getDateTimeStart()));
        announce.setDateTimeEnd(Timestamp.valueOf(announceDTO.getDateTimeEnd()));
        
        AnnounceStatus status = AnnounceStatus.fromValue(announceDTO.getStatus());
        announce.setStatus(status);
        
        AnnounceType type = AnnounceType.fromValue(announceDTO.getType());
        announce.setType(type);
        
        // Recalculer la durée
        long durationMinutes = java.time.temporal.ChronoUnit.MINUTES.between(
            announceDTO.getDateTimeStart(),
            announceDTO.getDateTimeEnd()
        );
        float durationHours = durationMinutes / 60.0f;
        announce.setDuration(durationHours);
        
        Announce updatedAnnounce = announceRepository.save(announce);

        // Créer une notification pour l'utilisateur
        try {
            notificationClient.notifyAnnounceUpdated(
                authorId,
                id,
                "ANNOUNCE_SAVED",
                "Votre annonce '" + announceDTO.getTitle() + "' a été modifiée avec succès."
            );
        } catch (Exception e) {
            // On log l'erreur mais on ne lève pas d'exception pour ne pas bloquer
            System.err.println("Erreur lors de la création de la notification: " + e.getMessage());
        }

        return updatedAnnounce;
    }

    /**
     * Supprime une annonce
     * @param id L'ID de l'annonce
     * @param authorId L'ID de l'utilisateur effectuant la suppression
     * @throws IllegalAccessException Si l'utilisateur n'est pas l'auteur
     */
    public void deleteAnnounce(int id, int authorId) throws IllegalAccessException {
        Optional<Announce> optional = announceRepository.findById(id);
        
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Announcement not found");
        }
        
        Announce announce = optional.get();
        
        // Vérifier que l'utilisateur est bien l'auteur
        if (announce.getAuthorId() != authorId) {
            throw new IllegalAccessException("You are not authorized to delete this announcement");
        }

        String announceTitle = announce.getTitle();
        
        announceRepository.deleteById(id);

        // Créer une notification pour l'utilisateur
        try {
            notificationClient.notifyAnnounceDeleted(
                authorId,
                id,
                "ANNOUNCE_DELETED",
                "Votre annonce '" + announceTitle + "' a été supprimée avec succès."
            );
        } catch (Exception e) {
            // On log l'erreur mais on ne lève pas d'exception pour ne pas bloquer
            System.err.println("Erreur lors de la création de la notification: " + e.getMessage());
        }
    }

    /**
     * Récupère toutes les annonces enrichies avec les informations de l'auteur
     * @return Une liste de tous les AnnounceDAO avec le username de l'auteur
     */
    public List<AnnounceDAO> getAllAnnouncesWithAuthorInfo() {
        List<Map<String, Object>> results = announceRepository.findAllWithAuthorInfo();
        return results.stream()
                .map(this::mapToAnnounceDAO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une annonce enrichie par son ID avec les informations de l'auteur
     * @param id L'ID de l'annonce
     * @return Un Optional contenant l'AnnounceDAO si trouvé
     */
    public Optional<AnnounceDAO> getAnnounceByIdWithAuthorInfo(int id) {
        Optional<Map<String, Object>> result = announceRepository.findByIdWithAuthorInfo(id);
        return result.map(this::mapToAnnounceDAO);
    }

    /**
     * Récupère toutes les annonces d'un utilisateur enrichies avec les informations de l'auteur
     * @param authorId L'ID de l'auteur
     * @return Une liste des AnnounceDAO de cet utilisateur
     */
    public List<AnnounceDAO> getAnnouncesByAuthorIdWithAuthorInfo(int authorId) {
        List<Map<String, Object>> results = announceRepository.findByAuthorIdWithAuthorInfo(authorId);
        return results.stream()
                .map(this::mapToAnnounceDAO)
                .collect(Collectors.toList());
    }

    /**
     * Convertit une Map de résultats SQL en objet AnnounceDAO
     * @param map La map contenant les données de la base
     * @return L'objet AnnounceDAO converti
     */
    private AnnounceDAO mapToAnnounceDAO(Map<String, Object> map) {
        AnnounceDAO dao = new AnnounceDAO();
        
        dao.setId(((Number) map.get("id")).intValue());
        dao.setTitle((String) map.get("title"));
        dao.setDescription((String) map.get("description"));
        
        // Conversion des timestamps
        Object dateTimeStart = map.get("date_time_start");
        if (dateTimeStart != null) {
            if (dateTimeStart instanceof java.sql.Timestamp) {
                dao.setDateTimeStart(((java.sql.Timestamp) dateTimeStart).toLocalDateTime());
            } else if (dateTimeStart instanceof java.util.Date) {
                dao.setDateTimeStart(((java.util.Date) dateTimeStart).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        }
        
        Object dateTimeEnd = map.get("date_time_end");
        if (dateTimeEnd != null) {
            if (dateTimeEnd instanceof java.sql.Timestamp) {
                dao.setDateTimeEnd(((java.sql.Timestamp) dateTimeEnd).toLocalDateTime());
            } else if (dateTimeEnd instanceof java.util.Date) {
                dao.setDateTimeEnd(((java.util.Date) dateTimeEnd).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        }
        
        Object publicationDate = map.get("publication_date");
        if (publicationDate != null) {
            if (publicationDate instanceof java.sql.Timestamp) {
                dao.setPublicationDate(((java.sql.Timestamp) publicationDate).toLocalDateTime());
            } else if (publicationDate instanceof java.util.Date) {
                dao.setPublicationDate(((java.util.Date) publicationDate).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        }
        
        // Récupération du status (entier ou enum)
        Object statusObj = map.get("status");
        int statusValue = 0;
        String statusLabel = "UNKNOWN";
        if (statusObj != null) {
            if (statusObj instanceof Number) {
                statusValue = ((Number) statusObj).intValue();
            } else if (statusObj instanceof String) {
                try {
                    statusValue = Integer.parseInt((String) statusObj);
                } catch (NumberFormatException e) {
                    statusLabel = (String) statusObj;
                    statusValue = -1;
                }
            }
            if (statusValue >= 0) {
                AnnounceStatus status = AnnounceStatus.fromValue(statusValue);
                dao.setStatus(status.getValue());
                dao.setStatusLabel(status.name());
            } else {
                dao.setStatusLabel(statusLabel);
            }
        }
        
        // Récupération du type (entier ou enum)
        Object typeObj = map.get("type");
        int typeValue = 0;
        String typeLabel = "UNKNOWN";
        if (typeObj != null) {
            if (typeObj instanceof Number) {
                typeValue = ((Number) typeObj).intValue();
            } else if (typeObj instanceof String) {
                try {
                    typeValue = Integer.parseInt((String) typeObj);
                } catch (NumberFormatException e) {
                    typeLabel = (String) typeObj;
                    typeValue = -1;
                }
            }
            if (typeValue >= 0) {
                AnnounceType type = AnnounceType.fromValue(typeValue);
                dao.setType(type.getValue());
                dao.setTypeLabel(type.name());
            } else {
                dao.setTypeLabel(typeLabel);
            }
        }
        
        dao.setAuthorId(((Number) map.get("author_id")).intValue());
        dao.setUsername((String) map.get("username"));
        
        Object duration = map.get("duration");
        if (duration != null) {
            dao.setDuration(((Number) duration).floatValue());
        }
        
        return dao;
    }

}

