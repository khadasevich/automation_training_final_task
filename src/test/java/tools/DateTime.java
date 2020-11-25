package tools;

import io.qameta.allure.Attachment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    @Attachment
    public static String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
