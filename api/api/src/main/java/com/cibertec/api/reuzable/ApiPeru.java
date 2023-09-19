package com.cibertec.api.reuzable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ApiPeru {
//https://dniruc.apisperu.com/api/v1/dni/75963002?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImxyMjcwNzIwMDFAZ21haWwuY29tIn0.uSeFR7UMIxYmAzZmllUAVPBZHFiqghwmoMYoFTbhTIo

	private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImxyMjcwNzIwMDFAZ21haWwuY29tIn0.uSeFR7UMIxYmAzZmllUAVPBZHFiqghwmoMYoFTbhTIo";
	
	public static String consultaPersona(String tipoDocumento, String numDocumento) {
		
		String res = "";
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {

			Unirest.setTimeouts(0, 0);
		    HttpResponse<String> response = Unirest.get("https://dniruc.apisperu.com/api/v1/"+tipoDocumento+"/"+numDocumento+"?token="+token).asString();
		    
		    System.out.println(response.getBody());
		    res = response.getBody();
		    
		    JsonObject jsonObject= new JsonObject();
		    
		    jsonObject = JsonParser.parseString(res).getAsJsonObject();
		    
		    res = gson.toJson(jsonObject);
		    
		}catch(Exception ex) {
			res = ex.getMessage();
		}
		
	  
		
		return res;
	}
	

}
