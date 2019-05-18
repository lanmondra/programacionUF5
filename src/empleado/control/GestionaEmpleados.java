package empleado.control;

import empleado.dominio.Empleado;
import java.util.Scanner;
import util.Color;
import empleado.dao.*;
import empleado.errores.codigoError;
import empleado.errores.passwordIncorrectException;
import empleado.errores.userIncorrectException;
import factura.Pedido;
import java.util.logging.Level;
import java.util.logging.Logger;
import tienda.control.GestionTienda;

public class GestionaEmpleados {

    Scanner scan = new Scanner(System.in);

    ControladorEmpleado controlador;
    Empleado empleadoAutenticado;

    public GestionaEmpleados(Empleado empleado) {
        this.controlador = new ControladorEmpleado();
        this.empleadoAutenticado = empleado;
    }

    public void login() throws userIncorrectException, passwordIncorrectException{
        Scanner leerTeclado = new Scanner(System.in);
        boolean esEmpleadoValido = false;
        boolean esPasswordValido = false;
        

//        System.out.println("Bienvenido a la tienda");
//        System.out.println("***************************************");
            System.out.print("Introduce el código de tu usuario: ");
            while (!leerTeclado.hasNextInt()) {
                System.out.println(Color.ERROR + "Debe escribir un valor numérico" + Color.DEFAULT);
                System.out.print("Introduce el código de tu usuario: ");
                leerTeclado.next();
            }
            int codigoEntrada = leerTeclado.nextInt();

            System.out.print("Introduce tu contraseña: ");
            String passwordEntrada = leerTeclado.next();

            System.out.println("***************************************");
            System.out.println("");

            // empleadoAutenticado = controlador.getEmpleadoPorCodigo(codigoEntrada);
            empleadoAutenticado = controlador.getEmpleadoPorCodigo(codigoEntrada);
            if (codigoEntrada==empleadoAutenticado.getCodigo()) {
                esEmpleadoValido = true;
                if (passwordEntrada.equals(empleadoAutenticado.getPassword())) {
                    esPasswordValido = true;
                }
            }

        

            if (!esEmpleadoValido) {
                throw new userIncorrectException("empleado no valido ", codigoError.LOGIN_INCORRECTO);

                //throw new userIncorrectException(passwordEntrada, causa);
//            System.out.println("Usuario incorrecto");
            } else if (!esPasswordValido) {
                throw new passwordIncorrectException("Contraseña incorrecta" ,codigoError.CONTRASEÑA_INCORRECTA);
                //System.out.println("Contraseña incorrecta");
            }
        
                
    }

    public Empleado getEmpleadoAutenticado() {
        return empleadoAutenticado;
    }

    private static void usuarioCorrecto(int codigo) {

        try {

        } catch (Exception e) {
        }
    }

    public void actualizarPassword(Empleado empleado) {
        Scanner scan = new Scanner(System.in);

        boolean next = true;
        while (next) {
            System.out.println("entre el Password antiguo de : " + empleado.getNombre());

            String password = scan.next();

            if (password.equals(empleado.getPassword())) {
                next = true;
                System.out.println(Color.GREEN + "entrada valida \n" + Color.DEFAULT);
                System.out.println("Entra la nueva contraseña: ");
                String newPassword = scan.next();
                var employees = new EmpleadoDAOImp();
                employees.updatePassword(empleado, newPassword);
                System.out.println(Color.GREEN + "Contraseña cambiada correctamente \n" + Color.DEFAULT);

                GestionTienda gestionTienda = new GestionTienda();
                empleado.setPassword(newPassword);
                gestionTienda.menu(empleado);
            } else {

            }

            System.err.println("la contraseña no coincide");

        }

    }

    public void cerrarSesion(Empleado empleado) {

        boolean sigue = true;
        while (sigue) {
            System.out.print("Entre el password para " + empleado.getNombre()  + " : ");

            String passw = scan.next();
            if (esPassword(empleado, passw)) {

                System.out.println(Color.GREEN + "ha salido de la cuenta de " + empleado.getNombre() + "\n" + Color.DEFAULT);
                Pedido pedido = new Pedido();
                pedido.vaciarArray();
                sigue = false;

            } else {
                System.err.println("contaseña no valida");
                // System.out.println("pruebe otra vez");
            }
        }
        GestionTienda gestionTienda = new GestionTienda();
        gestionTienda.iniciar();

    }

    private boolean esPassword(Empleado emple, String password) {

        boolean seguir = false;
        if (password.equals(emple.getPassword())) {

            seguir = true;

        }

        return seguir;
    }

}
