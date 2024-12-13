package fimafeng.back.proto_back.controllers;

import fimafeng.back.proto_back.models.Location;
import fimafeng.back.proto_back.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable Long id){

        return new ResponseEntity<>(locationService.findByIdLocation(id), HttpStatus.OK);
    }

        @PostMapping("add")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location createdLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Location>> findAllLocation(){
        return new ResponseEntity<>(locationService.findAllLocation(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location){
        boolean isUpdated = locationService.updateLocation(location);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = locationService.deleteLocation(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }
}