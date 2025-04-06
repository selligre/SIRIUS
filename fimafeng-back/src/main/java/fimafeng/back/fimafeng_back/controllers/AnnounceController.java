package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.implementations.mocks.AnnounceFactory;
import fimafeng.back.fimafeng_back.implementations.profiles.AnnounceProfile;
import fimafeng.back.fimafeng_back.implementations.profiles.AnnounceProfileImplementation;
import fimafeng.back.fimafeng_back.models.*;
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
import java.util.logging.Logger;

@RestController
@RequestMapping("announce")
public class AnnounceController {
    Logger LOGGER = Logger.getLogger(AnnounceController.class.getName());

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
    public Page<Announce> searchAnnounces(@RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer refLocationId,
            @RequestParam(required = false) List<Long> tagIds,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "publicationDate") String sortBy,
            @RequestParam(required = false) String sortDirection) {
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
        boolean isUpdated = announceService.update(announce, false);
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
        LOGGER.info("buildClientProfiles()");
        AnnounceProfileImplementation announceProfileImplementation = new AnnounceProfileImplementation(announceService, announceTagService);
        return new ResponseEntity<>(announceProfileImplementation.getAnnouncesData(), HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<String> generateAnnounce(@RequestParam(required = false) Integer amount, @RequestParam(required = false) Integer delay) {
        LOGGER.info("generateAnnounce()");
        if (amount == null) {
            amount = 1;
        }
        if (delay == null) {
            delay = 0;
        }

        int savedAmount = 0;

        List<Tag> tags = tagService.findAll();
        List<Location> locations = locationService.findAllLocation();
        List<Client> clients = clientService.findAll();
        List<District> districts = districtService.findAll();

        for (int i = 0; i < amount; i++) {
            Collections.shuffle(tags);
            List<Tag> selectedTags = tags.subList(0, Math.min(2, tags.size()));
            AnnounceFactory announceFactory = new AnnounceFactory();
            Location location = announceFactory.selectLocationBasedOnPopulation(locations, districts);
            Announce generatedAnnounce = announceService.save(announceFactory.generateAnnounce(selectedTags, location, clients));
            for (Tag tag : selectedTags) {
                AnnounceTag announceTag = new AnnounceTag();
                announceTag.setRefTagId(tag.getId());
                announceTag.setRefAnnounceId(generatedAnnounce.getId());
                announceTagService.save(announceTag);
            }
            savedAmount += 1;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String msg = String.format("Success: %d / %d", savedAmount, amount);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
}