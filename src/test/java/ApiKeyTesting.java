import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiKeyTesting {





    @Test
    public void apiKeyTest(){

        given()
                .header("x-api-key", "GwMco9Tpstd5vbzBzlzW9I7hr6E1D7w2zEIrhOra")   // API KEY BU KISMA YAZILIYOR  --İNTERWİEV DE TOKEN BAREAR YERİNE API KEY GELEBİLİR

                .when()
                .get("https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user")  // END POİNT

                .then()
                .log().body()
                .statusCode(200)
        ;

    }

}
