package uhu.amc2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Clase para leer ficheros e inputs de teclado
 *
 * @author diego
 */
public class Data {

    /**
     * Lee desde teclado el tipo de autómata. Acepta los valores "afd", "AFD",
     * "afnd" y "AFND" (sin comillas).
     *
     * @param m Menú que recibe el input.
     * @return int que codifica el tipo del autómata.
     * @throws Exception si el input no es una de las cadenas aceptadas.
     */
    public static int leerTipo(Menu m) throws Exception {
        int tipo = -1;
        Object input = JOptionPane.showInputDialog(m,
                "Tipo de autómata: [AFD]/[AFND]", "Elegir tipo", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) { //longitud máxima 20
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

    /**
     * Lee desde teclado los estados del autómata. Acepta cadenas de hasta 20
     * carácteres, con los nombres de los estados separados por espacios, y es
     * sensible a mayúsculas y minúsculas.
     *
     * @param m Menú que recibe el input.
     * @return Lista de estados del autómata.
     * @throws Exception si la cadena es demasiado grande.
     */
    public static ArrayList<Estado> leerEstados(Menu m) throws Exception {
        ArrayList<Estado> listaE = new ArrayList<>();
        Object input = JOptionPane.showInputDialog(m,
                "Estados: [E E ...]", "Introducir estados", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) { //longitud máxima 20
                String tokens[] = s.split("\\s+");
                if (tokens.length > 0) {
                    for (int i = 0; i < tokens.length; i++) {
                        listaE.add(new Estado(tokens[i]));
                    }
                } else {
                    throw new Exception("Lista de estados no válida.");
                }
            } else {
                throw new Exception("Lista de estados no válida.");
            }
        } else {
            throw new Exception("Lista de estados no válida.");
        }
        return listaE;
    }

    /**
     * Lee desde teclado el estado inicial del autómata. Comprueba que el estado
     * existe en la lista de estados recibida, teniendo en cuenta mayúsculas y
     * minúsculas.
     *
     * @param m Menú que recibe el input.
     * @param listaE Lista de estados del autómata.
     * @return Estado marcado como inicial.
     * @throws Exception si el estado input no existe en la lista.
     */
    public static Estado leerInicial(Menu m, ArrayList<Estado> listaE) throws Exception {
        Estado inicial = null;
        Object input = JOptionPane.showInputDialog(m,
                "Estado inicial: [E]", "Introducir estado inicial", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) { //longitud máxima 20
                int pos = Estado.pertenece(s, listaE);
                if (pos != -1) {
                    inicial = listaE.get(pos);
                    inicial.esInicial = true;
                } else {
                    throw new Exception("No existe el estado input.");
                }
            } else {
                throw new Exception("No existe el estado input.");
            }
        } else {
            throw new Exception("No existe el estado input.");
        }
        return inicial;
    }

    /**
     * Lee desde teclado la lista de estados finales del autómata. Acepta
     * cadenas de hasta 20 carácteres, con los nombres de los estados separados
     * por espacios. Comprueba que los estados existen en la lista de estados
     * recibida, teniendo en cuenta mayúsculas y minúsculas.
     *
     * @param m Menú que recibe el input.
     * @param listaE Lista de estados del autómata.
     * @throws Exception si algún estado input no existe en la lista.
     */
    public static void leerFinales(Menu m, ArrayList<Estado> listaE) throws Exception {
        Object input = JOptionPane.showInputDialog(m,
                "Estados finales: [E E ...]", "Introducir estados finales", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            String s = String.valueOf(input);
            if (s.length() <= 20) { //longitud máxima 20
                String tokens[] = s.split("\\s+");
                if (tokens.length > 0) {
                    for (int i = 0; i < tokens.length; i++) {
                        int pos = Estado.pertenece(tokens[i], listaE);
                        if (pos != -1) {
                            listaE.get(pos).esFinal = true;
                        } else {
                            throw new Exception("No existe uno de los estados input.");
                        }
                    }
                } else {
                    throw new Exception("No existe uno de los estados input.");
                }
            } else {
                throw new Exception("No existe uno de los estados input.");
            }
        } else {
            throw new Exception("No existe uno de los estados input.");
        }
    }

    /**
     * Lee desde teclado la lista de transiciones de un AFD, una a una. Acepta
     * cadenas de hasta 20 carácteres, con los nombres de los estados y el
     * símbolo de transición separados por espacios. Comprueba que los estados
     * existen en la lista de estados recibida, teniendo en cuenta mayúsculas y
     * minúsculas. Termina cuando recibe la cadena "fin" (sin comillas).
     *
     * @param m Menú que recibe el input.
     * @param listaE Lista de estados del autómata.
     * @return Lista de transiciones del AFD.
     * @throws Exception si algún estado input no existe en la lista.
     */
    public static ArrayList<Transicion> leerTransicionesAFD(Menu m, ArrayList<Estado> listaE) throws Exception {
        ArrayList<Transicion> listaT = new ArrayList<>();
        Object input = "";
        while (!input.equals("fin")) {
            input = JOptionPane.showInputDialog(m,
                    "Transiciones: [E s E]/[fin]", "Introducir transiciones", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                String s = String.valueOf(input);
                if (s.length() <= 20) { //longitud máxima 20
                    String tokens[] = s.split("\\s+");
                    if (tokens.length > 0) {
                        if (!tokens[0].equals("fin")) {
                            //comprueba los estados
                            int posA = Estado.pertenece(tokens[0], listaE);
                            int posB = Estado.pertenece(tokens[2], listaE);
                            if (posA != -1 && posB != -1) {
                                //crea las transiciones
                                Estado a = listaE.get(posA);
                                Estado b = listaE.get(posB);
                                Transicion t = new Transicion(a, tokens[1], b);
                                listaT.add(t);
                            } else {
                                throw new Exception("Transición no válida.");
                            }
                        } else {
                            return listaT;
                        }
                    } else {
                        throw new Exception("Transición no válida.");
                    }
                } else {
                    throw new Exception("Transición no válida.");
                }
            } else {
                throw new Exception("Transición no válida.");
            }
        }
        return listaT;
    }

    /**
     * Lee desde teclado la lista de transiciones de un AFND, una a una. Acepta
     * cadenas de hasta 20 carácteres, con los nombres de los estados y el
     * símbolo de transición separados por espacios. Comprueba que los estados
     * existen en la lista de estados recibida, teniendo en cuenta mayúsculas y
     * minúsculas. Termina cuando recibe la cadena "fin" (sin comillas).
     *
     * @param m Menú que recibe el input.
     * @param listaE Lista de estados del autómata.
     * @return Lista de transiciones del AFND.
     * @throws Exception si algún estado input no existe en la lista.
     */
    public static ArrayList<Transicion> leerTransicionesAFND(Menu m, ArrayList<Estado> listaE) throws Exception {
        ArrayList<Transicion> listaT = new ArrayList<>();
        Object input = "";
        while (!input.equals("fin")) {
            input = JOptionPane.showInputDialog(m,
                    "Transiciones: [E s E E ...]/[fin]", "Introducir transiciones", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                String s = String.valueOf(input);
                if (s.length() <= 20) { //longitud máxima 20
                    String tokens[] = s.split("\\s+");
                    if (tokens.length > 0) {
                        if (!tokens[0].equals("fin")) {
                            //comprueba los estados
                            int[] pos = new int[tokens.length];
                            for (int i = 0; i < tokens.length; i++) {
                                pos[i] = Estado.pertenece(tokens[i], listaE);
                                if (pos[i] == -1 && i != 1) {
                                    throw new Exception("Transición no válida.");
                                }
                            }
                            //crea las transiciones
                            for (int i = 2; i < tokens.length; i++) {
                                Estado a = listaE.get(pos[0]);
                                Estado b = listaE.get(pos[i]);
                                Transicion t = new Transicion(a, tokens[1], b);
                                listaT.add(t);
                            }
                        } else {
                            return listaT;
                        }
                    } else {
                        throw new Exception("Transición no válida.");
                    }
                } else {
                    throw new Exception("Transición no válida.");
                }
            } else {
                throw new Exception("Transición no válida.");
            }
        }
        return listaT;
    }

    /**
     * Lee desde teclado la lista de transiciones lambda, una a una. Acepta
     * cadenas de hasta 20 carácteres, con los nombres de los estados separados
     * por espacios. Comprueba que los estados existen en la lista de estados
     * recibida, teniendo en cuenta mayúsculas y minúsculas. Termina cuando
     * recibe la cadena "fin" (sin comillas).
     *
     * @param m Menú que recibe el input.
     * @param listaE Lista de estados del autómata.
     * @return Lista de transiciones lambda.
     * @throws Exception si algún estado input no existe en la lista.
     */
    public static ArrayList<Transicion> leerTransicionesLambda(Menu m, ArrayList<Estado> listaE) throws Exception {
        ArrayList<Transicion> listaL = new ArrayList<>();
        Object input = "";
        while (!input.equals("fin")) {
            input = JOptionPane.showInputDialog(m,
                    "Transiciones Lambda: [E E ...]/[fin]", "Introducir transiciones lambda", JOptionPane.QUESTION_MESSAGE);
            if (input != null) {
                String s = String.valueOf(input);
                if (s.length() <= 20) { //longitud máxima 20
                    String tokens[] = s.split("\\s+");
                    if (tokens.length > 0) {
                        if (!tokens[0].equals("fin")) {
                            //comprueba los estados
                            int[] pos = new int[tokens.length];
                            for (int i = 0; i < tokens.length; i++) {
                                pos[i] = Estado.pertenece(tokens[i], listaE);
                                if (pos[i] == -1) {
                                    throw new Exception("Transición lambda no válida.");
                                }
                            }
                            //crea las transiciones
                            for (int i = 1; i < tokens.length; i++) {
                                Estado a = listaE.get(pos[0]);
                                Estado b = listaE.get(pos[i]);
                                Transicion t = new Transicion(a, "lambda", b);
                                listaL.add(t);
                            }
                        } else {
                            return listaL;
                        }
                    } else {
                        throw new Exception("Transición lambda no válida.");
                    }
                } else {
                    throw new Exception("Transición lambda no válida.");
                }
            } else {
                throw new Exception("Transición lambda no válida.");
            }
        }
        return listaL;
    }

    /**
     * Lee un autómata desde un archivo ".sm" elegido por el usuario.
     *
     * @param filename Dirección completa del archivo ".sm".
     * @return Autómata descrito en el archivo.
     * @throws Exception si el archivo no tiene la estructura correcta.
     */
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
            //marcar estado inicial
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
            //marcar estados finales
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
                            //mismo origen y símbolo, múltiples destinos
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
                        //mismo origen, múltiples destinos
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
