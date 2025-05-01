package fimafeng.back.fimafeng_back.implementations.recommendations;

import fimafeng.back.fimafeng_back.models.*;
import fimafeng.back.fimafeng_back.services.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecommendationImplementation {
    public static final Logger LOGGER = Logger.getLogger(RecommendationImplementation.class.getName());

    private TagService tagService;
    private ClientTagService clientTagService;
    private AnnounceService announceService;
    private AnnounceTagService announceTagService;
    private ClientService clientService;
    private ConsultationService consultationService;

    List<Tag> clientTags;
    Client client;
    List<Consultation> allConsultations;
    List<Announce> announces = new ArrayList<>();
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

    public RecommendationImplementation() {
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
        // 3. Reduce announces list to more pertinent announces (with a district close to the client)
        int announcesSizeLimit = 50;
        for (Announce announce : announceService.findAll()) {
            if (scoringDistrictProximity(announce) > 3)
                announces.add(announce);
            if (announces.size() > announcesSizeLimit)
                break;
        }
        // 4. Calculate score for each announce
        allConsultations = consultationService.findAll();
        allAnnounceTags = announceTagService.findAll();
        Map<Announce, Integer> scoredAnnounces = new HashMap<>();
        for (Announce announce : announces) {
            scoredAnnounces.put(announce, generateScore(announce));
        }
        // 5. Sort result
        Map<Announce, Integer> sortedScoredConsultations = sortByValues(scoredAnnounces);
        // 6. Return the amount from the best announces
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
                {2, 86}, // Séniors
                {3, 11}, // Coup de main
                {4, 52}, // Animalerie
                {5, 87}, // Administratif
                {6, 40}, // Accompagnement
                {7, 65}, // Enseignement
                {8, 3}, // Travail
                {9, 83}, // Vacances
                {10, 65}, // Chant/Théâtre
                {11, 13}, // Cinéma
                {12, 96}, // Musée
                {13, 22}, // Artisanat
                {14, 63}, // Compétition
                {15, 97}, // Plein air
                {16, 11}, // Intérieur
                {17, 67}, // Entrainement
                {18, 75}, // Initiation
                {19, 12}, // Associatif
                {20, 12}, // Environnemental
                {21, 80}, // Social
                {22, 10}, // Municipal
                {23, 74}, // Bricolage
                {24, 39}, // Jardinage
                {25, 64}, // Nettoyage
                {26, 24}, // Electronique
                {27, 58}, // Aménagement
                {28, 49}, // Cuisine
                {29, 44}, // Jeux & Jouets
                {30, 60}, // Immobilier
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
        int scoreTagPopularity = scoringTagPopularity(announce) * tagPopularityCoefficient;
        score += scoreTagPopularity;
        // 2. Add to the score the proximity between the customer's district and that of the announcement
        int districtProximityCoefficient = 4;
        int scoreDistrictProximity = scoringDistrictProximity(announce) * districtProximityCoefficient;
        score += scoreDistrictProximity;
        // 3. Add to the score the number of times the announcement has been visited
        int visitsCoefficient = 2;
        int scoreVisits = scoringVisits(announce) * visitsCoefficient;
        score += scoreVisits;
        // 4. Add to the score the number of announces viewed by the customer that have a tag in common with the current
        int tagInConsultationsCoefficient = 3;
        int scoreCommonTagInConsultations = scoringCommonTagInConsultations() * tagInConsultationsCoefficient;
        score += scoreCommonTagInConsultations;
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
        int[][] proximityValues = {
                {10, 6, 4, 2, 1, 1, 1, 1, 1, 1}, // 1
                {6, 10, 1, 2, 2, 2, 1, 1, 1, 1}, // 2
                {2, 1, 10, 6, 1, 1, 1, 1, 1, 2}, // 3
                {2, 4, 6, 10, 4, 1, 1, 1, 1, 2}, // 4
                {1, 2, 1, 6, 10, 6, 4, 1, 1, 1}, // 5
                {1, 2, 1, 1, 4, 10, 6, 1, 1, 1}, // 6
                {1, 1, 1, 1, 4, 6, 10, 4, 1, 4}, // 7
                {1, 1, 1, 1, 1, 1, 6, 10, 4, 1}, // 8
                {1, 1, 1, 1, 1, 1, 1, 4, 10, 4}, // 9
                {1, 1, 4, 2, 1, 1, 6, 1, 4, 10} // 10
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

