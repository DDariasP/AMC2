/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uhu.amc2;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class AFD implements IProceso {

    public final int tipo;
    public Estado inicial;
    public ArrayList<Estado> listaE;
    public ArrayList<Transicion> listaT;

    public AFD() {
        tipo = 0;
        listaE = new ArrayList<>();
        listaT = new ArrayList<>();
    }

    public AFD(Estado ini, ArrayList<Estado> a, ArrayList<Transicion> b) {
        tipo = 0;
        inicial = ini;
        listaE = a;
        listaT = b;
    }

    @Override
    public boolean esFinal(String estado) {
        int pos = Estado.pertenece(estado, listaE);
        if (pos != -1) {
            return listaE.get(pos).esFinal;
        } else {
            return false;
        }
    }

    @Override
    public boolean reconocer(String cadena) {
        Estado actual = inicial;
        int i = 0;
        while (i < cadena.length()) {
            String s = String.valueOf(cadena.charAt(i));
            int j = 0;
            boolean encontrado = false;
            while (j < listaT.size() && !encontrado) {
                Transicion t = listaT.get(j);
                if (t.origen == actual && t.simbolo.equals(s)) {
                    encontrado = true;
                    actual = t.destino;
                }
                j++;
            }
            if (!encontrado) {
                return false;
            } else {
                i++;
            }
        }
        return actual.esFinal;
    }

    @Override
    public String toString() {
        return ("AFD  = { estados:" + listaE.toString() + ", transiciones:" + listaT.toString() + " }");
    }

    @Override
    public int getTipo() {
        return tipo;
    }

}
