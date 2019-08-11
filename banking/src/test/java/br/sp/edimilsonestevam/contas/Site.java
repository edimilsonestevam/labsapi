package br.sp.edimilsonestevam.contas;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.sp.edimilsonestevam.core.Base;
import br.sp.edimilsonestevam.utils.DataUtils;

import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)//Coloca os metodos para serem executados em ordem alfabetica
public class Site extends Base{

	private static String CONTA_NAME = "Conta" + System.nanoTime();//Serve para colocar o horário, quando a conta é criada
	private static Integer CONTA_ID;
	private static Integer QUANTIDADE_DIAS = 2;
	private static Integer QUANTIDADE_DIAS_TRANSACAO = -1;
	private static Integer QUANTIDADE_DIAS_PAGAMENTO = 5;
	private static Integer MOVIMENTACAO_ID;
	
	private ApoioMovimentacao getMovimentacaoValida() {
		
		ApoioMovimentacao movimentacao = new ApoioMovimentacao();
		movimentacao.setConta_id(CONTA_ID);
		//movimentacao.setUsuario_id(usuario_id);
		movimentacao.setDescricao("Cheque");
		movimentacao.setEnvolvido("Edimilson Estevam");
		movimentacao.setTipo("REC");
		movimentacao.setData_transacao(DataUtils.getDataDiferencaDias(QUANTIDADE_DIAS_TRANSACAO));
		movimentacao.setData_pagamento(DataUtils.getDataDiferencaDias(QUANTIDADE_DIAS_PAGAMENTO));
		movimentacao.setValor(100f);
		movimentacao.setStatus(true);
		
		return movimentacao;
		
	}
	
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
					   		.log().all()
					   		.statusCode(200)
					   		.extract().path("token")
					   ;
		
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);//Para os métodos herdarem o TOKEN
		
	}
	
	@Test
	public void ct02_deveIncluirContaComSucesso() {
		
		CONTA_ID = given()
						.body("{\"nome\": \""+CONTA_NAME+"\"}")//Usando a variável CONTA_NAME
				  .when()
				  		.post("/contas")
				  .then()
				        .log().all()
				  		.statusCode(201)//Status de inclusao
				  		.extract().path("id")//Extrai o código de identificacao da conta criada
				  ;
		
	}
	
	@Test
	public void ct03_deveAlterarContaComSucesso() {
		
		given()
			.body("{\"nome\": \""+CONTA_NAME+"\"}")//Usando a variável CONTA_NAME
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")//id da conta criada
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", is(CONTA_NAME))
		;
		
	}
	
	@Test
	public void ct04_naoDeveInserirContaMesmoNome() {
		
		given()
			.body("{\"nome\": \""+CONTA_NAME+"\"}")//Usando a variável CONTA_NAME
		.when()
			.post("/contas")//Inclusao sempre sera tipo POST
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
		
	}
	
	@Test
	public void ct05_deveInserirMovimentacaoSucesso() {

		ApoioMovimentacao movimentacao = getMovimentacaoValida();
		
		MOVIMENTACAO_ID = given()
								.body(movimentacao)
						  .when()
						  		.post("/transacoes")
						  .then()
						  		.log().all()
						  		.statusCode(201)
						  		.extract().path("id")
						  ;
		
	}
	
	@Test
	public void ct06_deveValidarCamposObrigatoriosMovimentacao() {
		
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.log().all()
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
	public void ct07_naoDeveInserirMovimentacaoComDataFutura() {
		
		ApoioMovimentacao movimentacao = getMovimentacaoValida();
		movimentacao.setData_transacao(DataUtils.getDataDiferencaDias(QUANTIDADE_DIAS));
		
		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
		
	}
	
	@Test
	public void ct08_naoDeveRemoverContaComMovimentacao() {
		
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.log().all()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
		
	}
	
	@Test
	public void ct09_deveCalcularSaldoContas() {
		
		given()
		.when()
			.get("/saldo")
		.then()
			.log().all()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("100.00")) //Valor que está na conta
		;
		
	}
	
	@Test
	public void ct10_deveRemoverMovimentacao() {
		
		given()
			.pathParam("id", MOVIMENTACAO_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.log().all()
			.statusCode(204)
		;
		
	}
	
	@Test
	public void ct11_naoDeveAcessarAPISemToken() {
		
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		given()
		.when()
			.get("/contas")//Rota
		.then()
			.log().all()
			.statusCode(401)//Status nao autorizado
		;
		
	}
	
}