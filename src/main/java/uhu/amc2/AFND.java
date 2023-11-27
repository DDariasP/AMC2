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
public class AFND implements IProceso {

    public final int tipo;
    public Macroestado iniciales;
    public Macroestado verde;
    public ArrayList<Transicion> ultimas;
    public ArrayList<Estado> listaE;
    public ArrayList<Transicion> listaT;
    public ArrayList<Transicion> listaL;

    public AFND(Estado ini, ArrayList<Estado> a, ArrayList<Transicion> b, ArrayList<Transicion> c) {
        tipo = 1;
        listaE = a;
        listaT = b;
        listaL = c;
        iniciales = clausuraLambda(ini);
        verde = clausuraLambda(ini);
    }

    public AFND(IProceso a) {
        AFND original = (AFND) a;
        tipo = 1;
        iniciales = original.iniciales;
        verde = original.iniciales;
        listaE = original.listaE;
        listaT = original.listaT;
        listaL = original.listaL;
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

    public Macroestado clausuraLambda(Estado e) {
        Macroestado nuevo = new Macroestado();
        nuevo.addE(e);
        ArrayList<Estado> comprobar = new ArrayList<>();
        comprobar.add(e);
        while (!comprobar.isEmpty()) {
            Estado tmp = comprobar.get(0);
            comprobar.remove(0);
            for (int i = 0; i < listaL.size(); i++) {
                Transicion t = listaL.get(i);
                if (t.origen == tmp) {
                    comprobar.add(t.destino);
                    nuevo.addE(t.destino);
                }
            }
        }
        return nuevo;
    }

    @Override
    public boolean reconocer(String cadena) {
        Macroestado comprobar = iniciales;
        System.out.println("iniciales:");
        for (int abc = 0; abc < comprobar.size(); abc++) {
            System.out.println(comprobar.getE(abc));
        }
        System.out.println("");
        int i = 0;
        while (i < cadena.length()) {
            String simbolo = String.valueOf(cadena.charAt(i));
            System.out.println("simbolo:" + simbolo);
            int j = 0;
            Macroestado siguiente = new Macroestado();
            while (j < comprobar.size()) {
                Estado etmp = comprobar.getE(j);
                for (int k = 0; k < listaT.size(); k++) {
                    Transicion ttmp = listaT.get(k);
                    if (ttmp.origen == etmp && ttmp.simbolo.equals(simbolo)) {
                        System.out.println("etmp:" + etmp);
                        System.out.println("destino:" + ttmp.destino);
                        Macroestado metmp = clausuraLambda(ttmp.destino);
                        for (int m = 0; m < metmp.size(); m++) {
                            siguiente.addE(metmp.getE(m));
                        }
                    }
                }
                j++;
            }
            System.out.println("siguiente:" + siguiente);
            comprobar = siguiente;
            System.out.println("");
            i++;
        }
        return esFinal(comprobar);
    }

    @Override
    public String toString() {
        return ("AFND = { estados:" + listaE.toString() + ", transiciones:" + listaT.toString() + listaL.toString() + " }");
    }

    @Override
    public int getTipo() {
        return tipo;
    }

    public static boolean paso(AFND a, String s) {
        Macroestado comprobar = a.verde;
        System.out.println("verde:");
        for (int abc = 0; abc < comprobar.size(); abc++) {
            System.out.println(comprobar.getE(abc));
        }
        System.out.println("");
        System.out.println("simbolo:" + s);
        int i = 0;
        Macroestado siguiente = new Macroestado();
        a.ultimas = new ArrayList<>();
        while (i < comprobar.size()) {
            Estado etmp = comprobar.getE(i);
            for (int j = 0; j < a.listaT.size(); j++) {
                Transicion ttmp = a.listaT.get(j);
                if (ttmp.origen == etmp && ttmp.simbolo.equals(s)) {
                    a.ultimas.add(ttmp);
                    System.out.println("etmp:" + etmp);
                    System.out.println("destino:" + ttmp.destino);
                    Macroestado metmp = a.clausuraLambda(ttmp.destino);
                    for (int m = 0; m < metmp.size(); m++) {
                        siguiente.addE(metmp.getE(m));
                    }
                }
            }
            i++;
        }
        System.out.println("siguiente:" + siguiente);
        a.verde = siguiente;
        System.out.println("");
        return (siguiente.size() > 0);
    }

}
