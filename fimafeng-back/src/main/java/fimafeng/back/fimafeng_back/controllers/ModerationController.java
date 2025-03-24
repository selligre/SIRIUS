package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.services.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableSpringDataWebSupport
@RestController
@RequestMapping("moderation")
public class ModerationController {
    Logger LOGGER = Logger.getLogger(ModerationController.class.getName());

    @Autowired
    private ModerationService moderationService;

    @GetMapping("/{id}")
    public ResponseEntity<Moderation> findModerationById(@PathVariable int id) {
        LOGGER.info("findModerationById()");
        return new ResponseEntity<>(moderationService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Moderation> addModeration(@RequestBody Moderation moderation) {
        LOGGER.info("addModeration()");
        Moderation createdModeration = moderationService.save(moderation);
        return new ResponseEntity<>(createdModeration, HttpStatus.CREATED);
    }

    //@GetMapping("all")
    public ResponseEntity<List<Moderation>> findAllModeration() {
        LOGGER.info("findAllModeration()");
        LOGGER.log(Level.FINE, "findAllLatestAction()");
        return new ResponseEntity<>(moderationService.findAllLatestAction(), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<Page<Moderation>> findAllModeration(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LOGGER.info("findAllModeration() (pages)");
        return new ResponseEntity<>(moderationService.findAllLatestAction(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Moderation> updateModeration(@RequestBody Moderation moderation) {
        LOGGER.info("updateModeration()");
        boolean isUpdated = moderationService.update(moderation);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteModeration(@PathVariable int id) {
        LOGGER.info("deleteModeration()");
        boolean isRemoved = moderationService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((long) id, HttpStatus.OK);

    }

    @GetMapping("history/{announceId}")
    public ResponseEntity<Page<Moderation>> findModerationByAnnounceId(
            @PathVariable int announceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LOGGER.info("findModerationByAnnounceId() (pages)");
        return new ResponseEntity<>(moderationService.findModerationByAnnounceId(announceId, PageRequest.of(page, size)), HttpStatus.OK);
    }

}