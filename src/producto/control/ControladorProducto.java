package producto.control;

import java.util.ArrayList;
import java.util.List;
import producto.dao.ProductoDAO;
import producto.dao.ProductoDAOImp;
import producto.dominio.Producto;

public class ControladorProducto {

    public List<Producto> leerProductos() {
        ProductoDAO pdao = new ProductoDAOImp();
        return pdao.leerProductos();
    }

    public boolean actualizarProducto(Producto producto, int codigo) {
        ProductoDAO pdao = new ProductoDAOImp();
        return pdao.actualizarProducto(producto, codigo);
    }

}
