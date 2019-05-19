package producto.control;

import empleado.dao.EmpleadoDAOImp;
import empleado.dominio.Empleado;
import factura.Pedido;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import producto.dao.ProductoDAO;
import producto.dao.ProductoDAOImp;
import producto.dominio.Producto;
import tienda.vista.VistaProductos;
import util.Color;

public class GestionProductos {

    Empleado empleadoAuten;

    Scanner scan = new Scanner(System.in);

    String archivoPorduc = "src/File/archivoProducto.txt";
    private List<Producto> productos;

    public GestionProductos(Empleado empleadoLogado) {

        Path path = Paths.get(this.archivoPorduc);

        List<Producto> productList = new ArrayList<Producto>();

        try ( var reader = Files.newBufferedReader(path)) {

            int productCode = 0;
            String productName = null;
            String productDescription = null;
            double productPrice = 0.0;

            while (reader.readLine() != null) {

                reader.readLine();
                productCode = Integer.parseInt(reader.readLine().trim());

                reader.readLine();
                productName = reader.readLine().trim();

                reader.readLine();
                productDescription = reader.readLine().trim();

                reader.readLine();
                productPrice = Double.parseDouble(reader.readLine().trim().replace(',', '.'));

                productList.add(new Producto(productCode, productName, productDescription, productPrice));

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setProductos(productList);
        this.empleadoAuten = empleadoLogado;

    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> products) {
        this.productos = products;
    }

    private boolean esCodigoValido(int productCode) {

        boolean validProductCode = false;

        for (int i = 0; i < productos.size(); i++) {

            if (productCode == productos.get(i).getCodigo()) {
                validProductCode = true;
            }
        }

        return validProductCode;
    }

    public void actualizarCodigo() {

        int newProductCode = 0;
        boolean validNewProductCode = false;
        boolean validFormat = true;

        while (!validNewProductCode) {

            validFormat = true;
            imprimirProductos();
            System.out.print("Entre un codigo de un productos : ");
            String newProductCodeString = scan.nextLine();

            try {
                newProductCode = Integer.parseInt(newProductCodeString);

            } catch (NumberFormatException e) {
                System.err.println("El valor introducido no es un entero. Vuelva a intentarlo de nuevo.\n");
                validFormat = false;
            }

            if (validFormat) {
                if (esCodigoValido(newProductCode)) {
                    validNewProductCode = true;
                } else {
                    System.err.println("Código del producto inexistente\n");
                }
            }
        }

        boolean cambioCodigoProducto = true;
        while (cambioCodigoProducto) {

            boolean codigoYaExistente = false;
            System.out.println("Entre el nuevo codigo: ");
            int newCod = scan.nextInt();

            for (Producto producto : productos) {
                if (newCod == producto.getCodigo()) {
                    codigoYaExistente = true;
                }
            }
            if (!codigoYaExistente) {
                var newProduct = new ProductoDAOImp();
                newProduct.updateCode(newProductCode, newCod);
                System.out.println(Color.GREEN + "Cambiado exitosamente" + Color.DEFAULT);
                cambioCodigoProducto = false;
            } else {
                System.err.println("El código introducido ya pertenece a otro producto.");
            }
        }
        //para volver a la pantalla de producto
        VistaProductos.opcionDesdeMenuProductos(empleadoAuten);

    }

    public void actualizarNombre() {

        int newProductCode = 0;
        boolean validNewProductCode = false;
        boolean validFormat = true;

        while (!validNewProductCode) {

            validFormat = true;
            imprimirProductos();
            System.out.print("Entre un codigo de un productos : ");
            String newProductCodeString = scan.nextLine();

            try {
                newProductCode = Integer.parseInt(newProductCodeString);

            } catch (NumberFormatException e) {
                System.err.println("Error - solo se aceptan enteros\n");
                validFormat = false;
            }

            if (validFormat) {
                if (esCodigoValido(newProductCode)) {

                    validNewProductCode = true;
                } else {
                    System.err.println("El codigo no existe \n");
                }
            }
        }
        System.out.println("Entre el nuevo nombre ");

        String newName = scan.nextLine();

        var newProduct = new ProductoDAOImp();
        newProduct.updateName(newProductCode, newName);
        System.out.println(Color.GREEN + "Nombre cambiado exitosamente" + Color.DEFAULT);

        //para volver a la pantalla de producto
        VistaProductos.opcionDesdeMenuProductos(empleadoAuten);

    }

    public void actualizarPrecio() {

        int newProductCode = 0;
        boolean validNewProductCode = false;
        boolean validFormat = true;

        while (!validNewProductCode) {

            validFormat = true;
            imprimirProductos();
            System.out.print("Entre un codigo de un productos : ");
            String newProductCodeString = scan.nextLine();

            try {
                newProductCode = Integer.parseInt(newProductCodeString);

            } catch (NumberFormatException e) {
                System.err.println("Error - solo se aceptan enteros\n");
                validFormat = false;
            }

            if (validFormat) {
                if (esCodigoValido(newProductCode)) {

                    validNewProductCode = true;
                } else {
                    System.err.println("Código del producto inexistente\n");
                }
            }
        }
        System.out.println("entre el nuevo precio ");
        double productPrice = 0;
        String newCod = scan.next();
        try {
            productPrice = Double.parseDouble(newCod);

        } catch (NumberFormatException e) {
            System.err.println("Error - solo se acepta dobles\n");
            validFormat = false;
        }

        var newProduct = new ProductoDAOImp();
        newProduct.updatePrice(newProductCode, productPrice);

        System.out.println(Color.GREEN + "Precio cambiado exitosamente" + Color.DEFAULT);

        //para volver a la pantalla de producto
        VistaProductos.opcionDesdeMenuProductos(empleadoAuten);
        Pedido pedido = new Pedido();
        pedido.leeProductos();

    }

    void imprimirProductos() {
        System.out.println(Color.BLUE + "\n************************************************" + Color.DEFAULT);
        for (Producto producto : productos) {
            System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", producto.getCodigo(),
                    producto.getNombre(), producto.getDescripcion(), producto.getPrecio());

        }
        System.out.println(Color.BLUE + "************************************************\n" + Color.DEFAULT);
    }

}
