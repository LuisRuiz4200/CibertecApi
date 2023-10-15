package com.cibertec.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.serviceImpl.PrestamistaServiceImpl;

@SpringBootTest
class ApiApplicationTests extends PrestamistaServiceImpl {


	@Autowired
	PrestamistaService prestamistaService;

	@Test
	void contextLoads() {
		
		//creamos variable encoder de tipo BCryptPass...
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		//Imprimo en consola por medio de encoder.encode y pasamos dentro de "el password" ya encriptado la contraseña
		System.out.println(encoder.encode("123"));
		
	}
	
	

}
