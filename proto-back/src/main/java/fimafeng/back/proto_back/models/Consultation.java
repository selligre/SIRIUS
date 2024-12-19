package fimafeng.back.proto_back.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "consultation")
public class Consultation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ref_user_id")
    private int ref_user_id;

    @Column(name = "ref_announce_id")
    private int ref_announce_id;
}
