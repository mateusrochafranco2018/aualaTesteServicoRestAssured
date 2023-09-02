package aulara;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

public class Exercicio04 {

    private Usuario usuario;
    private Produto produto;
    private Carrinho carrinho;
    private Login login;

    private Faker faker;


    @Before
    public void preCondittion(){
        faker = new Faker();
        String userName = faker.name().firstName();
        String email = userName + "@qa.com.br";
        String password = faker.internet().password();
        usuario = new Usuario(userName,email,password,"true");
        login = new Login(email,password);
        String productName = faker.pokemon().name();
        produto = new Produto(productName,1000,"Pokemon",100);
        carrinho = new Carrinho();

    }

    @Test
    public void verificarExcluirUsuarioComCarrinhoAssociado(){
        String userID = usuario.cadatrarUsuario(usuario);
        String userToken = login.efetuarLogin(login);
        String productID = produto.cadastrarProduto(produto,userToken);
        usuario.listarUsuarioPorID(userID);
        carrinho.cadastrarCarrinho(productID,5,userToken);
        usuario.excluirUsuarioPorIDComCarrinhoAssociado(userID);
        carrinho.cancelarCompra(userToken);
        usuario.excluirUsuarioPorID(userID);
    }
}
