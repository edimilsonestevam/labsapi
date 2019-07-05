package br.sp.edimilsoestevam.corrente;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.edimilsonestevam.contas.ApoioMovimentacao;
import br.sp.edimilsonestevam.core.Base;
import br.sp.edimilsonestevam.utils.SiteUtils;
import br.sp.edimilsonestevam.utils.DataUtils;

public class Movimentacao extends Base{
	
	@Test
	public void deveInserirMovimentacaoSucesso() {

		ApoioMovimentacao movimentacao = getMovimentacaoValida();
		
		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
		;
		
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
		
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))//Quantidade de itens na lista
			.body("msg", hasItems(
								  "Data da Movimentação é obrigatório",
								  "Data do pagamento é obrigatório",
								  "Descrição é obrigatório",
								  "Interessado é obrigatório",
								  "Valor é obrigatório",
								  "Valor deve ser um número",
								  "Conta é obrigatório",
								  "Situação é obrigatório"
								  ))
		;
		
	}
	
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura() {
		
		ApoioMovimentacao movimentacao = getMovimentacaoValida();
		movimentacao.setData_transacao(DataUtils.getDataDiferencaDias(2));
		
		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
		
	}
	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		
		Integer CONTA_ID = SiteUtils.getIdContaPeloNome("Conta com movimentacao");
				
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
		
	}
	
	@Test
	public void deveRemoverMovimentacao() {
		
		Integer MOVIMENTACAO_ID = SiteUtils.getIdMovimentacaoPelaDescricao("Movimentacao para exclusao");
		
		given()
			.pathParam("id", MOVIMENTACAO_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
		
	}
	
	private ApoioMovimentacao getMovimentacaoValida() {
		
		ApoioMovimentacao movimentacao = new ApoioMovimentacao();
		movimentacao.setConta_id(SiteUtils.getIdContaPeloNome("Conta para movimentacoes"));
		movimentacao.setDescricao("Cheque");
		movimentacao.setEnvolvido("Edimilson Estevam");
		movimentacao.setTipo("REC");
		movimentacao.setData_transacao(DataUtils.getDataDiferencaDias(-1));
		movimentacao.setData_pagamento(DataUtils.getDataDiferencaDias(5));
		movimentacao.setValor(100f);
		movimentacao.setStatus(true);
		
		return movimentacao;
		
	}
}
