package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.implementations.mocks.AnnounceFactory;
import fimafeng.back.fimafeng_back.implementations.profiles.AnnounceProfile;
import fimafeng.back.fimafeng_back.implementations.profiles.AnnounceProfileImplementation;
import fimafeng.back.fimafeng_back.models.*;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.repositories.TagCountProjection;
import fimafeng.back.fimafeng_back.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
@RequestMapping("announce")
public class AnnounceController {
    private static final Logger LOGGER = Logger.getLogger(AnnounceController.class.getName());

    @Autowired
    private AnnounceService announceService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AnnounceTagService announceTagService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DistrictService districtService;

    private ScheduledExecutorService scheduler;

    @GetMapping("/{id}")
    public ResponseEntity<Announce> findAnnounceById(@PathVariable int id) {
        LOGGER.info("findAnnounceById()");
        return new ResponseEntity<>(announceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<TagCountProjection>> countTagsByDistrict(@PathVariable int districtId) {
        LOGGER.info("countTagsByDistrict()");
        return new ResponseEntity<>(announceTagService.countTagsByDistrict(districtId), HttpStatus.OK);
    }

    @GetMapping("/tagscount")
    public ResponseEntity<List<TagCountProjection>> countTags() {
        LOGGER.info("countTags()");
        return new ResponseEntity<>(announceTagService.countTags(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public Page<Announce> searchAnnounces(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) Integer refLocationId, @RequestParam(required = false) List<Long> tagIds, @RequestParam(required = false) String status, @RequestParam(defaultValue = "publicationDate") String sortBy, @RequestParam(required = false) String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return announceService.searchAnnounces(keyword, refLocationId, tagIds, status, PageRequest.of(page, size, Sort.by(direction, sortBy)));
    }

    @GetMapping("tagsfind/{announceId}")
    public ResponseEntity<List<Tag>> findTagsByAnnounceId(@PathVariable Integer announceId) {
        List<Tag> tags = announceTagService.findTagsByAnnounceId(announceId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Announce> addAnnounce(@RequestBody Announce announce) {
        LOGGER.info("addAnnounce()");
        announce.setRefLocationId(1);
        Announce createdAnnounce = announceService.save(announce);
        return new ResponseEntity<>(createdAnnounce, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Announce>> findAllAnnounces() {
        LOGGER.info("findAllAnnounces()");
        return new ResponseEntity<>(announceService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Announce> updateAnnounce(@RequestBody Announce announce) {
        LOGGER.info("updateAnnounce()");
        boolean isUpdated = announceService.update(announce, false, true);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @PostMapping("markAs")
    public ResponseEntity<Announce> markAnnounce(@RequestParam int announceId, @RequestParam String status) {
        LOGGER.info("markAnnounce()");

        Announce announce = announceService.findById(announceId);
        AnnounceStatus futurStatus;
        try {
            futurStatus = AnnounceStatus.valueOf(status);
            announce.setStatus(futurStatus);
        } catch (IllegalArgumentException e) {
            LOGGER.severe("Invalid announce status: " + status);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        boolean isUpdated = announceService.update(announce, true, false);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAnnounce(@PathVariable int id) {
        LOGGER.info("deleteAnnounce()");
        boolean isRemoved = announceService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((long) id, HttpStatus.OK);

    }

    @GetMapping("profiles")
    public ResponseEntity<List<AnnounceProfile>> buildClientProfiles() {
        LOGGER.info("buildAnnounceProfiles()");
        AnnounceProfileImplementation announceProfileImplementation = new AnnounceProfileImplementation(announceService, announceTagService);
        return new ResponseEntity<>(announceProfileImplementation.getAnnouncesData(), HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<String> generateAnnounce(@RequestParam(required = false) Integer frequency, @RequestParam(required = false) Boolean activation) {
        LOGGER.info("generateAnnounce()");

        if (activation == null || !activation) {
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdownNow();
                LOGGER.info("Génération d'annonces désactivée.");
            }
            return new ResponseEntity<>("Génération désactivée.", HttpStatus.OK);
        }

        if (frequency == null || frequency <= 0) {
            return new ResponseEntity<>("La fréquence doit être un entier positif.", HttpStatus.BAD_REQUEST);
        }

        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                long startTime = System.currentTimeMillis();
                long elapsedTime = 0;

                while (elapsedTime < frequency * 1000) {
                    try {
                        Thread.sleep(1000);
                        elapsedTime = System.currentTimeMillis() - startTime;

                        // 10% to generate an announce before the end of the interval
                        if (new Random().nextInt(100) < 10) {
                            generateSingleAnnounce();
                            LOGGER.info("Annonce générée avant la fin du délai. Temps écoulé : " + elapsedTime / 1000.0 + " secondes.");
                            scheduler.shutdownNow();
                            scheduler = null;
                            generateAnnounce(frequency, true);
                            return;
                        }
                    } catch (InterruptedException e) {
                        LOGGER.severe("Erreur lors de l'attente : " + e.getMessage());
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // generate an announce at the end of the interval
                generateSingleAnnounce();
                LOGGER.info("Annonce générée automatiquement à la fin de l'intervalle.");
            }, 0, frequency, TimeUnit.SECONDS);
        }

        return new ResponseEntity<>("Génération d'annonces activée avec une fréquence de " + frequency + " secondes.", HttpStatus.OK);
    }

    private void generateSingleAnnounce() {
        List<Tag> tags = tagService.findAll();
        List<Location> locations = locationService.findAllLocation();
        List<Client> clients = clientService.findAll();
        List<District> districts = districtService.findAll();

        Collections.shuffle(tags);
        AnnounceFactory announceFactory = new AnnounceFactory();
        String type = announceFactory.selectedType();
        List<Tag> selectedTags = announceFactory.selectTags(tags, type);
        Location location = announceFactory.selectLocationBasedOnPopulation(locations, districts);
        Announce generatedAnnounce = announceService.save(announceFactory.generateAnnounce(selectedTags, location, clients, type));
        for (Tag tag : selectedTags) {
            AnnounceTag announceTag = new AnnounceTag();
            announceTag.setRefTagId(tag.getId());
            announceTag.setRefAnnounceId(generatedAnnounce.getId());
            announceTagService.save(announceTag);
        }
        LOGGER.info("Annonce générée avec succès.");
    }
}