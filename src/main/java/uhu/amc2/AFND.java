package uhu.amc2;

import java.util.ArrayList;

/**
 * Implementa un AFND y los métodos que definen su comportamiento.
 *
 * @author diego
 */
public class AFND implements IProceso {

    public final int tipo;
    public Macroestado iniciales;
    public Macroestado verde;
    public ArrayList<Transicion> ultimas;
    public ArrayList<Estado> listaE;
    public ArrayList<Transicion> listaT;
    public ArrayList<Transicion> listaL;

    /**
     * Constructor con parámetros.
     *
     * @param ini Estado inicial.
     * @param a Lista de estados.
     * @param b Lista de transiciones.
     * @param c Lista de transiciones lambda.
     */
    public AFND(Estado ini, ArrayList<Estado> a, ArrayList<Transicion> b, ArrayList<Transicion> c) {
        tipo = 1;
        listaE = a;
        listaT = b;
        listaL = c;
        iniciales = clausuraLambda(ini);
        verde = clausuraLambda(ini);
    }

    /**
     * Constructor de copia. Los métodos que modifican listas lo hacen creando
     * listas nuevas y sustituyendo las referencias, por lo que la copia puede
     * compartir las referencias del original.
     *
     * @param a Autómata original.
     */
    public AFND(IProceso a) {
        AFND original = (AFND) a;
        tipo = 1;
        iniciales = original.iniciales;
        verde = original.iniciales;
        listaE = original.listaE;
        listaT = original.listaT;
        listaL = original.listaL;
    }

    /**
     * Comprueba si un estado es final.
     *
     * @param estado Nombre del estado a comprobar.
     * @return true si es final o false en caso contrario.
     */
    @Override
    public boolean esFinal(String estado) {
        int pos = Estado.pertenece(estado, listaE);
        if (pos != -1) {
            return listaE.get(pos).esFinal;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si un macroestado es final.
     *
     * @param macro Macroestado a pendientes.
     * @return true si alguno de sus estados es final o false en caso contrario.
     */
    public boolean esFinal(Macroestado macro) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < macro.size()) {
            int pos = Estado.pertenece(macro.getE(i).nombre, listaE);
            if (pos != -1) {
                encontrado = listaE.get(pos).esFinal;
            }
            i++;
        }
        return encontrado;
    }

    /**
     * Calcula la clausura lambda de un estado, es decir, la lista de estados a
     * los que se puede llegar desde el recibido, mediante transiciones lambda,
     * incluyendo el estado recibido.
     *
     * @param e Estado para calcular la clausura lambda.
     * @return Macroestado resultante.
     */
    public Macroestado clausuraLambda(Estado e) {
        //crea un nuevo macroestado
        Macroestado nuevo = new Macroestado();
        //añade el estado recibido al macroestado
        nuevo.addE(e);
        //crea una lista de estados pendientes por comprobar
        ArrayList<Estado> pendientes = new ArrayList<>();
        //añade el estado recibido a la lista de pendientes
        pendientes.add(e);
        //mientras queden estados pendientes
        while (!pendientes.isEmpty()) {
            //toma el primer estado de pendientes
            Estado tmp = pendientes.get(0);
            //lo elimina de pendientes
            pendientes.remove(0);
            //busca en la lista de transiciones lambda
            for (int i = 0; i < listaL.size(); i++) {
                Transicion t = listaL.get(i);
                //aquellas con origen igual al estado tomado
                if (t.origen == tmp) {
                    //se añade el destino a la lista de pendientes
                    pendientes.add(t.destino);
                    //se añade el destino a la clausura lambda
                    nuevo.addE(t.destino);
                }
            }
        }
        //devuelve el macroestado resultante, que puede estar vacío
        return nuevo;
    }

    /**
     * Comprueba la cadena pasada e indica si el autómata la reconoce o no.
     *
     * @param cadena String con la cadena a comprobar.
     * @return true en caso de reconocer la cadena o false en caso contrario.
     */
    @Override
    public boolean reconocer(String cadena) {
        //el primer macroestado es la clausura lambda del inicial
        Macroestado actual = iniciales;
        //analiza cada carácter de la cadena
        for (int i = 0; i < cadena.length(); i++) {
            //toma el siguiente carácter
            String simbolo = String.valueOf(cadena.charAt(i));
            //el macroestado del siguiente paso comienza vacío
            Macroestado siguiente = new Macroestado();
            //comprueba cada estado del macroestado actual
            for (int j = 0; j < actual.size(); j++) {
                Estado etmp = actual.getE(j);
                //comprueba todas las transiciones posibles
                for (int k = 0; k < listaT.size(); k++) {
                    Transicion ttmp = listaT.get(k);
                    //cuando el estado transiciona con el carácter que se analiza
                    if (ttmp.origen == etmp && ttmp.simbolo.equals(simbolo)) {
                        //hace la clausura lambda de su destino
                        Macroestado metmp = clausuraLambda(ttmp.destino);
                        //añade los estados de la clausura lambda al siguiente macroestado
                        for (int m = 0; m < metmp.size(); m++) {
                            siguiente.addE(metmp.getE(m));
                        }
                    }
                }
            }
            //si no hay transiciones posibles, rechaza la cadena
            if (siguiente.size() == 0) {
                return false;
            }
            //da el siguiente paso
            actual = siguiente;
        }
        //devuelve true si algún estado es final
        return esFinal(actual);
    }

    /**
     * Devuelve una representación en formato String de los estados y las
     * transiciones del autómata.
     *
     * @return String con los datos del autómata.
     */
    @Override
    public String toString() {
        return ("AFND = { estados:" + listaE.toString() + ", transiciones:" + listaT.toString() + listaL.toString() + " }");
    }

    /**
     * Devuelve el tipo de autómata.
     *
     * @return int que codifica el tipo del autómata.
     */
    @Override
    public int getTipo() {
        return tipo;
    }

    /**
     * Comprueba paso a paso si el autómata reconoce o no la cadena.
     *
     * @param a AFND donde se da el paso.
     * @param s String con el carácter a comprobar.
     * @return true en caso de reconocer el carácter o false en caso contrario.
     */
    public static boolean paso(AFND a, String s) {
        //analiza el macroestado actual
        Macroestado pendientes = a.verde;
        //el macroestado del siguiente paso comienza vacío
        Macroestado siguiente = new Macroestado();
        //guarda las transiciones que hace para mostrarlas
        a.ultimas = new ArrayList<>();
        //comprueba cada estado del macroestado actual
        for (int i = 0; i < pendientes.size(); i++) {
            Estado etmp = pendientes.getE(i);
            //comprueba todas las transiciones posibles
            for (int j = 0; j < a.listaT.size(); j++) {
                Transicion ttmp = a.listaT.get(j);
                //cuando el estado transiciona con el carácter que se analiza
                if (ttmp.origen == etmp && ttmp.simbolo.equals(s)) {
                    //guarda la transición realizada
                    a.ultimas.add(ttmp);
                    //hace la clausura lambda de su destino
                    Macroestado metmp = a.clausuraLambda(ttmp.destino);
                    //añade los estados de la clausura lambda al siguiente macroestado
                    for (int m = 0; m < metmp.size(); m++) {
                        siguiente.addE(metmp.getE(m));
                    }
                }
            }
        }
        //prepara siguiente paso
        a.verde = siguiente;
        //devuelve false si no hay transiciones posibles
        return (siguiente.size() > 0);
    }

}
