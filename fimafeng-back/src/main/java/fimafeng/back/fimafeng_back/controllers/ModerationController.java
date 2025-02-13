package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.services.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("moderation")
public class ModerationController {
    Logger LOGGER = Logger.getLogger(ModerationController.class.getName());

    @Autowired
    private ModerationService moderationService;

    @GetMapping("/{id}")
    public ResponseEntity<Moderation> findById(@PathVariable int id) {
        return new ResponseEntity<>(moderationService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Moderation> addModeration(@RequestBody Moderation moderation) {
        Moderation createdModeration = moderationService.save(moderation);
        return new ResponseEntity<>(createdModeration, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Moderation>> findAll() {
        LOGGER.log(Level.FINE, "findAll()");
        return new ResponseEntity<>(moderationService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Moderation> update(@RequestBody Moderation moderation) {
        boolean isUpdated = moderationService.update(moderation);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable int id) {
        boolean isRemoved = moderationService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((long) id, HttpStatus.OK);

    }


}