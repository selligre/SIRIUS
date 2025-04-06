package fimafeng.back.fimafeng_back.implementations.mocks;

import fimafeng.back.fimafeng_back.models.*;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.AnnounceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnnounceFactoryTest {

    private AnnounceFactory announceFactory;
    private List<Tag> tags;
    private List<Location> locations;
    private List<Client> clients;
    private List<District> districts;

    @BeforeEach
    public void setUp() {
        announceFactory = new AnnounceFactory();

        // Initialize test data
        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(2);
        tag2.setName("tag2");

        tags = Arrays.asList(tag1, tag2);

        Location location1 = new Location();
        location1.setIdLocation(1L);
        location1.setLatitude(0.0);
        location1.setLongitude(0.0);
        location1.setName("location1");
        location1.setRef_district(1);
        Location location2 = new Location();
        location2.setIdLocation(2L);
        location2.setLatitude(1.0);
        location2.setLongitude(1.0);
        location2.setName("Location2");
        location2.setRef_district(2);

        locations = Arrays.asList(location1, location2);

        Client client1 = new Client();
        client1.setId(1);
        Client client2 = new Client();
        client2.setId(2);

        clients = Arrays.asList(client1, client2);

        District district1 = new District();
        district1.setId(1);
        district1.setPopulationPercentile(50);
        District district2 = new District();
        district2.setId(2);
        district2.setPopulationPercentile(50);

        districts = Arrays.asList(district1, district2);
    }

    @Test
    public void testSelectLocationBasedOnPopulationAndGenerateAnnounce() {
        // Test selectLocationBasedOnPopulation
        Location selectedLocation = announceFactory.selectLocationBasedOnPopulation(locations, districts);
        assertNotNull(selectedLocation);
        assertTrue(locations.contains(selectedLocation));

        // Test generateAnnounce using the selected location
        Announce announce = announceFactory.generateAnnounce(tags, selectedLocation, clients);

        assertNotNull(announce);
        assertTrue(announce.getTitle().contains(selectedLocation.getName()));
        assertNotNull(announce.getDescription());
        assertNotNull(announce.getPublicationDate());
        assertNotNull(announce.getDateTimeStart());
        assertNotNull(announce.getDateTimeEnd());
        assertEquals(selectedLocation.getIdLocation(), announce.getRefLocationId());
        boolean authorExists = false;
        for (Client client : clients) {
            if (client.getId().equals(announce.getAuthorId())) {
                authorExists = true;
                break;
            }
        }
        assertTrue(authorExists);
        assertFalse(announce.getIsRecurrent());
        assertEquals(AnnounceType.EVENT, announce.getType());
        assertEquals(AnnounceStatus.PUBLISHED, announce.getStatus());
        assertNotNull(announce.getDuration());
        assertEquals(selectedLocation.getRef_district(), announce.getRefDistrictId());
    }
}