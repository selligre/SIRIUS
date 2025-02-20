package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.implementations.profiles.AnnounceProfileImplementation;
import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.services.AnnounceService;
import fimafeng.back.fimafeng_back.services.AnnounceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
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
    public ResponseEntity<Announce> findById(@PathVariable int id) {
        return new ResponseEntity<>(announceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public Page<Announce> searchAnnounces(@RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer refLocationId,
            @RequestParam(defaultValue = "publicationDate") String sortBy,
            @RequestParam(required = false) String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return announceService.searchAnnounces(keyword, refLocationId, PageRequest.of(page, size, Sort.by(direction, sortBy)));
    }

    @PostMapping("add")
    public ResponseEntity<Announce> addAnnounce(@RequestBody Announce announce) {
        Announce createdAnnounce = announceService.save(announce);
        return new ResponseEntity<>(createdAnnounce, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Announce>> findAll() {
        LOGGER.log(Level.FINE, "findAll()");
        return new ResponseEntity<>(announceService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Announce> update(@RequestBody Announce announce) {
        boolean isUpdated = announceService.update(announce, false);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable int id) {
        boolean isRemoved = announceService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((long) id, HttpStatus.OK);

    }

    @GetMapping("profile")
    public ResponseEntity<String> buildClientProfiles() {
        AnnounceProfileImplementation announceProfileImplementation = new AnnounceProfileImplementation(announceService, announceTagService);
        return new ResponseEntity<>(announceProfileImplementation.getAnnouncesData(), HttpStatus.OK);
    }
}