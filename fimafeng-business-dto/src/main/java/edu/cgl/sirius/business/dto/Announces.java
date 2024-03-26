package edu.cgl.sirius.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Announces {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("announces")
    private  Set<Announce> announces = new LinkedHashSet<Announce>();

    public Set<Announce> getAnnounces() {
        return announces;
    }

    public void setAnnounces(Set<Announce> announces) {
        this.announces = announces;
    }

    public final Announces add (final Announce announce) {
        announces.add(announce);
        return this;
    }

    @Override
    public String toString() {
        return "Announces{" +
                "announces=" + announces +
                '}';
    }
}
