package fimafeng.back.fimafeng_back.implementations.recommendations;

import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.Client;
import fimafeng.back.fimafeng_back.models.Consultation;
import fimafeng.back.fimafeng_back.models.Tag;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of functions from RecommendationImplementation
 */
public class RecommendationImplementationTest {

    @Test
    public void correctTagPopularity() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        Tag tag = new Tag();
        tag.setId(7);
        // When
        int tagPopularity = recommendationImplementation.getTagPopularity(tag);
        // Then
        Assert.assertEquals(65, tagPopularity);
    }

    @Test
    public void missingTagPopularity() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        Tag tag = new Tag();
        tag.setId(999);
        // When
        int tagPopularity = recommendationImplementation.getTagPopularity(tag);
        // Then
        Assert.assertEquals(0, tagPopularity);
    }

    @Test
    public void correctDistrictProximity() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        recommendationImplementation.client = new Client();
        recommendationImplementation.client.setDistrict(1);
        Announce announce = new Announce();
        announce.setRefDistrictId(1);
        // When
        int districtProximity = recommendationImplementation.scoringDistrictProximity(announce);
        // Then
        Assert.assertEquals(10, districtProximity);
    }

    @Test
    public void missingClientDistrictProximity() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        recommendationImplementation.client = new Client();
        recommendationImplementation.client.setDistrict(99);
        Announce announce = new Announce();
        announce.setRefDistrictId(1);
        // When
        int districtProximity = recommendationImplementation.scoringDistrictProximity(announce);
        // Then
        Assert.assertEquals(0, districtProximity);
    }

    @Test
    public void missingAnnounceDistrictProximity() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        recommendationImplementation.client = new Client();
        recommendationImplementation.client.setDistrict(1);
        Announce announce = new Announce();
        announce.setRefDistrictId(99);
        // When
        int districtProximity = recommendationImplementation.scoringDistrictProximity(announce);
        // Then
        Assert.assertEquals(0, districtProximity);
    }

    @Test
    public void correctVisits() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        Announce announce = new Announce();
        announce.setId(1);
        List<Consultation> allConsultations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Consultation consultation = new Consultation();
            consultation.setRefAnnounceId(i);
            allConsultations.add(consultation);
        }
        recommendationImplementation.allConsultations = allConsultations;
        // When
        int visits = recommendationImplementation.scoringVisits(announce);
        // Then
        Assert.assertEquals(1, visits);
    }

    @Test
    public void noVisits() {
        // Given
        RecommendationImplementation recommendationImplementation = new RecommendationImplementation();
        Announce announce = new Announce();
        announce.setId(1);
        List<Consultation> allConsultations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Consultation consultation = new Consultation();
            consultation.setRefAnnounceId(99);
            allConsultations.add(consultation);
        }
        recommendationImplementation.allConsultations = allConsultations;
        // When
        int visits = recommendationImplementation.scoringVisits(announce);
        // Then
        Assert.assertEquals(0, visits);
    }

}
