package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.Location;
import fimafeng.back.proto_back.repositories.DistrictAnnounceCountProjection;
import fimafeng.back.proto_back.repositories.LocationCountProjection;
import fimafeng.back.proto_back.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location findByIdLocation(Long idLocation) {
        Optional<Location> optionalLocation = locationRepository.findById(idLocation);
        return optionalLocation.orElse(null);
    }

    public List<Location> findAllLocation(){
        return locationRepository.findAll();
    }

    public List<LocationCountProjection> countAnnouncesByLocation(){
        return locationRepository.countAnnouncesByLocation();
    }

    public List<DistrictAnnounceCountProjection> countOfAnnounceByDistrict(){return locationRepository.countOfAnnounceByDistrict();}

    public boolean updateLocation(Location updatedLocation) {
        if (updatedLocation == null || updatedLocation.getIdLocation() == null) {
            throw new IllegalArgumentException("The updated announce or its ID must not be null");
        }

        Long id = updatedLocation.getIdLocation();

        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isEmpty()) {
            return false;
        }

        Location location = optionalLocation.get();
        location.setName(updatedLocation.getName());
        location.setLongitude(updatedLocation.getLongitude());
        location.setLatitude(updatedLocation.getLatitude());

        locationRepository.saveAndFlush(location);

        return true;
    }

    public boolean deleteLocation(Long idLocation) {
        Optional<Location> optionalLocation = locationRepository.findById(idLocation);
        if (optionalLocation.isPresent()) {
            optionalLocation.ifPresent(location -> locationRepository.delete(location));
            return true;
        }
        return false;
    }
}