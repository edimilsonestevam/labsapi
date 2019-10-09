package br.sp.edimilsonestevam.starwars;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

import br.sp.edimilsonestevam.core.Base;

public class Planet extends Base{

	@Test
	public void ValidarCaracteristicasPlaneta() {
		
		String nomePlaneta = "Tatooine";
		String periodoRotacaoPlaneta = "23";
		String periodoOrbitalPlaneta = "304";
		String diametroPlaneta = "10465";
		String climaPlaneta = "arid";
		String gravidadePlaneta = "1 standard";
		String tipoPlaneta = "desert";
		String superficePlaneta = "1";
		String populacaoPlaneta = "200000";
		
		given()
		
		.when()
			.log().all()
			.get("/planets/1/")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is(nomePlaneta))
			.body("rotation_period", Matchers.is(periodoRotacaoPlaneta))
			.body("orbital_period", Matchers.is(periodoOrbitalPlaneta))
			.body("diameter", Matchers.is(diametroPlaneta))
			.body("climate", Matchers.is(climaPlaneta))
			.body("gravity", Matchers.is(gravidadePlaneta))
			.body("terrain", Matchers.is(tipoPlaneta))
			.body("surface_water", Matchers.is(superficePlaneta))
			.body("population", Matchers.is(populacaoPlaneta))		
		;
	}
}
