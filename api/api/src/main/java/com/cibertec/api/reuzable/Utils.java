package com.cibertec.api.reuzable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.service.ComprobanteDetalleService;
import com.cibertec.api.serviceImpl.ComprobanteDetalleServiceImpl;
import com.cibertec.api.serviceImpl.ComprobanteServiceImpl;

public class Utils {

	public static int ROL_ADMINISTRADOR = 1;
	public static int ROL_JEFE_PRESTAMISTA = 2;
	public static int ROL_PRESTAMISTA = 3;
	public static int ROL_PRESTATARIO = 4;

	public static String PAGO_PENDIENTE = "Pendiente";
	public static String PAGO_PAGADO = "Pagado";
	public static String PAGO_PARCIAL = "Pago Parcial";

	public static String PRESTAMO_APROBADO = "Aprobado";
	public static String PRESTAMO_RECHAZADO = "Rechazado";

	public static void print(String name, Object object) {
		System.out.println("\n\n" + name + " ======================");
		System.out.println(object);
		System.out.println("======================\n\n");
	}

	public static Double formatearDecimales(Double valor, String decimalRegex) {

		Double respuesta = Double.parseDouble(new DecimalFormat(decimalRegex).format(valor));

		return respuesta;
	}

	public static Double calcularMora(Double montoMensual, Integer dias, Double tea) {

		Double tasaDiaria = (Math.pow((1 + tea), (1.0 / 30))) - 1;
		Double montoMora = montoMensual * (Math.pow((1 + tasaDiaria), (dias)) - 1);

		return formatearDecimales(montoMora, "0.00");
	}

}
