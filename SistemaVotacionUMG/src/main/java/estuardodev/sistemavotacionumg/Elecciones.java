package estuardodev.sistemavotacionumg;

import static estuardodev.sistemavotacionumg.Administrador.HomeAdmin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Estuardo
 * @version 1.0
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Elecciones {
    private static final String ELECTION = SistemaVotacionUMG.ELECTIONS;
    
    public static void GestionarElecciones() {
        Utilidades.Limpiar();
        System.out.println("Gestionar Elecciones");
        System.out.println("1 - Crear Elección");
        System.out.println("2 - Eliminar Elección");
        System.out.println("0 - Regresar");
        Scanner scan = new Scanner(System.in);
        String opcion;
        System.out.println("Elige una opción:");
        while (true) {
            opcion = scan.next();
            if (opcion.equals("1")) {
                GestionarEleccionesCrear();
                break;
            } else if (opcion.equals("2")) {
                GestionarEleccionesEliminar();
                break;
            } else if (opcion.equals("0")) {
                HomeAdmin();
                break;
            }
        }
    }

    public static void GestionarEleccionesCrear() {
        Utilidades.Limpiar();
        System.out.println("Sistema de Votaciones");

        Scanner scan = new Scanner(System.in);
        File file = new File(ELECTION);
        String titulo, proposito, descripcion, codigo;

        System.out.println("Ingrese un título para esta elección:");
        titulo = scan.nextLine();
        Utilidades.Sleep(1);
        System.out.println("Ingrese un propósito para esta elección:");
        proposito = scan.nextLine();
        Utilidades.Sleep(1);
        System.out.println("Ingrese una descripción para esta elección:");
        descripcion = scan.nextLine();
        Utilidades.Sleep(1);
        System.out.println("Ingrese un código único para esta elección (SIN ESPACIOS):");
        codigo = scan.next();
        Utilidades.Sleep(1);

        try {
            
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            
            FileReader fr = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fr);
            String linea;
            
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.contains(codigo)) {
                    System.out.println("ESTE CÓDIGO YA EXISTE.");
                    Utilidades.Sleep(3);
                    GestionarElecciones();
                }
            }
            bw.write(codigo + "/" + titulo + "/" + proposito + "/" + descripcion + "/" + "/" + "/" + "/" + "/");
            bw.newLine(); 

            bw.close(); 

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Elección creada con éxito.");
        Utilidades.Sleep(1);
        HomeAdmin();
    }


    public static void GestionarEleccionesEliminar() {
        Utilidades.Limpiar();
        System.out.println("Sistema de Votaciones");
        System.out.println("Elecciones existentes:");

        File file = new File(ELECTION);
        String opcion;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String linea;
            List<String> lista = new ArrayList<>();

            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("/");
                lista.add(partes[0]); // Agrega el código al lista para referencia
                System.out.println("Código: " + partes[0] + ", Título: " + partes[1]);
            }

            Scanner scan = new Scanner(System.in);

            System.out.println("Ingrese el código que deseas eliminar: ");
            opcion = scan.next();

            if (lista.contains(opcion)) {
                // Elimina la elección del archivo
                List<String> lineas = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    while ((linea = br.readLine()) != null) {
                        // Verifica si la línea es la que deseas eliminar y omítela
                        if (!linea.startsWith(opcion)) {
                            lineas.add(linea);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return; // Manejo de excepciones
                }

                // Escribe el contenido actualizado en el archivo
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    for (String lineaa : lineas) {
                        bw.write(lineaa);
                        bw.newLine(); // Agrega un salto de línea después de cada línea
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return; // Manejo de excepciones
                }
                System.out.println("Elección eliminada.");
                Utilidades.Sleep(3);
                HomeAdmin();
            } else {
                System.out.println("El código ingresado no existe.");
                Utilidades.Sleep(3);
                GestionarElecciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
