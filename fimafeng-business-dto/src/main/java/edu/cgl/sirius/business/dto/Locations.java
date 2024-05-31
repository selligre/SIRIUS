package edu.cgl.sirius.business.dto;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Locations {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("locations")
    private Set<Location> locations = new LinkedHashSet<Location>();

    private Map<String, String> locationsMap = new HashMap<>();

    public Map<String, String> getLocationsMap() {
        return locationsMap;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public final Locations add(final Location location) {
        locationsMap.put(location.getLocation_id(), location.getName());
        locations.add(location);
        return this;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "Locations=" + locationsMap +
                '}';
    }
}
