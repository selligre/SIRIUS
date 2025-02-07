package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.models.District;
import fimafeng.back.fimafeng_back.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("district")
public class DistrictController {

    Logger LOGGER = Logger.getLogger(DistrictController.class.getName());

    @Autowired
    private DistrictService districtService;

    @GetMapping("/{id}")
    public ResponseEntity<District> findDistrictById(@PathVariable int id){
        LOGGER.info("findDistrictById()");
        return new ResponseEntity<>(districtService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<District> addDistrict(@RequestBody District location) {
        LOGGER.info("addDistrict()");
        District createdLocation = districtService.save(location);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<District>> findAllDistricts(){
        LOGGER.info("findAllDistricts()");
        return new ResponseEntity<>(districtService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<District> updateDistrict(@RequestBody District location){
        LOGGER.info("updateDistrict()");
        boolean isUpdated = districtService.update(location);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDistrict(@PathVariable int id){
        LOGGER.info("deleteDistrict()");
        boolean isRemoved = districtService.delete(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>((long) id, HttpStatus.OK);
    }
}