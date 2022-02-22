package io.dummyapi;

import in.reqres.Specifications;
import io.dummyapi.pojo_classes.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.dummyapi.Endpoints.CREATE_USER;
import static io.dummyapi.TestData.*;
import static io.dummyapi.Url.DUMMYAPI;
import static io.dummyapi.Utils.getCurrentDate;
import static io.dummyapi.Utils.getRandPhoneNumber;
import static io.restassured.RestAssured.given;

public class Tests {

    @Test
    public void createUser() {
        in.reqres.Specifications.installSpecification(in.reqres.Specifications.requestSpec(DUMMYAPI),
                Specifications.responseSpec200());

        String date = getCurrentDate();
        String phoneNumber = getRandPhoneNumber();

        User userBody = new User("mr", "Dan" + date, "Smith" + date, "male",
                "ldan" + phoneNumber + "@mail.ru", phoneNumber, PICTURE_URL);

        User user = given()
                .header(APP_ID_HEADER_NAME, APP_ID_KEY)
                .body(userBody)
                .when()
                .post(CREATE_USER)
                .then().log().all()
                .extract().as(User.class);

        Assertions.assertEquals(user.getFirstName(), userBody.getFirstName(),
                "Имя пользователя не соответствует создаваемому в запросе");
        Assertions.assertEquals(user.getLastName(), userBody.getLastName(),
                "Фамилия пользователя не соответствует создаваемой в запросе");
        Assertions.assertEquals(user.getEmail(), userBody.getEmail(),
                "Email пользователя не соответствует создаваемому в запросе");
    }

}
