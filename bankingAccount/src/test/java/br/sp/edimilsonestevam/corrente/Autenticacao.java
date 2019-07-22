package br.sp.edimilsonestevam.corrente;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.sp.edimilsonestevam.core.Base;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class Autenticacao extends Base{
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		given()
		.when()
			.get("/contas")//Rota
		.then()
			.statusCode(401)//Status nao autorizado
		;
		
	}
}
