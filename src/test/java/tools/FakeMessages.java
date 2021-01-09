package tools;

import com.github.javafaker.Faker;

public class FakeMessages {

    public static String generateFakeEmailBody() {
        Faker faker = new Faker();
        return faker.chuckNorris().fact();
    }

    public static String generateFakeEmailSubject() {
        Faker faker = new Faker();
        return faker.dragonBall().character();
    }
}
