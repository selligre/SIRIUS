package fimafeng.back.fimafeng_back.implementations.recommendations;

import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.AnnounceTag;
import fimafeng.back.fimafeng_back.models.ClientTag;
import fimafeng.back.fimafeng_back.models.Consultation;
import fimafeng.back.fimafeng_back.services.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecommendationImplementationPOC {
    Logger LOGGER = Logger.getLogger(RecommendationImplementationPOC.class.getName());
    private final ClientTagService clientTagService;
    private final AnnounceService announceService;
    private final ConsultationService consultationService;
    private final AnnounceTagService announceTagService;
    private final TagService tagService;

    public RecommendationImplementationPOC(ClientTagService clientTagService, AnnounceService announceService, ConsultationService consultationService, AnnounceTagService announceTagService, TagService tagService) {
        LOGGER.info("started RecommendationImplementationPOC");
        this.clientTagService = clientTagService;
        this.announceService = announceService;
        this.consultationService = consultationService;
        this.announceTagService = announceTagService;
        this.tagService = tagService;
    }

    /**
     * Returns the 10 announces that are best suited for the client.
     * Triggered on http://localhost:8080/client/recommendations/{id} using Postman.
     *
     * @param clientId the client id
     * @return the list of announces
     */
    public List<Announce> generateRecommendations(int clientId) {
        // 1. Get client tags from clientId
        List<Integer> clientTags = new ArrayList<>();
        for (ClientTag tag : clientTagService.findAll()) {
            if (tag.getRefClientId() == clientId) clientTags.add(tag.getRefTagId());
        }
        // 2. Get tags popularity from tags
        // Values are determined arbitrarily (based on personal judgement)
        // https://www.baeldung.com/java-initialize-hashmap
        Map<Integer, Integer> tagPopularity = Stream.of(new Object[][]{{1, 1}, // Enfants
                {2, 7}, // Séniors
                {3, 6}, // Coup de main
                {4, 8}, // Animalerie
                {5, 5}, // Administratif
                {6, 6}, // Accompagnement
                {7, 7}, // Enseignement
                {8, 8}, // Travail
                {9, 7}, // Vacances
                {10, 6}, // Chant/Théâtre
                {11, 8}, // Cinéma
                {12, 7}, // Musée
                {13, 5}, // Artisanat
                {14, 6}, // Compétition
                {15, 8}, // Plein air
                {16, 6}, // Intérieur
                {17, 7}, // Entrainement
                {18, 6}, // Initiation
                {19, 5}, // Associatif
                {20, 7}, // Environnemental
                {21, 7}, // Social
                {22, 6}, // Municipal
                {23, 5}, // Bricolage
                {24, 6}, // Jardinage
                {25, 5}, // Nettoyage
                {26, 7}, // Électronique
                {27, 6}, // Aménagement
                {28, 7}, // Cuisine
                {29, 8}, // Jeux & Jouets
                {30, 7}, // Immobilier
                {31, 6}, // Véhicule
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (Integer) data[1]));
        // Impact tags popularity with clients tags
        for (Integer tagKey : tagPopularity.keySet()) {
            String tagKeyCategory = tagService.findById(tagKey).getCategory();
            for (Integer clientTagId : clientTags) {
                String clientTagIdCategory = tagService.findById(clientTagId).getCategory();
                if (tagKeyCategory.equals(clientTagIdCategory)) {
                    // bump up the tag popularity if it is the same as the client
                    tagPopularity.put(tagKey, tagPopularity.get(tagKey) * 10);
                }
            }
        }
        // For each Announce
        List<Announce> announceList = new ArrayList<>(announceService.findAll());
        List<Consultation> consultations = new ArrayList<>(consultationService.findAll());
        // List<Integer> announcesScores = new ArrayList<>();
        Map<Integer, Integer> announcesScores = new HashMap<>();
        for (Announce announce : announceList) {
            // 3. Get announce number of consultations
            int numberOfConsultations = 0;
            for (Consultation consultation : consultations) {
                if (consultation.getRefAnnounceId() == announce.getId()) numberOfConsultations++;
            }
            // 4. Calculate announce score
            int popularityImportance = 3;
            int popularityTag1 = 0;
            int popularityTag2 = 0;
            List<AnnounceTag> announceTags = new ArrayList<>(announceTagService.findAll());
            for (AnnounceTag announceTag : announceTags) {
                if (announceTag.getRefAnnounceId() == announce.getId()) {
                    if (popularityTag1 == 0) popularityTag1 = tagPopularity.get(announceTag.getRefTagId());
                    if (popularityTag2 == 0 && popularityTag1 != 0)
                        popularityTag2 = tagPopularity.get(announceTag.getRefTagId());
                }
            }
            int consultationsImportance = 1;
            int score = (popularityTag1 + popularityTag2) * popularityImportance + (numberOfConsultations) * consultationsImportance;
            // announcesScores.add(announce.getId(), score);
            announcesScores.put(announce.getId(), score);
        }
        // Return 10 best scores
        ArrayList<Announce> bestAnnounces = new ArrayList<>();
        while (bestAnnounces.size() < 10) {
            int max = Collections.max(announcesScores.values());
            for (Integer key : announcesScores.keySet()) {
                if (announcesScores.get(key) == max) {
                    bestAnnounces.add(announceService.findById(key));
                    announcesScores.remove(key);
                    break;
                }
            }
        }
        return bestAnnounces;
    }
}
