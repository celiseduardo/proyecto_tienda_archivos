package empleado.vista;

import java.util.Scanner;
import util.Color;
import static util.Funciones.*;

public class VistaEmpleado {
    
    public static String CambiarContrasenia() {
        Scanner leerTeclado = new Scanner(System.in);
        boolean hayError = false;
        String opcion = null;
        System.out.print("");
        do {
            System.out.print("Ingrese nueva contraseña: ");
            opcion = leerTeclado.nextLine();
            if (opcion.isEmpty() || opcion.trim().length() == 0 ){
                hayError = true;
                muestraMensaje("Debe ingresar una contraseña", Color.ERROR);
            } else {
                hayError = false;
            }

        } while (hayError);

        return opcion;
        
    }

    public static String[] Login(){
        Scanner leerTeclado = new Scanner(System.in);
        borrarPantalla();
        System.out.println("");
        System.out.println("Bienvenido a la tienda");
        System.out.println("***************************************");

        System.out.print("Introduce el código de tu usuario: ");
        while (!leerTeclado.hasNextInt()) {
            muestraMensaje("Debe escribir un valor numérico", Color.ERROR);
            System.out.print("Introduce el código de tu usuario: ");
            leerTeclado.next();
        }
        int codigoEntrada = leerTeclado.nextInt();

        System.out.print("Introduce tu contraseña: ");
        String passwordEntrada = leerTeclado.next();

        System.out.println("***************************************");
        System.out.println("");
        String[] login = {Integer.toString(codigoEntrada), passwordEntrada};
        return login;
    }


}
