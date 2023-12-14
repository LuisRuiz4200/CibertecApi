package com.cibertec.api.controllerrest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestamoService;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardApiController {
	
	@Autowired
	private PrestamoService prestamoService;
	@Autowired
	private PrestamistaService prestamistaService;
	@Autowired
	private GrupoPrestamistaService grupoPrestamistaService;
	
	@GetMapping("/rentabilidad")
	public Map<?, ?> rentabilidad(
			@RequestParam(name ="idPrestamista",required = false) Integer idPrestamista,
			@RequestParam(name="idJefePrestamista",required = false) Integer idJefePrestamista){
		
		Map<String, Object> response = new LinkedHashMap<>();
		Map<String, Object> mapPrestamista = new LinkedHashMap<>();
		Map<String, Object> mapPrestamistaJefe = new LinkedHashMap<>();
		Map<String, Object> mapPrestamistaResumen = new LinkedHashMap<>();
		
		List<Map<String, Object>> mapListaPrestamistasPorJefePrestamista = new ArrayList<>();
		
		List<Prestamo> listaPrestamosPorPrestamista = new ArrayList<>();
		List<GrupoPrestamista> listaGrupoPrestamistas = new ArrayList<>();
		List<Prestamista> listaPrestimastiasPorPrestemistaJefe = new ArrayList<>();
		Prestamista prestamista = new Prestamista();
		Prestamista prestamistaJefe = new Prestamista();
		GrupoPrestamista grupoPrestamista = new GrupoPrestamista();
		
		Double montoTotalPrestado =  0.00;
		Integer totalPrestamos = 0;
		Double maxMontoTotalPrestadoPorPrestamista = 0.00;
		Double minMontoTotalPrestadoPorPrestamista = 0.00;
		Double promMontoTotalPrestadoPorPrestamista = 0.00;
		
		try {
			
			Thread hilo1 = new Thread(()->{
				listaGrupoPrestamistas.addAll(grupoPrestamistaService.listar());
			});
			Thread hilo2 = new Thread(()->{
				listaPrestamosPorPrestamista.addAll(prestamoService.listar());
			});
			
			hilo1.start();
			hilo2.start();
			
			hilo1.join();
			hilo2.join();
					
			prestamistaJefe = listaGrupoPrestamistas.stream()
					.filter(c->c.getJefePrestamista().getIdPrestamista()==idJefePrestamista)
					.map(c->c.getJefePrestamista())
					.findFirst().get();

			mapPrestamistaJefe.put("idPrestamistaJefe",prestamistaJefe.getPrestamista().getIdPersona());
			mapPrestamistaJefe.put("nombreApellido",prestamistaJefe.getPrestamista().getNombresApellidos());
			mapPrestamistaJefe.put("docIdentidad",prestamistaJefe.getPrestamista().getDni());
			mapPrestamistaJefe.put("email",prestamistaJefe.getPrestamista().getEmail());
			
			listaPrestimastiasPorPrestemistaJefe = listaGrupoPrestamistas.stream()
					.filter(c->c.getJefePrestamista().getIdPrestamista() == idJefePrestamista)
					.map(c->c.getAsesorPrestamista())
					.toList();
			
			for(Prestamista objPrestamistaAsesor : listaPrestimastiasPorPrestemistaJefe ) {
				

				Integer idPrestamistaAsesor = objPrestamistaAsesor.getPrestamista().getIdPersona();
				
				/*información por cada prestamista*/
				mapPrestamista = new LinkedHashMap<>();
				mapPrestamista.put("idPrestamista", objPrestamistaAsesor.getPrestamista().getIdPersona());
				mapPrestamista.put("nombreApellido", objPrestamistaAsesor.getPrestamista().getNombresApellidos());
				mapPrestamista.put("docIdentidad", objPrestamistaAsesor.getPrestamista().getDni());
				mapPrestamista.put("email", objPrestamistaAsesor.getPrestamista().getEmail());
				
				
				/*lista de los prestamos por prestamista asesor*/
				
				/*las estadistas de los prestamos*/
				DoubleSummaryStatistics estadistica = listaPrestamosPorPrestamista.stream()
						.filter(c->c.getSolicitudPrestamo().getPrestatario().getPrestamistaPrestatario().getPrestamista().getIdPersona()== idPrestamistaAsesor)
						.mapToDouble(c->c.getMonto())
						.summaryStatistics();
				
				totalPrestamos = (int)estadistica.getCount();
				montoTotalPrestado = estadistica.getSum();
				maxMontoTotalPrestadoPorPrestamista = totalPrestamos>0?estadistica.getMax():0.00;
				minMontoTotalPrestadoPorPrestamista = totalPrestamos>0? estadistica.getMin():0.00;
				promMontoTotalPrestadoPorPrestamista = totalPrestamos>0? estadistica.getAverage():0.00;
				
				/*asignamos las estadisticas*/
				mapPrestamistaResumen = new LinkedHashMap<>();
				mapPrestamistaResumen.put("montoTotalPrestado", montoTotalPrestado);
				mapPrestamistaResumen.put("maxMontoTotalPrestado", maxMontoTotalPrestadoPorPrestamista);
				mapPrestamistaResumen.put("minMontoTotalPrestado", minMontoTotalPrestadoPorPrestamista);
				mapPrestamistaResumen.put("promMontoTotalPrestado", promMontoTotalPrestadoPorPrestamista);
				mapPrestamistaResumen.put("totalPrestamos", totalPrestamos);
				
				/*agregamos los resumentes por prestamista*/
				mapPrestamista.put("resumen", mapPrestamistaResumen);
				
				/*agregamos al prestamista a la lista de prestamistas por jefe de prestamistas*/
				mapListaPrestamistasPorJefePrestamista.add(mapPrestamista);
				
			
			}
			
			/*agregamos la lista de prestamista al jef de prestamista*/
			mapPrestamistaJefe.put("prestamistas", mapListaPrestamistasPorJefePrestamista);	
		
			/*colocamos como respuesta el mensaje y el detalle de la consulta*/
			response.put("mensaje", "Búsqueda realizada correctamente");
			response.put("detalle", mapPrestamistaJefe);
			
		}catch(Exception ex) {
			response.put("mensaje", ex.getMessage());
			ex.printStackTrace();
		}
		
		
		return response;
	}
	
	
}
