package fimafeng.back.proto_back.models;

import javax.persistence.*;

@Entity
@Table(name = "district")
public class District {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

}
