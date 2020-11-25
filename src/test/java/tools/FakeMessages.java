package tools;

import com.github.javafaker.Faker;

public class FakeMessages {

    public static String generateFakeMessage() {
        Faker faker = new Faker();
        return faker.chuckNorris().fact();
    }
}
