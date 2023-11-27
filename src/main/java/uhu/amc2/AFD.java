package uhu.amc2;

import java.util.ArrayList;

/**
 * Implementa un AFD y los métodos que definen su comportamiento.
 *
 * @author diego
 */
public class AFD implements IProceso {

    public final int tipo;
    public Estado inicial;
    public Estado verde;
    public Transicion ultima;
    public ArrayList<Estado> listaE;
    public ArrayList<Transicion> listaT;

    /**
     * Constructor con parámetros.
     *
     * @param ini Estado inicial.
     * @param a Lista de estados.
     * @param b Lista de transiciones.
     */
    public AFD(Estado ini, ArrayList<Estado> a, ArrayList<Transicion> b) {
        tipo = 0;
        inicial = ini;
        verde = ini;
        listaE = a;
        listaT = b;
    }

    /**
     * Constructor de copia. Los métodos que modifican listas lo hacen creando
     * listas nuevas y sustituyendo las referencias, por lo que la copia puede
     * compartir las referencias del original.
     *
     * @param a Autómata original.
     */
    public AFD(IProceso a) {
        AFD original = (AFD) a;
        tipo = 0;
        inicial = original.inicial;
        verde = original.inicial;
        listaE = original.listaE;
        listaT = original.listaT;
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
     * Comprueba la cadena pasada e indica si el autómata la reconoce o no.
     *
     * @param cadena String con la cadena a comprobar.
     * @return true en caso de reconocer la cadena o false en caso contrario.
     */
    @Override
    public boolean reconocer(String cadena) {
        //el primer estado es el inicial
        Estado actual = inicial;
        //analiza cada carácter de la cadena
        for (int i = 0; i < cadena.length(); i++) {
            //toma el siguiente carácter
            String s = String.valueOf(cadena.charAt(i));
            //comprueba las transiciones posibles
            int j = 0;
            boolean encontrado = false;
            while (j < listaT.size() && !encontrado) {
                Transicion t = listaT.get(j);
                //cuando el estado transiciona con el carácter que se analiza
                if (t.origen == actual && t.simbolo.equals(s)) {
                    //termina de comprobar las transiciones
                    encontrado = true;
                    //da el siguiente paso
                    actual = t.destino;
                }
                j++;
            }
            //si no hay transiciones posibles, rechaza la cadena
            if (!encontrado) {
                return false;
            }
        }
        //devuelve true si el estado es final
        return actual.esFinal;
    }

    /**
     * Devuelve una representación en formato String de los estados y las
     * transiciones del autómata.
     *
     * @return String con los datos del autómata.
     */
    @Override
    public String toString() {
        return ("AFD  = { estados:" + listaE.toString() + ", transiciones:" + listaT.toString() + " }");
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
     * @param a AFD donde se da el paso.
     * @param s String con el carácter a comprobar.
     * @return true en caso de reconocer el carácter o false en caso contrario.
     */
    public static boolean paso(AFD a, String s) {
        //analiza el estado actual
        Estado actual = a.verde;
        //el estado del siguiente paso comienza vacío
        Estado siguiente = new Estado("null");
        //comprueba las transiciones posibles
        int i = 0;
        boolean encontrado = false;
        while (i < a.listaT.size() && !encontrado) {
            Transicion t = a.listaT.get(i);
            //cuando el estado transiciona con el carácter que se analiza
            if (t.origen == actual && t.simbolo.equals(s)) {
                //termina de comprobar las transiciones
                encontrado = true;
                //guarda el estado siguiente
                siguiente = t.destino;
                //guarda la transición realizada
                a.ultima = t;
            }
            i++;
        }
        //prepara siguiente paso
        a.verde = siguiente;
        //devuelve false si no hay transición posible
        return encontrado;
    }

}
