package com.cibertec.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.UtilExcel.UtilExcel;
import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.Rol;
import com.cibertec.api.model.SolicitudDto;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.reuzable.Utils;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;
import com.cibertec.api.service.UService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PrestamistaController {

	// ----
	// service
	private PrestamistaService service;
	private GrupoPrestamistaController grupoController;
	private UService userService;
	PrestatarioService prestatarioService;
	private SolicitudPrestamoService solicitudService;
	private GrupoPrestamistaService grupoPrestamistaService;

	@GetMapping({ "/listar", "/", "" }) // localhost:9090 /
	public String listarPrestamista(Model model, HttpSession session) {
		// Obtener al JefePrestamista desde la session de su Usuario
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		// obtener el rol
		int rolIngreso = userLogged.getRol().getIdRol();
		// System.out.println(rolIngreso);
		// Listado declarado
		List<Prestamista> lista = new ArrayList<>();
		// for mensaje
		String titulo = "";
		String txtButton = "";
		switch (rolIngreso) {
			// admin lista de jefes

			case 1: {
				// obtener id
				int idAdministrador = userLogged.getPersona().getIdPersona();

				// Validación correspondiente
				if (idAdministrador == -1)
					return "redirect:/intranet";

				/* Listado por Jefes */
				Rol rolJefes = new Rol();
				rolJefes.setIdRol(2);
				List<Usuario> users = userService.getUsuarioByRol(rolJefes);

				List<Prestamista> jefesPrestamistas = new ArrayList<>();
				jefesPrestamistas = users.stream()
						.map(usuario -> service.getByIdPrestamistaActivo(usuario.getPersona().getIdPersona()))
						.collect(Collectors.toList());

				if (jefesPrestamistas != null) {
					lista = jefesPrestamistas.stream().filter(Objects::nonNull) // Filtrar
																				// elementos
																				// no
																				// nulos
							.collect(Collectors.toList());
				}
				model.addAttribute("navbar", true);
				titulo = "Lista de Jefes de Prestamista";
				txtButton = "Agregar Jefe Prestamista";
				break;
			}
			// jefe de prestamista, lista de prestamistas
			case 2: {
				int idJefePrestamista = userLogged.getPersona().getIdPersona();
				Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
				// Validación correspondiente
				if (jefePrestamista == null)
					return "redirect:/intranet";
				// Obtener la lista de prestamistas asociado al Jefe y que estén Activos
				lista = grupoController.listGrupoByJefePrestamistaAndActivo(jefePrestamista);
				titulo = "Lista de Prestamistas";
				model.addAttribute("navbar", false);
				txtButton = "Agregar Prestamista";
				break;
			}
			default:
				break;

		} // fin de switch
		model.addAttribute("lista", lista);
		model.addAttribute("titulo", titulo);
		model.addAttribute("txtButton", txtButton);
		return "listar";
	} // fin de listarPrestamista

	@GetMapping("/registrar")
	public String mostrarFormularioRegistroPrestamista(Model model, HttpSession session) {
		// for mensaje
		String titulo = "";
		// objeto prestamista en vacio
		Prestamista prestamista = null;
		// Obtener la sesion de quien ingresa
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";
		// obtener el rol
		int rolIngreso = userLogged.getRol().getIdRol();
		prestamista = new Prestamista();
		// crear un nuevo PersonaM para registrar al darle al boton registrar crea un
		// nuevo objeto PersonaM
		prestamista.setPrestamista(new Persona());

		if (rolIngreso == 1)
			titulo = "Registrar Jefe Prestamista";
		else
			titulo = "Registrar Prestamista";

		model.addAttribute("prestamista", prestamista);

		model.addAttribute("titulo", titulo);

		return "formulario";
	} // fin de mostrarFormularioRegistroPrestamista

	@PostMapping("/registrar") // localhost:9090/registrar
	public String guardarPrestamista(Prestamista prestamista, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status, HttpSession session) {
		if (result.hasErrors()) {

			model.addAttribute("titulo", "Registro");

			return "formulario";
		}
		prestamista.setActivo(true);
		prestamista.getPrestamista().setActivo(true);

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		int rolUsuario = userLogged.getRol().getIdRol();

		String mensaje;
		int idPersona = prestamista.getPrestamista().getIdPersona();
		// if (prestamista.getIdPrestamista() != 0)

		String dni = prestamista.getPrestamista().getDni();

		if (idPersona != 0) {
			service.guardarPrestamista(prestamista);
			return "redirect:/listar";
		}

		else {

			if (service.buscarPorDniAndActivo(dni) != null) {
				mensaje = "El dni " + dni + " ha sido registrado anteriormente";
				flash.addFlashAttribute("mensaje", mensaje);
				return "redirect:/registrar";
			}

			mensaje = "El Prestamista se registró correctamente";
			Prestamista newPrestamista = service.guardarPrestamista(prestamista); // Marca el status como completo.

			if (rolUsuario == 1) {
				int registrarJefe = 2;
				return "redirect:/registrarUsuario/" + registrarJefe + "/" + newPrestamista.getIdPrestamista();
			} else {
				int idJefePrestamista = userLogged.getPersona().getIdPersona();
				Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
				// Registrar Asesor Prestamista
				if (idPersona == 0) {
					grupoController.insertGrupoPrestamista(jefePrestamista, newPrestamista, userLogged);
				}
				int registrarAsesor = 3;
				return "redirect:/registrarUsuario/" + registrarAsesor + "/" + newPrestamista.getIdPrestamista();
			}
		}

	} // fin de guardarPrestamista

	// Metodo para actualizar
	@GetMapping("/actualizar/{id}")
	public String editarPrestamista(@PathVariable(name = "id") int id, Model model, RedirectAttributes flash,
			HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		// creamos objeto presta inicializado en null
		Prestamista presta = null;
		// Valida que el id sea mayor a 0
		if (id > 0) {
			// Si es válido, busca el presta en el servicio por id
			presta = service.listarPrestamistaPorId(id);
			// Valida que existe, si no arroja error
			if (Objects.isNull(presta)) {
				flash.addFlashAttribute("error", "El ID de Prestamista no existe");
				// Retorna un redirect a la URL /listar para mostrar la lista con el atributo
				// error que almacena
				// el mensaje
				return "redirect:/listar";

			} // fin de if
		} else {
			flash.addFlashAttribute("error", "El ID de Prestamista no puede ser menor a 1");
			// Retorna un redirect a la URL /listar para mostrar la lista con el atributo
			// error que almacena
			// el mensaje
			return "redirect:/listar";
		}
		// Si existe, agrega el empleado al modelo
		// Si el empleado existe, la función lo agrega a un objeto llamado empleado
		// y lo pasaremos a la vista por medio del atributo empleado
		model.addAttribute("prestamista", presta);
		// enviamos en atributo llamado titulo, un string que
		// dice Detalle de Empleado concantenado el nombre
		model.addAttribute("titulo", "Actualización");
		// esta vista se usara para el agregar y actualizar reutilizando la vista
		return "formulario";
	} // fin de editarEmpleado

	// Metodo para eliminar
	// Mapea la petición GET a la URL "/eliminar/{id}"
	// Extrae el id de la URL usando @PathVariable
	@GetMapping("/eliminar/{id}")
	public String eliminarPrestamista(@PathVariable(name = "id") int id, RedirectAttributes flash,
			HttpSession session) {
		// Valida que el id sea mayor a 0
		if (id > 0) {
			Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
			if (userLogged == null)
				return "redirect:/login";

			int rolIngreso = userLogged.getRol().getIdRol();

			// Como admin - Elimino a un Jefe
			if (rolIngreso == 1) {
				Prestamista jefePrestamista = service.getPrestamistaById(id).orElse(null);
				int totalDeMiembros = grupoController.listGrupoByJefePrestamistaAndActivo(jefePrestamista).size();
				// De ser el caso que no tenga miembros, se podrá eliminar al jefePrestamista
				if (totalDeMiembros < 1)
					service.eliminarPrestamista(id);
				else
					flash.addFlashAttribute("errorMessage", "Error al eliminar al Jefe Prestamista");
			}

			// Como Jefe - Elimino a un asesorPrestamista
			else {
				int idJefePrestamista = userLogged.getPersona().getIdPersona();
				Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
				Prestamista asesorPresmista = service.getPrestamistaById(id).orElse(null);

				// Eliminacion Lógica
				service.eliminarPrestamista(id);
				grupoController.deleteGrupoPrestamista(jefePrestamista, asesorPresmista, userLogged);

				flash.addFlashAttribute("success", "El Prestamista ha sido eliminado");
				return "redirect:/listar";
			}
		} // fin de if
		return "redirect:/listar";
	} // fin de eliminarEmpleado

	@GetMapping("/aprobarPrestamo")
	private String listarSolicitudes(Model model, HttpSession session) {

		// Antes
		// List<Prestatario> listaPrestatario = new ArrayList<Prestatario>();
		// listaPrestatario = prestatarioService.listarPrestatario();
		// model.addAttribute("listaPrestatario",listaPrestatario);
		// Despues
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		Prestamista prestamista = service.listarPrestamistaPorId(userLogged.getPersona().getIdPersona());
		List<Prestatario> PrestatariosList = new ArrayList<>();

		PrestatariosList = prestamista.getPrestatariosList().stream()
				.filter(item -> Boolean.TRUE.equals(item.isActivo())).collect(Collectors.toList());
		model.addAttribute("listaPrestatario", PrestatariosList);
		// instancia la lista para evitar problemas de nullos
		List<SolicitudPrestamo> listaSolicitudes = new ArrayList<SolicitudPrestamo>();
		listaSolicitudes = PrestatariosList.stream()
				.flatMap(item -> solicitudService.listarPorPrestatario(item.getIdPrestatario()).stream())
				.collect(Collectors.toList());

		// Ordenar la lista por codigo de solicitud
		// Comparator<SolicitudPrestamo> reversedOrder =
		// Comparator.comparingInt(SolicitudPrestamo::getIdSolicitudPrestamo).reversed();
		// Collections.sort(listaSolicitudes, reversedOrder);
		listaSolicitudes.sort(Comparator.comparingInt(SolicitudPrestamo::getIdSolicitudPrestamo).reversed());

		model.addAttribute("listaSolicitudes", listaSolicitudes);

		SolicitudDto solicitudDto = new SolicitudDto();
		model.addAttribute("solicitudDto", solicitudDto);
		return "ApruebaByPrestamista";
	}

	@PostMapping("/aprobarPrestamo")
	private String aprobarSolicitudes(SolicitudDto solicitudDto, Model model) {
		SolicitudPrestamo solicitud = solicitudService.buscarPorId(solicitudDto.getId());
		int state = solicitudDto.getState();
		if (state == 1)
			solicitud.setEstado("Aprobado");
		if (state == 2)
			solicitud.setEstado("Rechazado");

		solicitudService.guardar(solicitud);

		return "redirect:/aprobarPrestamo";
	}// fin de aprobarSolicitudes

	// -------------------------

	@GetMapping("/filtrarSolicitudes")
	public String filtrarSolicitudes(@RequestParam("prestamista") int idPrestamista,
			@RequestParam("fechaDesde") String fechaDesde,
			@RequestParam("fechaHasta") String fechaHasta,
			Model model, HttpSession session) throws ParseException {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		List<SolicitudPrestamo> listaSolicitudes = new ArrayList<SolicitudPrestamo>();
		if (idPrestamista == -1) {

			return "redirect:/aprobarPrestamo";
		}

		if (!fechaDesde.equals("") || !fechaHasta.equals("")) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaDesdeDate = formatter.parse(fechaDesde);
			Date fechaHastaDate = formatter.parse(fechaHasta);
			listaSolicitudes = solicitudService.filtrarSolicitudes(idPrestamista, fechaDesdeDate, fechaHastaDate);

		} else {
			listaSolicitudes = solicitudService.listarPorPrestamista(idPrestamista);
		}

		listaSolicitudes.sort(Comparator.comparingInt(SolicitudPrestamo::getIdSolicitudPrestamo).reversed());

		// filtra llena combobox
		listarSolicitudes(model, session);
		model.addAttribute("listaSolicitudes", listaSolicitudes);
		return "ApruebaByPrestamista";
	}

	// ------------

	@GetMapping("/revisarEstadoPrestamoByJefePrestamista")
	private String listarPrestamoRevisarByBoss(Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");

		if (userLogged == null)
			return "redirect:/login";

		return "ChiefRevisaPrestamo";
	}

	// ----------------

	// ------------ ADMIN REVISA RENDIMIENTO

	@GetMapping("/revisarRendimientoByAdmin")
	private String LookRendiByAdmin(Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		List<Prestamista> listaPrestamistasJefe = new ArrayList<>();

		Rol rolJefes = new Rol();
		rolJefes.setIdRol(Utils.ROL_JEFE_PRESTAMISTA);
		List<Usuario> users = userService.getUsuarioByRol(rolJefes);

		listaPrestamistasJefe = users.stream()
				.map(usuario -> service.getByIdPrestamistaActivo(usuario.getPersona().getIdPersona()))
				.collect(Collectors.toList());

		if (listaPrestamistasJefe != null) {
			listaPrestamistasJefe = listaPrestamistasJefe.stream().filter(Objects::nonNull)
					.collect(Collectors.toList());
		}
		// listaPrestamistasJefe = grupoPrestamistaService.listar().stream().map(c ->
		// c.getJefePrestamista()).distinct()
		// .toList();

		model.addAttribute("listaPrestamistasJefe", listaPrestamistasJefe);

		return "AdminRevisaRendimiento";
	}// fin de LookRendiByAdmin

	// REPORTE EXCEL CONSTANTES
	private static String[] HEADERs = { "Prestamista", "Prestado","Interes","Pagado", "Pendiente", "Rentabilidad" };
	private static String SHEET = "Listado";
	private static String TITLE = "Listado de Prestamistas y Rendimiento Financiero  - Administrador";
	private static int[] HEADER_WITH = { 20000, 10000, 6000,6000 ,10000, 6000 };

	// REPORTE EN EXCEL
	@PostMapping("/reporteRendimientoExcel")
	public void exportaExcel(
			@RequestParam(name = "idJefePrestamista", required = false, defaultValue = "-1") int idJefePrestamista,
			HttpServletRequest request,
			HttpServletResponse response) {

		Workbook excel = null;
		try {
			// Se crear el Excel
			excel = new XSSFWorkbook();

			// Se crear la hoja del Excel
			Sheet hoja = excel.createSheet(SHEET);

			hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WITH.length - 1));

			// Se establece el ancho de las columnas
			for (int i = 0; i < HEADER_WITH.length; i++) {
				hoja.setColumnWidth(i, HEADER_WITH[i]);
			}

			CellStyle estiloHeadCentrado = UtilExcel.setEstiloHeadCentrado(excel);
			CellStyle estiloHeadIzquierda = UtilExcel.setEstiloHeadIzquierda(excel);
			CellStyle estiloNormalCentrado = UtilExcel.setEstiloNormalCentrado(excel);
			CellStyle estiloNormalIzquierda = UtilExcel.setEstiloNormalIzquierdo(excel);

			// Fila 0
			Row fila1 = hoja.createRow(0);
			Cell celAuxs = fila1.createCell(0);
			celAuxs.setCellStyle(estiloHeadIzquierda);
			celAuxs.setCellValue(TITLE);

			// Fila 1
			Row fila2 = hoja.createRow(1);
			Cell celAuxs2 = fila2.createCell(0);
			celAuxs2.setCellValue("");

			// Fila 2
			Row fila3 = hoja.createRow(2);
			for (int i = 0; i < HEADERs.length; i++) {
				Cell celda1 = fila3.createCell(i);
				celda1.setCellStyle(estiloHeadCentrado);
				celda1.setCellValue(HEADERs[i]);
			}

			// Listado declarado
			List<Prestamista> lista = new ArrayList<>();
			// coge el idJefePrestamista de parametro, lo almacena en jefePrestamista
			Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
			// listado de prestamista por jefe y que esten activos
			List<Prestamista> lstSalida = grupoController.listGrupoByJefePrestamistaAndActivo(jefePrestamista);

			// Fila 3....n
			int rowIdx = 3;
			for (Prestamista obj : lstSalida) {
				Row row = hoja.createRow(rowIdx++);

				Cell cel0 = row.createCell(0);
				cel0.setCellValue(obj.getPrestamista().getNombresApellidos()); // Nombres y apellidos
				cel0.setCellStyle(estiloNormalCentrado);

				Cell cel1 = row.createCell(1);
				cel1.setCellValue(obj.getPrestamista().getNombresApellidos());// Prestado
				cel1.setCellStyle(estiloNormalIzquierda);

				Cell cel2 = row.createCell(2);
				cel2.setCellValue(obj.getPrestamista().getNombresApellidos());// Pagado
				cel2.setCellStyle(estiloNormalCentrado);

				Cell cel3 = row.createCell(3);
				cel3.setCellValue(obj.getPrestamista().getNombresApellidos()); // Pendiente
				cel3.setCellStyle(estiloNormalCentrado);

				Cell cel4 = row.createCell(4);
				cel4.setCellValue(obj.getPrestamista().getNombresApellidos()); // Rentabilidad
				cel4.setCellStyle(estiloNormalIzquierda);

			}

			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-disposition", "attachment; filename=ReporteRendimientoPrestamista.xlsx");

			OutputStream outStream = response.getOutputStream();
			excel.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (excel != null)
					excel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// fin de exportaExcel

	// ----------------

} // Fin de PrestamistaController
