package br.sp.edimilsonestevam.utils;

import io.restassured.RestAssured;

public class SiteUtils {
	
	public static Integer getIdContaPeloNome(String nomeContaId) { //Quando o metodo esta com o modificador STATIC, qualquer metodo pode usa-lo
		
		return RestAssured.get("/contas?nome="+nomeContaId).then().extract().path("id[0]");
		
	}
	
	public static Integer getIdMovimentacaoPelaDescricao(String descricaoMovimentacaoId) {
		
		return RestAssured.get("/transacoes?descricao="+descricaoMovimentacaoId).then().extract().path("id[0]");
		
	}
	
}
