package in.reqres;

import in.reqres.pojo_classes.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
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

}