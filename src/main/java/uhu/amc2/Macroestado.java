package uhu.amc2;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Macroestado {

    public ArrayList<Estado> lista;

    public Macroestado() {
        lista = new ArrayList<>();
    }

    public Macroestado(Macroestado me) {
        lista = new ArrayList<>();
        for (int i = 0; i < me.size(); i++) {
            lista.add(me.getE(i));
        }
    }

    public Estado getE(int index) {
        return lista.get(index);
    }

    public void addE(Estado e) {
        lista.add(e);
    }

    public void removeE(int index) {
        lista.remove(index);
    }

    public int size() {
        return lista.size();
    }

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
