package uhu.amc2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author diego
 */
public class Data {

    public static IProceso parsearSM(String filename) {
        try {
            //crear el scanner
            Scanner scanner = new Scanner(new File(filename));
            String line = "";
            String[] tokens;
            //averiguar el tipo
            int tipo = -1;
            line = scanner.nextLine();
            tokens = line.split("\\s+");
            if ("TIPO:".equals(tokens[0])) {
                String tsm = tokens[1];
                switch (tsm) {
                    case "AFD":
                        tipo = 0;
                        break;
                    case "AFND":
                        tipo = 1;
                        break;
                }
            }
            //leer los estados
            ArrayList<Estado> listaE = new ArrayList<>();
            line = scanner.nextLine();
            tokens = line.split("\\s+");
            if ("ESTADOS:".equals(tokens[0])) {
                for (int i = 1; i < tokens.length; i++) {
                    listaE.add(new Estado(tokens[i]));
                }
            }
            //marcar inicial
            Estado ini = null;
            line = scanner.nextLine();
            tokens = line.split("\\s+");
            if ("INICIAL:".equals(tokens[0])) {
                int pos = Estado.pertenece(tokens[1], listaE);
                listaE.get(pos).esInicial = true;
                ini = listaE.get(pos);
            }
            //marcar finales
            line = scanner.nextLine();
            tokens = line.split("\\s+");
            if ("FINALES:".equals(tokens[0])) {
                for (int i = 1; i < tokens.length; i++) {
                    int pos = Estado.pertenece(tokens[i], listaE);
                    listaE.get(pos).esFinal = true;
                }
            }
            //crear las transiciones
            //AFD
            ArrayList<Transicion> listaT = new ArrayList<>();
            if (tipo == 0) {
                line = scanner.nextLine();
                tokens = line.split("\\s+");
                if ("TRANSICIONES:".equals(tokens[0])) {
                    line = scanner.nextLine();
                    tokens = line.split("\\s+");
                    while (!tokens[0].equals("FIN")) {
                        Estado origen = listaE.get(Estado.pertenece(tokens[0], listaE));
                        String simbolo = tokens[1].substring(1, tokens[1].length() - 1);
                        Estado destino = listaE.get(Estado.pertenece(tokens[2], listaE));
                        listaT.add(new Transicion(origen, simbolo, destino));
                        line = scanner.nextLine();
                        tokens = line.split("\\s+");
                    }
                }
            }
            //AFND
            ArrayList<Transicion> listaL = new ArrayList<>();
            if (tipo == 1) {
                line = scanner.nextLine();
                tokens = line.split("\\s+");
                if ("TRANSICIONES:".equals(tokens[0])) {
                    line = scanner.nextLine();
                    tokens = line.split("\\s+");
                    while (!tokens[1].equals("LAMBDA:")) {
                        for (int i = 2; i < tokens.length; i++) {
                            Estado origen = listaE.get(Estado.pertenece(tokens[0], listaE));
                            String simbolo = tokens[1].substring(1, tokens[1].length() - 1);
                            Estado destino = listaE.get(Estado.pertenece(tokens[i], listaE));
                            listaT.add(new Transicion(origen, simbolo, destino));
                        }
                        line = scanner.nextLine();
                        tokens = line.split("\\s+");
                    }
                }
                //lambda
                line = scanner.nextLine();
                tokens = line.split("\\s+");
                while (!tokens[0].equals("FIN")) {
                    for (int i = 1; i < tokens.length; i++) {
                        Estado origen = listaE.get(Estado.pertenece(tokens[0], listaE));
                        Estado destino = listaE.get(Estado.pertenece(tokens[i], listaE));
                        listaL.add(new Transicion(origen, "lambda", destino));
                    }
                    line = scanner.nextLine();
                    tokens = line.split("\\s+");
                }
            }
            //crear el automata correspondiente
            IProceso sm = null;
            switch (tipo) {
                case 0:
                    sm = new AFD(ini, listaE, listaT);
                    break;
                case 1:
                    sm = new AFND(ini, listaE, listaT, listaL);
                    break;
            }
            //cerrar el scanner
            scanner.close();
            //devolver el automata creado
            return sm;
        } catch (IOException ex) {
            return null;
        }
    }

}
