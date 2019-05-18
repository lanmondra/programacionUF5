/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.control;

import empleado.control.GestionaEmpleados;
import empleado.dominio.Empleado;
import factura.Pedido;
import factura.pedido1;
import factura.pedidoDAOImp;
import java.util.Scanner;
import producto.dao.ProductoDAO;
import producto.dao.ProductoDAOImp;
import producto.dominio.Producto;
import static tienda.vista.VistaTienda.muestraMensaje;
import util.Color;

/**
 *
 * @author miguelmondragon
 */
public class Gestion {
    Empleado empleado;
    public Gestion(Empleado empleado) {
        this.empleado=empleado;
    }

    public void HACER_PEDIDO() {

        System.out.println("------------------------------------------");
        System.out.println("------------MENU PEDIDO------------------");
        System.out.println("1.1 ._Añadir un producto a la cesta");
        System.out.println("1.2 ._Visualizar el precio total de la cesta");
        System.out.println("1.3 ._Imprimir factura");
        System.out.println("1.4 ._Terminar pedido");
        System.out.println("------------------------------------------");

        int opcion = opcion(1, 4);
        MenuPrincipal menu = null;

        switch (opcion) {
            case 1:
                Pedido p = new Pedido(empleado);
                p.leeProductos();
                p.añadir();
                //menu = MenuPrincipal.HACER_PEDIDO;
                break;
            case 2:

                // menu = MenuPrincipal.MODIFICAR_PRODUCTO;
                break;
            case 3:
                // menu = MenuPrincipal.CAMBIAR_PASSWORD;
                break;
            case 4:
                //menu = MenuPrincipal.CERRAR_SESION;
                break;
        }

        //return menu;
    }

    private static int opcion(int min, int max) {

        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;

        while (hayError) {
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

}
