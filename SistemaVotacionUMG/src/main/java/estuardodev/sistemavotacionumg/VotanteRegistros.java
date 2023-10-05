package estuardodev.sistemavotacionumg;

import java.io.*;
/**
 *
 * @author Estuardo
 * @version 0.3
 * @see https://github.com/estuardodev/ProyectoAlgoritmosUMG.git
 */
public class VotanteRegistros {
    private static final String REGISTRO_VOTOS = SistemaVotacionUMG.REGISTRO_VOTOS;
    private static final String USERS = SistemaVotacionUMG.USERS;

    public static void RegistroVotos(String codigo, String votante) {

        File file = new File(REGISTRO_VOTOS);
        File file2 = new File(USERS);

        try {
            FileReader fr = new FileReader(file2);
            BufferedReader reader = new BufferedReader(fr);
            String currentLine;
            String edad = null, genero = null;

            // Lee el archivo línea por línea
            while ((currentLine = reader.readLine()) != null) {
                // Divide la línea en campos usando el delimitador "/"
                String[] campos = currentLine.split("/");

                if (campos.length > 1 && campos[1].equals(votante)) {
                    edad = campos[5];
                    genero = campos[4];
                    break; // Debes salir del bucle una vez que encuentres el votante
                }
            }

            // Verifica si ya existe un registro para el código de elección
            if (existeRegistro(file, codigo)) {
                // Si existe, agrega la nueva información al registro existente
                agregarInformacion(file, codigo, edad, genero);
            } else {
                // Si no existe, crea un nuevo registro
                crearNuevoRegistro(file, codigo, edad, genero);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción para depuración
        }
    }

    private static boolean existeRegistro(File file, String codigo) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String currentLine;

        // Verifica si ya existe un registro para el código de elección
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith(codigo + "/")) {
                return true;
            }
        }

        return false;
    }

    private static void agregarInformacion(File file, String codigo, String edad, String genero) throws IOException {
        // Lee el archivo original
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;

        // Lee línea por línea y agrega la nueva información al registro existente
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith(codigo + "/")) {
                String[] partes = currentLine.split("/");

                // Extrae las edades y generos actuales
                String edades = partes[1].substring(1, partes[1].length() - 1);
                String generos = partes[2].substring(1, partes[2].length() - 1);

                // Agrega la nueva edad y género
                edades += "," + edad;
                generos += "," + genero;

                // Reconstruye la línea con las nuevas listas de edades y géneros
                currentLine = String.format("%s/[%s]/[%s]", codigo, edades, generos);
            }
            stringBuilder.append(currentLine).append("\n");
        }
        reader.close();

        // Escribe el archivo con la nueva información
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(stringBuilder.toString());
        writer.close();
    }

    private static void crearNuevoRegistro(File file, String codigo, String edad, String genero) throws IOException {
        // Crea un nuevo registro
        FileWriter fw = new FileWriter(file, true); // true para añadir al final del archivo
        BufferedWriter bw = new BufferedWriter(fw);

        String nuevoRegistro = String.format("%s/[%s]/[%s]", codigo, edad, genero);
        bw.write(nuevoRegistro);
        bw.newLine();

        bw.close();
    }
}
