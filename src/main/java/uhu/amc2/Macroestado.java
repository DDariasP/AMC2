package uhu.amc2;

import java.util.ArrayList;

/**
 * Tipo de datos para manejar el conjunto de estados posibles tras cada
 * transición en un AFND.
 *
 * @author diego
 */
public class Macroestado {

    public ArrayList<Estado> lista;

    /**
     * Constructor sin parámetros para crear la lista vacía.
     *
     */
    public Macroestado() {
        lista = new ArrayList<>();
    }

    /**
     * Constructor de copia. Los estados solo se modifican durante la creación
     * del autómata, por lo que la copia puede compartir las referencias. La
     * lista va a añadir y eliminar estados, por lo que debe ser una nueva.
     *
     * @param me Macroestado original.
     */
    public Macroestado(Macroestado me) {
        lista = new ArrayList<>();
        for (int i = 0; i < me.size(); i++) {
            lista.add(me.getE(i));
        }
    }

    /**
     * Devuelve uno de los estados del macroestado.
     *
     * @param index Índice del estado a devolver.
     * @return Estado solicitado.
     */
    public Estado getE(int index) {
        return lista.get(index);
    }

    /**
     * Añade un estado al macroestado al final.
     *
     * @param e Estado a añadir.
     */
    public void addE(Estado e) {
        lista.add(e);
    }

    /**
     * Elimina un estado al macroestado.
     *
     * @param index Índice del estado a eliminar.
     */
    public void removeE(int index) {
        lista.remove(index);
    }

    /**
     * Devuelve el número de estados del macroestado sin necesitar una
     * referencia a la lista.
     *
     * @return Número de estados.
     */
    public int size() {
        return lista.size();
    }

    /**
     * Devuelve una representación en formato String del macroestado.
     *
     * @return String con los estados del macroestado.
     */
    @Override
    public String toString() {
        String s = "[ ";
        for (int i = 0; i < lista.size(); i++) {
            s = s + lista.get(i).nombre + " ";
        }
        s = s + "]";
        return s;
    }

}
