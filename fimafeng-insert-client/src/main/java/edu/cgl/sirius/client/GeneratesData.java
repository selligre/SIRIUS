package edu.cgl.sirius.client;

import edu.cgl.sirius.business.dto.User;

import java.util.ArrayList;

import com.github.javafaker.Faker;

public class GeneratesData {

    private static Faker faker = new Faker();

    public static User generatesUser() {
        return new User(faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                "user",
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    public static ArrayList<User> generatesUsers(int number) {
        ArrayList<User> array = new ArrayList<User>();
        for (int i = 0; i < number; i++) {
            array.add(generatesUser());
        }
        return array;
    }

    public static void main(String[] args) {
        ArrayList<User> array = generatesUsers(10);
        for (User user : array) {
            System.out.println(user);
        }
    }

}
