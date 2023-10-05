package estuardodev.sistemavotacionumg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static estuardodev.sistemavotacionumg.Registrador.cumpleCondicionesDeFecha;
/**
 *
 * @author Estuardo
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Auditor {
    private static final String ELECTION = SistemaVotacionUMG.ELECTIONS;
    private static final String REGISTRO_VOTOS = SistemaVotacionUMG.REGISTRO_VOTOS;
    private static final String RELACION_CANDIDATOS_ELECCION = SistemaVotacionUMG.RELACION_CANDIDATOS_ELECCION;
    private static final String REGEX_NUMBERS = "\\d+";
    public static void VerificarAuditor() {
        Boolean verificar = LoginAuditor();
        if (verificar) {
            HomeAuditor();
        } else {
            System.out.println("NO PUDIMOS VERIFICAR TU IDENTIDAD, LO SENTIMOS.");
        }
    }
    public static boolean LoginAuditor(){
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        String pass, user;
        System.out.println("¡Bienvenido Auditor!");
        System.out.println("¡Debemos verificar unas cosas!");
        System.out.println("INGRESA LAS CREDENCIALES: ");
        System.out.print("Nombre de Usuario: ");
        user = scan.next();
        System.out.print("Contraseña: ");
        pass = scan.next();

        Boolean credenciales = Utilidades.VerificarCredenciales(user, pass, "auditor");

        if (credenciales) {
            return true;
        }

        return false;
    }

    public static void HomeAuditor() {
        Utilidades.Limpiar();
        System.out.println("-- Sistema de Votación -- ");
        System.out.println("¿De qué elección deseas ver sus estadísticas?");
        System.out.println("Ingrese 0 para salir.");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ELECTION))) {
            String linea;
            boolean eleccionEncontrada = false;

            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("/");
                if (cumpleCondicionesDeFecha(partes[6], partes[7])) {
                    System.out.println("Código: " + partes[0] + ", Título: " + partes[1]);
                    eleccionEncontrada = true;
                }
            }

            if (!eleccionEncontrada) {
                System.out.println("Ninguna elección ha empezado votaciones. Regresando al menú principal.");
                Utilidades.Sleep(3);
                Home.Home();
            }

            Scanner scan = new Scanner(System.in);
            System.out.print("Ingresa el código de una opción: ");
            String opcion = scan.nextLine();
            if (opcion.equals("0")){
                System.out.println("Saliendo");
                Utilidades.Sleep(2);
                Home.Home();
            }

            bufferedReader.close(); // Cierra el lector antes de usarlo nuevamente

            // Vuelve a leer el archivo para encontrar la opción seleccionada
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(ELECTION));
            while ((linea = bufferedReader2.readLine()) != null) {
                String[] partes = linea.split("/");
                if (partes[0].equals(opcion) && cumpleCondicionesDeFecha(partes[6], partes[7])) {
                    Opciones(opcion);
                    return; // Sale del método después de llamar a Opciones
                }
            }

            System.out.println("No existe esa elección. Regresando al menú principal.");
            Utilidades.Sleep(2);
            Home.Home();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void Opciones(String opcion){
        Utilidades.Limpiar();
        System.out.println("-- Sistema de Votaciones --");
        System.out.println("¿Como deseas filtar las votaciones?");
        System.out.println("[1] Votos totales");
        System.out.println("[2] Edades");
        System.out.println("[3] Generos");
        System.out.println("[0] Regresar");

        String eleccion;
        Scanner scanner = new Scanner(System.in);
        eleccion = scanner.next();

        switch (eleccion){
            case "1", "2", "3":
                Estadistica(opcion, eleccion, REGISTRO_VOTOS);
                break;
            case "0":
                HomeAuditor();
            default:
                System.out.println("La opción no es correcta.");
                Utilidades.Sleep(2);
                HomeAuditor();
                break;
        }
    }


    public static void Estadistica(String codigo, String opcion, String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split("/");
                if (partes.length == 3 && partes[0].equals(codigo)) {
                    switch (opcion) {
                        case "1":
                            try (BufferedReader bufferedReadersR = new BufferedReader(new FileReader(RELACION_CANDIDATOS_ELECCION))) {
                                String linea;
                                // Lee el archivo y muestra las elecciones
                                int dato = 0;
                                while ((linea = bufferedReadersR.readLine()) != null) {
                                    String[] partesR = linea.split("/");
                                    if (partesR[0].equals(codigo)) {
                                        dato = Integer.parseInt(partesR[2]);
                                        System.out.println("El total de votos es: " + dato);
                                        System.out.println("\n0 para salir...");
                                        String a;
                                        Scanner scan = new Scanner(System.in);
                                        a = scan.next();
                                        Utilidades.Sleep(1);
                                        HomeAuditor();
                                        break;
                                    }
                                }


                            }catch (Exception e){

                            }
                            break;
                        case "2":
                            filtroPorEdades(partes);
                            break;
                        case "3":
                            filtroPorGenero(partes);
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void filtroPorEdades(String[] partes) {
        String edadesString = partes[1].replaceAll("[\\[\\]]", "");
        String[] edadesArray = edadesString.split(",");
        Map<Integer, Integer> edadContador = new HashMap<>();

        for (String edad : edadesArray) {
            int edadInt = Integer.parseInt(edad);
            edadContador.put(edadInt, edadContador.getOrDefault(edadInt, 0) + 1);
        }

        System.out.println("Estadísticas por edades para la elección " + partes[0] + ":");
        for (Map.Entry<Integer, Integer> entry : edadContador.entrySet()) {
            System.out.println(entry.getValue() + " votos de personas de " + entry.getKey() + " años.");
        }
        System.out.println("\n0 para salir...");
        String a;
        Scanner scan = new Scanner(System.in);
        a = scan.next();
        Utilidades.Sleep(1);
        HomeAuditor();
    }

    private static void filtroPorGenero(String[] partes) {
        String generosString = partes[2].replaceAll("[\\[\\],]", "");
        int hombres = 0;
        int mujeres = 0;

        for (char genero : generosString.toCharArray()) {
            if (genero == 'M') {
                hombres++;
            } else if (genero == 'F') {
                mujeres++;
            }
        }

        System.out.println("Estadísticas por género para la elección " + partes[0] + ":");
        System.out.println(hombres + " Hombres votaron.");
        System.out.println(mujeres + " Mujeres votaron.");
        System.out.println("\n0 para salir...");
        String a;
        Scanner scan = new Scanner(System.in);
        a = scan.next();
        Utilidades.Sleep(1);
        HomeAuditor();
    }
}
