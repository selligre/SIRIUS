package fimafeng.back.proto_back.models;


import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "district")
public class District {

    // Getters and setters
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;


}