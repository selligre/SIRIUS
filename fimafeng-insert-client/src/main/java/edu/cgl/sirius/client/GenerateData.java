package edu.cgl.sirius.client;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Location;
import edu.cgl.sirius.business.dto.Tag;
import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.business.dto.AnnounceTypes.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

public class GenerateData {

    private static final Faker faker = new Faker();
    private static final ArrayList<String> announceTypes = new ArrayList<String>(
            Arrays.asList("activity", "loan", "service"));

    public static User generateUser() {
        String fName = faker.name().firstName();
        String lName = faker.name().lastName();
        String pName = (fName + "." + lName).toLowerCase();
        return new User(fName, lName, pName, "user",
                pName + "@" + faker.internet().domainName(),
                faker.internet().password());
    }

    // Test ok
    public static ArrayList<User> generateUsers(int number) {
        ArrayList<User> array = new ArrayList<User>();
        for (int i = 0; i < number; i++) {
            array.add(generateUser());
        }
        return array;
    }

    public static Location generateLocation() {
        return new Location(faker.address().cityName());
    }

    // Test ok
    public static ArrayList<Location> generateLocations(int number) {
        ArrayList<Location> array = new ArrayList<Location>();
        for (int i = 0; i < number; i++) {
            array.add(generateLocation());
        }
        return array;
    }

    public static Tag generateTag() {
        return new Tag(faker.commerce().productName(), faker.commerce().department());
    }

    // Test ok
    public static ArrayList<Tag> generateTags(int number) {
        ArrayList<Tag> array = new ArrayList<Tag>();
        for (int i = 0; i < number; i++) {
            array.add(generateTag());
        }
        return array;
    }

    public static Announce generateAnnounce() {
        return new Announce("online", announceTypes.get(faker.random().nextInt(0, 2)),
                faker.lorem().sentence(4, 4), faker.lorem().paragraph(),
                faker.date().future(200, TimeUnit.DAYS), (float) faker.random().nextInt(1, 4),
                faker.date().future(200, TimeUnit.DAYS, faker.date().future(400, TimeUnit.DAYS)), false);
    }

    // Test ok
    public static ArrayList<Announce> generateAnnounces(int number) {
        ArrayList<Announce> array = new ArrayList<Announce>();
        for (int i = 0; i < number; i++) {
            array.add(generateAnnounce());
        }
        return array;
    }

    public static Activity generateActivity() {
        return new Activity("online", faker.lorem().sentence(4, 4), faker.lorem().paragraph(),
                faker.date().future(200, TimeUnit.DAYS), (float) faker.random().nextInt(1, 4),
                faker.date().future(400, TimeUnit.DAYS), false, faker.random().nextInt(2, 16),
                (float) faker.random().nextInt(0, 20));
    }

    // Test ok
    public static ArrayList<Activity> generateActivities(int number) {
        ArrayList<Activity> array = new ArrayList<Activity>();
        for (int i = 0; i < number; i++) {
            array.add(generateActivity());
        }
        return array;
    }

    public static void main(String[] args) {
        ArrayList<Activity> array = generateActivities(10);
        for (Object o : array) {
            System.out.println(o);
            System.out.println();
        }
    }

}
