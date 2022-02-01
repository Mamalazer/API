package in.reqres;

import in.reqres.pojo_classes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static in.reqres.Endpoints.*;
import static in.reqres.TestData.*;
import static in.reqres.Url.REQRES;
import static io.restassured.RestAssured.given;

public class Tests {

    @Test
    public void checkAvatarAndId() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec200());

        List<UserData> users = given()
                .when()
                .get(LIST_USERS)
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString()),
                "Аватар не содержит id пользователя"));

        Assertions.assertTrue(
                users.stream()
                .allMatch(x -> x.getEmail().endsWith("reqres.in")), "Email не заканчивается на reqres.in"
        );

        List<String> avatars = users.stream()
                .map(UserData::getAvatar)
                .collect(Collectors.toList());
        List<String> ids = users.stream()
                .map(x -> x.getId().toString())
                .collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).endsWith(ids.get(i) + "-image.jpg"),
                    "Аватар пользователся с id = " + ids.get(i) + " не содержит его в названии файла");
        }
    }

    @Test
    public void successRegistration() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec200());
        Registration user = new Registration(EMAIL_FOR_REGISTRATION, PASSWORD_FOR_REGISTRATION);

        SuccessfulRegistration successfulRegistration = given()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .extract().as(SuccessfulRegistration.class);

        Assertions.assertNotNull(successfulRegistration);
        Assertions.assertEquals(ID_FOR_REGISTRATION, successfulRegistration.getId(), "Неверный Id");
        Assertions.assertEquals(TOKEN, successfulRegistration.getToken(), "Неверный токен");

    }

    @Test
    public void unsuccessfulRegistration() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec400());
        Registration user = new Registration(EMAIL_FOR_REGISTRATION, "");

        UnsuccessfulRegistration unsuccessfulRegistration = given()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .extract().as(UnsuccessfulRegistration.class);

        Assertions.assertNotNull(unsuccessfulRegistration);
        Assertions.assertEquals(MISSING_PASSWORD_ERROR, unsuccessfulRegistration.getError(),
                "Неверный текст ошибки или отсутствие ошибки");

    }

    @Test
    public void checkOrderOfYearsInResources() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec200());

        List<ListResource> resources = given()
                .when()
                .get(RESOURCES)
                .then().log().all()
                .extract().body().jsonPath().getList("data", ListResource.class);

        List<Integer> actual = resources.stream()
                .map(ListResource::getYear)
                .collect(Collectors.toList());

        List<Integer> expected = resources.stream()
                .map(ListResource::getYear)
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual, "Неверная сортировка годов в ответе");

    }

    @Test
    public void deleteUser() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec204());

        given()
                .when()
                .delete(DELETE)
                .then().log().all();

    }

    @Test
    public void updateUser() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec200());

        UpdateUser updateUser = new UpdateUser(NAME, JOB);

        UpdatedUser updatedUser = given()
                .body(updateUser)
                .when()
                .put(UPDATE)
                .then().log().all()
                .extract().as(UpdatedUser.class);

        LocalDate date = LocalDate.now();
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Assertions.assertTrue(updatedUser.getUpdatedAt().contains(date.format(d)),
                "Дата обновления информации о пользователе не совпадает с текущей");

    }

    @Test
    public void getSingleUser() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec200());

        SingleUser singleUser = given()
                .when()
                .get(SINGLE_USER)
                .then().log().all()
                .extract().as(SingleUser.class);

        UserData userData = new UserData(2, "janet.weaver@reqres.in", "Janet", "Weaver",
                "https://reqres.in/img/faces/2-image.jpg");

        Assertions.assertEquals(userData, singleUser.getData(), "Возвращён неверный user");

    }

    @Test
    public void getUnknownUser() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec404());

        String response = given()
                .when()
                .get(UNKNOWN_USER)
                .then().log().all()
                .extract().asString();

        Assertions.assertEquals("{}", response, "Найден пользователь, которого не существует");

    }

    @Test
    public void createUser() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec201());

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", "morpheus");
        newUser.put("job", "leader");
        LocalDate date = LocalDate.now();
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CreatedUser user = given()
                .body(newUser)
                .when()
                .post(CREATE_USER)
                .then().log().all()
                .extract().as(CreatedUser.class);

        Assertions.assertEquals( "morpheus", user.getName(), "Неверное имя пользователя");
        Assertions.assertEquals( "leader", user.getJob(), "Неверное название работы пользователя");
        Assertions.assertTrue(user.getCreatedAt().contains(date.format(d)), "Дата создания не соответствует текущей");

    }

    @Test
    public void positiveLogin() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec200());

        Registration credentials = new Registration("eve.holt@reqres.in", "cityslicka");

        Token token = given()
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .extract().as(Token.class);

        Assertions.assertNotNull(token);

    }

    @Test
    public void negativeLogin() {

        Specifications.installSpecification(Specifications.requestSpec(REQRES),
                Specifications.responseSpec400());

        Map<String, String> email = new HashMap<>();
        email.put("email", "peter@klaven");

        UnsuccessfulRegistration error = given()
                .body(email)
                .when()
                .post(LOGIN)
                .then().log().all()
                .extract().as(UnsuccessfulRegistration.class);

        Assertions.assertEquals("Missing password", error.getError(),
                "Неверный текст ошибки при логине без указания пароля");

    }

}
