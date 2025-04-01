package fimafeng.back.fimafeng_back.implementations.mocks;

import fimafeng.back.fimafeng_back.controllers.ClientController;
import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.Client;
import fimafeng.back.fimafeng_back.models.Location;
import fimafeng.back.fimafeng_back.models.Tag;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.models.enums.AnnounceType;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

 @Service
public class AnnounceFactory extends Announce {
    Logger LOGGER = Logger.getLogger(ClientController.class.getName());

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
