package fimafeng.back.proto_back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
public class District {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "population_percentile")
    private float populationPercentile;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPopulationPercentile() {
        return populationPercentile;
    }

    public void setPopulationPercentile(float populationPercentile) {
        this.populationPercentile = populationPercentile;
    }
}