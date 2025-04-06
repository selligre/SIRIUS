package fimafeng.back.fimafeng_back.services;

import fimafeng.back.fimafeng_back.models.District;
import fimafeng.back.fimafeng_back.repositories.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository locationRepository;

    public District save(District location) {
        return locationRepository.save(location);
    }

    public District findById(int idLocation) {
        Optional<District> optionalLocation = locationRepository.findById(idLocation);
        return optionalLocation.orElse(null);
    }

    public List<District> findAll() {
        return locationRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public boolean update(District updatedLocation) {
        // Vérifiez si l'objet est null
        if (updatedLocation == null) {
            throw new IllegalArgumentException("The updated announce must not be null");
        }

        int id = updatedLocation.getId();

        // Vérifiez si l'annonce existe
        Optional<District> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isEmpty()) {
            return false; // Si l'annonce n'existe pas, retournez false
        }

        // Mise à jour des champs de l'entité existante
        District location = optionalLocation.get();
        location.setName(updatedLocation.getName());

        // Sauvegardez les modifications
        locationRepository.saveAndFlush(location);

        return true;
    }

    public boolean delete(int idLocation) {
        Optional<District> optionalLocation = locationRepository.findById(idLocation);
        if (optionalLocation.isPresent()) {
            optionalLocation.ifPresent(location -> locationRepository.delete(location));
            return true;
        }
        return false;
    }
}