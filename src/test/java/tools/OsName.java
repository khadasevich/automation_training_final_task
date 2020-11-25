package tools;

import io.qameta.allure.Attachment;

public class OsName {

    @Attachment
    public static String getOSName() {
        return System.getProperty("os.name");
    }
}
