package com.cibertec.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.api.service.GrupoPrestamistaService;

@Controller
@RequestMapping("/grupoPrestamista")
public class GrupoPrestamistaController {
	
	@Autowired
	private GrupoPrestamistaService grupoPrestamistaService;

}
