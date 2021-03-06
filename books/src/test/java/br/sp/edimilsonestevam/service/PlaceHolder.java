package br.sp.edimilsonestevam.service;

import static io.restassured.RestAssured.given;

public class PlaceHolder {

	public void MostrarAPI(String url) {
		
		System.out.println("API NAME: " + "\n" + url);
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		
	}
	
	public void MostrarCookie(String url) {
		
		System.out.println("COOKIE NAME:");
		given().get(url).then().statusCode(200).log().cookies();
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		
	}
	
	public void MostrarCodigoDaResposta(String url) {

		System.out.println("RESPONSE CODE:");
		given().get(url).then().statusCode(200).log().status();
		System.out.println("------------------------------------------------------------------------------------------------------------------");

	}
	
	public void MostrarCorpoDaResposta(String url) {
		
		System.out.println("RESPONSE BODY:");
		given().get(url).then().statusCode(200).log().body();
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		
	}
}
