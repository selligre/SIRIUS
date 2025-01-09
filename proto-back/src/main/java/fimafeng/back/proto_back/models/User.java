package fimafeng.back.proto_back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    // Getters and setters
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "ref_district")
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

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }
}
