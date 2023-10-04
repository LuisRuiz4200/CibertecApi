package com.cibertec.api.reuzable;

import java.sql.Date;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.serviceImpl.PrestamistaServiceImpl;


public class TEST {
	
	@Autowired
	PrestamistaService prestamistaService = new PrestamistaServiceImpl();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TEST();
	}
	
	public TEST() {
		agregar();
	}
	
	private void agregar () {
		
		Prestamista model = new Prestamista();
		model.getPrestamista().setNombres("Luis");
		model.getPrestamista().setApellidos("Ruiz");
		model.getPrestamista().setEmail("test@gmail");
		model.getPrestamista().setFechaRegistro(new Date(new java.util.Date().getTime()));
		model.getPrestamista().setFechaEdicion(new Date(new java.util.Date().getTime()));
		model.setFechaRegistro(new Date(new java.util.Date().getTime()));
		model.setFechaEdicion(new Date(new java.util.Date().getTime()));
		model.setActivo(true);
		
		model = prestamistaService.guardar(model);
		
		if(Objects.isNull(model)) {
			JOptionPane.showMessageDialog(null, "ERROR !!!");
		}else {
			JOptionPane.showMessageDialog(null, "REGISTRADO OK");	
		}
		
		
	}

}
