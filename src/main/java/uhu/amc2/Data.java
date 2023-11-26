package uhu.amc2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class Data {

    public static int leerTipo(Menu m) throws Exception {
        int tipo = -1;
        Object input = JOptionPane.showInputDialog(m,
                "Tipo de autómata: [AFN]/[AFND]", "Elegir tipo", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) {
                String t = s.toUpperCase();
                switch (t) {
                    case "AFD":
                        tipo = 0;
                        break;
                    case "AFND":
                        tipo = 1;
                        break;
                    default:
                        throw new Exception("Tipo no válido.");
                }
            } else {
                throw new Exception("Tipo no válido.");
            }
        } else {
            throw new Exception("Tipo no válido.");
        }
        return tipo;
    }

    static ArrayList<Estado> leerEstados(Menu m) throws Exception {
        ArrayList<Estado> listaE = new ArrayList<>();
        Object input = JOptionPane.showInputDialog(m,
                "Estados: [q0 q1 ...]", "Introducir estados", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) {
                String tokens[] = s.split("\\s+");
                if (tokens.length > 0) {
                    for (int i = 0; i < tokens.length; i++) {
                        listaE.add(new Estado(tokens[i]));
                    }
                } else {
                    throw new Exception("Input no válido.");
                }
            } else {
                throw new Exception("Input no válido.");
            }
        } else {
            throw new Exception("Input no válido.");
        }
        return listaE;
    }

    public static Estado leerInicial(Menu m, ArrayList<Estado> listaE) throws Exception {
        Estado inicial = null;
        Object input = JOptionPane.showInputDialog(m,
                "Estado inicial: [qX]", "Introducir estado inicial", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) {
                int pos = Estado.pertenece(s, listaE);
                if (pos != -1) {
                    inicial = listaE.get(pos);
                    inicial.esInicial = true;
                } else {
                    throw new Exception("Input no válido.");
                }
            } else {
                throw new Exception("Input no válido.");
            }
        } else {
            throw new Exception("Input no válido.");
        }
        return inicial;
    }

    public static void leerFinales(Menu m, ArrayList<Estado> listaE) throws Exception {
        Object input = JOptionPane.showInputDialog(m,
                "Estados finales: [q0 q1 ...]", "Introducir estados finales", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) {
                String tokens[] = s.split("\\s+");
                if (tokens.length > 0) {
                    for (int i = 0; i < tokens.length; i++) {
                        int pos = Estado.pertenece(tokens[i], listaE);
                        if (pos != -1) {
                            listaE.get(pos).esFinal = true;
                        } else {
                            throw new Exception("Input no válido.");
                        }
                    }
                } else {
                    throw new Exception("Input no válido.");
                }
            } else {
                throw new Exception("Input no válido.");
            }
        } else {
            throw new Exception("Input no válido.");
        }
    }

    public static ArrayList<Transicion> leerTransicionesAFD(Menu m, ArrayList<Estado> listaE) throws Exception {
        ArrayList<Transicion> listaT = new ArrayList<>();
        Object input = "";
        while (!input.equals("fin")) {
            input = JOptionPane.showInputDialog(m,
                    "Transiciones: [qA s qB]/[fin]", "Introducir transiciones", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                String s = String.valueOf(input);
                if (s.length() <= 20) {
                    String tokens[] = s.split("\\s+");
                    if (tokens.length > 0) {
                        if (!tokens[0].equals("fin")) {
                            int posA = Estado.pertenece(tokens[0], listaE);
                            int posB = Estado.pertenece(tokens[2], listaE);
                            if (posA != -1 && posB != -1) {
                                Estado a = listaE.get(posA);
                                Estado b = listaE.get(posB);
                                Transicion t = new Transicion(a, tokens[1], b);
                                listaT.add(t);
                            } else {
                                throw new Exception("Input no válido.");
                            }
                        }
                    } else {
                        throw new Exception("Input no válido.");
                    }
                } else {
                    throw new Exception("Input no válido.");
                }
            } else {
                throw new Exception("Input no válido.");
            }
        }
        return listaT;
    }

    public static ArrayList<Transicion> leerTransicionesAFND(Menu m, ArrayList<Estado> listaE) throws Exception {
        ArrayList<Transicion> listaT = new ArrayList<>();
        Object input = "";
        while (!input.equals("fin")) {
            input = JOptionPane.showInputDialog(m,
                    "Transiciones: [qA s qB qC ...]/[fin]", "Introducir transiciones", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                String s = String.valueOf(input);
                if (s.length() <= 20) {
                    String tokens[] = s.split("\\s+");
                    if (tokens.length > 0) {
                        if (!tokens[0].equals("fin")) {
                            int[] pos = new int[tokens.length];
                            for (int i = 0; i != 1 && i < tokens.length; i++) {
                                pos[i] = Estado.pertenece(tokens[i], listaE);
                                if (pos[i] == -1) {
                                    throw new Exception("Input no válido.");
                                }
                            }
                            for (int i = 2; i < tokens.length; i++) {
                                Estado a = listaE.get(pos[0]);
                                Estado b = listaE.get(pos[i]);
                                Transicion t = new Transicion(a, tokens[1], b);
                                listaT.add(t);
                            }
                        }
                    } else {
                        throw new Exception("Input no válido.");
                    }
                } else {
                    throw new Exception("Input no válido.");
                }
            } else {
                throw new Exception("Input no válido.");
            }
        }
        return listaT;
    }

    public static ArrayList<Transicion> leerTransicionesLambda(Menu m, ArrayList<Estado> listaE) throws Exception {
        ArrayList<Transicion> listaL = new ArrayList<>();
        Object input = "";
        while (!input.equals("fin")) {
            input = JOptionPane.showInputDialog(m,
                    "Transiciones Lambda: [qA qB ...]/[fin]", "Introducir transiciones lambda", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                String s = String.valueOf(input);
                if (s.length() <= 20) {
                    String tokens[] = s.split("\\s+");
                    if (tokens.length > 0) {
                        if (!tokens[0].equals("fin")) {
                            int[] pos = new int[tokens.length];
                            for (int i = 0; i < tokens.length; i++) {
                                pos[i] = Estado.pertenece(tokens[i], listaE);
                                if (pos[i] == -1) {
                                    throw new Exception("Input no válido.");
                                }
                            }
                            for (int i = 0; i < tokens.length; i++) {
                                Estado a = listaE.get(pos[0]);
                                Estado b = listaE.get(pos[i]);
                                Transicion t = new Transicion(a, "lambda", b);
                                listaL.add(t);
                            }
                        }
                    } else {
                        throw new Exception("Input no válido.");
                    }
                } else {
                    throw new Exception("Input no válido.");
                }
            } else {
                throw new Exception("Input no válido.");
            }
        }
        return listaL;
    }

    public static IProceso parsearSM(File filename) throws Exception {
        try {
            //crear el scanner
            Scanner scanner = new Scanner(filename);
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
                    default:
                        throw new Exception("Archivo no válido.");
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
            } else {
                throw new Exception("Archivo no válido.");
            }
            //marcar inicial
            Estado ini = null;
            line = scanner.nextLine();
            tokens = line.split("\\s+");
            if ("INICIAL:".equals(tokens[0])) {
                int pos = Estado.pertenece(tokens[1], listaE);
                listaE.get(pos).esInicial = true;
                ini = listaE.get(pos);
            } else {
                throw new Exception("Archivo no válido.");
            }
            //marcar finales
            line = scanner.nextLine();
            tokens = line.split("\\s+");
            if ("FINALES:".equals(tokens[0])) {
                for (int i = 1; i < tokens.length; i++) {
                    int pos = Estado.pertenece(tokens[i], listaE);
                    listaE.get(pos).esFinal = true;
                }
            } else {
                throw new Exception("Archivo no válido.");
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
                } else {
                    throw new Exception("Archivo no válido.");
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
                } else {
                    throw new Exception("Archivo no válido.");
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
