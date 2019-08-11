package br.sp.edimilsonestevam.core;

import io.restassured.http.ContentType;

public interface Constantes {

	String APP_BASE_URL = "https://swapi.co/api";
	Integer APP_PORT = 443; //http -> 80
	String APP_BASE_PATH = ""; //Uso para indicar versoes da aplicacao, como v1, v2 etc
	
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	
	Long MAX_TIMEOUT = 5000L; //5 segundos
	
}
