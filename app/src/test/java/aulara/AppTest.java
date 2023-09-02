package aulara;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class AppTest {

    private String userID;

    @BeforeClass
    public static void preCondition() {
        baseURI = "http://localhost";
        port = 3000;
    }

    @Test
    public void getUsuarios() {
        when()
                .get("/usuarios")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void postUsuario() {
        userID = given()
                        .body("{\n" +
                                "  \"nome\": \"6Rest Assured\",\n" +
                                "  \"email\": \"testrestassured6@qa.com.br\",\n" +
                                "  \"password\": \"teste\",\n" +
                                "  \"administrador\": \"true\"\n" +
                                "}")
                        .contentType("application/json")
                .when()
                        .post("/usuarios")
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .body("message", is("Cadastro realizado com sucesso"))
                        .extract().path("_id");
        System.out.println(getUserID());
    }

    public String getUserID() {
        return userID;
    }
}
