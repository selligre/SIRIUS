package edu.cgl.sirius.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnounceTags {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("announces")
    private Set<AnnounceTag> announceTags = new LinkedHashSet<AnnounceTag>();

    public Set<AnnounceTag> getAnnounces() {
        return announceTags;
    }

    public void setAnnounces(Set<AnnounceTag> announceTags) {
        this.announceTags = announceTags;
    }

    public final AnnounceTags add(final AnnounceTag announceTag) {
        announceTags.add(announceTag);
        return this;
    }

    @Override
    public String toString() {
        return "AnnounceTags{" +
                "announceTags=" + announceTags +
                '}';
    }
}
