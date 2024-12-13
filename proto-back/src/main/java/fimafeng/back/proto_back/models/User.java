package fimafeng.back.proto_back.models;

import lombok.Data;

@Entity
@Data

@Table(name = "user")
public class User {
    
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last-name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="ref_district")
    private int district;

}
