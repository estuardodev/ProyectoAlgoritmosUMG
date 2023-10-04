package estuardodev.sistemavotacionumg;

import java.util.Scanner;

/**
 *
 * @author Estuardo
 * @version 1.0
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class Home {

    public static void Home() {
        Scanner scan = new Scanner(System.in);
        String opcion;

        do {
            Utilidades.Limpiar();
            System.out.println("-- Sistema de Votación --");
            System.out.println("- ¿Cúal es tu rol? -");
            System.out.println("1 - VOTANTE");
            System.out.println("2 - REGISTRADOR");
            System.out.println("3 - AUDITOR");
            System.out.println("9 - ADMINISTRADOR");
            System.out.println("0 - Salir");
            System.out.print("Ingresa una opción: ");
            opcion = scan.next();

            switch (opcion) {
                case "1":
                    Votante.VerificarVotante();
                    break;
                case "2":
                    Registrador.VerificarRegistrador();
                    break;
                case "3":
                    Auditor.VerificarAuditor();
                    break;
                case "9":
                    Administrador.VerificarAdmin();
                    break;
                case "0":
                    Utilidades.Exit();
                    break;
                default:
                    Utilidades.Limpiar();
                    System.out.println("¡Opción no válida. Por favor, ingresa una opción válida!");
            }
        } while (!opcion.equals("0"));
    }
}
