package fr.upec.episen.sirius.fimafeng.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

    // Columns
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;


    @Override
    public String toString() {
        String res = "Client[";
        res += "ID: " + id + ", ";
        res += "username: " + username + ", ";
        res += "email: " + email + "]";
        return res;
    }
}
