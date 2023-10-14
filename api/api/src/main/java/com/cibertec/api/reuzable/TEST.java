package com.cibertec.api.reuzable;

import java.sql.Date;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.cibertec.api.model.PrestamistaM;
import com.cibertec.api.service.PrestamistaMService;
import com.cibertec.api.serviceImpl.PrestamistaMServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class TEST {
	
	final PrestamistaMService prestamistaService = new PrestamistaMServiceImpl();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TEST();
	}
	
	public TEST(PrestamistaMService prestamistaService) {
		agregar();
        this.prestamistaService = prestamistaService; 
	}
	
	private void agregar () {
		
		PrestamistaM model = new PrestamistaM();
		model.getPrestamista().setNombres("Luis");
		model.getPrestamista().setApellidos("Ruiz");
		model.getPrestamista().setEmail("test@gmail");
		model.getPrestamista().setFechaRegistro(new Date(new java.util.Date().getTime()));
		model.getPrestamista().setFechaRegistro(new Date(new java.util.Date().getTime()));
		model.getPrestamista().setFechaEdicion(new Date(new java.util.Date().getTime()));
		model.setFechaRegistro(new Date(new java.util.Date().getTime()));
		model.setFechaEdicion(new Date(new java.util.Date().getTime()));
		model.setActivo(true);

		
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyy-MM-dd").create();
		
		System.out.println(gson.toJson(model));
		
		//model = prestamistaService.guardar(model);
		
		if(Objects.isNull(model)) {
			JOptionPane.showMessageDialog(null, "ERROR !!!");
		}else {
			JOptionPane.showMessageDialog(null, "REGISTRADO OK");	
		}
		try {
			Unirest.setTimeouts(0, 0);
			HttpResponse<String> response = Unirest.post("http://localhost:9090/prestamista/registrar")
			  .header("Content-Type", "application/json")
			  .body(gson.toJson(model))
			  .asString();
			
			System.out.println(response.getBody());
			
		}catch(Exception ex){
			
		}
		
	}

}
