package fimafeng.back.proto_back.controllers;

import fimafeng.back.proto_back.models.Announce;
import fimafeng.back.proto_back.services.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("announce")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    @GetMapping("/{id}")
    public ResponseEntity<Announce> findById(@PathVariable Long id){

        return new ResponseEntity<>(announceService.findByIdAnnounce(id), HttpStatus.OK);
    }

        @PostMapping("add")
    public ResponseEntity<Announce> addAnnounce(@RequestBody Announce announce) {
        Announce createdAnnounce = announceService.saveAnnounce(announce);
        return new ResponseEntity<>(createdAnnounce, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Announce>> findAllAnnounce(){
        return new ResponseEntity<>(announceService.findAllAnnounce(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Announce> updateAnnounce(@RequestBody Announce announce){
        boolean isUpdated = announceService.updateAnnounce(announce);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = announceService.deleteAnnounce(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }
}