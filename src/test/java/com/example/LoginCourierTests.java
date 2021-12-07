package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты на логин курьера")
public class LoginCourierTests {
    CourierDeleteClient courierDeleteClient;
    CourierLoginClient courierLoginClient;
    CourierCreateClient courierCreateClient;

    @Before
    public void setUp(){
        courierDeleteClient = new CourierDeleteClient();
        courierLoginClient = new CourierLoginClient();
        courierCreateClient = new CourierCreateClient();
    }

    @Test
    @DisplayName("Позитивный тест на авторизацию курьера")
    public void courierLoginPositiveTest(){
        Courier courier = Courier.getRandomCourier();
        courierCreateClient.courierAdd(courier);
        Response loginResponse = courierLoginClient.courierLogin(CourierCredentials.from(courier));
        loginResponse.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
        int courierId = loginResponse.path("id");
        courierDeleteClient.deleteCourier(courierId);

    }

    @Test
    @DisplayName("Негативный тест на авторизацию курьера. Нет обязательного поля")
    public void courierLoginWithOutLoginNegativeTest(){
        CourierCredentials courierCredentials = new CourierCredentials(null, RandomStringUtils.randomAlphabetic(10));
        courierLoginClient.courierLogin(courierCredentials)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Негативный тест на авторизацию курьера. Логин не соответсвует паролю")
    public void courierLoginWithPassNotMatchNegativeTest() {
        Courier courier = Courier.getRandomCourier();
        courierCreateClient.courierAdd(courier);
        courier.login = RandomStringUtils.randomAlphabetic(10);
        courierLoginClient.courierLogin(CourierCredentials.from(courier))
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Негативный тест на авторизацию курьера. Несуществующий курьер")
    public void unexistCourierLoginNegativeTest(){
        Courier courier = Courier.getRandomCourier();
        courierLoginClient.courierLogin(CourierCredentials.from(courier))
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}