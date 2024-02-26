package edu.cgl.sirius.business.dto;

public class Location {

    private String name;

    public Location(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "N: " + this.name;
    }

}
