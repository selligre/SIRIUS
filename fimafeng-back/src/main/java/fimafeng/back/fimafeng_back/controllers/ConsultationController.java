package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.implementations.mocks.ConsultationFactory;
import fimafeng.back.fimafeng_back.models.Consultation;
import fimafeng.back.fimafeng_back.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("consultation")
public class ConsultationController {
    private static final Logger LOGGER = Logger.getLogger(ConsultationController.class.getName());

    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientTagService clientTagService;
    @Autowired
    private AnnounceService announceService;
    @Autowired
    private AnnounceTagService announceTagService;

    @GetMapping("/id")
    public ResponseEntity<Consultation> findConsultationById(@RequestParam("id") int id) {
        LOGGER.info("findConsultationById()");
        return new ResponseEntity<>(consultationService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Consultation> addConsultation(@RequestBody Consultation consultation) {
        LOGGER.info("addConsultation()");
        Consultation createdConsultation = consultationService.save(consultation);
        return new ResponseEntity<>(createdConsultation, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Consultation>> findAllConsultations() {
        LOGGER.info("findAllConsultations()");
        return new ResponseEntity<>(consultationService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Consultation> updateConsultation(@RequestBody Consultation consultation) {
        LOGGER.info("updateConsultation()");
        boolean isUpdated = consultationService.update(consultation);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(consultation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Consultation> deleteConsultation(@PathVariable int id) {
        LOGGER.info("deleteConsultation()");
        boolean isRemoved = consultationService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<String> generateConsultations(@RequestParam(required = false) Integer amount, @RequestParam(required = false) Integer delay) {
        LOGGER.info("generateConsultation()");
        if (amount == null) {
            amount = 1;
        }
        if (delay == null) {
            delay = 0;
        }
        boolean error = false;
        int savedAmount = 0;
        ConsultationFactory factory = new ConsultationFactory(clientService, clientTagService, announceService, announceTagService);
        for (int i = 0; i < amount; i++) {
            LOGGER.info("generateConsultation(): " + (i + 1) + "/" + amount);
            // Generates a consultation
            try {
                consultationService.save(factory.generateConsultation());
            } catch (IllegalArgumentException e) {
                error = true;
                break;
            }
            savedAmount += 1;
            // Add delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                error = true;
                break;
            }
        }
        // If error occurred, then returning Http Error and amount generated
        if (error) {
            String msg = String.format("Error: %d / %d", savedAmount, amount);
            return new ResponseEntity<>(msg, HttpStatus.EXPECTATION_FAILED);
        }
        // Otherwise, returning Http Success and amount generated
        String msg = String.format("Success: %d / %d", savedAmount, amount);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
}
