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
    public ResponseEntity<Announce> findById(@PathVariable int id){
        return new ResponseEntity<>(announceService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Announce> addAnnounce(@RequestBody Announce announce) {
        Announce createdAnnounce = announceService.save(announce);
        return new ResponseEntity<>(createdAnnounce, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Announce>> findAll(){
        return new ResponseEntity<>(announceService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Announce> update(@RequestBody Announce announce){
        boolean isUpdated = announceService.update(announce);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable int id){
        boolean isRemoved = announceService.delete(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((long) id, HttpStatus.OK);

    }
}