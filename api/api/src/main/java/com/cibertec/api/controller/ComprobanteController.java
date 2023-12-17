package com.cibertec.api.controller;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.TipoComprobante;
import com.cibertec.api.model.TipoDocumento;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.reuzable.Utils;
import com.cibertec.api.service.ComprobanteDetalleService;
import com.cibertec.api.service.ComprobanteService;
import com.cibertec.api.service.CuotaPrestamoService;
import com.cibertec.api.service.PrestamoService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.TipoComprobanteService;
import com.cibertec.api.service.TipoDocumentoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("web/comprobante")
public class ComprobanteController {

	@Autowired
	private ComprobanteService comprobanteService;
	@Autowired
	private ComprobanteDetalleService comprobanteDetalleService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private TipoComprobanteService tipoComprobanteService;
	@Autowired
	private PrestatarioService prestatarioService;
	@Autowired
	private PrestamoService prestamoService;
	@Autowired
	private CuotaPrestamoService cuotaPrestamoService;

	@GetMapping("/listaCuotaPorPrestamo")
	private String listarCuotaPorPrestamo(Model model, HttpSession session) {

		Usuario usuario = new Usuario();
		List<Prestatario> listaPrestatario = new ArrayList<>();

		usuario = (Usuario) session.getAttribute("UserLogged");
		Integer idPrestamista = usuario.getPersona().getIdPersona();

		listaPrestatario = prestatarioService.listarPrestatario().stream()
				.filter(c -> c.getPrestamistaPrestatario().getIdPrestamista() == idPrestamista).toList();

		model.addAttribute("listaPrestatario", listaPrestatario);

		return "listaCuotaPorPrestamo";
	}

	@GetMapping("/registrar")
	private String registrar(Model model) {

		Comprobante comprobante = new Comprobante();
		List<TipoComprobante> listaTipoComprobante = new ArrayList<>();
		List<TipoDocumento> listaTipoDocumento = new ArrayList<>();
		List<CuotaPrestamo> listaCuotaPrestamoPendiente = new ArrayList<>();

		try {

			comprobante.setRucEmisor("20759630049");
			comprobante.setNomEmisor("ME PRESTA ONLINE SAC SAC");
			comprobante.setCorrelativo(0);

			listaCuotaPrestamoPendiente = cuotaPrestamoService.listar().stream()
					.filter(c -> c.getEstado().matches("(" + Utils.PAGO_PENDIENTE + ")||(" + Utils.PAGO_PARCIAL + ")"))
					.toList();

			listaTipoComprobante = tipoComprobanteService.listar().stream()
					.filter(tipo -> !tipo.getDescripcion().equals("FACTURA")).toList();

			listaTipoDocumento = tipoDocumentoService.listar();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		model.addAttribute("listaTipoComprobante", listaTipoComprobante);
		model.addAttribute("listaTipoDocumento", listaTipoDocumento);
		model.addAttribute("comprobante", comprobante);
		model.addAttribute("listaCuotaPrestamoPendiente", listaCuotaPrestamoPendiente);

		return "guardarComprobante";
	}

	@GetMapping("/registrar/prestamo")
	private String registrar(Model model, HttpServletRequest request,
			@RequestParam(name = "idPrestamo", required = false, defaultValue = "0") Integer idPrestamo,
			@RequestParam(name = "idCuotaPrestamo", required = false, defaultValue = "0") Integer idCuotaPrestamo) {

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
		String numDocReceptor = "";
		Double montoMora = 0.00;
		Integer diasMora = 0;
		Double montoTotal = 0.00;
		Double montoPagado = 0.00;

		try {

			prestamo = prestamoService.buscarPorId(idPrestamo);

			nombresApellidos = prestamo.getSolicitudPrestamo().getPrestatario().getPrestatario().getNombresApellidos();
			numDocReceptor = prestamo.getSolicitudPrestamo().getPrestatario().getPrestatario().getDni();

			if (!Objects.isNull(prestamo)) {
				listaCuotaPrestamo = prestamo.getListaCuotaPrestamo();
			}

			listaCuotaPrestamoPendiente = listaCuotaPrestamo.stream()
					.filter(c -> c.getEstado().matches("(" + Utils.PAGO_PENDIENTE + ")||(" + Utils.PAGO_PARCIAL + ")"))
					.toList();

			cuotaPrestamo = listaCuotaPrestamo.stream()
					.filter(c -> c.getCuotaPrestamoPk().getIdCuotaPrestamo() == idCuotaPrestamo).findFirst().get();

			comprobante.getTipoComprobante().setIdTipoComprobante(1);
			comprobante.getTipoDocumento().setIdTipoDocumento(2);
			comprobante.setSerie("B001");
			comprobante.setCorrelativo(000);
			comprobante.setNumDocReceptor(numDocReceptor);
			comprobante.setRucEmisor("20759630049");
			comprobante.setNomEmisor("ME PRESTA ONLINE SAC SAC");

			Date fechaPago = cuotaPrestamo.getFechaPago();
			Date fechaActual = new Date(new java.util.Date().getTime());
			diasMora = Integer
					.parseInt(ChronoUnit.DAYS.between(fechaPago.toLocalDate(), fechaActual.toLocalDate()) + "");

			montoMora = Utils.calcularMora(cuotaPrestamo.getMontoTotal(), diasMora, 0.8);
			montoMora = montoMora < 0 ? 0.00 : montoMora;
			montoTotal = Utils.formatearDecimales((cuotaPrestamo.getMontoTotal() * 1) + montoMora, "0.00");
			montoPagado = montoPagadoPorCuotaPrestamo(idPrestamo, idCuotaPrestamo);

			System.out.println("fechaPafo = " + fechaPago);
			System.out.println("fechaActual = " + fechaActual);
			System.out.println("diasMora = " + diasMora);
			System.out.println("cuotaPrestamo = " + cuotaPrestamo.getMontoTotal());
			System.out.println("montoMora = " + montoMora);
			System.out.println("montoPagado = " + montoPagado);

			comprobanteDetalle.setCuotaPrestamo(cuotaPrestamo);
			comprobanteDetalle.setCodItem("C-" + cuotaPrestamo.getCuotaPrestamoPk().getIdCuotaPrestamo());
			comprobanteDetalle.setCantidadItem(1);
			comprobanteDetalle.setDescripcion(
					"PAGO COMPLETO DE LA CUOTA NRO " + cuotaPrestamo.getCuotaPrestamoPk().getIdCuotaPrestamo());
			comprobanteDetalle.setMontoItem(cuotaPrestamo.getMontoTotal());
			comprobanteDetalle.setMontoTotal(Utils.formatearDecimales(montoTotal, "0.00"));
			comprobanteDetalle.setMontoMora(montoMora);

			if (montoPagado > 0) {
				comprobanteDetalle.setDescripcion(
						"PAGO PARCIAL DE LA CUOTA NRO " + cuotaPrestamo.getCuotaPrestamoPk().getIdCuotaPrestamo());
				comprobanteDetalle
						.setMontoItem(Utils.formatearDecimales(cuotaPrestamo.getMontoTotal() - montoPagado, "#.##"));
				comprobanteDetalle.setMontoTotal(montoTotal - montoPagado);
			}

			comprobante.getListaComprobanteDetalle().add(comprobanteDetalle);

			listaTipoComprobante = tipoComprobanteService.listar().stream()
					.filter(tipo -> !tipo.getDescripcion().equals("FACTURA")).toList();

			listaTipoDocumento = tipoDocumentoService.listar();

		} catch (Exception ex) {
			mensaje = ex.getMessage();
			ex.printStackTrace();
		}

		model.addAttribute("nombresApellidos", nombresApellidos);
		model.addAttribute("listaDetalleComprobante", comprobante.getListaComprobanteDetalle());
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

	/*-----------------------------------*/

	public Double montoPagadoPorCuotaPrestamo(Integer idPrestamo, Integer idCuotaPrestamo) {

		List<ComprobanteDetalle> listaComprobanteDetalles = new ArrayList<>();
		Double montoPagado = 0.00;

		CuotaPrestamoPK cuotaPrestamoPK = new CuotaPrestamoPK();
		cuotaPrestamoPK.setIdPrestamo(idPrestamo);
		cuotaPrestamoPK.setIdCuotaPrestamo(idCuotaPrestamo);

		listaComprobanteDetalles = comprobanteDetalleService.listar();

		for (ComprobanteDetalle comprobanteDetalle : listaComprobanteDetalles) {

			if (comprobanteDetalle.getCuotaPrestamo().getCuotaPrestamoPk().getIdPrestamo() == idPrestamo) {
				if (comprobanteDetalle.getCuotaPrestamo().getCuotaPrestamoPk()
						.getIdCuotaPrestamo() == idCuotaPrestamo) {

					montoPagado += comprobanteDetalle.getMontoTotal();
				}
			}
		}
		return montoPagado;
	}

}
