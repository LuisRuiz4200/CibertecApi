package com.cibertec.api.reuzable;

import java.text.DecimalFormat;

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
		// Convert the Double to a formatted string with two decimal places
		String formattedString = new DecimalFormat(decimalRegex).format(valor);

		// Replace commas with dots for proper parsing
		formattedString = formattedString.replace(',', '.');

		// Parse the cleaned string to a Double
		try {
			return Double.parseDouble(formattedString);
		} catch (NumberFormatException e) {
			// Handle the exception or rethrow it as needed
			throw new NumberFormatException("Invalid number format: " + formattedString);
		}

		// Double respuesta = Double.parseDouble(new
		// DecimalFormat(decimalRegex).format(valor));
		// return respuesta;
	}

	public static Double calcularMora(Double montoMensual, Integer dias, Double tea) {

		Double tasaDiaria = (Math.pow((1 + tea), (1.0 / 30))) - 1;
		Double montoMora = montoMensual * (Math.pow((1 + tasaDiaria), (dias)) - 1);

		return formatearDecimales(montoMora, "0.00");
	}

}
