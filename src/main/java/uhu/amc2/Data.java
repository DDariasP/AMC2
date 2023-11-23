package uhu.amc2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author diego
 */
public class Data {

    public static Punto[] crearTSP(int talla, boolean peor) {
        try {
            //crear el nuevo .tsp
            String outputName = "dataset" + String.valueOf(talla) + ".tsp";
            File output = new File(outputName);
            Menu.fileName = outputName;
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
            //rellenar el nuevo .tsp
            FileWriter writer = new FileWriter(outputName);
            writer.write("DIMENSION: " + String.valueOf(talla) + "\n");
            writer.write("NODE_COORD_SECTION\n");
            Punto p[] = new Punto[talla];
            Punto.rellenar(p, talla, peor); //crear el array correspondiente
            DecimalFormat numberFormat = new DecimalFormat("#.0000000000");
            for (int i = 0; i < talla; i++) {
                String x = numberFormat.format(p[i].x);
                String y = numberFormat.format(p[i].y);
                writer.write(p[i].id + " " + x + " " + y + "\n");
            }
            writer.write("EOF");
            writer.close();
            //devolver el array creado
            return p;
        } catch (IOException ex) {
            return null;
        }
    }

    public static Punto[] parsearTSP(File file) {
        try {
            //averiguar la dimension
            Scanner scanner = new Scanner(file);
            String line = "";
            boolean found = false;
            String[] tokens;
            int talla = 0;
            while (!found) {
                line = scanner.nextLine();
                if (line.trim().length() > 0) {
                    tokens = line.split("\\s+");
                    if ("DIMENSION:".equals(tokens[0]) || "DIMENSION".equals(tokens[0])) {
                        found = true;
                        talla = Integer.parseInt(tokens[tokens.length - 1]);
                    }
                }
            }
            //crear el array correspondiente
            Punto p[] = new Punto[talla];
            int i = 0;
            found = false;
            while (!found) { //encontrar inicio
                line = scanner.nextLine();
                if (line.trim().length() > 0) {
                    tokens = line.split("\\s+");
                    if ("NODE_COORD_SECTION".equals(tokens[0]) || "NODE".equals(tokens[0])) {
                        found = true;
                    }
                }
            }
            while (scanner.hasNextLine()) { //guardar puntos
                line = scanner.nextLine();
                if (!line.equals("EOF") && line.length() > 0) {
                    tokens = line.split("\\s+");
                    int id = Integer.parseInt(tokens[0]);
                    double x = Double.parseDouble(tokens[1]);
                    double y = Double.parseDouble(tokens[2]);
                    p[i] = new Punto(id, x, y);
                    i++;
                }
            }
            scanner.close();
            //devolver el array creado
            return p;
        } catch (IOException ex) {
            return null;
        }
    }

    public static void guardarBusqueda(Punto[] p, String tipo) {
        try {
            //crear el nuevo .tsp
            String outputName = tipo + String.valueOf(p.length) + ".tsp";
            File output = new File(outputName);
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
            //rellenar el nuevo .tsp
            FileWriter writer = new FileWriter(outputName);
            writer.write("DIMENSION: " + String.valueOf(p.length) + "\n");
            writer.write("NODE_COORD_SECTION\n");
            DecimalFormat numberFormat = new DecimalFormat("#.0000000000");
            for (int i = 0; i < p.length; i++) {
                String x = numberFormat.format(p[i].x);
                String y = numberFormat.format(p[i].y);
                writer.write(p[i].id + " " + x + " " + y + "\n");
            }
            writer.close();
        } catch (IOException ex) {
        }
    }

}
