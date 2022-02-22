package io.dummyapi;

import static io.dummyapi.Utils.getCurrentDate;
import static io.dummyapi.Utils.getRandPhoneNumber;

public class TestData {

    public static final String APP_ID_HEADER_NAME = "app-id";
    public static final String APP_ID_KEY = "62148cf0571de83a9a0c3403";
    public static final String PICTURE_URL = "https://sun1-91.userapi.com/s/v1/if1/mZ4tZtznXRUXvMZ96_TlFANTAuTmxYa5ghbn2VdRIfx5qs24dX3BB4b054Wl5pheLsvQcxOk.jpg?size=200x299&quality=96&crop=33,107,649,973&ava=1";
    public static final String NAME = "Dan" + getCurrentDate();
    public static final String LAST_NAME = "Smith" + getCurrentDate();
    public static final String TITTLE = "mr";
    public static final String GENDER = "male";
    public static final String EMAIL = "ldan" + getRandPhoneNumber() + "@mail.ru";
    public static final String PHONE_NUMBER = getRandPhoneNumber();

}
