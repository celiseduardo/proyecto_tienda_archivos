package producto.control;

import producto.dominio.Producto;
import producto.vista.VistaProducto;
import tienda.control.GestionaTienda;
import tienda.vista.VistaTienda;
import util.Color;

import java.util.List;

import static util.Funciones.*;

public class GestionaProductos {
    ControladorProducto controlador;
    GestionaTienda gestionaTienda;
    Producto producto;

    public GestionaProductos() {
        this.controlador = new ControladorProducto();
        this.producto = null;
    }

    public boolean ModificarProducto(){
        List<Producto> productos = controlador.leerProductos();
        boolean regresar = false;
        //mostrar sub menu
        var subMenuProducto = VistaProducto.opcionDesdeSubMenuProducto();
        switch (subMenuProducto) {
            case MENU_PRINCIPAL:
                regresar = true;
                break;
            case MODIFICAR_PRODUCTOS:
                regresar = false;
                var producto = VistaProducto.seleccionarProducto(productos);
                MostrarMenuProductos(productos, producto);
                break;
        }


        return regresar;
    }

    public void MostrarMenuProductos(List<Producto> productos, Producto producto){
        var menuProducto = VistaProducto.opcionDesdeMenuProducto(producto);
        boolean prodModificado = false;
        var codigo = producto.getCodigo();

        switch (menuProducto) {
            case MODIFICAR_NOMBRE:
                prodModificado = controlador.actualizarProducto(VistaProducto.modificarNombre(productos, producto), codigo);

                if (prodModificado){
                    muestraMensaje("Nombre actualizado", Color.EXITO);
                    ModificarProducto();
                }
                break;
            case MODIFICAR_PRECIO:
                prodModificado = controlador.actualizarProducto(VistaProducto.modificarPrecio(producto), codigo);

                if (prodModificado){
                    muestraMensaje("Precio actualizado", Color.EXITO);
                    ModificarProducto();
                }
                break;
            case MODIFICAR_CODIGO:
                prodModificado = controlador.actualizarProducto(VistaProducto.modificarCodigo(productos, producto), codigo);


                if (prodModificado){
                    muestraMensaje("Código actualizado", Color.EXITO);
                    ModificarProducto();
                }
                break;
            case SELECCIONAR_OTRO:
                ModificarProducto();
                break;
        }
    }

    public List<Producto> ObtenerProductos(){
        List<Producto> productos = controlador.leerProductos();
        return productos;
    }

    public void ListarProductos(){
        List<Producto> productos = controlador.leerProductos();
        VistaProducto.ListarProductos(productos);
    }

    public Producto IndicarProducto() {
        List<Producto> productos = controlador.leerProductos();
        return VistaProducto.seleccionarProducto(productos);
    }

}
