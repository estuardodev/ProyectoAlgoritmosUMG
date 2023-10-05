package estuardodev.sistemavotacionumg;

import java.io.*;
import java.util.Scanner;

import static estuardodev.sistemavotacionumg.Administrador.HomeAdmin;

/**
 *
 * @author Estuardo
 * @version 0.2
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class RegistradorUsuarios {
    private static String USERS = SistemaVotacionUMG.USERS;
    private static String AUDITORS = SistemaVotacionUMG.AUDITORS;
    private static String REGISTERS = SistemaVotacionUMG.REGISTRARS;


    public static void ResetearPassword(){
        Scanner scanner = new Scanner(System.in);
        Utilidades.Limpiar();
        // Solicita el DPI del usuario que se desea editar
        System.out.println("Ingresa el DPI del usuario que deseas resetear la contraseña: ");
        String dpiEditar = scanner.nextLine();


        File file = new File(USERS);
        File tempFile = new File("src\\main\\java\\estuardodev\\documentos\\temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean encontrado = false;

            // Lee el archivo línea por línea
            while ((currentLine = reader.readLine()) != null) {
                // Divide la línea en campos usando el delimitador "/"
                String[] campos = currentLine.split("/");

                // Comprueba si el DPI coincide con el DPI a editar
                if (campos.length > 1 && campos[1].equals(dpiEditar)) {
                    encontrado = true;

                    campos[3] = Utilidades.generarPass(16);

                    // Reconstruye la línea con los campos editados
                    currentLine = String.join("/", campos);

                    System.out.println("La nueva contraseña es: " + campos[3]);
                    System.out.println("NO OLVIDE SU CONTRASEÑA");
                    Utilidades.Sleep(5);

                }

                // Escribe la línea actual en el archivo temporal
                writer.write(currentLine);
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Renombra el archivo temporal al original
            if (encontrado) {
                if (!file.delete()) {
                    System.out.println("No se pudo eliminar el archivo original.");
                    return;
                }

                if (!tempFile.renameTo(file)) {
                    System.out.println("No se pudo renombrar el archivo temporal.");
                } else {
                    System.out.println("Contraseña reseteada con éxito.");

                    Utilidades.Sleep(3);
                    Registrador.HomeRegistrador();
                }
            } else {
                System.out.println("Usuario no encontrado.");
                Utilidades.Sleep(3);
                Registrador.HomeRegistrador();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void RegistroUsuarioDeshabilitar(){
        Utilidades.Limpiar();
        System.out.println("-- Sistema de votación --");
        System.out.println("NOTA: Por seguridad solo se pueden deshabilitar votantes.");
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el DPI del usuario a deshabilitar por fallecimiento o volver a habilitar: ");
        String opcion = scan.next();

        boolean existe = Utilidades.VerificarExistencia(USERS, opcion, 1);
        if (!existe){
            System.out.println("USUARIO NO ENCONTRADO.");
            Utilidades.Sleep(3);
            Registrador.HomeRegistrador();
        }
        String accion = "";
        File file = new File(USERS);
        File tempFile = new File("src\\main\\java\\estuardodev\\documentos\\temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean encontrado = false;

            // Lee el archivo línea por línea
            while ((currentLine = reader.readLine()) != null) {
                // Divide la línea en campos usando el delimitador "/"
                String[] campos = currentLine.split("/");

                // Comprueba si el nombre de usuario coincide con el nombre de usuario a editar
                if (campos.length > 1 && campos[1].equals(opcion)) {
                    encontrado = true;


                    if (campos[6].equals("false")){
                        campos[6] = "true";
                        accion = "habilitado";
                    }else {
                        campos[6] = "false";
                        accion = "deshabilitado";
                    }
                    // Reconstruye la línea con los campos editados
                    currentLine = String.join("/", campos);
                }

                // Escribe la línea actual en el archivo temporal
                writer.write(currentLine);
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Renombra el archivo temporal al original
            if (encontrado) {
                if (!file.delete()) {
                    System.out.println("No se pudo eliminar el archivo original.");
                    return;
                }

                if (!tempFile.renameTo(file)) {
                    System.out.println("No se pudo renombrar el archivo temporal.");
                } else {
                    System.out.println("Usuario "+ accion +" con éxito.");
                    System.out.println("Para habilitar/deshabilitar nuevamente el usuario, vuelva a realizar la solicitud.");
                    Utilidades.Sleep(3);
                    Registrador.HomeRegistrador();
                }
            } else {
                System.out.println("Usuario no encontrado.");
                Utilidades.Sleep(3);
                Registrador.HomeRegistrador();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void RegistradorVotante(){
        Scanner scanner = new Scanner(System.in);

        String nombre, DPI, password, genero;
        int edad;
        String codigo = Utilidades.generarIdentificadorUnico();

        Utilidades.Limpiar();
        System.out.println("Ingresa el nombre: ");
        nombre = scanner.nextLine();
        System.out.println("Ingresa el DPI (Sin Espacios): ");
        DPI = scanner.nextLine();
        boolean existe = Utilidades.VerificarExistencia(USERS, DPI, 1);

        if (existe) {
            System.out.println("El DPI ya existe.");
            Utilidades.Sleep(3);
            Registrador.HomeRegistrador();
        }
        if (DPI.length() != 13){
            System.out.println("DPI INCORRECTO");
            Utilidades.Sleep(3);
            Registrador.HomeRegistrador();
        }
        System.out.println("Ingresa el genero " +
                "[M] Masculino" +
                "[F] Femenino ");
        genero = scanner.next();
        genero.toLowerCase();

        System.out.println("Ingresa la edad: ");
        edad = scanner.nextInt();
        if (edad < 18){
            System.out.println("La persona es menor de edad, no se puede registrar.");
            Utilidades.Sleep(3);
            Registrador.HomeRegistrador();
        }
        password = Utilidades.generarPass(16);

        File file = new File(USERS);

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigo + "/" + DPI + "/" + nombre + "/" + password + "/" + genero + "/" + edad + "/" + "true"+ "/" + "[]");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Usuario creado con éxito.");
        System.out.println("LA CONTRASEÑA DEL USUARIO ES: ");
        System.out.println(password);
        Utilidades.Sleep(5);
        Registrador.HomeRegistrador();

    }

    public static void EditarVotante(String USERS){
        Scanner scanner = new Scanner(System.in);
        Utilidades.Limpiar();
        // Solicita el DPI del usuario que se desea editar
        System.out.println("Ingresa el DPI del usuario que deseas editar: ");
        String dpiEditar = scanner.nextLine();


        File file = new File(USERS);
        File tempFile = new File("src\\main\\java\\estuardodev\\documentos\\temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean encontrado = false;

            // Lee el archivo línea por línea
            while ((currentLine = reader.readLine()) != null) {
                // Divide la línea en campos usando el delimitador "/"
                String[] campos = currentLine.split("/");

                // Comprueba si el DPI coincide con el DPI a editar
                if (campos.length > 1 && campos[1].equals(dpiEditar)) {
                    encontrado = true;

                    // Solicita al usuario qué campo desea editar
                    System.out.println("Selecciona el campo que deseas editar:");
                    System.out.println("1. Nombre");
                    System.out.println("2. Genero");
                    System.out.println("3. Edad");

                    Scanner scanner2 = new Scanner(System.in);
                    int opcion = scanner2.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.println("Ingresa el nuevo nombre: ");
                            campos[2] = scanner2.nextLine();
                            break;
                        case 2:
                            System.out.println("Ingresa el nuevo genero (M/F): ");
                            campos[4] = scanner2.next().toLowerCase();
                            break;
                        case 3:
                            System.out.println("Ingresa la nueva edad: ");
                            campos[5] = String.valueOf(scanner2.nextInt());
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            Utilidades.Sleep(3);
                            RegistroUsuarios.HomeRegistroUsuario();
                            break;
                    }

                    // Reconstruye la línea con los campos editados
                    currentLine = String.join("/", campos);
                }

                // Escribe la línea actual en el archivo temporal
                writer.write(currentLine);
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Renombra el archivo temporal al original
            if (encontrado) {
                if (!file.delete()) {
                    System.out.println("No se pudo eliminar el archivo original.");
                    return;
                }

                if (!tempFile.renameTo(file)) {
                    System.out.println("No se pudo renombrar el archivo temporal.");
                } else {
                    System.out.println("Usuario editado con éxito.");
                    Utilidades.Sleep(3);
                    RegistroUsuarios.HomeRegistroUsuario();
                }
            } else {
                System.out.println("Usuario no encontrado.");
                Utilidades.Sleep(3);
                Registrador.HomeRegistrador();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
