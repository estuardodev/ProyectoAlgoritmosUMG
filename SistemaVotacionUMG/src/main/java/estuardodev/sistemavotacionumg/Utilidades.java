package estuardodev.sistemavotacionumg;

import estuardodev.sistemavotacionumg.SistemaVotacionUMG;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;

/**
 *
 * @author Estuardo
 * @version 0.3
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Utilidades {
    private static final String ADMIN = SistemaVotacionUMG.ADMIN;
    private static final String USERS = SistemaVotacionUMG.USERS;
    private static final String CANDIDATOS = SistemaVotacionUMG.CANDIDATOS;
    private static final String AUDITOR = SistemaVotacionUMG.AUDITORS;
    private static final String REGISTERS = SistemaVotacionUMG.REGISTRARS;

    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static void Limpiar(){
        
        for (int i = 0; i < 20; i++){
            System.out.println("");
        }
    }
    
    public static void Sleep(int Tiempo){
        /*Tiempo en Segundos*/
        try {
            // Espera durante el tiempo indicado
            Thread.sleep(Tiempo * 1000);
        } catch (InterruptedException e) {
            // Manejar la excepción si se interrumpe la espera
            e.printStackTrace();
        }
    }
    
    public static void Exit(){
        Limpiar();
        System.out.println("BYE");
        Sleep(1);
        System.exit(0);
        
    }

    public static String generarIdentificadorUnico() {
        Random random = new Random();

        int numeroAleatorio;
        String identificador;
        Set<String> identificadoresExist = leerIdentificadores();

        do {
            numeroAleatorio = random.nextInt(10000);
            identificador = String.format("%04d", numeroAleatorio);
        } while (identificadoresExist.contains(identificador));

        return identificador;
    }

    private static Set<String> leerIdentificadores() {
        Set<String> identificadores = new HashSet<>();

        // Agrega todos los archivos que necesitas buscar
        String[] archivos = {USERS, ADMIN, AUDITOR, CANDIDATOS, REGISTERS};

        for (String archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    identificadores.add(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return identificadores;
    }

    public static boolean VerificarExistencia(String Archivo, String Dato, int indice){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Archivo))) {
            String linea;
            List<String> lista = new ArrayList<>();

            // Lee el archivo y muestra las elecciones
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("/");
                lista.add(partes[indice]); // Agrega el código al lista para referencia
            }
            for (String i : lista){
                if (i.equals(Dato)){
                    return  true;
                }
            }
            return false;
        } catch (Exception e){

        }
        return false;
    }

    public static boolean VerificarCredenciales(String user, String pass, String rol){
        
        switch (rol) {
            case "admin":
                try {
                    File file = new File(ADMIN);
                    FileReader fr = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fr);

                    String linea;
                    boolean usuarioEncontrado = false;
                    boolean passwordEncontrada = false;

                    // Lee el archivo línea por línea
                    while ((linea = bufferedReader.readLine()) != null) {
                        if (linea.contains(user)) {
                            usuarioEncontrado = true;
                        }
                        if (linea.contains(pass)) {
                            passwordEncontrada = true;
                        }
                    }

                    // Cierra el archivo
                    bufferedReader.close();

                    // Comprueba si se encontró el usuario y la contraseña
                    if (usuarioEncontrado && passwordEncontrada) {
                        return true;
                    } else {
                        return false;
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
                
                break;
            case "registrador":
                try {
                    File file = new File(REGISTERS);
                    FileReader fr = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fr);

                    String linea;
                    boolean usuarioEncontrado = false;
                    boolean passwordEncontrada = false;

                    // Lee el archivo línea por línea
                    while ((linea = bufferedReader.readLine()) != null) {
                        if (linea.contains(user)) {
                            usuarioEncontrado = true;
                        }
                        if (linea.contains(pass)) {
                            passwordEncontrada = true;
                        }
                    }

                    // Cierra el archivo
                    bufferedReader.close();

                    // Comprueba si se encontró el usuario y la contraseña
                    if (usuarioEncontrado && passwordEncontrada) {
                        return true;
                    } else {
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

                break;
            case "auditor":
                try {
                    File file = new File(AUDITOR);
                    FileReader fr = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fr);

                    String linea;
                    boolean usuarioEncontrado = false;
                    boolean passwordEncontrada = false;

                    // Lee el archivo línea por línea
                    while ((linea = bufferedReader.readLine()) != null) {
                        if (linea.contains(user)) {
                            usuarioEncontrado = true;
                        }
                        if (linea.contains(pass)) {
                            passwordEncontrada = true;
                        }
                    }

                    // Cierra el archivo
                    bufferedReader.close();

                    // Comprueba si se encontró el usuario y la contraseña
                    if (usuarioEncontrado && passwordEncontrada) {
                        return true;
                    } else {
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

                break;
            default:
                throw new AssertionError();
        }
        
        return false;
    }

    public static boolean VerificarCredenciales(String user, String pass, String rol, String dpi){

        try {
            File file = new File(USERS);
            FileReader fr = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fr);

            String linea;
            boolean usuarioEncontrado = false;
            boolean passwordEncontrada = false;
            boolean dpiEncontrada = false;

            // Lee el archivo línea por línea
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.contains(user)) {
                    usuarioEncontrado = true;
                }
                if (linea.contains(pass)) {
                    passwordEncontrada = true;
                }
                if (linea.contains(pass)) {
                    dpiEncontrada = true;
                }
            }

            // Cierra el archivo
            bufferedReader.close();

            // Comprueba si se encontró el usuario y la contraseña
            if (usuarioEncontrado && passwordEncontrada && dpiEncontrada) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

    public static void crearUsuario(String filePath) {
        Scanner scanner = new Scanner(System.in);

        String nombre, username, password;
        String codigo = Utilidades.generarIdentificadorUnico();

        Utilidades.Limpiar();
        System.out.println("Ingresa el nombre: ");
        nombre = scanner.nextLine();
        System.out.println("Ingresa el nombre de usuario: ");
        username = scanner.nextLine();

        boolean existe = Utilidades.VerificarExistencia(filePath, username, 1);

        if (existe) {
            System.out.println("El nombre de usuario ya existe.");
            Utilidades.Sleep(3);
            RegistroUsuarios.HomeRegistroUsuario();
        }

        System.out.println("Ingresa una contraseña: ");
        password = scanner.nextLine();

        File file = new File(filePath);

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigo + "/" + username + "/" + nombre + "/" + password);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Usuario creado con éxito.");
        Utilidades.Sleep(3);
        RegistroUsuarios.HomeRegistroUsuario();
    }



    public static String generarPass(int longitud) {
        StringBuilder sb = new StringBuilder(longitud);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
            char caracter = CARACTERES_PERMITIDOS.charAt(indice);
            sb.append(caracter);
        }

        return sb.toString();
    }
    
}
