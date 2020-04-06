package br.sp.edimilsonestevam.stepDefinition;

import br.sp.edimilsonestevam.service.PlaceHolder;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestSteps {

	protected String url = "http://jsonplaceholder.typicode.com/posts";

	@Given("^eu executo a API Place Holder atraves da URL <url>$")
	public void eu_executo_a_API_Place_Holder_atraves_da_URL_url() throws Throwable {

		new PlaceHolder().MostrarAPI(url);
	}

	@When("^o sistema retorna um Cookie$")
	public void o_sistema_retorna_um_Cookie() throws Throwable {

		new PlaceHolder().MostrarCookie(url);
	}

	@Then("^obtenho o codigo da resposta <responseCode>$")
	public void obtenho_o_codigo_da_resposta_responseCode() throws Throwable {

		new PlaceHolder().MostrarCodigoDaResposta(url);
	}

	@Then("^obtenho o corpo da resposta <responseBody>$")
	public void obtenho_o_corpo_da_resposta_responseBody(DataTable arg1) throws Throwable {

		new PlaceHolder().MostrarCorpoDaResposta(url);
	}
}