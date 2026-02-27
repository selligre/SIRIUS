package fr.upec.episen.sirius.fimafeng.commons.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

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
