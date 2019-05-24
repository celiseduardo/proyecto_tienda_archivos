package producto.dao;

import conexion.ConexionBD;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import producto.dominio.Producto;

public class ProductoDAOImp implements ProductoDAO {

    private final String archivoProductos;

    public ProductoDAOImp() {
        this.archivoProductos = "productos.txt";
    }

    @Override
    public List<Producto> leerProductos() {
        List<Producto> productos = new ArrayList<>();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);
        Number numero;
        String lineaConDatos;

        try ( var archivo = Files.newBufferedReader(Paths.get(archivoProductos))) {
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

                //descripcion
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String descripcion = lineaConDatos;

                // precio
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(lineaConDatos);
                double precio = numero.doubleValue();

                productos.add(new Producto(codigo, nombre, descripcion, precio));
            }
        } catch (ParseException e) {
            System.err.println("Error de formato en " + archivoProductos);

        } catch (IOException e) {
            System.err.println("Error de lectura en " + archivoProductos);


        }

        return productos;
    }

    @Override
    public boolean actualizarProducto(Producto producto, int codigo) {
        String fichero = archivoProductos;
        Path path = Paths.get(archivoProductos);

        List<Producto> productos = leerProductos();

        for (int i = 0; i < productos.size(); i++) {
            if (codigo == productos.get(i).getCodigo()){
                productos.get(i).setCodigo(producto.getCodigo());
                productos.get(i).setNombre(producto.getNombre());
                productos.get(i).setPrecio(producto.getPrecio());
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

            for (int i = 0; i < productos.size(); i++) {
                var p_list = productos.get(i);
                salida.write("[producto]");
                salida.newLine();
                salida.write("[codigo]");
                salida.newLine();
                salida.write(String.format("%d%n", p_list.getCodigo()));
                salida.write("[nombre]");
                salida.newLine();
                salida.write(String.format("%s%n", p_list.getNombre()));
                salida.write("[descripcion]");
                salida.newLine();
                salida.write(String.format("%s%n", p_list.getDescripcion()));
                salida.write("[precio]");
                salida.newLine();
                salida.write(String.format("%.2f%n", p_list.getPrecio()));

            }
        } catch (IOException ex) {
            System.out.println("Error, no se ha podido escribir en el archivo");
        }
        return true;
    }




}
