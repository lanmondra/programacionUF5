package tienda.control;

import empleado.control.GestionaEmpleados;
import empleado.dominio.Empleado;
import empleado.errores.passwordIncorrectException;
import empleado.errores.userIncorrectException;
import factura.Pedido;
import java.util.ArrayList;
import java.util.List;
import producto.control.ControladorProducto;
import producto.control.GestionaProductos;
import producto.dominio.Producto;
import producto.vista.VistaProducto;
import tienda.vista.VistaProductos;
import tienda.vista.VistaTienda;

public class GestionTienda {
    
    private Empleado empleadoAutenticado;
    private List<Producto> cesta;
    private GestionaEmpleados gestionaEmpleados;
    private GestionaEmpleados gestionaProductos;
    
    public GestionTienda() {
        empleadoAutenticado = null;
        cesta = new ArrayList<>();
        gestionaEmpleados = new GestionaEmpleados(empleadoAutenticado);
    }
    
    public void iniciar() {
        boolean esLoginCorrecto = false;
        while (!esLoginCorrecto) {
            try {
                gestionaEmpleados.login();
                esLoginCorrecto = true;
            } catch (userIncorrectException e) {
                System.err.println(e.getMessage());
                System.err.println("Código de error: "+e.getCodigoError());
            } catch(passwordIncorrectException p){
                 System.err.println(p.getMessage());
                System.err.println("Código de error: "+p.getCodigoError());
                
            }
        }
        
        empleadoAutenticado = gestionaEmpleados.getEmpleadoAutenticado();
        // VistaTienda.mensajeBienvenida(empleadoAutenticado);
        System.out.println("bienvenido " + empleadoAutenticado.getNombre());
        
        menu(empleadoAutenticado);
        
    }
    
    public void menu(Empleado empleadoAutenticado) {
        switch (VistaTienda.opcionDesdeMenuPrincipal()) {
            case HACER_PEDIDO:
                Pedido pedido = new Pedido(empleadoAutenticado);
                
                pedido.Pedido();
                //hacerPedido
                break;
            case MODIFICAR_PRODUCTO:
                VistaProductos vista = new VistaProductos(empleadoAutenticado);
                vista.opcionDesdeMenuProductos(empleadoAutenticado);
//                GestionaProductos c = new GestionaProductos();
//                c.menuProductos();

                break;
            case CAMBIAR_PASSWORD:
                GestionaEmpleados gestiona = new GestionaEmpleados(empleadoAutenticado);
                gestiona.actualizarPassword(empleadoAutenticado);
                // cambiarPassword
                break;
            case CERRAR_SESION:
                
                GestionaEmpleados g = new GestionaEmpleados(empleadoAutenticado);
                g.cerrarSesion(empleadoAutenticado);
                 //11cerrarSesion
                break;
        }
    }
    
}
