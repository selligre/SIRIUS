package fr.upec.episen.sirius.fimafeng.notifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.upec.episen.sirius.fimafeng.commons.models.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * Récupère toutes les notifications d'un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return La liste de toutes les notifications de cet utilisateur
     */
    List<Notification> findByUserId(int userId);

    /**
     * Récupère toutes les notifications non-lues d'un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return La liste des notifications non-lues
     */
    List<Notification> findByUserIdAndHasBeenRedFalse(int userId);

    /**
     * Compte le nombre de notifications non-lues pour un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return Le nombre de notifications non-lues
     */
    long countByUserIdAndHasBeenRedFalse(int userId);

    /**
     * Récupère les notifications d'un utilisateur pour une annonce spécifique
     * @param userId L'ID de l'utilisateur
     * @param announceId L'ID de l'annonce
     * @return La liste des notifications pour cette annonce
     */
    List<Notification> findByUserIdAndAnnounceId(int userId, int announceId);
}