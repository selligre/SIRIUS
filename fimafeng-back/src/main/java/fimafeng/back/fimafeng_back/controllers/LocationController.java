package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.models.Location;
import fimafeng.back.fimafeng_back.repositories.DistrictAnnounceCountProjection;
import fimafeng.back.fimafeng_back.repositories.LocationCountProjection;
import fimafeng.back.fimafeng_back.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("location")
public class LocationController {


    Logger LOGGER = Logger.getLogger(AnnounceController.class.getName());

    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<Location> findLocationById(@PathVariable Long id){
        LOGGER.info("findLocationById()");
        return new ResponseEntity<>(locationService.findByIdLocation(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        LOGGER.info("addLocation()");
        Location createdLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Location>> findAllLocation(){
        LOGGER.info("findAllLocation()");
        return new ResponseEntity<>(locationService.findAllLocation(), HttpStatus.OK);
    }

    @GetMapping("count")
    public ResponseEntity<List<LocationCountProjection>> countOfLocation(){
        LOGGER.info("countOfLocation()");
        return new ResponseEntity<>(locationService.countOfLocation(), HttpStatus.OK);
    }

    @GetMapping("countDis")
    public ResponseEntity<List<DistrictAnnounceCountProjection>> countOfAnnounceByDistrict(){
        LOGGER.info("countOfAnnounceByDistrict()");
        return new ResponseEntity<>(locationService.countOfAnnounceByDistrict(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location){
        LOGGER.info("updateLocation()");
        boolean isUpdated = locationService.updateLocation(location);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteLocation(@PathVariable Long id){
        LOGGER.info("deleteLocation()");
        boolean isRemoved = locationService.deleteLocation(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }
}