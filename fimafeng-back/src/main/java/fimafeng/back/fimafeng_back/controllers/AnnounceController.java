package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.implementations.profiles.AnnounceProfileImplementation;
import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.AnnounceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("announce")
public class AnnounceController {
    Logger LOGGER = Logger.getLogger(AnnounceController.class.getName());

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private AnnounceTagService announceTagService;

    @GetMapping("/{id}")
    public ResponseEntity<Announce> findAnnounceById(@PathVariable int id) {
        LOGGER.info("findAnnounceById()");
        return new ResponseEntity<>(announceService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Announce> addAnnounce(@RequestBody Announce announce) {
        LOGGER.info("addAnnounce()");
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

    @GetMapping("profile")
    public ResponseEntity<String> buildClientProfiles() {
        LOGGER.info("buildClientProfiles()");
        AnnounceProfileImplementation announceProfileImplementation = new AnnounceProfileImplementation(announceService, announceTagService);
        return new ResponseEntity<>(announceProfileImplementation.getAnnouncesData(), HttpStatus.OK);
    }
}