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

public class GestionaProductos {

    Empleado empleadoAuten;

   

    Scanner scan = new Scanner(System.in);

    String archivoPorduc = "src/File/archivoProducto.txt";
    private List<Producto> productos;

    public GestionaProductos(Empleado empleadoLogado) {

        Path path = Paths.get(this.archivoPorduc);

        List<Producto> productList = new ArrayList<Producto>();

        try (var reader = Files.newBufferedReader(path)) {

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

    public void updateCode() {

        int newProductCode = 0;
        boolean validNewProductCode = false;
        boolean validFormat = true;

        while (!validNewProductCode) {

            validFormat = true;
            imprimirProductos();
            System.out.print("Introduce el codigo: ");
            String newProductCodeString = scan.nextLine();

            try {
                newProductCode = Integer.parseInt(newProductCodeString);

            } catch (NumberFormatException e) {
                System.err.println("Error - solo se aceptan enteros\n");
                validFormat = false;
            }

            if (validFormat) {
                if (esCodigoValido(newProductCode)) {
                    System.out.println("\nValido !\n");
                    validNewProductCode = true;
                } else {
                    System.err.println("Error - Invalid Product Code\n");
                }
            }
        }
        System.out.println("entre el nuevo codigo");

        int newCod = scan.nextInt();

        var newProduct = new ProductoDAOImp();
        newProduct.updateCode(newProductCode, newCod);
        System.out.println("cambiado");

        //para volver a la pantalla de producto
        VistaProductos.opcionDesdeMenuProductos(empleadoAuten);

    }

    public void updateName() {

        int newProductCode = 0;
        boolean validNewProductCode = false;
        boolean validFormat = true;

        while (!validNewProductCode) {

            validFormat = true;
            imprimirProductos();
            System.out.print("Introduce el codigo: ");
            String newProductCodeString = scan.nextLine();

            try {
                newProductCode = Integer.parseInt(newProductCodeString);

            } catch (NumberFormatException e) {
                System.err.println("Error - solo se aceptan enteros\n");
                validFormat = false;
            }

            if (validFormat) {
                if (esCodigoValido(newProductCode)) {
                    System.out.println("\nValido !\n");
                    validNewProductCode = true;
                } else {
                    System.err.println("Error - Invalid Product Code\n");
                }
            }
        }
        System.out.println("entre el nuevo nombre ");

        String newName = scan.nextLine();

        var newProduct = new ProductoDAOImp();
        newProduct.updateName(newProductCode, newName);
        System.out.println("cambiado");

        //para volver a la pantalla de producto
        VistaProductos.opcionDesdeMenuProductos(empleadoAuten);

    }

    public void updatePrice() {

        int newProductCode = 0;
        boolean validNewProductCode = false;
        boolean validFormat = true;

        while (!validNewProductCode) {

            validFormat = true;
            imprimirProductos();
            System.out.print("Introduce el codigo: ");
            String newProductCodeString = scan.nextLine();

            try {
                newProductCode = Integer.parseInt(newProductCodeString);

            } catch (NumberFormatException e) {
                System.err.println("Error - solo se aceptan enteros\n");
                validFormat = false;
            }

            if (validFormat) {
                if (esCodigoValido(newProductCode)) {
                    System.out.println("\nValido !\n");
                    validNewProductCode = true;
                } else {
                    System.err.println("Error - Invalid Product Code\n");
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

        System.out.println("cambiado");

        //para volver a la pantalla de producto
        VistaProductos.opcionDesdeMenuProductos(empleadoAuten);
        Pedido pedido = new Pedido();
        pedido.leeProductos();

    }

    void imprimirProductos() {
        System.out.println("\n************************************************");
        for (Producto producto : productos) {
            System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", producto.getCodigo(),
                    producto.getNombre(), producto.getDescripcion(), producto.getPrecio());

        }
        System.out.println("************************************************\n");
    }

}
