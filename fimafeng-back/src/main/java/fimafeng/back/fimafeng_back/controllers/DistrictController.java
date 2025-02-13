package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.models.District;
import fimafeng.back.fimafeng_back.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/{id}")
    public ResponseEntity<District> findById(@PathVariable int id){
        return new ResponseEntity<>(districtService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<District> add(@RequestBody District location) {
        District createdLocation = districtService.save(location);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<District>> findAll(){
        return new ResponseEntity<>(districtService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<District> update(@RequestBody District location){
        boolean isUpdated = districtService.update(location);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable int id){
        boolean isRemoved = districtService.delete(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>((long) id, HttpStatus.OK);
    }
}