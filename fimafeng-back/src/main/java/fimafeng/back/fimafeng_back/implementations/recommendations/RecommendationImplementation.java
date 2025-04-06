package fimafeng.back.fimafeng_back.implementations.recommendations;

import fimafeng.back.fimafeng_back.models.*;
import fimafeng.back.fimafeng_back.services.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecommendationImplementation {
    public static final Logger LOGGER = Logger.getLogger(RecommendationImplementation.class.getName());

    private final TagService tagService;
    private final ClientTagService clientTagService;
    private final AnnounceService announceService;
    private final AnnounceTagService announceTagService;
    private final ClientService clientService;
    private final ConsultationService consultationService;

    List<Tag> clientTags;
    Client client;
    List<Consultation> allConsultations;
    List<Announce> announces;
    List<AnnounceTag> allAnnounceTags;

    public RecommendationImplementation(TagService tagService, ClientTagService clientTagService, AnnounceService announceService, AnnounceTagService announceTagService, ClientService clientService, ConsultationService consultationService) {
        // LOGGER.info("started RecommendationImplementation()");
        this.tagService = tagService;
        this.clientTagService = clientTagService;
        this.announceService = announceService;
        this.announceTagService = announceTagService;
        this.clientService = clientService;
        this.consultationService = consultationService;
    }

    /**
     * @param clientId client's id
     * @param amount   number of announces to return
     * @return list of the announces that got the highest recommendation score
     */
    public List<Announce> generateRecommendations(int clientId, int amount) {
        LOGGER.info("started generateRecommendations(" + clientId + ", " + amount + ")");
        // 1. Get client
        client = clientService.findById(clientId);
        // 2. Get client tags from clientId
        clientTags = getClientTags(clientId);
        // 3. Calculate score for each announce
        announces = announceService.findAll();
        allConsultations = consultationService.findAll();
        allAnnounceTags = announceTagService.findAll();
        Map<Announce, Integer> scoredAnnounces = new HashMap<>();
        for (Announce announce : announces) {
            scoredAnnounces.put(announce, generateScore(announce));
        }
        // 4. Sort result
        Map<Announce, Integer> sortedScoredConsultations = sortByValues(scoredAnnounces);
        // 5. Return the amount from the best announces
        List<Announce> chosenAnnounces = new ArrayList<>();
        for (Announce announce : sortedScoredConsultations.keySet()) {
            if (chosenAnnounces.size() < amount) {
                chosenAnnounces.add(announce);
            } else {
                break;
            }
        }
        return chosenAnnounces;
    }

    // https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    /**
     * @param clientId client's id
     * @return list of tags corresponding to the client's --> VERIFIED
     */
    public List<Tag> getClientTags(int clientId) {
        List<Tag> result = new ArrayList<>();
        for (ClientTag clientTag : clientTagService.findAll())
            if (clientTag.getRefClientId() == clientId) result.add(tagService.findById(clientTag.getRefTagId()));
        return result;
    }

    /**
     * @param tag measured tag
     * @return int value corresponding to the popularity of the tag, else returns -1 --> VERIFIED
     */
    public int getTagPopularity(Tag tag) {
        // Values are determined arbitrarily (based on personal judgement)
        // https://www.baeldung.com/java-initialize-hashmap
        Map<Integer, Integer> tagPopularityValues = Stream.of(new Object[][]{{1, 1}, // Enfants
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
                {14, 6}, // Comptétition
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
                {26, 7}, // Electronique
                {27, 6}, // Aménagement
                {28, 7}, // Cuisine
                {29, 8}, // Jeux & Jouets
                {30, 7}, // Immobilier
                {31, 6}, // Véhicule
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (Integer) data[1]));
        for (Map.Entry<Integer, Integer> entry : tagPopularityValues.entrySet()) {
            if (entry.getKey() == tag.getId()) {
                return tagPopularityValues.get(entry.getKey());
            }
        }
        return 0;
    }

    /**
     * @param announce announce analyzed
     * @return score value
     */
    private Integer generateScore(Announce announce) {
        int score = 0;
        // 1. Add tags to the score according to their popularity
        int tagPopularityCoefficient = 1;
        int scoreTagPopularity = scoringTagPopularity(announce);
        score += scoreTagPopularity * tagPopularityCoefficient;
        // 2. Add to the score the proximity between the customer's district and that of the announcement
        int districtProximityCoefficient = 1;
        int scoreDistrictProximity = scoringDistrictProximity(announce);
        score += scoreDistrictProximity * districtProximityCoefficient;
        // 3. Add to the score the number of times the announcement has been visited
        int visitsCoefficient = 1;
        int scoreVisits = scoringVisits(announce);
        score += scoreVisits * visitsCoefficient;
        // 4. Add to the score the number of announces viewed by the customer that have a tag in common with the current
        int tagInConsultationsCoefficient = 1;
        int scoreCommonTagInConsultations = scoringCommonTagInConsultations();
        score += scoreCommonTagInConsultations * tagInConsultationsCoefficient;
        // Return final score
        LOGGER.info("announce: " + announce.getId() + ", tagPopularity: " + scoreTagPopularity + ", districtProximity: " + scoreDistrictProximity + ", visits: " + scoreVisits + ", commonTagInConsultations: " + scoreCommonTagInConsultations + ", score: " + score);
        return score;
    }

    /**
     * @param announce announce analyzed
     * @return score part related to tags popularity
     */
    public int scoringTagPopularity(Announce announce) {
        int score = 0;
        // Retrieve announce tags
        List<AnnounceTag> allAnnounceTags = announceTagService.findAll();
        List<Tag> announceTags = new ArrayList<>();
        for (AnnounceTag announceTag : allAnnounceTags) {
            if (announceTag.getRefAnnounceId() == announce.getId()) {
                announceTags.add(tagService.findById(announceTag.getRefTagId()));
            }
        }
        // Compare to client tags
        for (Tag announceTag : announceTags) {
            for (Tag clientTag : clientTags) {
                if (announceTag.getId() == clientTag.getId()) {
                    // If tag in common, then augment the tag popularity value
                    score += getTagPopularity(clientTag) * 10;
                } else {
                    // Else, just add the tag popularity value
                    score += getTagPopularity(clientTag);
                }
            }
        }
        // Return score
        return score;
    }

    /**
     * @param announce announce analyzed
     * @return score part related to district proximity
     */
    public int scoringDistrictProximity(Announce announce) {
        int score = 0;
        // Retrieve districts
        int announceDistrictId = announce.getRefDistrictId();
        int clientDistrictId = client.getDistrict();
        // Mapping proximity values (arbitrarily)
        int[][] proximityValues = {{5, 3, 2, 1, 0, 0, 0, 0, 0, 0}, // 1
                {3, 5, 0, 1, 1, 1, 0, 0, 0, 0}, // 2
                {1, 0, 5, 3, 0, 0, 0, 0, 0, 1}, // 3
                {1, 2, 3, 5, 2, 0, 0, 0, 0, 1}, // 4
                {0, 1, 0, 3, 5, 3, 2, 0, 0, 0}, // 5
                {0, 1, 0, 0, 2, 5, 3, 0, 0, 0}, // 6
                {0, 0, 0, 0, 2, 3, 5, 2, 0, 2}, // 7
                {0, 0, 0, 0, 0, 0, 3, 5, 2, 0}, // 8
                {0, 0, 0, 0, 0, 0, 0, 2, 5, 2}, // 9
                {0, 0, 2, 1, 0, 0, 3, 0, 2, 5} // 10
        };
        // Add value to score
        for (int i = 0; i < proximityValues.length; i++) {
            for (int j = 0; j < proximityValues[i].length; j++) {
                if (i == announceDistrictId && j == clientDistrictId) score += proximityValues[i][j];
            }
        }
        // Return score
        return score;
    }

    /**
     * @param announce announce analyzed
     * @return score part related to consultations
     */
    public int scoringVisits(Announce announce) {
        int score = 0;
        // Retrieve consultations
        for (Consultation consultation : allConsultations) {
            if (consultation.getRefAnnounceId() == announce.getId()) {
                // Add to score
                score++;
            }
        }
        // Return score
        return score;
    }

    /**
     * @return score part related to common tag in consultations
     */
    public int scoringCommonTagInConsultations() {
        int score = 0;
        // Retrieve client consultations
        List<Consultation> clientConsultations = new ArrayList<>();
        for (Consultation consultation : allConsultations) {
            if (consultation.getRefUserId() == client.getId()) {
                clientConsultations.add(consultation);
            }
        }
        // Retrieve tags from visited announces by client
        List<Tag> visitedAnnounceTags = new ArrayList<>();
        for (Consultation consultation : clientConsultations) {
            for (AnnounceTag announceTag : allAnnounceTags) {
                if (consultation.getRefAnnounceId() == announceTag.getRefAnnounceId()) {
                    visitedAnnounceTags.add(tagService.findById(announceTag.getRefTagId()));
                }
            }
        }
        // Go through tags from visited announces
        for (Tag visitedAnnounceTag : visitedAnnounceTags) {
            for (Tag clientTag : clientTags) {
                // If tag in common with client, then add to the score
                if (visitedAnnounceTag.getId() == clientTag.getId()) {
                    score++;
                }
            }
        }
        // Return score
        return score;
    }
}

