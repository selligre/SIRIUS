package fimafeng.back.proto_back.implementations.mocks;

import com.github.javafaker.Faker;
import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.models.ClientTag;
import fimafeng.back.proto_back.models.District;
import fimafeng.back.proto_back.models.Tag;
import fimafeng.back.proto_back.services.DistrictService;
import fimafeng.back.proto_back.services.TagService;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ClientFactory extends Client {

    Logger LOGGER = Logger.getLogger(ClientFactory.class.getName());

    private static List<Integer> districtsListWeighted;
    private static List<Integer> tagIdsList;


    public ClientFactory(DistrictService districtService, TagService tagService) {
        LOGGER.info("Initializing clientFactory");
        if (districtsListWeighted == null || districtsListWeighted.isEmpty()) {
            districtsListWeighted = new ArrayList<>();

            // Getting districts from DB
            LOGGER.info("Retrieving districts data");
            List<District> districtsList = districtService.findAll();

            // Generating a list weighted from district data
            LOGGER.info("Generating districts weights");

            for (District district : districtsList) {
                for (int i = 0 ; i < district.getPopulationPercentile() ; i++) {
                    districtsListWeighted.add(district.getId());
                }
            }

            LOGGER.info("Data generated: " + districtsListWeighted.toString());

        } else {
            LOGGER.info("Districts already initialized");
        }

        if (tagIdsList == null || tagIdsList.isEmpty()) {
            tagIdsList = new ArrayList<>();

            // Getting tags from DB
            LOGGER.info("Retrieving tags data");
            List<Tag> tagsList = tagService.findAll();
            tagIdsList = tagsList.stream().map(Tag::getId).collect(Collectors.toList());

            LOGGER.info("Data retrieved: " + tagIdsList);
        } else {
            LOGGER.info("Tags already initialized");
        }


    }


    public Client generateClient() {
        LOGGER.info("Generating random client");
        Faker faker = new Faker(new Locale("fr-FR"));
        Client client = new Client();
        client.setFirstName(faker.name().firstName());
        client.setLastName(faker.name().lastName());

        // Email generation
        String email = client.getFirstName();
        switch (faker.random().nextInt(3)) {
            case 0 -> email += '-';
            case 1 -> email += '.';
            default -> email += "";
        }
        email+=client.getLastName();
        if (faker.random().nextBoolean()) {
            email += faker.random().nextInt(100);
        }
        email+='@';
        email += faker.internet().domainName();
        email = email.toLowerCase();

        // Email normalization, i.e. convert accent letters to non-accent letters
        // source : https://stackoverflow.com/questions/4122170/java-change-%c3%a1%c3%a9%c5%91%c5%b1%c3%ba-to-aeouu?noredirect=1&lq=1
        String normalizedEmail = Normalizer
                .normalize(email, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        client.setEmail(normalizedEmail);

        client.setDistrict(districtsListWeighted.get(faker.random().nextInt(districtsListWeighted.size())));
        LOGGER.info("Random client generated: " + client);
        return client;
    }

    public List<ClientTag> generateClientTags(int userId) {
        LOGGER.info("Generating random tags");
        List<Integer> tempTagslist = new ArrayList<>(tagIdsList);
        Collections.shuffle(tempTagslist);
        List<Integer> choosenTagsIdList = new ArrayList<>(2);
        choosenTagsIdList.add(tempTagslist.remove(0));
        LOGGER.info("First tag generated: " + choosenTagsIdList.get(0).toString());

        // Adding possibly a 2nd tag
        if (new Random().nextBoolean()) {
            LOGGER.info("Adding a second tag");
            choosenTagsIdList.add(tempTagslist.remove(0));
        }

        List<ClientTag> clientTagList = new ArrayList<>(2);
        while (!choosenTagsIdList.isEmpty()) {
            clientTagList.add(new ClientTag(choosenTagsIdList.remove(0), userId));
        }
        LOGGER.info("Random tags generated: " + clientTagList);
        return clientTagList;
    }


}