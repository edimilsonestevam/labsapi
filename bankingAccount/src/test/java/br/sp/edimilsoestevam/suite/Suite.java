package br.sp.edimilsoestevam.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.sp.edimilsoestevam.corrente.Autenticacao;
import br.sp.edimilsoestevam.corrente.Conta;
import br.sp.edimilsoestevam.corrente.Movimentacao;
import br.sp.edimilsoestevam.corrente.Saldo;
import br.sp.edimilsonestevam.core.Base;
import io.restassured.RestAssured;

@RunWith(org.junit.runners.Suite.class)//Declaracao para informar que esta classe eh uma Suite
@SuiteClasses({
		
	Conta.class,
	Movimentacao.class,
	Saldo.class,
	Autenticacao.class
	
})

public class Suite extends Base{

	@BeforeClass
	public static void login() {
		
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "edimilsonestevam@gmail.com");
		login.put("senha", "teste123");
		
		String TOKEN = given()
							.body(login)
					   .when()
					   		.post("/signin")
					   .then()
					   		.statusCode(200)
					   		.extract().path("token")
					   ;
		
		RestAssured.requestSpecification.header("Authorization","JWT "+TOKEN);
		
		RestAssured.get("/reset").then().statusCode(200);
		
	}
	
}
