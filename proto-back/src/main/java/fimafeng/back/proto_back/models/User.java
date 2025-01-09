package fimafeng.back.proto_back.models;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    // Getters and setters
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="ref_district")
    private int district;

    @Override
    public String toString() {
        String res = "User[";
        res += "ID: " + id + ", ";
        res += "firstName: " + firstName + ", ";
        res += "lastName: " + lastName + ", ";
        res += "email: " + email + ", ";
        res += "district: " + district + "]";
        return res;
    }

}
