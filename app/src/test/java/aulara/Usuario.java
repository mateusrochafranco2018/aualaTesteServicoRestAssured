package aulara;

import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class Usuario {

    private String nome;
    private String email;
    private String password;
    private String administrador;

    public Usuario(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public void listarUsuarios() {
        when()
                .get("http://localhost:3000/usuarios")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public String cadatrarUsuario(Usuario usuario) {
        String userID = given()
                                    .body("{\n" +
                                            "  \"nome\": \"" +  usuario.nome + "\",\n" +
                                            "  \"email\": \"" + usuario.email + "\",\n" +
                                            "  \"password\": \"" + usuario.password + "\",\n" +
                                            "  \"administrador\": \"" + usuario.administrador + "\"\n" +
                                            "}")
                                    .contentType("application/json")
                        .when()
                                    .post("http://localhost:3000/usuarios")
                        .then()
                                    .statusCode(HttpStatus.SC_CREATED)
                                    .body("message", is("Cadastro realizado com sucesso"))
                                    .extract().path("_id");
        return userID;
    }

    public void listarUsuarioPorID(String userID) {
        given()
                .pathParam("_id", userID)
        .when()
                .get("http://localhost:3000/usuarios/{_id}")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("_id", is(userID));
    }

    public void listarUsuarioPorIDErro(String userID) {
        given()
                .pathParam("_id", userID)
                .when()
                .get("http://localhost:3000/usuarios/{_id}")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("_id", is(userID));
    }

    public void excluirUsuarioPorID(String userID) {
        given()
                .pathParam("_id", userID)
        .when()
                .delete("http://localhost:3000/usuarios/{_id}")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", is("Registro excluído com sucesso"));
    }

    public void excluirUsuarioPorIDComCarrinhoAssociado(String userID) {
        given()
                .pathParam("_id", userID)
                .when()
                .delete("http://localhost:3000/usuarios/{_id}")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Não é permitido excluir usuário com carrinho cadastrado"));
    }

    public void editarUsuarioExistente(String userID, Usuario usuario, Boolean exists) {

        String message;
        Integer statusCode;

        if (exists) {
            statusCode = HttpStatus.SC_OK;
            message = "Registro alterado com sucesso";
        } else {
            statusCode = HttpStatus.SC_CREATED;
            message = "Cadastro realizado com sucesso";
        }

        given()
                .pathParam("_id", userID)
                .body("{\n" +
                        "  \"nome\": \"" +  usuario.nome + "\",\n" +
                        "  \"email\": \"" + usuario.email + "\",\n" +
                        "  \"password\": \"" + usuario.password + "\",\n" +
                        "  \"administrador\": \"" + usuario.administrador + "\"\n" +
                        "}")
                .contentType("application/json")
        .when()
                .put("http://localhost:3000/usuarios/{_id}")
        .then()
                .statusCode(statusCode)
                .body("message", is(message));
    }
}