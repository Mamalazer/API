package io.dummyapi.user_data_steps;

import in.reqres.Specifications;
import io.dummyapi.pojo_classes.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static io.dummyapi.Endpoints.CREATE_USER;
import static io.dummyapi.Endpoints.DELETE_USER;
import static io.dummyapi.TestData.*;
import static io.dummyapi.Url.DUMMYAPI;
import static io.restassured.RestAssured.given;

public class Steps {

    @Step("Создание нового пользователя")
    @Owner("DRKuznetsov")
    public static User createUser() {
        in.reqres.Specifications.installSpecification(in.reqres.Specifications.requestSpec(DUMMYAPI),
                Specifications.responseSpec200());

        User userBody = new User(TITTLE, NAME, LAST_NAME, GENDER,
                EMAIL, PHONE_NUMBER, PICTURE_URL);

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

        return user;
    }

    @Step("Удаление пользователя")
    @Owner("DRKuznetsov")
    public static void deleteUser(User user) {
        in.reqres.Specifications.installSpecification(in.reqres.Specifications.requestSpec(DUMMYAPI),
                Specifications.responseSpec200());

        String id = given()
                .header(APP_ID_HEADER_NAME, APP_ID_KEY)
                .when()
                .delete(DELETE_USER + user.getId())
                .then().log().all()
                .extract().body().jsonPath().getString("id");

        Assertions.assertEquals(id, user.getId(), "Удалён не тот пользователь");
    }

}
