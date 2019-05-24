package tienda.control;

import archivos.control.GestionaArchivos;
import empleado.control.GestionaEmpleados;
import empleado.dominio.Empleado;
import java.util.ArrayList;
import java.util.List;

import excepciones.LoginError;
import producto.control.GestionaProductos;
import producto.dominio.Producto;
import tienda.vista.VistaTienda;
import util.Color;
import static util.Funciones.*;

public class GestionaTienda {

    private Empleado empleadoAutenticado;
    private List<Producto> cesta;
    GestionaEmpleados gestionaEmpleados;
    GestionaProductos gestionaProductos;


    boolean esLoginCorrecto = false;
    int cantidad = 0;

    public GestionaTienda() {
        empleadoAutenticado = null;
        cesta = new ArrayList<>();
        gestionaEmpleados = new GestionaEmpleados();
        gestionaProductos = new GestionaProductos();
    }

    public void iniciar() {
        while (!esLoginCorrecto)  {

            try{
                var empleadoValido = gestionaEmpleados.login();
                if (empleadoValido == 1) {
                    esLoginCorrecto = true;
                } else {

                    throw new LoginError( empleadoValido );
                }
            } catch (LoginError e){
                muestraMensaje(e.getMessage(), Color.ERROR);
            }
        }
        
        if (esLoginCorrecto){
            empleadoAutenticado = gestionaEmpleados.getEmpleadoAutenticado();
            VistaTienda.mensajeBienvenida(empleadoAutenticado);

            switch (VistaTienda.opcionDesdeMenuPrincipal()) {
                case HACER_PEDIDO:
                    HacerPedido();
                    break;
                case MODIFICAR_PRODUCTO:
                    var modificar = gestionaProductos.ModificarProducto();
                    iniciar();
                    break;
                case CAMBIAR_PASSWORD:
                    boolean actualizar = gestionaEmpleados.actualizarEmpleado(empleadoAutenticado);

                    if (actualizar){
                        muestraMensaje("Usuario actualizado", Color.EXITO);
                        iniciar();
                    }
                    break;
                case CERRAR_SESION:
                    esLoginCorrecto = false;
                    iniciar();
                    break;
            }
        }

    }

    public void HacerPedido(){
       cantidad = VistaTienda.indicarCantidad();
       MostrarSubMenuPedido();

    }

    public void MostrarSubMenuPedido(){
        switch (VistaTienda.opcionDesdeSubMenuPedido()){
            case AGREGAR_PRODUCTO:
                var producto = gestionaProductos.IndicarProducto();
                if (cesta.size() == cantidad){//validar si esta llena la cesta
                    muestraMensaje("Tiene "+ cesta.size()+" productos en la cesta y está llena, no puede añadir mas productos", Color.ERROR);
                    MostrarSubMenuPedido();
                } else {
                    ArrayList codigos = new ArrayList();
                    for (int i = 0; i < cesta.size(); i++){
                        codigos.add(cesta.get(i).getCodigo());
                    }
                    if (codigos.contains(producto.getCodigo())){//validar si esta en la cesta
                        muestraMensaje("Este producto ya se encuentra en la cesta, por favor seleccione otro", Color.ERROR);
                        MostrarSubMenuPedido();
                    } else { //agregar producto a la cesta
                        cesta.add(new Producto(producto.getCodigo(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio()));
                        muestraMensaje("Producto añadido a la cesta", Color.EXITO);
                        MostrarSubMenuPedido();
                    }
                }
                break;
            case VER_PRECIO:
                VistaTienda.MostrarPrecio(cesta);
                MostrarSubMenuPedido();
                break;
            case IMPRIMIR_FACTURA:
                VistaTienda.ImprimirFactura(cesta, empleadoAutenticado.getNombre());
                //aqui guardar en archivo
                GestionaArchivos.imprimirFactura(cesta, empleadoAutenticado.getNombre());
                MostrarSubMenuPedido();
                break;
            case TERMINAR_PEDIDO:
                iniciar();
                break;
        }
    }






}
