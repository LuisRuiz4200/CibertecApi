package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.TipoComprobante;
import com.cibertec.api.model.TipoDocumento;
import com.cibertec.api.reuzable.Utils;
import com.cibertec.api.service.ComprobanteService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestamoService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.TipoComprobanteService;
import com.cibertec.api.service.TipoDocumentoService;
import com.mashape.unirest.request.HttpRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("web/comprobante")
public class ComprobanteController {

	@Autowired
	private ComprobanteService comprobanteService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private TipoComprobanteService tipoComprobanteService;
	@Autowired
	private PrestamistaService prestamistaService;
	@Autowired
	private PrestatarioService prestatarioService;
	@Autowired
	private PrestamoService prestamoService;

	@GetMapping("/listaCuotaPorPrestamo")
	private String listarCuotaPorPrestamo(Model model) {

		List<Prestatario> listaPrestatario = new ArrayList();

		listaPrestatario = prestatarioService.listarPrestatario().stream()
				.filter(c -> c.getPrestamistaPrestatario().getIdPrestamista() == 59).toList();

		model.addAttribute("listaPrestatario", listaPrestatario);

		return "listaCuotaPorPrestamo";
	}
	
	@GetMapping("/registrar")
	private String registrar(Model model) {


		Comprobante comprobante = new Comprobante();
		List<TipoComprobante> listaTipoComprobante = new ArrayList<>();
		List<TipoDocumento> listaTipoDocumento = new ArrayList<>();
		
		try {

			listaTipoComprobante = tipoComprobanteService.listar().stream()
					.filter(tipo -> !tipo.getDescripcion().equals("FACTURA")).toList();

			listaTipoDocumento = tipoDocumentoService.listar();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		

		model.addAttribute("listaTipoComprobante", listaTipoComprobante);
		model.addAttribute("listaTipoDocumento", listaTipoDocumento);
		model.addAttribute("comprobante", comprobante);
		
		return "guardarComprobante";
	}

	@GetMapping("/registrar/prestamo")
	private String registrar(Model model, HttpServletRequest request,
			@RequestParam(name = "idPrestamo", required = false,defaultValue = "0") Integer idPrestamo,
			@RequestParam(name = "idCuotaPrestamo", required = false,defaultValue = "0") Integer idCuotaPrestamo) {
		

		Comprobante comprobante = new Comprobante();
		Prestamo prestamo = new Prestamo();
		CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();
		ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
		List<CuotaPrestamo> listaCuotaPrestamo = new ArrayList<>();
		List<CuotaPrestamo> listaCuotaPrestamoPendiente = new ArrayList<>();
		List<TipoComprobante> listaTipoComprobante = new ArrayList<>();
		List<TipoDocumento> listaTipoDocumento = new ArrayList<>();

		String mensaje = "";
		String nombresApellidos = "";
		String numDocReceptor= "";

		try {

			prestamo = prestamoService.buscarPorId(idPrestamo);

			nombresApellidos = prestamo.getSolicitudPrestamo().getPrestatario().getPrestatario().getNombresApellidos();
			numDocReceptor = prestamo.getSolicitudPrestamo().getPrestatario().getPrestatario().getDni();
			
			if(!Objects.isNull(prestamo)) {
				listaCuotaPrestamo = prestamo.getListaCuotaPrestamo();
			}
			
			
			listaCuotaPrestamoPendiente = listaCuotaPrestamo.stream()
					.filter(c->c.getEstado().matches("("+Utils.PAGO_PENDIENTE+")||("+Utils.PAGO_PARCIAL+")"))
					.toList();
			
			cuotaPrestamo = listaCuotaPrestamo.stream()
					.filter(c -> c.getCuotaPrestamoPk().getIdCuotaPrestamo() == idCuotaPrestamo)
					.findFirst().get();
			
			comprobante.getTipoComprobante().setIdTipoComprobante(1);
			comprobante.getTipoDocumento().setIdTipoDocumento(2);
			comprobante.setSerie("B001");
			comprobante.setCorrelativo(000);
			comprobante.setNumDocReceptor(numDocReceptor);
			comprobante.setRucEmisor("20759630049");
			comprobante.setNomEmisor("ME PRESTA ONLINE SAC SAC");
			
			comprobanteDetalle.setCuotaPrestamo(cuotaPrestamo);
			comprobanteDetalle.setCodItem("C-" + cuotaPrestamo.getCuotaPrestamoPk().getIdCuotaPrestamo());
			comprobanteDetalle.setCantidadItem(1);
			comprobanteDetalle.setDescripcion("PAGO COMPLETO DE LA CUOTA " + cuotaPrestamo.getCuotaPrestamoPk().getIdCuotaPrestamo());
			comprobanteDetalle.setMontoItem(cuotaPrestamo.getMontoTotal());
			comprobanteDetalle.setMontoTotal(cuotaPrestamo.getMontoTotal() * comprobanteDetalle.getCantidadItem());
			
			comprobante.getListaComprobanteDetalle().add(comprobanteDetalle);
			
			listaTipoComprobante = tipoComprobanteService.listar().stream()
					.filter(tipo -> !tipo.getDescripcion().equals("FACTURA")).toList();
			
			listaTipoDocumento = tipoDocumentoService.listar();

		} catch (Exception ex) {
			mensaje = ex.getMessage();
			ex.printStackTrace();
		}

		model.addAttribute("nombresApellidos", nombresApellidos);
		model.addAttribute("listaDetalleComprobante",comprobante.getListaComprobanteDetalle());
		model.addAttribute("listaTipoComprobante", listaTipoComprobante);
		model.addAttribute("listaTipoDocumento", listaTipoDocumento);
		model.addAttribute("listaCuotaPrestamoPendiente", listaCuotaPrestamoPendiente);
		model.addAttribute("comprobante", comprobante);
		model.addAttribute("cuotaPrestamo", cuotaPrestamo);
		model.addAttribute("mensaje", mensaje);

		return "guardarComprobante";
	}

	@PostMapping("/guardar")
	private String guardar(@ModelAttribute Comprobante comprobante, Model model) {

		String mensaje = "";

		try {

			comprobante = comprobanteService.guardar(comprobante);

		} catch (Exception ex) {
			mensaje = ex.getMessage();
		}

		model.addAttribute("mensaje", mensaje);
		model.addAttribute("comprobante", comprobante);

		return "guardarComprobante";
	}
}
