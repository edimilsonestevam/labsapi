package br.sp.edimilsoestevam.corrente;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.edimilsonestevam.core.Base;
import br.sp.edimilsonestevam.utils.SiteUtils;

public class Saldo extends Base{
	
	@Test
	public void deveCalcularSaldoContas() {
		
		Integer CONTA_ID = SiteUtils.getIdContaPeloNome("Conta para saldo");
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("534.00")) //Valor que está na conta
		;
		
	}

}
