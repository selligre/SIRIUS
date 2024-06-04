package edu.cgl.sirius.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberCounts {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("numberCounts")
    private Set<NumberCount> numberCounts = new LinkedHashSet<NumberCount>();

    public Set<NumberCount> getNumberCounts() {
        return numberCounts;
    }

    public void setNumberCounts(Set<NumberCount> numberCounts) {
        this.numberCounts = numberCounts;
    }

    public final NumberCounts add(final NumberCount numberCount) {
        numberCounts.add(numberCount);
        return this;
    }

    @Override
    public String toString() {
        return "NumberCounts{" +
                "numberCounts=" + numberCounts +
                '}';
    }
}
