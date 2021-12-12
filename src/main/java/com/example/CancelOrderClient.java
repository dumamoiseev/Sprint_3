package com.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class CancelOrderClient extends ApiClient {

    private String baseURI = "api/v1/orders/cancel";

    @Step("Отмена заказа")
    public Response cancelOrder(int id){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(id)
                .when()
                .put(baseURI);
    }
}