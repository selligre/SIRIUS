package edu.cgl.sirius.business.dto;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTags {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("users_tags")
    private Set<UserTag> tags = new LinkedHashSet<UserTag>();

    private Map<String, String> tagsMap = new HashMap<>();

    public Map<String, String> getUserTagsMap() {
        return tagsMap;
    }

    public Set<UserTag> getUserTags() {
        return tags;
    }

    public void setUserTags(Set<UserTag> tags) {
        this.tags = tags;
    }

    public final UserTags add(final UserTag tag) {
        tagsMap.put(tag.getRef_user_id(), tag.getRef_tag_id());
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
