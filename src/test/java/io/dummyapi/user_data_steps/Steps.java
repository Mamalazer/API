package io.dummyapi.user_data_steps;

import io.dummyapi.Specifications;
import io.dummyapi.pojo_classes.User;
import io.dummyapi.pojo_classes.UserPreview;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static io.dummyapi.Endpoints.*;
import static io.dummyapi.Specifications.*;
import static io.dummyapi.TestData.*;
import static io.dummyapi.Url.DUMMYAPI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Steps {

    @Step("Создание нового пользователя")
    @Owner("DRKuznetsov")
    public static User createUser() {
        installSpecification(requestSpec(DUMMYAPI), responseSpec200());

        User userBody = new User(TITTLE, NAME, LAST_NAME, GENDER,
                EMAIL, PHONE_NUMBER, PICTURE_URL);

        User user = given()
                .header(APP_ID_HEADER_NAME, APP_ID_KEY)
                .body(userBody)
                .when()
                .post(CREATE_USER)
                .then().log().all()
                .extract().as(User.class);

        assertEquals(user.getFirstName(), userBody.getFirstName(),
                "Имя пользователя не соответствует создаваемому в запросе");
        assertEquals(user.getLastName(), userBody.getLastName(),
                "Фамилия пользователя не соответствует создаваемой в запросе");
        assertEquals(user.getEmail(), userBody.getEmail(),
                "Email пользователя не соответствует создаваемому в запросе");

        return user;
    }

    @Step("Удаление пользователя")
    @Owner("DRKuznetsov")
    public static void deleteUser(User user) {
        installSpecification(requestSpec(DUMMYAPI), responseSpec200());

        String id = given()
                .header(APP_ID_HEADER_NAME, APP_ID_KEY)
                .when()
                .delete(DELETE_USER + user.getId())
                .then().log().all()
                .extract().body().jsonPath().getString("id");

        assertEquals(id, user.getId(), "Удалён не тот пользователь");
    }

    /**
     * @param page value should be in range [0-999]. Default value: 0
     * @param limit value should be in range [5-50]. Default value: 20
     */
    @Step("Получение полного списка пользователей")
    @Owner("DRKuznetsov")
    public static UserPreview getUsers(int page, int limit) {
        installSpecification(requestSpec(DUMMYAPI), responseSpec200());

        UserPreview users = given()
                .param("page", page)
                .param("limit", limit)
                .header(APP_ID_HEADER_NAME, APP_ID_KEY)
                .when()
                .get(LIST_OF_USERS)
                .then().log().all()
                .extract().as(UserPreview.class);

        assertNotNull(users.getData(), "Список пользователей пуст");
        assertEquals(users.getData().size(), limit, "Количество пользователей не соответствует запрошенному");
        assertEquals(users.getPage(), page, "Открытая страница не соответствует запрашиваемой");

        return users;
    }
}
