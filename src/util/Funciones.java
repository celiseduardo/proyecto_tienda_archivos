/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Scanner;

/**
 *
 * @author Eduardo Celis
 */
public class Funciones {
    
    public static void muestraMensaje(String mensaje, Color color) {
        System.out.println(color + mensaje + Color.DEFAULT);
        System.out.println("");
    }
    
    public static void borrarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void muestraMensaje(String mensaje) {
        muestraMensaje(mensaje, Color.DEFAULT);
    }

    public static int pedirOpcionEnRango(int min, int max) {

        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;

        while (hayError) {
            System.out.print("Seleccione una opción: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = opcion < min || opcion > max;
                if (hayError) {
                    muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.ERROR);
                }
            } else {
                muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.ERROR);
                leerTeclado.next();
            }
        }
        return opcion;
    }
    
}
