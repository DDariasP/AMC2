package uhu.amc2;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Estado {

    public final String nombre;
    public Boolean esInicial, esFinal;

    public Estado(String a) {
        nombre = a;
        esInicial = false;
        esFinal = false;
    }

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

    @Override
    public boolean equals(Object o) {
        //Si el objeto se compara consigo mismo, devuelve true  
        if (o == this) {
            return true;
        }

        //Hacer typecast del objeto a Estado y compararlo 
        Estado e = (Estado) o;
        return e.nombre.equals(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }

}
