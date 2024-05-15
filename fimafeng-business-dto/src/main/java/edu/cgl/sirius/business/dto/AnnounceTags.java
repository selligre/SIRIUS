package edu.cgl.sirius.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnounceTags {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("announceTags")
    private Set<AnnounceTags> announceTags = new LinkedHashSet<AnnounceTags>();

    public Set<AnnounceTags> getAnnounceTags() {
        return announceTags;
    }

    public void setAnnounceTags(Set<AnnounceTag> announceTags) {
        this.announceTags = announceTags;
    }

    public final AnnounceTags add(final AnnounceTag announceTag) {
        announceTags.add(announceTag);
        return this;
    }

    @Override
    public String toString() {
        return "Announces{" +
                "announces=" + announceTags +
                '}';
    }
}
