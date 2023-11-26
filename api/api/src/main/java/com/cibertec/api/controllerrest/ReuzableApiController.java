package com.cibertec.api.controllerrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@RestController
@RequestMapping("/api/reuzable/")
public class ReuzableApiController {


	
	@GetMapping("/consulta/{tipoDocumento}/{numDocumento}")
public static String consultaDni(@PathVariable String tipoDocumento  , @PathVariable String numDocumento) {
		
		String res = "";
		
		
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImxyMjcwNzIwMDFAZ21haWwuY29tIn0.uSeFR7UMIxYmAzZmllUAVPBZHFiqghwmoMYoFTbhTIo";
		
		
		try {
			
			Unirest.setTimeouts(0, 0);
			HttpResponse<String> response = Unirest.get("https://dniruc.apisperu.com/api/v1/"+tipoDocumento+"/"+ numDocumento +"?token=" + token)
			  .header("Content-Type", "application/json")
			  .asString();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			res =response.getBody();
			
			JsonObject jsonObject = JsonParser.parseString(res).getAsJsonObject();
			
			res = gson.toJson(jsonObject);
			
			
		}catch(Exception ex) {
			res = ex.getMessage();
		}
		

		System.out.println(res);
		
		return res;
	}
}
