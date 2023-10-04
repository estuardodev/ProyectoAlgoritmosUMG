package estuardodev.sistemavotacionumg;

import static estuardodev.sistemavotacionumg.Administrador.HomeAdmin;
import static estuardodev.sistemavotacionumg.Elecciones.GestionarElecciones;
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
public class Candidatos {
    private static final String CANDIDATOS = SistemaVotacionUMG.CANDIDATOS;
    private static final String CANDIDATOS_ELECCION = SistemaVotacionUMG.RELACION_CANDIDATOS_ELECCION;
    private static final String ELECTION = SistemaVotacionUMG.ELECTIONS;
    
    
    public static void GestionCandidatos(){
        Utilidades.Limpiar();
        System.out.println("Gestionar Elecciones");
        System.out.println("1 - Crear Candidatos");
        System.out.println("2 - Eliminar Elección");
        System.out.println("3 - Asignar Candidatos a Elección");
        System.out.println("0 - Regresar");
        Scanner scan = new Scanner(System.in);
        String opcion;
        System.out.println("Elige una opción:");
        while (true) {
            opcion = scan.next();
            if (opcion.equals("1")) {
                GestionarCandidatosCrear();
                break;
            } else if (opcion.equals("2")) {
                GestionarCandidatosEliminar();
                break;
            } else if (opcion.equals("3")){
                GestionarCandidatosAsignar();
            } else if (opcion.equals("0")) {
                HomeAdmin();
                break;
            }
        }
    }
    
    public static void GestionarCandidatosCrear(){
        Utilidades.Limpiar();
        System.out.println("-- Agregar Candidato --");
        
        Scanner scan = new Scanner(System.in);
        File file = new File(CANDIDATOS);
        String nombre, formacion, experiencia, codigo;
        codigo = Utilidades.generarIdentificadorUnico();
        
        System.out.println("Nombre y apellidos completos del Candidato: ");
        nombre = scan.nextLine();
        System.out.println("Formación del Candidato: ");
        formacion = scan.nextLine();
        System.out.println("Experiencia del Candidato: ");
        experiencia = scan.nextLine();
        
        try {
            
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            
            FileReader fr = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fr);
            String linea;
            
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.contains(codigo)) {
                    codigo = Utilidades.generarIdentificadorUnico();
                }
            }
            
            bw.write(codigo + "/" + nombre + "/" + formacion + "/" + experiencia);
            bw.newLine(); 

            bw.close(); 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Código de Identificación del candidato: " + codigo);
        Utilidades.Sleep(3);
        
        System.out.println("Elección creada con éxito.");
        Utilidades.Sleep(1);
        HomeAdmin();
        
    }
    
    public static void GestionarCandidatosEliminar(){
        Utilidades.Limpiar();
        System.out.println("Sistema de Votaciones");
        System.out.println("Candidatos Existentes:");

        File file = new File(CANDIDATOS);
        String opcion;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String linea;
            List<String> lista = new ArrayList<>();

            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("/");
                lista.add(partes[0]); // Agrega el código al lista para referencia
                System.out.println("Código: " + partes[0] + ", Nombre: " + partes[1]);
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
                System.out.println("Candidato eliminado con éxito.");
                Utilidades.Sleep(2);
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
    
    public static void GestionarCandidatosAsignar(){
        Utilidades.Limpiar();
        File file_candidatos_eleccion = new File(CANDIDATOS_ELECCION);
        File file2 = new File(ELECTION);
        File file_candidatos = new File(CANDIDATOS);
        
        String opcion_eleccion = "", opcion_candidato = "";
        System.out.println("Sistema de Votación");
        System.out.println("¿A qué elección deseas asignar un nuevo candidato?");
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file2))){
            String linea;
            List<String> lista = new ArrayList<>();

            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split(",");
                lista.add(partes[0]); // Agrega el código al lista para referencia
                System.out.println("Código: " + partes[0] + ", Título: " + partes[1]);
            }

            Scanner scan = new Scanner(System.in);

            System.out.println("Ingrese el código de la elección: ");
            opcion_eleccion = scan.next();
            
            if (!lista.contains(opcion_eleccion)){
                System.out.println("El código no existe.");
                Utilidades.Sleep(2);
                GestionCandidatos();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try (BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file_candidatos))){
            String linea_txt;
            List<String> lista_txt = new ArrayList<>();
            System.out.println("\nCandidatos existentes:");

            // Lee el archivo y muestra las elecciones
            while ((linea_txt = bufferedReader1.readLine()) != null) {
                String[] partes_txt = linea_txt.split("/");
                lista_txt.add(partes_txt[0]); // Agrega el código al lista para referencia
                System.out.println("Código: " + partes_txt[0] + ", Título: " + partes_txt[1]);
            }

            Scanner scan = new Scanner(System.in);

            System.out.println("Ingrese el código del candidato a asignar: ");
            opcion_candidato = scan.next();
            
            if (!lista_txt.contains(opcion_candidato)){
                System.out.println("El código no existe.");
                Utilidades.Sleep(2);
                GestionCandidatos();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            
            FileOutputStream fos = new FileOutputStream(file_candidatos_eleccion, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(opcion_eleccion + "/" + opcion_candidato + "/" + "0");
            bw.newLine(); 

            bw.close(); 

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Se asigno correctamente.");
        Utilidades.Sleep(2);
        HomeAdmin();
    }
    
}
