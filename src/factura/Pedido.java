package factura;

import empleado.control.GestionEmpleados;
import empleado.dominio.Empleado;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import producto.control.GestionProductos;
import producto.dominio.Producto;
import producto.vista.VistaProducto;

import tienda.control.GestionTienda;

import tienda.vista.VistaPedidos;
import static tienda.vista.VistaPedidos.muestraMensaje;

import util.Color;

public class Pedido {

    Empleado empleado;

    String archivoPorductos = "src/File/archivoProducto.txt";

    Scanner scan = new Scanner(System.in);

    //ArrayList<Producto> newProductList = new ArrayList<Producto>();
    ArrayList<Producto> ProductList = new ArrayList<Producto>();

    private static ArrayList<Producto> listaCompletaDeProductosList = new ArrayList<Producto>();

    public Pedido(Empleado empleadoLogado) {
        this.empleado = empleadoLogado;

    }

    public Pedido() {
    }

    public Producto leeProductos() {

        Producto Productos = new Producto();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);// 
        // dependiendo de la localidad es punto o coma
        Number numero;
        String liniaconDatos;

        try ( var archivo = Files.newBufferedReader(Paths.get(archivoPorductos))) {

            while (archivo.readLine() != null) {
                // codigo
                archivo.readLine();//saltar un 
                liniaconDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(liniaconDatos);
                int codigo = numero.intValue();

                archivo.readLine();// salto
                //coger el nombre
                liniaconDatos = archivo.readLine().trim();
                String nombre = liniaconDatos;

                archivo.readLine();
                // descripcion
                liniaconDatos = archivo.readLine().trim();
                String descripcion = liniaconDatos;

                archivo.readLine();//saltar un 
                //precio
                liniaconDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(liniaconDatos);
                double precio = numero.doubleValue();

                Productos = new Producto(codigo, nombre, descripcion, precio);
                listaCompletaDeProductosList.add(Productos);

            }

        } catch (ParseException e) {
            System.out.println("Error de formato en: " + archivoPorductos);

        } catch (IOException ex) {
            System.out.println("Error de lectura en: " + archivoPorductos);

        }

        return Productos;

    }

    public void Pedido() {

        switch (VistaPedidos.opcionDesdeMenufactura()) {
            case AÑADIR_A_LA_CESTA:

                leeProductos();

                añadir();
                break;
            case VER_CESTA:
                verCesta(ProductList);
                break;
            case IMPRIMIR_FACTURA:
                imprimirFactura(ProductList, empleado);
                break;
            case TERMINAR_PEDIDO:
                terminar(empleado);
                break;

        }

    }

    public void añadir() {

        if (ProductList.size() > 0) {
            System.err.println("No podemos agregar más poductos");
            Pedido();

        }
        int opcion = pedirOpcion(1, 3);

        int inicio = 0;
        while (inicio < opcion) {

            boolean sigue = true;
            while (sigue) {
                int codigoEntrada = 0;
                System.out.println(Color.BLUE + "\n************************************************" + Color.DEFAULT);
                for (Producto producto : listaCompletaDeProductosList) {
                    System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", producto.getCodigo(),
                            producto.getNombre(), producto.getDescripcion(), producto.getPrecio());

                }
                System.out.println(Color.BLUE + "************************************************\n" + Color.DEFAULT);

                System.out.print("ENTRE EL CODIGO DEL PRODUCTO A COMPRAR:  ");
                String newCodigo = scan.next();

                try {
                    codigoEntrada = Integer.parseInt(newCodigo);

                } catch (NumberFormatException e) {

                }

                int aux = 0;
                if (Escodigovalido(codigoEntrada) && noEstaEnCesta(codigoEntrada)) {

                    for (int i = 0; i < listaCompletaDeProductosList.size(); i++) {

                        if (codigoEntrada == listaCompletaDeProductosList.get(i).getCodigo()) {
                            aux = aux + i;
                            ProductList.add(listaCompletaDeProductosList.get(aux));

                            System.out.println(Color.GREEN + "Guardado  correctamente " + Color.DEFAULT);

                            for (int j = 0; j < ProductList.size(); j++) {
                                System.out.println(ProductList.get(j).getNombre());
                                sigue = false;

                            }
                            System.out.println("\n");

                        }

                    }

                } else if (!Escodigovalido(codigoEntrada)) {
                    System.err.println("No existe ese codigo de producto");
                }

            }
            inicio++;

        }

        Pedido();

    }

    private boolean Escodigovalido(int codigoEntrada) {
        boolean seguir = false;
        try {

            for (int i = 0; i < listaCompletaDeProductosList.size(); i++) {
                if (listaCompletaDeProductosList.get(i).getCodigo() == codigoEntrada) {
                    seguir = true;

                }

            }
        } catch (InputMismatchException e) {
            System.out.println("debe ser entero");
        } catch (Exception d) {
            // System.out.println("ahora");

        }

        return seguir;

    }

    private boolean noEstaEnCesta(int codigoEntrada) {
        boolean seguir = true;

        for (int i = 0; i < ProductList.size(); i++) {
            if (codigoEntrada == ProductList.get(i).getCodigo()) {
                System.err.println("Producto Ya esta en la cesta");
                seguir = false;
            }
        }

        return seguir;
    }

    private static int pedirOpcion(int min, int max) {

        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;

        while (hayError) {
            System.out.println("Cuantos Productos deseas comparar entre [1-3] ?");
            System.out.print("Seleccione una opción: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = opcion < min || opcion > max;
                if (hayError) {
                    muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.ERROR);
                }
            } else {
                muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.ERROR);
                leerTeclado.next();
            }
        }

        return opcion;
    }

    private void verCesta(List ProductList) {

        if (ProductList.size() > 0) {

            double aux = 0;
            for (int i = 0; i < ProductList.size(); i++) {

                aux = aux + this.ProductList.get(i).getPrecio();

            }
            System.out.println(Color.BLACK_BACKGROUND + "\n\n------------------------------------------------\n" + Color.DEFAULT);
            System.out.println("Precio de la cesta : " + aux + " € \n");
            System.out.println(Color.BLACK_BACKGROUND + "------------------------------------------------\n\n" + Color.DEFAULT);
            //System.out.println("\t\t" + aux + " €");

        } else {
            System.out.println(Color.ERROR + "LA CESTA ESTA VACIA \n\n" + Color.DEFAULT);
        }
        Pedido();
    }

    private void imprimirFactura(List ProductList, Empleado empleado) {

        if (ProductList.size() > 0) {
            double aux = 0;
            System.out.println("\n\nFactura simplificada:");

            System.out.println(Color.PURPLE + "----------------------------------------" + Color.DEFAULT);
            for (int i = 0; i < ProductList.size(); i++) {
                System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", this.ProductList.get(i).getCodigo(),
                        this.ProductList.get(i).getNombre(), this.ProductList.get(i).getDescripcion(), this.ProductList.get(i).getPrecio());

            }

            System.out.println(Color.PURPLE + "----------------------------------------" + Color.DEFAULT);
            for (int i = 0; i < ProductList.size(); i++) {

                aux = aux + this.ProductList.get(i).getPrecio();

            }

            System.out.println("El precio TOTAL es: " + aux + "€");

            System.out.println("Atendido por: " + empleado.getNombre() + " " + empleado.getApellidos() + "\n\n");
        } else {
            System.out.println(Color.ERROR + "NO SE PUEDE IMPRIMIR POR QUE NO HAY PRODUCTOS\n\n" + Color.DEFAULT);
        }
        Pedido();
    }

    private void terminar(Empleado empleadoAuten) {
        GestionTienda gestionTienda = new GestionTienda();
        gestionTienda.menu(empleadoAuten);

    }

    public void vaciarArray() {
        listaCompletaDeProductosList.clear();

    }
}
