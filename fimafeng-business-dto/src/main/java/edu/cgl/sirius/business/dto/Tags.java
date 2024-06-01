package edu.cgl.sirius.business.dto;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tags {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tags")
    private Set<Tag> tags = new LinkedHashSet<Tag>();

    private Map<String, String> tagsMap = new HashMap<>();

    public Map<String, String> getTagsMap() {
        return tagsMap;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public final Tags add(final Tag tag) {
        tagsMap.put(tag.getTag_id(), tag.getName());
        tags.add(tag);
        return this;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "Tags=" + tagsMap +
                '}';
    }
}
