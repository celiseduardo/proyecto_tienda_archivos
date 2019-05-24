package producto.vista;

import producto.control.MenuProducto;
import producto.control.SubMenuProducto;
import producto.dominio.Producto;
import util.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static util.Funciones.*;

public class VistaProducto {


    public static MenuProducto opcionDesdeMenuProducto(Producto producto) {
        //menu
        System.out.println("");
        System.out.println("--Opciones para el producto (Código "+ producto.getCodigo()+") -------------------");
        System.out.println("   1. Modificar nombre");
        System.out.println("   2. Modificar precio");
        System.out.println("   3. Modificar código");
        System.out.println("   4. Regresar al Menú anterior");
        System.out.println("-----------------------------------------------------------------------------------");

        int opcion = pedirOpcionEnRango(1, 4);
        MenuProducto menu = null;

        switch (opcion) {
            case 1:
                menu = MenuProducto.MODIFICAR_NOMBRE;
                break;
            case 2:
                menu = MenuProducto.MODIFICAR_PRECIO;
                break;
            case 3:
                menu = MenuProducto.MODIFICAR_CODIGO;
                break;
            case 4:
                menu = MenuProducto.SELECCIONAR_OTRO;
                break;
        }

        return menu;

    }

    public static SubMenuProducto opcionDesdeSubMenuProducto() {
        //menu
        System.out.println("");
        System.out.println("--Sub Menú: Modificar Producto-----------------");
        System.out.println("   1. Modificar un producto");
        System.out.println("   2. Volver al Menú Principal");
        System.out.println("-----------------------------------------------");

        int opcion = pedirOpcionEnRango(1,2);
        SubMenuProducto menu = null;

        switch (opcion) {
            case 1:
                menu = SubMenuProducto.MODIFICAR_PRODUCTOS;
                break;
            case 2:
                menu = SubMenuProducto.MENU_PRINCIPAL;
                break;
        }

        return menu;

    }

    public static Producto seleccionarProducto ( List<Producto> productos){
        ListarProductos(productos);
        return indicarProducto(productos);

    }

    public static void ListarProductos ( List<Producto> productos){
        System.out.println("");
        System.out.println("--Productos --------------------------------------------------------------------");
        System.out.printf("%-10s%-50s%-10s%n", "Codigo", "Nombre", "Precio");
        System.out.println("--------------------------------------------------------------------------------");
        for (int i = 0; i < productos.size(); i++){
            System.out.printf("%-10d%-50s%10.2f%n", productos.get(i).getCodigo(), productos.get(i).getNombre(), productos.get(i).getPrecio());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");

    }

    public static Producto indicarProducto(List<Producto> productos) {
        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;
        int index = 0;

        ArrayList codigos = new ArrayList();
        for (int i = 0; i < productos.size(); i++){
            codigos.add(productos.get(i).getCodigo());
        }

        while (hayError) {
            System.out.print("Ingrese el codigo de un producto de la lista: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = !codigos.contains(opcion);
                if (hayError) {
                    muestraMensaje("Error, producto no pertenece a la lista.", Color.ERROR);
                }
            } else {
                muestraMensaje("Error, producto no pertenece a la lista", Color.ERROR);
                leerTeclado.next();
            }
        }
        return productos.get(codigos.indexOf(opcion)) ;


    }

    public static Producto modificarNombre(List<Producto> productos, Producto producto) {
        Scanner leerTeclado = new Scanner(System.in);
        String opcion = null;
        boolean hayError = false;
        boolean nombreValido = false;

        ArrayList nombres = new ArrayList();
        for (int i = 0; i < productos.size(); i++){
            nombres.add(productos.get(i).getNombre().trim());
        }

        do {
            System.out.print("Ingrese nuevo nombre: ");
            var nombre = leerTeclado.nextLine();

            if (nombre.trim().length() == 0 || nombre.isEmpty()) {
                hayError = true;
                muestraMensaje("Error, debe introducir un nombre", Color.ERROR);
            } else if (nombres.contains(nombre.trim())) {
                nombreValido = false;
                muestraMensaje("Error, el nombre ya está asignado a un producto, ingrese otro", Color.ERROR);
            } else {
                nombreValido = true;
                hayError = false;
                opcion = nombre;
            }

        } while (hayError || !nombreValido);

        producto.setNombre(opcion);

        return producto;


    }


    public static Producto modificarPrecio(Producto producto) {
        Scanner leerTeclado = new Scanner(System.in);
        float opcion = 0;
        boolean hayError = true;

        while (hayError) {
            System.out.print("Ingrese nuevo precio: ");
            if (leerTeclado.hasNextFloat()) {
                opcion = leerTeclado.nextFloat();
                hayError = opcion < 0;
                if (hayError) {
                    muestraMensaje("Error, precio incorrecto. Debe ser mayor a cero", Color.ERROR);
                }
            } else {
                muestraMensaje("Error, precio incorrecto. Debe ser mayor a cero ", Color.ERROR);
                leerTeclado.next();
            }
        }
        producto.setPrecio(opcion);

        return producto;
    }

    public static Producto modificarCodigo(List<Producto> productos, Producto producto) {
        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = false;

        ArrayList codigos = new ArrayList();
        for (int i = 0; i < productos.size(); i++){
            codigos.add(productos.get(i).getCodigo());
        }

        do {
            System.out.print("Ingrese nuevo codigo: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = codigos.contains(opcion);
                if (hayError) {
                    muestraMensaje("Error, el nombre ya está asignado a un producto, ingrese otro", Color.ERROR);
                }
            } else {
                muestraMensaje("Error, opción no válida. Debe ser un numero", Color.ERROR);
                leerTeclado.next();
            }
        } while (hayError);
        producto.setCodigo(opcion);

        return producto;
    }

}
