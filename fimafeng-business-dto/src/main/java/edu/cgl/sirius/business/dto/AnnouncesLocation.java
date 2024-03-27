package edu.cgl.sirius.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnouncesLocation {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("announcesLocation")
    private  Set<AnnounceLocation> announcesLocation = new LinkedHashSet<AnnounceLocation>();

    public Set<AnnounceLocation> getAnnouncesLocation() {
        return announcesLocation;
    }

    public void setAnnounces(Set<AnnounceLocation> announcesLocation) {
        this.announcesLocation = announcesLocation;
    }

    public final AnnouncesLocation add (final AnnounceLocation announceLocation) {
        announcesLocation.add(announceLocation);
        return this;
    }

    @Override
    public String toString() {
        return "AnnouncesLocation{" +
                "announcesLocation=" + announcesLocation +
                '}';
    }
}
