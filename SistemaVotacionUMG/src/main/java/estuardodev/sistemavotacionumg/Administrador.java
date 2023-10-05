package estuardodev.sistemavotacionumg;

import java.util.Scanner;

/**
 *
 * @author Estuardo
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Administrador {
    private static final String ELECTION = SistemaVotacionUMG.ELECTIONS;

    public static void VerificarAdmin() {
        Boolean verificar = LoginAdmin();
        if (verificar) {
            HomeAdmin();
        } else {
            System.out.println("NO PUDIMOS VERIFICAR TU IDENTIDAD, LO SENTIMOS.");
        }
    }

    public static boolean LoginAdmin() {
        Utilidades.Limpiar();
        Scanner scan = new Scanner(System.in);
        String pass;
        System.out.println("¡Bienvenido Administrador!");
        System.out.println("¡Debemos verificar unas cosas!");
        System.out.println("INGRESA LAS CREDENCIALES: ");
        System.out.println("Usuario: admin");
        System.out.print("Contraseña: ");
        pass = scan.next();

        Boolean credenciales = Utilidades.VerificarCredenciales("admin", pass, "admin");

        if (credenciales) {
            return true;
        }

        return false;
    }

    public static void HomeAdmin() {
        Utilidades.Limpiar();
        System.out.println("Sistema de Votaciones");
        System.out.println("Administrador de Elecciones");
        System.out.println("");
        System.out.println("1 - Gestionar Elecciones");
        System.out.println("2 - Gestionar Candidatos");
        System.out.println("3 - Registro de Usuarios");
        System.out.println("4 - Configurar opción de Elección");
        System.out.println("0 - Salir");
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingresa una opción: ");
        String opcion = scan.next();
        switch (opcion) {
            case "1":
                Elecciones.GestionarElecciones();
                break;
            case "2":
                Candidatos.GestionCandidatos();
                break;
            case "3":
                RegistroUsuarios.HomeRegistroUsuario();
                break;
            case "4":
                ConfigElecciones.HomeConfigElecciones();
            case "0":
                Utilidades.Exit();
                break;
            default:
                throw new AssertionError();
        }

    }

}
