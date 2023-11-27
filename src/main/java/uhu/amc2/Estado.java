package uhu.amc2;

import java.util.ArrayList;

/**
 * Tipo de datos para manejar los estados de cualquier autómata.
 *
 * @author diego
 */
public class Estado {

    public final String nombre;
    public Boolean esInicial, esFinal;

    /**
     * Constructor.
     *
     * @param a Nombre del estado.
     */
    public Estado(String a) {
        nombre = a;
        esInicial = false;
        esFinal = false;
    }

    /**
     * Comprueba si un estado pertenece a una lista de estados.
     *
     * @param elem Nombre del estado a comprobar.
     * @param lista Lista sobre la que hacer las comprobaciones.
     * @return Índice del estado en la lista si pertenece o -1 en caso
     * contrario.
     */
    public static int pertenece(String elem, ArrayList<Estado> lista) {
        boolean encontrado = false;
        int longitud = lista.size();
        int pos = -1;
        int i = 0;
        while (!encontrado && i < longitud) {
            String n = lista.get(i).nombre;
            if (n.equals(elem)) {
                encontrado = true;
                pos = i;
            }
            i++;
        }
        return pos;
    }

    /**
     * Comprueba si dos estados son iguales.
     *
     * @param o Estado a comparar.
     * @return true si sus nombres son iguales o false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        //si el objeto se compara consigo mismo, devuelve true  
        if (o == this) {
            return true;
        }
        //hace typecast del objeto a Estado y compara sus nombres 
        Estado e = (Estado) o;
        return e.nombre.equals(nombre);
    }

    /**
     * Devuelve una representación en formato String del estado.
     *
     * @return String con el nombre del estado.
     */
    @Override
    public String toString() {
        return nombre;
    }

}
