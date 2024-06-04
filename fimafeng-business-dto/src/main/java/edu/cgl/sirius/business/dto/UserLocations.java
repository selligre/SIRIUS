package edu.cgl.sirius.business.dto;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLocations {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("users_locations")
    private Set<UserLocation> userLocations = new LinkedHashSet<UserLocation>();

    private Map<String, String> userLocationsMap = new HashMap<>();

    public Map<String, String> getUserLocationsMap() {
        return userLocationsMap;
    }

    public Set<UserLocation> getUserLocations() {
        return userLocations;
    }

    public void setUserLocations(Set<UserLocation> userLocations) {
        this.userLocations = userLocations;
    }

    public final UserLocations add(final UserLocation userLocation) {
        userLocationsMap.put(userLocation.getRef_user_id(), userLocation.getRef_location_id());
        userLocations.add(userLocation);
        return this;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "Locations=" + userLocationsMap +
                '}';
    }
}
