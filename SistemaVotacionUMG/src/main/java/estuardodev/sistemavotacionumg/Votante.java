package estuardodev.sistemavotacionumg;

import estuardodev.sistemavotacionumg.SistemaVotacionUMG;
import estuardodev.sistemavotacionumg.Utilidades;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author Estuardo
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Votante {
    private static final String USERS = SistemaVotacionUMG.USERS;
    private static final String ELECTIONS = SistemaVotacionUMG.ELECTIONS;
    private static final String CANDIDATOS_ELECCION = SistemaVotacionUMG.RELACION_CANDIDATOS_ELECCION;
    private static final String CANDIDATOS = SistemaVotacionUMG.CANDIDATOS;

    static String dpiCodigo = "";
    public static void VerificarVotante(){
        Boolean verificar = LoginVotante();
        if (verificar) {
            HomeVotante();
        } else {
            System.out.println("NO PUDIMOS VERIFICAR TU IDENTIDAD, LO SENTIMOS.");
        }
    }

    public static boolean LoginVotante(){
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        String pass, user, dpi;
        System.out.println("¡Bienvenido Votante!");
        System.out.println("¡Debemos verificar unas cosas!");
        System.out.println("INGRESA LAS CREDENCIALES: ");
        System.out.print("Nombre: ");
        user = scan.next();
        System.out.print("Contraseña: ");
        pass = scan.next();
        System.out.print("DPI: ");
        dpi = scan.next();
        dpiCodigo = dpi;

        Boolean credenciales = Utilidades.VerificarCredenciales(user, pass, "votante", dpi);

        if (credenciales) {
            return true;
        }

        return false;
    }

    static String codigosss = "";

    public static void HomeVotante() {
        Utilidades.Limpiar();
        System.out.println("-- Sistema de Votación --");
        System.out.println("Estas son las elecciones a las que puedes votar: ");

        try {
            // Mostrar elecciones disponibles
            FileReader frE = new FileReader(ELECTIONS);
            BufferedReader brE = new BufferedReader(frE);
            String lineaE;
            String[] datosE;

            FileReader frU = new FileReader(USERS);
            BufferedReader brU = new BufferedReader(frU);
            String lineaU;
            String[] datosU;


            while ((lineaE = brE.readLine()) != null) {
                datosE = lineaE.split("/");

                while ((lineaU = brU.readLine()) != null) {
                    datosU = lineaU.split("/");

                    if (Registrador.cumpleCondicionesDeFecha(datosE[6], datosE[7]) && datosU[7].contains(datosE[0])) {
                        System.out.println("Código: " + datosE[0] + ", Título: " + datosE[1]);
                    }else{
                        System.out.println("No tienes elecciones asignadas");
                        Utilidades.Sleep(4);
                        Home.Home();
                    }
                }

            }

            brE.close();

            // Solicitar al usuario que elija una elección
            System.out.println("Elige el código de la elección para votar: ");
            Scanner scan = new Scanner(System.in);
            String opcion = scan.next();
            codigosss = opcion;

            // Verificar si la opción es válida
            if (esOpcionValida(opcion, ELECTIONS)) {
                // Obtener la lista de candidatos asociados a la elección
                List<String> candidatosDeEleccion = obtenerCandidatosDeEleccion(opcion, CANDIDATOS_ELECCION);

                // Mostrar información de los candidatos
                for (String codigoCandidato : candidatosDeEleccion) {
                    mostrarInformacionCandidato(codigoCandidato, CANDIDATOS);
                }

                // Solicitar al usuario que elija un candidato
                System.out.println("Elige el número del candidato para votar: ");
                opcion = scan.next();

                // Realizar el voto
                realizarVoto(opcion, candidatosDeEleccion, CANDIDATOS_ELECCION);

            } else {
                System.out.println("Opción no válida. Por favor, elige una opción válida.");
                Utilidades.Sleep(2);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores básico.
        }
    }

    private static boolean esOpcionValida(String opcion, String archivo) {
        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("/");
                if (datos[0].equals(opcion)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores básico.
        }
        return false;
    }

    private static List<String> obtenerCandidatosDeEleccion(String codigoEleccion, String archivo) {
        List<String> candidatos = new ArrayList<>();
        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("/");
                if (datos[0].equals(codigoEleccion)) {
                    candidatos.add(datos[1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores básico.
        }
        return candidatos;
    }

    private static void mostrarInformacionCandidato(String codigoCandidato, String archivo) {
        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            int contador = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("/");
                if (datos[0].equals(codigoCandidato)) {
                    // Puedes mostrar información del candidato si es necesario.
                    System.out.println("[" + contador + "] Candidato: " + datos[1] + ", Nombre: " + datos[2] + ", Otros datos: " + datos[3]);
                }
                contador++;
            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores básico.
        }
    }

    private static void realizarVoto(String opcion, List<String> candidatosDeEleccion, String archivo) {
        try {
            int indiceCandidato = Integer.parseInt(opcion) - 1;
            if (indiceCandidato >= 0 && indiceCandidato < candidatosDeEleccion.size()) {
                String codigoCandidato = candidatosDeEleccion.get(indiceCandidato);
                actualizarVotos(codigoCandidato, codigosss,archivo);
                eliminarEleccionesVotadas(USERS, codigosss);
                VotanteRegistros.RegistroVotos(codigosss, dpiCodigo);
                System.out.println("Voto registrado exitosamente.");
                Utilidades.Sleep(2);
                System.out.println("Regresando al menu principal.");
                Utilidades.Sleep(2);

            } else {
                System.out.println("Opción no válida. Por favor, elige una opción válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida. Por favor, elige una opción válida.");
        }
    }

    private static void actualizarVotos(String codigoCandidato, String codigoEleccion, String archivo) {
        try {
            Map<String, Integer> votosMap = new HashMap<>();

            // Leer el archivo y cargar la información en el mapa
            try (FileReader fr = new FileReader(archivo);
                 BufferedReader br = new BufferedReader(fr)) {

                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("/");
                    votosMap.put(datos[1], Integer.parseInt(datos[2]));
                }
            }

            // Incrementar el voto del candidato
            votosMap.put(codigoCandidato, votosMap.getOrDefault(codigoCandidato, 0) + 1);

            // Escribir de nuevo en el archivo, incluyendo el código de la elección
            try (FileWriter fw = new FileWriter(archivo);
                 PrintWriter pw = new PrintWriter(fw)) {

                for (Map.Entry<String, Integer> entry : votosMap.entrySet()) {
                    pw.println(codigoEleccion + "/" + entry.getKey() + "/" + entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores básico.
        }
    }



    private static void eliminarEleccionesVotadas(String archivoUsuarios, String codigoEleccion) {
        try {
            // Leer el archivo de usuarios y eliminar la elección votada por el usuario
            List<String> lineasUsuarios = new ArrayList<>();
            try (FileReader fr = new FileReader(archivoUsuarios);
                 BufferedReader br = new BufferedReader(fr)) {

                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("/");
                    String codigoUsuario = datos[1];

                    // Verificar si el usuario ha votado por la elección
                    if (datos[7].contains(codigoEleccion)) {
                        // Eliminar la elección votada por el usuario
                        String[] eleccionesVotadas = datos[7].substring(1, datos[7].length() - 1).split(", ");
                        List<String> nuevasEleccionesVotadas = new ArrayList<>();
                        for (String eleccionVotada : eleccionesVotadas) {
                            if (!eleccionVotada.equals(codigoEleccion)) {
                                nuevasEleccionesVotadas.add(eleccionVotada);
                            }
                        }
                        datos[7] = "[" + String.join(", ", nuevasEleccionesVotadas) + "]";
                    }

                    // Escribir la línea actualizada o sin cambios
                    lineasUsuarios.add(String.join("/", datos));
                }
            }

            // Escribir de nuevo en el archivo de usuarios
            try (FileWriter fw = new FileWriter(archivoUsuarios);
                 PrintWriter pw = new PrintWriter(fw)) {

                for (String linea : lineasUsuarios) {
                    pw.println(linea);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores básico.
        }
    }

}
