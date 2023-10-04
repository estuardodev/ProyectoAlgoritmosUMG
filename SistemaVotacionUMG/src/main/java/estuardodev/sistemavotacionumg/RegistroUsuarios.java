package estuardodev.sistemavotacionumg;

import jdk.jshell.execution.Util;

import static estuardodev.sistemavotacionumg.Administrador.HomeAdmin;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Estuardo
 * @version 1.0
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class RegistroUsuarios {
    private static String USERS = SistemaVotacionUMG.USERS;
    private static String AUDITORS = SistemaVotacionUMG.AUDITORS;
    private static String REGISTERS = SistemaVotacionUMG.REGISTRARS;


    public static void HomeRegistroUsuario(){
        Scanner scan = new Scanner(System.in);
        String opcion = "";
        
        do {
            Utilidades.Limpiar();
            System.out.println("-- Sistema de Votación --");
            System.out.println("1 - Crear Usuario");
            System.out.println("2 - Editar Usuario");
            System.out.println("3 - Habilitar/Deshabilitar Usuario");
            System.out.println("0 - Regresar");

            
            opcion = scan.next();
            
            switch (opcion) {
                case "1":
                    RegistroUsuarioCrear();
                    break;
                case "2":
                    RegistroUsuarioEditar();
                    break;
                case "3":
                    RegistroUsuarioDeshabilitar();
                    break;
                case "0":
                    HomeAdmin();
                    break;
                default:
                    System.out.println("Escoja bien.");
                    throw new AssertionError();
            }
            
        } while(!opcion.equals("0"));
    }
    
    public static void RegistroUsuarioCrear(){
        Scanner scan = new Scanner(System.in);
        String rol = "";

        Utilidades.Limpiar();
        System.out.println("¿Qué tipo de usuario desea crear?");
        System.out.println("[1] Registrador");
        System.out.println("[2] Auditor");
        System.out.println("[3] Votante");
        System.out.print("Escoje una opción: ");
        rol = scan.next();

        if (!rol.equals("1") && !rol.equals("2") && !rol.equals("3")) {
            System.out.println("POR FAVOR, ESCOJE UNA OPCION CORRECTA.");
            Utilidades.Sleep(2);
            HomeRegistroUsuario();
        }

        switch (rol) {
            case "1":
                Utilidades.crearUsuario(REGISTERS);
                break;
            case "2":
                Utilidades.crearUsuario(AUDITORS);
                break;
            case "3":
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
                    RegistroUsuarios.HomeRegistroUsuario();
                }
                if (DPI.length() != 13){
                    System.out.println("DPI INCORRECTO");
                    Utilidades.Sleep(3);
                    HomeRegistroUsuario();
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
                    HomeRegistroUsuario();
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
                RegistroUsuarios.HomeRegistroUsuario();

                break;

                default:
                    break;
            }

    }
    
    public static void RegistroUsuarioEditar(){
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        String entrada;
        System.out.println("-- Sistema de Votacion --");
        System.out.println("¿Qué usuario desea editar?");
        System.out.println("[1] Votante");
        System.out.println("[2] Auditor");
        System.out.println("[3] Registrador");
        System.out.println("[0] Regresar");
        entrada = scan.next();

        switch (entrada){
            case "1":
                EditarUsers.EditarVotante(USERS);
                break;
            case "2":
                EditarUsers.editarUsuarios(AUDITORS);
                break;
            case "3":
                EditarUsers.editarUsuarios(REGISTERS);
                break;
            default:
                System.out.println("OPCION INCORRECTA");
                Utilidades.Sleep(2);
                HomeRegistroUsuario();
                break;
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
            HomeRegistroUsuario();
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
