package estuardodev.sistemavotacionumg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Estuardo
 * @version 1.0
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class EditarUsers {
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
                RegistroUsuarios.HomeRegistroUsuario();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editarUsuarios(String USERS) {
        Scanner scanner1 = new Scanner(System.in);
        Utilidades.Limpiar();

        File file2 = new File(USERS);
        System.out.println("USUARIOS EXISTENTES:");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file2))) {
            String linea;
            List<String> lista = new ArrayList<>();

            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("/");
                lista.add(partes[0]); // Agrega el código al lista para referencia
                System.out.println("Código: " + partes[0] + ", Nombre: " + partes[2] + ", Username: " + partes[1]);
            }
        }catch (Exception e){

        }

        // Solicita el DPI del usuario que se desea editar
        System.out.println("Ingresa el código del usuario que deseas editar: ");
        String usernameEditar = scanner1.nextLine();

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
                if (campos.length > 1 && campos[0].equals(usernameEditar)) {
                    encontrado = true;

                    // Solicita al usuario qué campo desea editar
                    System.out.println("Selecciona el campo que deseas editar:");
                    System.out.println("1. Nombre de Usuario");
                    System.out.println("2. Nombre");

                    Scanner scanner = new Scanner(System.in);
                    int opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.println("Ingresa el nuevo nombre de usuario: ");
                            campos[1] = scanner.next();
                            break;
                        case 2:
                            System.out.println("Ingresa el nuevo nombre: ");
                            campos[2] = scanner.nextLine();
                            break;
                        default:
                            System.out.println("Opción no válida.");
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
                RegistroUsuarios.HomeRegistroUsuario();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
