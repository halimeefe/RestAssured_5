import Model.Location;
import Model.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured .*;
import static org.hamcrest.Matchers .*;

    public class ZippoTest {

        @Test
        public void test() {

            given()
                    // Hazırlık işlemleri : (token,send body, parametreler)

                    .when()
                    // endpoint (url), metodu

                    .then()
            // assertion, test, data işlemleri

                    .body("****",equalTo("******"))//  BODY NİN DEĞİŞKENİ **** YA EŞİT Mİ
                    .body("places[0].state",equalTo("******")) // PLACES DİZİ OLARAK 0.ELEMANI ***** MIDIR
                    .body("places.'place name'",hasItem("*****"))// BÜTÜN PLACES DİZİSİNDE PLACE NAME LERİN ARASINDA **** VAR MI
                    .body("places",hasSize(1)); // PLACES IN SİZE 1 Mİ

            ;
        }

        @Test
        public void statusCodeTest() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()    // dönen body json datat sı,   log.all()
                    .statusCode(200) // dönüş kodu 200 mü
            ;
        }


        @Test
        public void contentTypeTest() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()    // dönen body json datat sı,   log.all()
                    .statusCode(200) // dönüş kodu 200 mü
                    .contentType(ContentType.JSON) // dönen sonuç JSON mı  // CONTENT TYPE JSON MI

            ;
        }

        @Test
        public void checkCountryInResponseBody() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()    // dönen body json datat sı,   log.all()
                    .statusCode(200) // dönüş kodu 200 mü
                    .body("country", equalTo("United States")) // body nin country değişkeni "United States" eşit Mİ

            // pm.response.json().id -> body.id
            ;
        }

        @Test
        public void checkStateInResponseBody() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()    // dönen body json datat sı,   log.all()
                    .statusCode(200) // dönüş kodu 200 mü
                    .body("places[0].state", equalTo("California"));

        }

        @Test
        public void checkHasItemy() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/tr/01000")

                    .then()
                    //  .log().body()    // dönen body json datat sı,  hepsini göstermemesi için log body kapattık
                    .statusCode(200) // dönüş kodu 200 mü
                    .body("places.'place name'", hasItem("Dörtağaç Köyü"))// bütün place name lerde dörtağaç köyü var mı
            ;
        }

        @Test
        public void bodyArrayHasSizeTest() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    //  .log().body()    // dönen body json datat sı,   log.all()
                    .statusCode(200) // dönüş kodu 200 mü
                    .body("places", hasSize(1))// places 1 mi

            // pm.response.json().id -> body.id
            ;
        }

        @Test
        public void combiningTest() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    //  .log().body()    // dönen body json datat sı,   log.all()
                    .statusCode(200) // dönüş kodu 200 mü
                    .body("places", hasSize(1))//  size ı 1 mi
                    .body("places.state", hasItem("California")) // verilen path deki list bu item e ait mi
                    .body("places[0].'place name'", equalTo("Beverly Hills")); // verilen path deki değer buna eşit mi

        }

        @Test
        public void pathParamTest() {

            given()
                    .pathParam("ulke", "us")     // PATH PARAM GET OLARAK YAZDIĞIMIZ LİNKİN NASIL OLMASI GEREKTİĞİNİ BELİRLEMEK İÇİN KULLANIRIZ
                    .pathParam("postaKod", 90210) // YANİ ULKE YERİNE US ATARIZ POSTA KOD YERİNE 90210 ATADIK
                    .log().uri() //  reguest link


                    .when()
                    .get("http://api.zippopotam.us/{ulke}/{postaKod}")// PATH PARAM İLE BURADAKİ LİNKE DEĞERLERİ GİVEN DA ATAMA YAPTIK

                    .then()
                    .statusCode(200)
            // .log().body()

            ;
        }


        @Test
        public void queryParamTest() {

            given()
                    .param("page", 1)  // ?page=1 şeklinde link ekleniyor  --// query paramda  ise ?page=1 şeklinde linke ekleniyor
                    .log().uri() //  reguest link

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .statusCode(200)
                    .log().body()

            ;

        }

        @Test
        public void queryParamTest2() {

            // https://gorest.co.in/public/v1/users?page=3
            // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
            // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

            for (int i = 1; i < 10; i++) {

                given()
                        .param("page", i)  // ?page=1 şeklinde link ekleniyor //// bu şekilde 1 den 10 a kadar olan linkleri sıralamış oluruz
                        .log().uri() //  reguest link

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()  // log body ile listelediğimizde nasıl sıralama yapacağımızı belirleriz
                        .statusCode(200)
                        .body("meta.pagination.page", equalTo(i)) //  1 DEN 10 A KADAR OLAN URI LARI ÇAĞIRDIK

                ;
            }

        }

        RequestSpecification requestSpec;  // BU FONKSİYONDA  TEST KISMINDA REQUEST LERİMİZİ ÇAĞIRIYORUZ
        ResponseSpecification responseSpec;

        @BeforeClass
        public void Setup() {  // BAŞLANGIÇ İÇİN ÇALIŞMASINI İSTEDİĞİMİZ KISIM

            baseURI = "https://gorest.co.in/public/v1"; // REST ASURED KENDİ DEĞİŞKENİ BUNU TANIMLADIĞIMIZDA TESTTE GET KISMINA SADECE END POİNT İ YAZIYORUZ

            requestSpec = new RequestSpecBuilder()
                    .log(LogDetail.URI) // GİDEN URL Yİ GÖRMEK İSTİYORUZ
                    .setContentType(ContentType.JSON) // CONTENT TİPİNİ JSON İSTİYORUZ
                    .build();

            responseSpec = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON) // DÖNEN TİPİN JSON OLMASINI İSTİYORUZ
                    .expectStatusCode(200) // RESPONSE STATUS CODE 200 OLSUN
                    .log(LogDetail.BODY) //  LONG DETAİLİ BODY İSTİYORUZ
                    .build();
        }


        @Test
        public void requestResponseSpecification() {
            // https://gorest.co.in/public/v1/users?page=3

            given()
                    .param("page", 1)  // ?page=1  şeklinde linke ekleniyor
                    .spec(requestSpec)

                    .when()
                    .get("/users")  // ?page=1

                    .then()
                    .spec(responseSpec)
            ;
        }


        @Test
        public void extractingJsonPath() {
        // LOG BODY DEN SADECE COUNTRY İ ALDIK

            String countryName =  // ÖNCE STRİNG E EŞİTLEDİK

                    given()
                            .when()
                            .get("http://api.zippopotam.us/us/90210")
                            .then()
                            //.log().body()
                            .extract().path("country");
            System.out.println("country name:" + countryName);
            Assert.assertEquals(countryName, "United States");


        }


        @Test
        public void extractingJsonPath1() {

            // PLACE NAME İ SADECE ALDIK
            String placeName =

                    given()
                            .when()
                            .get("http://api.zippopotam.us/us/90210")

                            .then()
                            // .log().body()

                            .extract().path("places[0].'place name'"); //
            System.out.println("place name:" + placeName);
            Assert.assertEquals(placeName, "Beverly Hills");


        }

        @Test
        public void extractingJsonPath2() {


            // https://gorest.co.in/public/v1/users  dönen değerdeki limit bilgisini yazdırınız.

            int limit =
                    given()
                            .when()
                            .get(" https://gorest.co.in/public/v1/users  ")
                            .then()
//                            .log().body()
                            .statusCode(200)
                            .extract().path("meta.pagination.limit");

            System.out.println("limit:" + limit);
            Assert.assertEquals(limit, 10);

        }


        @Test
        public void extractingJsonPath3() {

         // DATA DAKİ BÜTÜN İD LERİ NASIL ALIRIZ

            List<Integer> idler = // İDLER İNTEGER HEPSİNİ LİSTE ATARIZ

                    given()
                            .when()
                            .get(" https://gorest.co.in/public/v1/users  ")
                            .then()
                            .log().body()
                            .statusCode(200)
                            .extract().path("data.id"); // DATA .İD ŞEKLİNDE BÜTÜN HEPSİNİ ALIRIZ

            System.out.println("idler:" + idler); // SOUT LA YAZDIRIRIZ


        }

        @Test
        public void extractingJsonPath4() {

            // DATA DAKİ BÜTÜN NAME LERİ ALIRIZ

            List<String> names =

                    given()
                            .when()
                            .get(" https://gorest.co.in/public/v1/users  ")
                            .then()
                        //       .log().body()
                            .statusCode(200)
                            .extract().path("data.name");

            System.out.println("names:" + names);

        }

        @Test
        public void extractingJsonPathResponsAll() {


           Response donenData= // BÜTÜN DATALARI : İD LER,NAME LER,LİMİT VS. HEPSİNİ ALT ALTA YAZDIRABİLİRİZ

                    given()
                            .when()
                            .get(" https://gorest.co.in/public/v1/users  ")
                            .then()
                                  .log().body()
                            .statusCode(200)
                            .extract().response();

           List<Integer> idler=donenData.path("data.id"); // ÖNCELİKLE LİSTE ATARIZ
           List<String> names=donenData.path("data.name");
           int limit=donenData.path("meta.pagination.limit");

            System.out.println("idler:"+idler);
            System.out.println("names:"+names);
            System.out.println("limit:"+limit);

            Assert.assertTrue(names.contains("Chakor Panicker"));
            Assert.assertTrue(idler.contains(1312988));
            Assert.assertEquals(limit,10,"test sonucu hatalı");
        }


        @Test
        public void extractJsonAll_POJO() { // POJO : JSON NESNESİ : LOCATİON NESNESİ ANLAMINA GELİR


            Location locationNesnesi=
            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()    // dönen body json datat sı,   log.all()
                    .extract().body().as(Location.class) // location şablonuna göre eşitle
            ;
            System.out.println("locationNesnesi.getCountry:"+locationNesnesi.getCountry());

            for (Place p : locationNesnesi.getPlaces()) // ARRAY LİST PLACES I YAZDIRMAK İÇİN FOR EACH OLUŞTURDUK
                System.out.println("p:"+p);

            System.out.println(locationNesnesi.getPlaces().get(0).getPlacename()); // 0.ELEMANIN GET PLACE NAME İ AL YANİ İLK PLACEİN BÜTÜN BİLGİLERİNİ AL
            // BUNUN  KONSOLDA DÜZGÜN OLUŞMASI İÇİN  PLACE CLASINDA TO STRİNG HAZIRLADIK

        }

        @Test
        public void extractPOJO_Soru() {

            // aşağıdaki end pointte Dörtağaç köyü ait diğer bilgileri yazdırınız

           Location adana=
                    given()

                            .when()
                            .get("http://api.zippopotam.us/tr/01000")

                            .then()
                          //  .log().body()    // dönen body json datat sı,   log.all()
                            .statusCode(200)
                            .extract().body().as(Location.class) // location şablonuna göre eşitle
                    ;


            for (Place p : adana.getPlaces())
                if (p.getPlacename().equalsIgnoreCase("Dörtağaç Köyü")){ // ARRAY LİST PLACES I YAZDIRMAK İÇİN FOR EACH OLUŞTURDUK

                    System.out.println("p:"+p);

                }



        }
    }

















