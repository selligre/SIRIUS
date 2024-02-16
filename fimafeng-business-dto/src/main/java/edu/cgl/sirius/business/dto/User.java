package edu.cgl.sirius.business.dto;

public class User {

    private String firstName;
    private String lastName;
    private String displayName;
    private String userType;
    private String email;
    private String password;

    public User(String firstname, String lastname, String display, String type, String mail, String pswd) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.displayName = display;
        this.userType = type;
        this.email = mail;
        this.password = pswd;
    }

}
