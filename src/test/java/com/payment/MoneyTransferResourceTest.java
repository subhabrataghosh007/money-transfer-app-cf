package com.payment;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import com.payment.model.Wallet;
import com.payment.repository.Repository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.payment.constant.ValidationMessage.AMOUNT_ERROR;
import static com.payment.constant.ValidationMessage.MOBILE_VALIDATION;;

@QuarkusTest
public class MoneyTransferResourceTest {

	@Inject
	Repository repository;
	
    @Test
    public void testTransactionNotAvailable() {
        given()
          .when().get("/v1/transactions")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }
    
}