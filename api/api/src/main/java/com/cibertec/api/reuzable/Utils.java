package com.cibertec.api.reuzable;

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
}
