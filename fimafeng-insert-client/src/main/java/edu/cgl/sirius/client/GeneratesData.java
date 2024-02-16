package edu.cgl.sirius.client;

import edu.cgl.sirius.business.dto.User;

import java.util.ArrayList;
import java.util.Locale;

import com.github.javafaker.Faker;

public class GeneratesData {

    private static Faker faker = new Faker(new Locale("fr"));

    public User generatesUser() {
        return new User(faker.name().firstName(),
                faker.name().fullName(),
                faker.name().username(),
                "user",
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    public ArrayList<User> generatesUsers(int number) {
        ArrayList<User> array = new ArrayList<User>();
        for (int i = 0; i < number; i++) {
            array.add(generatesUser());
        }
        return array;
    }
}
