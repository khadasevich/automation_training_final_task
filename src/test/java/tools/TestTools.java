package tools;

import com.github.javafaker.Faker;
import io.qameta.allure.Attachment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestTools {

    @Attachment
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    @Attachment
    public static String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String fakeMessageGenerator() {
        Faker faker = new Faker();
        return faker.chuckNorris().fact();
    }
}
