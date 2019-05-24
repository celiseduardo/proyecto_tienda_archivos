package empleado.control;

import empleado.dominio.Empleado;
import empleado.vista.*;
import java.util.Scanner;

import excepciones.LoginError;
import util.Color;
import static util.Funciones.*;

public class GestionaEmpleados {

    ControladorEmpleado controlador;
    Empleado empleadoAutenticado;

    public GestionaEmpleados() {
        this.controlador = new ControladorEmpleado();
        this.empleadoAutenticado = null;
    }

    public int login() {
        boolean esEmpleadoValido = false;
        boolean esPasswordValido = false;
        int usuarioValido = 0;

        var login = VistaEmpleado.Login();
        empleadoAutenticado = controlador.buscarEmpleadoPorCodigo(Integer.parseInt(login[0]));
        
        if (empleadoAutenticado != null) {
            esEmpleadoValido = true;
            if (login[1].equals(empleadoAutenticado.getPassword())) {
                esPasswordValido = true;
            }
        }

        if (!esEmpleadoValido) {
            usuarioValido = 111;
        } else if (!esPasswordValido) {
            usuarioValido = 222;
        } else {
            usuarioValido = 1;
        }

        return usuarioValido;

    }

    public Empleado getEmpleadoAutenticado() {
        return empleadoAutenticado;
    }
    
    public boolean actualizarEmpleado(Empleado empleado){
        String opcion = VistaEmpleado.CambiarContrasenia();
        empleado.setPassword(opcion);
        return controlador.actualizarEmpleado(empleado);
             
    } 
    
    
    
    

}
