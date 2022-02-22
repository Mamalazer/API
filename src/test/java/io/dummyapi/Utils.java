package io.dummyapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

    public static String getCurrentDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh:mm:ss");
        return date.format(d);
    }

    public static String getRandPhoneNumber() {
        Random random = new Random();
        return "8800" + random.nextInt(9999999);
    }

}
