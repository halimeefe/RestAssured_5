package GoRest;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured .*;
import static org.hamcrest.Matchers .*;

import static io.restassured.RestAssured .*;
import static org.hamcrest.Matchers .*;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured .*;
import static org.hamcrest.Matchers .*;

public class GoRestUsersTests {

   Faker faker=new Faker();  //RANDOM ÜRETİCİ
    int userID;

    RequestSpecification reqSpec;


    @BeforeClass
    public  void setup(){

        baseURI = "https://gorest.co.in/public/v2/users"; // baseUri RequestSpecification den önce tanımlanmalı.
        //baseURI ="https://test.gorest.co.in/public/v2/users/";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 01b66f5d4d36ff6362a80c9666873b8ea0c57800809450cf6f534c35b0f24013")
                .setContentType(ContentType.JSON)
                .setBaseUri(baseURI)
                .build();
    }

    @Test(enabled = false)
    public void createUserJson(){

        // POST https://gorest.co.in/public/v2/users
        // "Authorization: Bearer 523891d26e103bab0089022d20f1820be2999a7ad693304f560132559a2a152d"
        // {"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}


        String rndFullname=faker.name().fullName();
        String rndEmail=faker.internet().emailAddress();

         userID=
                given()
                        .spec(reqSpec)
                        .body("{\"name\":\""+rndFullname+"\", \"gender\":\"female\", \"email\":\""+rndEmail+"\", \"status\":\"active\"}")

                      //  .log().uri()
                      //  .log().body()

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;

    }
    @Test
    public void createUserMap() {

        // POST https://gorest.co.in/public/v2/users
        // "Authorization: Bearer 523891d26e103bab0089022d20f1820be2999a7ad693304f560132559a2a152d"
        // {"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}

        System.out.println("baseURI = " + baseURI);
        String rndFullname = faker.name().fullName();
        String rndEmail = faker.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",rndFullname);
        newUser.put("gender","male");
        newUser.put("email",rndEmail);
        newUser.put("status","active");


        userID =
                given()

                        .header("Authorization", "Bearer 01b66f5d4d36ff6362a80c9666873b8ea0c57800809450cf6f534c35b0f24013")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().uri()
                        //  .log().body()

                        .when()
                        .post("")

                        .then()
                        .log().body()

                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;

    }


    @Test(enabled =false)
    public void createUserClass() {  // CREATE USER3 İÇİN CLASS OLUŞTURDUK VE BİLGİLERİ ORADAN GÖNDERDİK NEW LEDİK

        // POST https://gorest.co.in/public/v2/users
        // "Authorization: Bearer 523891d26e103bab0089022d20f1820be2999a7ad693304f560132559a2a152d"
        // {"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}

        String rndFullname = faker.name().fullName();
        String rndEmail = faker.internet().emailAddress();

        User newUser=new User();
        newUser.name=rndFullname;
        newUser.gender="male";
        newUser.email=rndEmail;
        newUser.status="active";

        userID =
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        //  .log().uri()
                        //  .log().body()

                        .when()
                        .post("")

                        .then()
                        .log().body()

                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;
    }
    @Test(dependsOnMethods = "createUserMap")
    public void getUserByID() {

        given()
                .spec(reqSpec)
                .when()
                .get(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))
        ;

    }



    @Test(dependsOnMethods = "getUserByID")
    public void updateUser(){

            Map<String,String> updateUser=new HashMap<>();
            updateUser.put("name","halime efe");


            given()
                    .spec(reqSpec)
                    .body(updateUser)

                    .when()
                    .put(""+userID)

                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("id",equalTo(userID))
                    .body("name",equalTo("halime efe"))
                    ;



    }



    @Test(dependsOnMethods = "updateUser")
    public void deleteUser() {

        given()
                .spec(reqSpec)
                .when()
                .delete(""+userID)

                .then()
                .log().all()
                .statusCode(204)
        ;


    }

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative() {
        given()
                .spec(reqSpec)
                .when()
                .delete(""+userID)

                .then()
                .log().all()
                .statusCode(404);

    }




}
//    //TODO
//    @Test(dependsOnMethods = "updateUser")
//    public void deleteUser() {
//
//    }
//
//    //TODO
//    @Test(dependsOnMethods = "deleteUser")
//    public void deleteUserNegative() {
//
//    }
