package edu.cgl.sirius.business.dto;

public class Tag {

    private String name;
    private String categorie;

    public Tag(String name, String cat) {
        this.name = name;
        this.categorie = cat;
    }

    @Override
    public String toString() {
        return "N: " + this.name + ", c: " + this.categorie;
    }

}
