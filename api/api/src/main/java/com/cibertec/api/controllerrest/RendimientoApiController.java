package com.cibertec.api.controllerrest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.controller.GrupoPrestamistaController;
import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.modelDTO.RendimientoDTO;
import com.cibertec.api.reuzable.Utils;
import com.cibertec.api.service.CuotaPrestamoService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestamoService;
import com.cibertec.api.service.SolicitudPrestamoService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/redimiento")
@AllArgsConstructor
public class RendimientoApiController {

    PrestamistaService prestamistaService;
    GrupoPrestamistaController grupoController;
    SolicitudPrestamoService solicitudPrestamoService;
    PrestamoService prestamoService;
    CuotaPrestamoService cuotaPrestamoService;

    @GetMapping("/redimientoByJefe")
    public ResponseEntity<?> redimientoByJefe(
            @RequestParam(name = "id", required = false, defaultValue = "") String idJefePresmistaString) {
        List<RendimientoDTO> rendimientos = new ArrayList<>();

        if (idJefePresmistaString.isBlank()) {
            return ResponseEntity.ok(rendimientos);
        }

        // Transformar a entero el parametro
        int idJefePresmista = Integer.parseInt(idJefePresmistaString);

        // Buscaremos a los prestamistas que pertenecen al jefe prestamista
        Prestamista jefePrestamista = new Prestamista();
        jefePrestamista.setIdPrestamista(idJefePresmista);

        List<Prestamista> prestamistas = grupoController.listGrupoByJefePrestamistaAndActivo(jefePrestamista);

        // Recorreremos a los prestamistas para obtener sus rendimientos
        for (Prestamista prestamista : prestamistas) {

            // Acumuladores para el redimiento de cada prestamista
            double totalPrestado = 0;
            double totalPagado = 0;
            double totalInteres = 0;
            double totalPendiente = 0;
            double totalMora = 0;
            double rentabilidad = 0;

            // Obtendremos las solicitudes aprobadas del prestamista
            List<SolicitudPrestamo> solicitudes = new ArrayList<SolicitudPrestamo>();
            solicitudes = solicitudPrestamoService.listarPorPrestamista(prestamista.getIdPrestamista()).stream()
                    .filter(solicitud -> solicitud.getEstado().equals(Utils.PRESTAMO_APROBADO))
                    .collect(Collectors.toList());

            // Recorreremos las solicitudes para obtener los prestamos
            for (SolicitudPrestamo solicitud : solicitudes) {

                // Obtendremos el prestamo de la solicitud
                Prestamo prestamo = prestamoService.getBySolicitud(solicitud);

                // 1.- Total prestado
                totalPrestado += solicitud.getMonto();

                List<CuotaPrestamo> cuotas = prestamo.getListaCuotaPrestamo();
                // Recorreremos los prestamos para obtener las cuotas
                for (CuotaPrestamo cuota : cuotas) {

                    if (cuota.getEstado().equals(Utils.PAGO_PAGADO)) {
                        // 2.- Total pagado
                        totalPagado += cuota.getMonto() + cuota.getInteres();

                        // 4.- Total Interes Mora
                        totalInteres += cuota.getInteres();

                        if (cuota.getMontoMora() != null)
                            totalMora += cuota.getMontoMora();

                    } else {
                        // 3.- Total pendiente
                        totalPendiente += cuota.getMonto() + cuota.getInteres();
                    }
                }
            }
            Utils.print(prestamista.getIdPrestamista() + "", prestamista.getPrestamista().getNombresApellidos());
            Utils.print("totalMora", totalMora);
            Utils.print("totalInteres", totalInteres);
            Utils.print("totalPrestado", totalPrestado);

            // 5.- Rentabilidad
            // rentabilidad = totalInteresMora / totalPrestado;
            if (totalPrestado > 0) {
                rentabilidad = ((totalMora + totalInteres) / totalPrestado) * 100;
            } else {
                // o cualquier otro valor predeterminado que desees en caso de totalPrestado
                // igual a cero
                rentabilidad = 0.0;
            }
            Utils.print("rentabilidad", rentabilidad);

            // Agregamos el rendimiento del prestamista
            RendimientoDTO rendimientoDTO = new RendimientoDTO(idJefePresmista, prestamista.getIdPrestamista(),
                    prestamista.getPrestamista().getNombresApellidos(),
                    decimalFormat(totalPrestado), decimalFormat(totalInteres), decimalFormat(totalPagado),
                    decimalFormat(totalPendiente),
                    decimalFormat(rentabilidad));
            rendimientos.add(rendimientoDTO);
        }

        return ResponseEntity.ok(rendimientos);
    }

    private double decimalFormat(double value) {
        return Utils.formatearDecimales(value, "0.00");
    }

}
