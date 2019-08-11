package br.sp.edimilsonestevam.starwars;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

import br.sp.edimilsonestevam.core.Base;

public class People extends Base{

	@Test
	public void validarCaracteristicasPersonagem() {
		
		String nomePersonagem = "Luke Skywalker";
		String alturaPersonagem = "172";
		String pesoPersonagem = "77";
		String corCabeloPersonagem = "blond";
		String corPelePersonagem = "fair";
		String corOlhosPersonagem = "blue";
		String dataNascimentoPersonagem = "19BBY";
		String sexoPersonagem = "male";
		
		given()
			
		.when()
			.log().all()
			.get("/people/1/")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is(nomePersonagem))
			.body("height", Matchers.is(alturaPersonagem))
			.body("mass", Matchers.is(pesoPersonagem))
			.body("hair_color", Matchers.is(corCabeloPersonagem))
			.body("skin_color", Matchers.is(corPelePersonagem))
			.body("eye_color", Matchers.is(corOlhosPersonagem))
			.body("birth_year", Matchers.is(dataNascimentoPersonagem))
			.body("gender", Matchers.is(sexoPersonagem))
		;
	}
}
