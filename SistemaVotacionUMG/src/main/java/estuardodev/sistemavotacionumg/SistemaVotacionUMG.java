package estuardodev.sistemavotacionumg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import estuardodev.sistemavotacionumg.Utilidades;

/**
 *
 * @author Estuardo
 * @version 1.0
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class SistemaVotacionUMG {

    public static final String BASE = "";
    static Path ruta = Paths.get(BASE);
    static Path ruta_absoluta = ruta.toAbsolutePath();
    public static final String PRE_BASE_DIR = ruta_absoluta.toString();
    public static final String BASE_DIR = PRE_BASE_DIR + "\\src\\main\\java\\estuardodev\\";
    public static final String DOCS = BASE_DIR + "documentos\\";
    public static final String INIT = DOCS + "init.txt";
    public static final String ADMIN = DOCS + "admin.txt";
    public static final String USERS = DOCS + "users.txt";
    public static final String REGISTRARS = DOCS + "registrars.txt";
    public static final String AUDITORS = DOCS + "auditors.txt";
    public static final String ELECTIONS = DOCS + "election.txt";
    public static final String CANDIDATOS = DOCS + "candidatos.txt";
    public static final String RELACION_CANDIDATOS_ELECCION = DOCS + "candidatos_eleccion.txt";
    public static final String REGISTRO_VOTOS = DOCS + "registro_votos.txt";
    public static String fecha_hora = obtenerFechaActual();

    public static void main(String[] args)throws IOException {
        SistemaVotacionUMG sv = new SistemaVotacionUMG();

        Utilidades.Limpiar();
        System.out.println("Sistema de Votaciones");
        boolean verificar = sv.primeraVez();
        if (verificar){
            sv.admin();
        }else{
            Home.Home();
        }

    }

    public boolean primeraVez(){
        try{
            File file = new File(INIT);
            File file2 = new File(USERS);
            File file3 = new File(REGISTRARS);
            File file4 = new File(AUDITORS);
            File file5 = new File(ELECTIONS);
            File file6 = new File(CANDIDATOS);
            File file7 = new File(RELACION_CANDIDATOS_ELECCION);
            File file8 = new File(REGISTRO_VOTOS);
            Path directorioBase = Paths.get(DOCS);
            if (!Files.exists(directorioBase)){
                Files.createDirectories(directorioBase);
            }

            if(!file.exists()){
                try{

                    file.createNewFile();
                    file2.createNewFile();
                    file3.createNewFile();
                    file4.createNewFile();
                    file5.createNewFile();
                    file6.createNewFile();
                    file7.createNewFile();
                    file8.createNewFile();

                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(fecha_hora);
                    bw.close();
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void admin(){
        File file = new File(ADMIN);
        try{
            if(!file.exists()){
                file.createNewFile();

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                Scanner scan = new Scanner(System.in);
                System.out.println("POR SER LA PRIMERA VEZ QUE SE INICIA EL PROGRAMA NECESITAMOS QUE INGRESES UNA CONTRASEÑA DE ADMINISTRADOR:");

                String password = "";
                while (password.isEmpty()){
                    System.out.print("INGRESA UNA CONTRASEÑA SEGURA: ");
                    password = scan.next();
                }
                bw.write("USER: admin\nPASSWORD: " + password);
                bw.close();
                System.out.println("ESTA CONTRASEÑA ES INMUTABLE, NUNCA LA COMPARTAS CON NADIE");  
                Utilidades.Sleep(5);
                Utilidades.Limpiar();
                Home.Home();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private static String obtenerFechaActual() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }
}
