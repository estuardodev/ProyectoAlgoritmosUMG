package estuardodev.sistemavotacionumg;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Estuardo
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Registrador {
    private static String USERS = SistemaVotacionUMG.USERS;
    private static final String ELECTION = SistemaVotacionUMG.ELECTIONS;
    public static void VerificarRegistrador() {
        Boolean verificar = LoginRegistrador();
        if (verificar) {
            HomeRegistrador();
        } else {
            System.out.println("NO PUDIMOS VERIFICAR TU IDENTIDAD, LO SENTIMOS.");
        }
    }
    public static boolean LoginRegistrador(){
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        String pass, user;
        System.out.println("¡Bienvenido Registrador!");
        System.out.println("¡Debemos verificar unas cosas!");
        System.out.println("INGRESA LAS CREDENCIALES: ");
        System.out.print("Nombre de Usuario: ");
        user = scan.next();
        System.out.print("Contraseña: ");
        pass = scan.next();

        Boolean credenciales = Utilidades.VerificarCredenciales(user, pass, "registrador");

        if (credenciales) {
            return true;
        }

        return false;
    }

    public static void HomeRegistrador(){
        Utilidades.Limpiar();
        System.out.println("-- Sistema de Votación --");
        System.out.println("[1] Registrar votante ");
        System.out.println("[2] Editar votante");
        System.out.println("[3] Reinciar contraseña Votante");
        System.out.println("[4] Deshabilitar votante");
        System.out.println("[5] Asignar votaciones a votante");
        System.out.println("[0] Salir");
        Scanner scan = new Scanner(System.in);
        String opcion = scan.next();
        switch (opcion){
            case "1":
                RegistradorUsuarios.RegistradorVotante();
                break;
            case "2":
                RegistradorUsuarios.EditarVotante(USERS);
                break;
            case "3":
                RegistradorUsuarios.ResetearPassword();
                break;
            case "4":
                RegistradorUsuarios.RegistroUsuarioDeshabilitar();
                break;
            case "5":
                AsignarVotacion();
                break;
            case "0":
                Utilidades.Exit();
                break;
            default:
                break;
        }
    }

    private static void AsignarVotacion(){
        Scanner scanner = new Scanner(System.in);

        List<String> lista_votantes = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS))) {
            String linea;
            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                lista_votantes.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ingresa el DPI del usuario al que asignar elección: ");
        String dpiAsignar = scanner.nextLine();
        System.out.println();

        try (BufferedReader bufferedReaders = new BufferedReader(new FileReader(ELECTION))) {
            String linea;
            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReaders.readLine()) != null) {
                String[] partes = linea.split("/");
                if (cumpleCondicionesDeFecha(partes[4], partes[5])) {
                    System.out.println("Código: " + partes[0] + ", Título: " + partes[1]);
                }else{
                    System.out.println("Ninguna eleccion cumple con las fechas de registro.");
                    Utilidades.Sleep(3);
                    HomeRegistrador();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Ingrese el código que deseas asignar: ");
        String opcion = scanner.next();

        for (int i = 0; i < lista_votantes.size(); i++) {
            String[] partes = lista_votantes.get(i).split("/");
            if (partes.length >= 8 && partes[1].equals(dpiAsignar)) {

                String votaciones = partes[7];
                votaciones = votaciones.substring(1, votaciones.length() - 1);
                String[] votacionesArray = votaciones.split(", ");

                List<String> votacionesList = new ArrayList<>(List.of(votacionesArray));
                votacionesList.add(opcion);


                partes[7] = "[" + String.join(", ", votacionesList) + "]";

                // Actualiza la lista de votantes
                lista_votantes.set(i, String.join("/", partes));
                break;
            }
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS))) {
            for (String votante : lista_votantes) {
                writer.write(votante);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Se asigno correctamente.");
        Utilidades.Sleep(2);
        HomeRegistrador();

    }

    public static boolean cumpleCondicionesDeFecha(String inicio, String fin) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
            Date fechaInicio = sdf.parse(inicio);
            Date fechaFin = sdf.parse(fin);
            Date fechaActual = new Date();  // Obtener la fecha actual

            return fechaActual.after(fechaInicio) && fechaActual.before(fechaFin);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
