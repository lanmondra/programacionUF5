package empleado.dao;

import empleado.dominio.Empleado;
import java.sql.SQLException;
import java.util.List;
import empleado.dao.EmpleadoDAO;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpleadoDAOImp implements EmpleadoDAO {

    private List<Empleado> empleados;

    public EmpleadoDAOImp() {
        String archivoEmple = "src/File/archivoEmple.txt";
        Path path = Paths.get(archivoEmple);

        List<Empleado> employeesList = new ArrayList<Empleado>();

        try ( var reader = Files.newBufferedReader(path)) {

            int employeeAccessCode = 0;
            String employeeName = null;
            String employeeLastName = null;
            String employeePassword = null;

            while (reader.readLine() != null) {

                reader.readLine();
                employeeAccessCode = Integer.parseInt(reader.readLine().trim());

                reader.readLine();
                employeeName = reader.readLine().trim();

                reader.readLine();
                employeeLastName = reader.readLine().trim();

                reader.readLine();
                employeePassword = reader.readLine().trim();

                employeesList.add(new Empleado(employeeAccessCode, employeeName, employeeLastName, employeePassword));

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setEmpleados(employeesList);

    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public List<Empleado> leerEmpleados() {
        String archivoEmple = "src/File/archivoEmple.txt";
        List<Empleado> empleadoList = new ArrayList<>();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);// 
        Number numero;
        String liniaconDatos;

        try ( var archivo = Files.newBufferedReader(Paths.get(archivoEmple))) {

            while (archivo.readLine() != null) {

                archivo.readLine();
                liniaconDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(liniaconDatos);
                int codigoComp = numero.intValue();

                archivo.readLine();

                String nombre = archivo.readLine().trim();

                archivo.readLine();

                String apellidos = archivo.readLine().trim();

                archivo.readLine();

                String password = archivo.readLine().trim();

                empleadoList.add(new Empleado(codigoComp, nombre, apellidos, password));
            }
        } catch (ParseException e) {
            System.out.println("Error de fromato en: " + archivoEmple);

        } catch (IOException ex) {
            System.out.println("Error de lectura en: " + archivoEmple);

        }
        return empleadoList;
    }

    @Override

    public Empleado getEmpleadoPorCodigo(int codigo) {
        String archivoEmple = "src/File/archivoEmple.txt";
        Empleado empleado = new Empleado();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);// 
        Number numero;
        String liniaconDatos;

        try ( var archivo = Files.newBufferedReader(Paths.get(archivoEmple))) {

            while (archivo.readLine() != null) {

                archivo.readLine();
                liniaconDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(liniaconDatos);
                int codigoComp = numero.intValue();

                archivo.readLine();

                String nombre = archivo.readLine().trim();

                archivo.readLine();

                String apellidos = archivo.readLine().trim();

                archivo.readLine();

                String password = archivo.readLine().trim();

                if (codigoComp == codigo) {
                    empleado = new Empleado(codigo, nombre, apellidos, password);
                } else {

                }
            }

        } catch (ParseException e) {
            System.out.println("Error de fromato en: " + archivoEmple);

        } catch (IOException ex) {
            System.out.println("Error de lectura en: " + archivoEmple);

        }

        return empleado;

    }

    public void escribirEnArchivo() {
        String employeeListString = this.toString();
        String archivoEmple = "src/File/archivoEmple.txt";
        try {
            FileWriter writer = new FileWriter(archivoEmple);
            writer.write(employeeListString);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(EmpleadoDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarPassword(Empleado employee, String employeePassword) {
        for (int i = 0; i < getEmpleados().size(); i++) {
            if (getEmpleados().get(i).getCodigo() == employee.getCodigo()) {
                this.empleados.get(i).setPassword(employeePassword);
            }
        }
        this.escribirEnArchivo();
    }

    @Override
    public String toString() {

        String productListString = "";
        for (Empleado i : this.empleados) {
            productListString = "[empleado]" + "\n [codigo]\n " + i.getCodigo() + "\n [nombre]\n "
                    + i.getNombre() + "\n [apellidos]\n " + i.getApellidos() + "\n [contraseña]\n "
                    + i.getPassword()
                    + "\n";
        }
        return productListString;

    }

    @Override
    public boolean actualizarEmpleado(Empleado empleado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
