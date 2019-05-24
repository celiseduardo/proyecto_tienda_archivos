package empleado.dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import conexion.ConexionBD;
import empleado.dominio.Empleado;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import empleado.dao.EmpleadoDAO;

public class EmpleadoDAOImp implements EmpleadoDAO {

    private final String archivoEmpleados;

    public EmpleadoDAOImp() {
        this.archivoEmpleados = "empleados.txt";
    }

    @Override
    public List<Empleado> leerEmpleados() {

        List<Empleado> Empleados = new ArrayList<>();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);
        Number numero;
        String lineaConDatos;

        try ( var archivo = Files.newBufferedReader(Paths.get(archivoEmpleados))) {
            while (archivo.readLine() != null) {

                // codigo
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(lineaConDatos);
                int codigo = numero.intValue();

                //nombre
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String nombre = lineaConDatos;

                //apellidos
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String apellidos = lineaConDatos;

                // contraseña
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String contraseña = lineaConDatos;


                Empleados.add(new Empleado(codigo, nombre, apellidos, contraseña));
            }
        } catch (ParseException e) {
            System.err.println("Error de formato en " + archivoEmpleados);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de lectura en " + archivoEmpleados);
            System.exit(1);

        }

        return Empleados;
    }

    @Override
    public Empleado getEmpleadoPorCodigo(int codigo) {
        List<Empleado> Empleados = leerEmpleados();
        Empleado empleado = null;

        int i = 0;
        do{
            empleado = Empleados.get(i);
            i++;
        } while (Empleados.get(i).getCodigo() == codigo);

       return empleado;
    }


    @Override
    public boolean actualizarEmpleado(Empleado empleado) {

        String fichero = archivoEmpleados;
        Path path = Paths.get(archivoEmpleados);

        List<Empleado> empleados = leerEmpleados();

        for (int i = 0; i < empleados.size(); i++) {
            if (empleado.getCodigo() == empleados.get(i).getCodigo()){
                empleados.get(i).setPassword(empleado.getPassword());
            }
        }

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                System.out.println("Error, no se puede crear el archivo");
            }
        }

        try (BufferedWriter salida = Files.newBufferedWriter(
                path, StandardOpenOption.WRITE)) {

            for (int i = 0; i < empleados.size(); i++) {
                var p_list = empleados.get(i);
                salida.write("[empleado]");
                salida.newLine();
                salida.write("[codigo]");
                salida.newLine();
                salida.write(String.format("%d%n", p_list.getCodigo()));
                salida.write("[nombre]");
                salida.newLine();
                salida.write(String.format("%s%n", p_list.getNombre()));
                salida.write("[apellidos]");
                salida.newLine();
                salida.write(String.format("%s%n", p_list.getApellidos()));
                salida.write(String.format("%s%n","[password]"));
                salida.write(String.format("%s%n", p_list.getPassword()));

            }
        } catch (IOException ex) {
            System.out.println("Error, no se ha podido escribir en el archivo");
        }
        return true;
    }

}
