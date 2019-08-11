package br.sp.edimilsonestevam.corrente;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.edimilsonestevam.core.Base;
import br.sp.edimilsonestevam.utils.SiteUtils;

public class Conta extends Base{
	
	@Test
	public void deveIncluirContaComSucesso() {
		
		given()
			.body("{\"nome\": \"Conta para alterar\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;
		
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		
		Integer CONTA_ID = SiteUtils.getIdContaPeloNome("Conta para alterar");
		
		given()
			.body("{\"nome\": \"Personalité\"}")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")//id da conta criada
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", is("Personalité"))
		;
		
	}
	
	@Test
	public void naoDeveInserirContaMesmoNome() {
		
		given()
			.body("{\"nome\": \"Personalité\"}")
		.when()
			.post("/contas")//Inclusao sempre sera tipo POST
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
		
	}

}
