package fimafeng.back.fimafeng_back.implementations.mocks;

import fimafeng.back.fimafeng_back.controllers.ClientController;
import fimafeng.back.fimafeng_back.models.*;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.AnnounceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.logging.Logger;

@Service
public class AnnounceFactory extends Announce {
    Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    public Location selectLocationBasedOnPopulation(List<Location> locations, List<District> districts) {
        float randomValue = new Random().nextInt(100) + 1;

        District selectedDistrict = null;
        float cumulativePercentage = 0;
        for (District district : districts) {
            cumulativePercentage += district.getPopulationPercentile();
            if (randomValue <= cumulativePercentage) {
                selectedDistrict = district;
                break;
            }
        }

        List<Location> locationsInSelectedDistrict = new ArrayList<>();
        for (Location location : locations) {
            if (location.getRef_district() == selectedDistrict.getId()) {
                locationsInSelectedDistrict.add(location);
            }
        }

        return locationsInSelectedDistrict.get(new Random().nextInt(locationsInSelectedDistrict.size()));
    }

    public Announce generateAnnounce(List<Tag> tags, Location location, List<Client> clients) {
        Announce announce = new Announce();
        LOGGER.info("Generating random Announce");

        // Generate a random announce
        Faker faker = new Faker(new Locale("fr-FR"));
        char firstChar = Character.toLowerCase(location.getName().charAt(0));
        String prefix = " au ";
        if (String.valueOf(firstChar).matches("[aeiouyàâäéèêëîïôöùûüÿh]")) {
            prefix = " à l'";
        }
        if (StringUtils.split(location.getName())[0].toLowerCase().matches("^(piscine|faculté|gendarmerie|résidence|crèche)$")) {
            prefix = " à la";
        }
        announce.setTitle(tags.get(0).getName() + " et " + tags.get(1).getName() + prefix + location.getName());
        announce.setDescription(faker.lorem().paragraph(1));
        announce.setPublicationDate(new Date());
        announce.setDateTimeStart(faker.date().past(10, java.util.concurrent.TimeUnit.DAYS, new Date()));
        announce.setDateTimeEnd(faker.date().future(10, java.util.concurrent.TimeUnit.DAYS, announce.getDateTimeStart()));
        announce.setRefLocationId(Math.toIntExact(location.getIdLocation()));
        announce.setAuthorId(clients.get(new Random().nextInt(clients.size())).getId());
        announce.setIsRecurrent(false);
        announce.setType(AnnounceType.valueOf("EVENT"));
        announce.setStatus(AnnounceStatus.valueOf("PUBLISHED"));
        announce.setDuration(Float.valueOf(faker.random().nextInt(1, 10)));
        announce.setRefDistrictId(Math.toIntExact(location.getRef_district()));

        return announce;
    }
}
