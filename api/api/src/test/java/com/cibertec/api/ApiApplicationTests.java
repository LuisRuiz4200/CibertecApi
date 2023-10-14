package com.cibertec.api;

import java.sql.Date;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cibertec.api.model.PrestamistaM;
import com.cibertec.api.service.PrestamistaMService;
import com.cibertec.api.serviceImpl.PrestamistaMServiceImpl;

@SpringBootTest
class ApiApplicationTests extends PrestamistaMServiceImpl {
	
	@Autowired
	PrestamistaMService prestamistaService;

	@Test
	void contextLoads() {
		
		//creamos variable encoder de tipo BCryptPass...
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		//Imprimo en consola por medio de encoder.encode y pasamos dentro de "el password" ya encriptado la contraseña
		System.out.println(encoder.encode("123"));
		
	}
	
	

}
