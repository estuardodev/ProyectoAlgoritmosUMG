package estuardodev.sistemavotacionumg;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
/**
 *
 * @author Estuardo
 * @version 1.0
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class ConfigElecciones {
    private static final String ELECTIONS = SistemaVotacionUMG.ELECTIONS;

    public static void HomeConfigElecciones() {
        Utilidades.Limpiar();
        System.out.println("-- Sistema de votación --");
        System.out.println("En este menu solo puedes establecer horas de inicio/fin de votación y de inscripción");
        System.out.println("¿Qué deseas hacer?");
        System.out.println("[1] Establecer horas de votación");
        System.out.println("[2] Establecer horas de inscripción");
        System.out.println("[0] Salir");
        Scanner scan = new Scanner(System.in);
        String opcion = scan.next();

        switch (opcion) {
            case "1":
                HoraVotacion();
                break;
            case "2":
                HorasInscripcion();
                break;
            case "0":
                Administrador.HomeAdmin();
                break;
            default:
                System.out.println("Ingrese opciones válidas.");
                Utilidades.Sleep(2);
                Administrador.HomeAdmin();
                break;
        }
    }

    private static void HoraVotacion() {
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        Mostrar();

        System.out.println("Ingresa el código de la elección para establecer las fechas de votación:");
        String codigoEleccion = scan.nextLine();

        establecerFechasVotacion(codigoEleccion);
    }

    private static void Mostrar() {
        File file = new File(ELECTIONS);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String linea;
            List<String> lista = new ArrayList<>();
            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("/");
                lista.add(partes[0]); // Agrega el código a la lista para referencia
                System.out.println("Código: " + partes[0] + ", Título: " + partes[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void establecerFechasVotacion(String codigoEleccion) {
        Scanner scan = new Scanner(System.in);
        File fc = new File("src\\main\\java\\estuardodev\\documentos\\temp.txt");
        File f = new File(ELECTIONS);

        try (
                FileReader fr = new FileReader(ELECTIONS);
                BufferedReader br = new BufferedReader(fr);
                FileWriter fw = new FileWriter(fc);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            String linea = "";
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("/");
                if (datos[4].isEmpty() || datos[5].isEmpty() || datos[4].isBlank() || datos[5].isBlank()){
                    System.out.println("PRIMERO ESTABLECE LAS HORAS DE INSCRIPCION");
                    Utilidades.Sleep(3);
                    Administrador.HomeAdmin();
                }

                if (datos[0].equals(codigoEleccion)) {
                    String horaInicio, horaFin;

                    do {
                        System.out.println("Ingresa una hora para el inicio de las votaciones (FORMATO HH:mm dd-MM-yyyy):");
                        horaInicio = scan.nextLine();
                        if (horaInicio.matches("\\d{2}:\\d{2} \\d{1,2}-\\d{1,2}-\\d{4}")) {
                            break;
                        } else {
                            System.out.println("La hora ingresada no cumple con el formato esperado.");
                        }
                    } while (true);

                    do {
                        System.out.println("Ingresa una hora para el fin de las votaciones (FORMATO HH:mm dd-MM-yyyy):");
                        horaFin = scan.nextLine();
                        if (horaFin.matches("\\d{2}:\\d{2} \\d{1,2}-\\d{1,2}-\\d{4}")) {
                            break;
                        } else {
                            System.out.println("La hora ingresada no cumple con el formato esperado.");
                        }
                    } while (true);

                    linea = datos[0] + "/" + datos[1] + "/" + datos[2] + "/" + datos[3] + "/" + datos[4] + "/" + datos[5] + "/" + horaInicio + "/" + horaFin + "/";
                }
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Se actualizó la hora.");
            Utilidades.Sleep(3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.move(fc.toPath(), f.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Administrador.HomeAdmin();
    }

    private static void establecerFechasInscripcion(String codigoEleccion) {
        Scanner scan = new Scanner(System.in);
        File fc = new File("src\\main\\java\\estuardodev\\documentos\\temp.txt");
        File f = new File(ELECTIONS);

        try (
                FileReader fr = new FileReader(ELECTIONS);
                BufferedReader br = new BufferedReader(fr);
                FileWriter fw = new FileWriter(fc);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            String linea = "";
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("/");
                if (datos[4].isEmpty() || datos[5].isEmpty() || datos[4].isBlank() || datos[5].isBlank()){
                    System.out.println("PRIMERO ESTABLECE LAS HORAS DE INSCRIPCION");
                    Utilidades.Sleep(3);
                    Administrador.HomeAdmin();
                }

                if (datos[0].equals(codigoEleccion)) {
                    String horaInicio, horaFin;

                    do {
                        System.out.println("Ingresa una hora para el inicio de las inscripciones (FORMATO HH:mm dd-MM-yyyy):");
                        horaInicio = scan.nextLine();
                        if (horaInicio.matches("\\d{2}:\\d{2} \\d{1,2}-\\d{1,2}-\\d{4}")) {
                            break;
                        } else {
                            System.out.println("La hora ingresada no cumple con el formato esperado.");
                        }
                    } while (true);

                    do {
                        System.out.println("Ingresa una hora para el fin de las inscripciones (FORMATO HH:mm dd-MM-yyyy):");
                        horaFin = scan.nextLine();
                        if (horaFin.matches("\\d{2}:\\d{2} \\d{1,2}-\\d{1,2}-\\d{4}")) {
                            break;
                        } else {
                            System.out.println("La hora ingresada no cumple con el formato esperado.");
                        }
                    } while (true);

                    linea = datos[0] + "/" + datos[1] + "/" + datos[2] + "/" + datos[3] + "/" + horaInicio + "/" + horaFin + "/" +  datos[6] + "/" +  datos[7]+ "/";
                }
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Se actualizó la hora.");
            Utilidades.Sleep(3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.move(fc.toPath(), f.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Administrador.HomeAdmin();
    }

    private static void HorasInscripcion(){
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        Mostrar();

        System.out.println("Ingresa el código de la elección para establecer las fechas de inscripcion:");
        String codigoEleccion = scan.nextLine();

        establecerFechasInscripcion(codigoEleccion);
    }
}
