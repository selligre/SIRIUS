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

    public String selectedType() {
        List<String> types = new ArrayList<>();
        types.add("EVENT");
        types.add("LOAN");
        types.add("SERVICE");

        int nb = new Random().nextInt(types.size());

        return types.get(nb);
    }

    public List<Tag> selectTags(List<Tag> tags, String type) {
        List<Tag> selectedTags = new ArrayList<>();
        String category;
        switch (type) {
            case "EVENT":
                List<String> categoriesEvent = Arrays.asList("Culture", "Sport", "Citoyen");
                Collections.shuffle(tags);
                for (Tag tag : tags) {
                    if (categoriesEvent.contains(tag.getCategory())) {
                        selectedTags.add(tag);
                        category = tag.getCategory();
                        if (new Random().nextBoolean()) {
                            switch (category) {
                                case "Culture":
                                    List<String> secondTagsCulture = Arrays.asList("Environnemental", "Municipal");
                                    String secondTagCulture = secondTagsCulture.get(new Random().nextInt(secondTagsCulture.size()));
                                    for (Tag tagCulture : tags) {
                                        if (tagCulture.getName().equals(secondTagCulture)) {
                                            selectedTags.add(tagCulture);
                                            break;
                                        }
                                    }
                                    break;
                                case "Sport":
                                    List<String> secondTagsSport = Arrays.asList("Associatif", "Social");
                                    String secondTagSport = secondTagsSport.get(new Random().nextInt(secondTagsSport.size()));
                                    for (Tag tagSport : tags) {
                                        if (tagSport.getName().equals(secondTagSport)) {
                                            selectedTags.add(tagSport);
                                            break;
                                        }
                                    }
                                    break;
                                case "Citoyen":
                                    String secondTagCitoyen = "Grand public";
                                    for (Tag tagCitoyen : tags) {
                                        if (tagCitoyen.getCategory().equals(secondTagCitoyen)) {
                                            selectedTags.add(tagCitoyen);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    }
                }
                break;
            case "LOAN":
                List<String> categoriesLoan = Arrays.asList("Matériel", "Outils", "Biens");
                Collections.shuffle(tags);
                for (Tag tag : tags) {
                    if (categoriesLoan.contains(tag.getCategory())) {
                        selectedTags.add(tag);
                        if (new Random().nextBoolean()) {
                            List<String> secondTag = Arrays.asList("Associatif", "Social", "Initiation");
                            for (Tag tagLoan : tags) {
                                if (secondTag.contains(tagLoan.getCategory())) {
                                    selectedTags.add(tagLoan);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            case "SERVICE":
                List<String> categoriesService = Arrays.asList("Aide à la personne", "Covoiturage");
                Collections.shuffle(tags);
                for (Tag tag : tags) {
                    if (categoriesService.contains(tag.getCategory())) {
                        selectedTags.add(tag);
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return selectedTags;
    }

    public Announce generateAnnounce(List<Tag> tags, Location location, List<Client> clients, String type) {
        Announce announce = new Announce();
        LOGGER.info("Generating random Announce");

        // Generate a random announce
        Faker faker = new Faker(new Locale("fr-FR"));
        char firstChar = Character.toLowerCase(location.getName().charAt(0));
        String prefix = " au ";
        if (String.valueOf(firstChar).matches("[aeiouyàâäéèêëîïôöùûüÿh]")) {
            prefix = " à l'";
        } else if (StringUtils.split(location.getName())[0].toLowerCase().matches("^(piscine|faculté|gendarmerie|résidence|crèche|place|maison)$")) {
            prefix = " à la ";
        }

        String title;

        if (tags.size() == 2) {
            title = tags.get(0).getName() + " et " + tags.get(1).getName() + prefix + location.getName();
        } else {
            title = tags.get(0).getName() + prefix + location.getName();
        }

        announce.setTitle(title);
        announce.setDescription(faker.lorem().paragraph(1));
        announce.setPublicationDate(new Date());
        announce.setDateTimeStart(faker.date().past(10, java.util.concurrent.TimeUnit.DAYS, new Date()));
        announce.setDateTimeEnd(faker.date().future(10, java.util.concurrent.TimeUnit.DAYS, announce.getDateTimeStart()));
        announce.setRefLocationId(Math.toIntExact(location.getIdLocation()));
        announce.setAuthorId(clients.get(new Random().nextInt(clients.size())).getId());
        announce.setIsRecurrent(false);
        announce.setType(AnnounceType.valueOf(type));
        announce.setStatus(AnnounceStatus.valueOf("PUBLISHED"));
        announce.setDuration(Float.valueOf(faker.random().nextInt(1, 10)));
        announce.setRefDistrictId(Math.toIntExact(location.getRef_district()));

        return announce;
    }
}
