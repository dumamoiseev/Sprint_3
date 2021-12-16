package com.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("Тесты на создание курьера")
public class CreateCourierTests {
    CourierLoginClient courierLoginClient;
    CourierCreateClient courierUsingPostMethods;
    CourierDeleteClient courierDeleteClient;

    @Before
    public void setUp(){
        courierLoginClient = new CourierLoginClient();
        courierUsingPostMethods = new CourierCreateClient();
        courierDeleteClient = new CourierDeleteClient();
    }

    @Test
    @DisplayName("Позивитный тест создания курьера")
    public void createCourierPositiveTest(){
        Courier courier = Courier.getRandomCourier();
        courierUsingPostMethods.courierAdd(courier)
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        courierDeleteClient.deleteCourier(courierLoginClient.getCourierId(CourierCredentials.from(courier)));
    }

    @Test
    @DisplayName("Негативный тест на создание с уже существующим логином")
    public void createCourierDuplicateLoginNegativeTest(){
        Courier courier = Courier.getRandomCourier();
        courierUsingPostMethods.courierAdd(courier);
        courierUsingPostMethods.courierAdd(courier).
                then()
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierDeleteClient.deleteCourier(courierLoginClient.getCourierId(CourierCredentials.from(courier)));

    }

    @Test
    @DisplayName("Негативный тест на создание курьера без обязательного поля 'Логин' ")
    public void createCourierWithOutRequiredFieldLoginNegativeTest(){
        Courier courier = Courier.getCourierWithOutLogin();
        courierUsingPostMethods.courierAdd(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Негативный тест на создание курьера без обязательного поля 'Пароль' ")
    public void createCourierWithOutRequiredFieldPasswordNegativeTest(){
        Courier courier = Courier.getCourierWithOutPassword();
        courierUsingPostMethods.courierAdd(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Негативный тест на создание курьера без обязательного поля 'Имя' ")
    public void createCourierWithOutRequiredFieldNameNegativeTest(){
        Courier courier = Courier.getCourierWithOutName();
        courierUsingPostMethods.courierAdd(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}