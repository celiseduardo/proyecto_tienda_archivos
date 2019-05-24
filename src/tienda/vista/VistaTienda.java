package tienda.vista;

import empleado.dominio.Empleado;

import java.util.List;
import java.util.Scanner;

import producto.dominio.Producto;
import tienda.control.MenuPrincipal;
import tienda.control.SubMenuPedido;
import util.Color;

import static util.Funciones.*;

public class VistaTienda {

    public static MenuPrincipal opcionDesdeMenuPrincipal() {
        borrarPantalla();
        System.out.println("--Menú principal -------------------");
        System.out.println("   1. Hacer pedido");
        System.out.println("   2. Modificar producto");
        System.out.println("   3. Cambiar contraseña de empleado");
        System.out.println("   4. Cerrar sesión");
        System.out.println("------------------------------------");

        int opcion = pedirOpcionEnRango(1, 4);
        MenuPrincipal menu = null;

        switch (opcion) {
            case 1:
                menu = MenuPrincipal.HACER_PEDIDO;
                break;
            case 2:
                menu = MenuPrincipal.MODIFICAR_PRODUCTO;
                break;
            case 3:
                menu = MenuPrincipal.CAMBIAR_PASSWORD;
                break;
            case 4:
                menu = MenuPrincipal.CERRAR_SESION;
                break;
        }

        return menu;

    }


    public static void mensajeBienvenida(Empleado empleado) {
        muestraMensaje("Bienvenido " + empleado.getNombre());
        System.out.println();
    }

    public static int indicarCantidad() {
        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = false;

        do {
            System.out.print("Ingrese cantidad de productos a ingresar: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = opcion < 0;
                if (hayError) {
                    muestraMensaje("Error, debe indicar una cantidad mayor a cero", Color.ERROR);
                }
            } else {
                muestraMensaje("Error, debe indicar una cantidad mayor a cero", Color.ERROR);
                leerTeclado.next();
            }
        } while (hayError);
        return opcion;
    }

    public static SubMenuPedido opcionDesdeSubMenuPedido() {
        borrarPantalla();
        System.out.println("--Sub menú: Pedidos -------------------------");
        System.out.println("   1. Añadir producto a la cesta");
        System.out.println("   2. Visualizar precio total de la cesta");
        System.out.println("   3. Imprimir factura");
        System.out.println("   4. Terminar pedido");
        System.out.println("---------------------------------------------");

        int opcion = pedirOpcionEnRango(1, 4);
        SubMenuPedido menu = null;

        switch (opcion) {
            case 1:
                menu = SubMenuPedido.AGREGAR_PRODUCTO;
                break;
            case 2:
                menu = SubMenuPedido.VER_PRECIO;
                break;
            case 3:
                menu = SubMenuPedido.IMPRIMIR_FACTURA;
                break;
            case 4:
                menu = SubMenuPedido.TERMINAR_PEDIDO;
                break;
        }

        return menu;

    }

    public static String MostrarPrecio(List<Producto> productos) {
        double total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total += productos.get(i).getPrecio();
        }

        borrarPantalla();
        System.out.println("--Total de la Cesta -------------------");
        System.out.println(" Cantidad: " + productos.size());
        System.out.println(" Total:    " + total);
        System.out.println("---------------------------------------");
        System.out.println("");
        Scanner leerTeclado = new Scanner(System.in);
        System.out.print("Presione cualquier tecla para continuar : ");
        return leerTeclado.nextLine();


    }

    public static void ImprimirFactura(List<Producto> productos, String empleado) {
        var total = 0;
        System.out.println("");
        System.out.println("Factura Simplificada");
        System.out.println("-----------------------------------------------------------------------------");
        for (int i = 0; i < productos.size(); i++) {
            System.out.printf("%-15s: %s%n", "Código", productos.get(i).getCodigo());
            System.out.printf("%-15s: %s%n", "Nombre", productos.get(i).getNombre());
            System.out.printf("%-15s: %s%n", "Descripción", productos.get(i).getDescripcion());
            System.out.printf("%-15s: %.2f%n", "Precio", productos.get(i).getPrecio());
            System.out.println("");
            total += productos.get(i).getPrecio();
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("El precio TOTAL es: " + total + " Euros");
        System.out.println("Atendido por: " + empleado);
        System.out.println("");
    }
}
