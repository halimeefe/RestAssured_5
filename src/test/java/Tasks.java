import Model.Location;
import Model.Place;
import Model.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured .*;
import static org.hamcrest.Matchers .*;

import static io.restassured.RestAssured .*;
import static org.hamcrest.Matchers .*;



public class Tasks {

    /**
     * Task 2
     * create a request to https://httpstat.us/203  : REGUEST OLUŞTUR
     * expect status 203  : EXPECT STATÜSÜ 203 DOĞRULA
     * expect content type TEXT : CONTENT TYPE TEXT OLARAK DOĞRULA
     */


//         SORU 1:    // aşağıdaki endpointte(link)  Dörtağaç Köyü ait diğer bilgileri yazdırınız   .when()
//                .get("http://api.zippopotam.us/tr/01000%22)
//
//                             .then()
//                .log().body()
//
    @Test
    public void task2() {
        given()

                .when()
                .get("https://httpstat.us/203")
                .then()
                .log().all()
            .statusCode(203)
            .contentType(ContentType.TEXT)

        ;

    }


    @Test
    public void task1() {
        //    create a request to https://jsonplaceholder.typicode.com/todos/2
        //    expect status 200
        //    Converting Into POJO*/


        ToDo todo =
                given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().as(ToDo.class) // location şablonuna göre eşitle
                ;

        System.out.println("todo:" + todo);
        System.out.println("todo.getTitle()=" + todo.getTitle());

    }

    @Test
    public void task3() {

//        Task 3
//        create a request to   https : //jsonplaceholder.typicode.com/todos/2   -- end pointe request oluştur
//        expect status 200  --- status 200 olsun
//        expect content type JSON  --- content type json mı
//        expect title in response body to be "quis ut nam facilis et officia qui" --- expect title body nin title ına eşit mi
//                */ BU SORUDA NESNE ŞART DEĞİL HAMCRES İLE PRATİK BİR ŞEKİLDE ASSERTİON YAPABİLİRİZ

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
                .extract().body().as(ToDo.class) // location şablonuna göre eşitle
        ;

    }

    /**
     * Task 4
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200  -- STATUS KODU 200 OLMALI
     * expect content type JSON  -- CONTENT TYPE JSON OLMALI
     * expect response completed status to be false ---hamcres ile egual to olarak false olduğunu doğrula ,1.yöntem
     * extract completed field and testNG assertion  -- testng assertion ile doğrula boolean olarak, 2. yöntem
     */


    @Test
    public void task4() {


        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed",equalTo(false))
        ;
         //2.yöntem
        Boolean completed=
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().path("completed")
                ;
        Assert.assertFalse(completed);

    }
}



