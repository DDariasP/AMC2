package uhu.amc2;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Busqueda {

    public static final int NUM = 4;
    public static final int EXH = 0;
    public static final int PODA = 1;
    public static final int DYV = 2;
    public static final int DYVP = 3;
    public final ArrayList<Punto[]> array = new ArrayList<>();
    public final int numcal[] = new int[NUM];
    public final double t[] = new double[NUM];
    public final Distancia min[] = new Distancia[NUM];

    public Busqueda(Punto p[]) {
        for (int i = 0; i < NUM; i++) {
            array.add(Punto.copiar(p));
        }
        for (int i = 0; i < NUM; i++) {
            numcal[i] = 0;
        }
    }

    public Distancia exhaustiva() {
        double t1 = System.nanoTime();
        Punto p[] = array.get(EXH);
        Distancia dmin = new Distancia(Double.MAX_VALUE, p[0], p[0]);
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
                Distancia d = new Distancia(p[i], p[j]);
                numcal[EXH]++;
                if (dmin.valor > d.valor) {
                    dmin = d;
                }
            }
        }
        double t2 = System.nanoTime();
        t[EXH] = (double) (t2 - t1);
        min[EXH] = dmin;
        return dmin;
    }

    private Distancia exhaustivaDYV(Punto p[], int a, int b) {
        Distancia dmin = new Distancia(Double.MAX_VALUE, p[a], p[a]);
        for (int i = a; i <= b; i++) {
            for (int j = i + 1; j <= b; j++) {
                Distancia d = new Distancia(p[i], p[j]);
                numcal[DYV]++;
                if (dmin.valor > d.valor) {
                    dmin = d;
                }
            }
        }
        return dmin;
    }

    private Distancia exhaustivaDYVP(Punto p[], int a, int b) {
        Distancia dmin = new Distancia(Double.MAX_VALUE, p[a], p[a]);
        numcal[DYVP]++;
        for (int i = a; i <= b; i++) {
            for (int j = i + 1; j <= b; j++) {
                Distancia d = new Distancia(p[i], p[j]);
                numcal[DYVP]++;
                if (dmin.valor > d.valor) {
                    dmin = d;
                }
            }
        }
        return dmin;
    }

    private Distancia exhaustiva12(Punto[] p) {
        Distancia dmin = new Distancia(Double.MAX_VALUE, p[0], p[0]);
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length && j < i + 12; j++) {
                Distancia d = new Distancia(p[i], p[j]);
                numcal[DYVP]++;
                if (dmin.valor > d.valor) {
                    dmin = d;
                }
            }
        }
        return dmin;
    }

    public Distancia poda() {
        double t1 = System.nanoTime();
        Punto p[] = array.get(PODA);
        Punto.quicksort(p, 'x');
        Distancia dmin = new Distancia(Double.MAX_VALUE, p[0], p[0]);
        for (int i = 1; i < p.length; i++) {
            int j = i + 1;
            while ((j < p.length) && (p[j].x - p[i].x < dmin.valor)) {
                Distancia d = new Distancia(p[i], p[j]);
                numcal[PODA]++;
                if (dmin.valor > d.valor) {
                    dmin = d;
                }
                j++;
            }
        }
        double t2 = System.nanoTime();
        t[PODA] = (double) (t2 - t1);
        min[PODA] = dmin;
        return dmin;
    }

    public Distancia dyv() {
        double t1 = System.nanoTime();
        Punto p[] = array.get(DYV);
        Punto.quicksort(p, 'x');
        Distancia dmin = dyv(p, 0, p.length - 1);
        double t2 = System.nanoTime();
        t[DYV] = (double) (t2 - t1);
        min[DYV] = dmin;
        return dmin;
    }

    private Distancia dyv(Punto[] p, int i, int d) {
        if (d - i + 1 > 3) {
            int m = (i + d) / 2;
            Distancia di = dyv(p, i, m);
            Distancia dd = dyv(p, m + 1, d);
            Distancia dmin;
            if (dd.valor > di.valor) {
                dmin = di;
            } else {
                dmin = dd;
            }
            //sector izquierdo: p[i] -> p[m]
            int a = m;
            while (a >= i && dmin.valor > p[m + 1].x - p[a].x) {
                a--;
            }
            //franja izquierda: p[a+1] -> p[m]
            //sector derecho: p[m+1] -> p[dmin]
            int b = m + 1;
            while (b <= d && dmin.valor > p[b].x - p[m].x) {
                b++;
            }
            //franja derecha: p[m+1] -> p[b-1]
            int tam = (b - 1) - (a + 1) + 1;
            if (tam > 1) {
                Distancia df = exhaustivaDYV(p, a + 1, b - 1);
                if (dmin.valor > df.valor) {
                    dmin = df;
                }
            }
            return dmin;
        } else {
            Distancia dmin = exhaustivaDYV(p, i, d);
            return dmin;
        }
    }

    public Distancia dyvplus() {
        double t1 = System.nanoTime();
        Punto p[] = array.get(DYVP);
        Punto.quicksort(p, 'x');
        Distancia dmin = dyvplus(p, 0, p.length - 1);
        double t2 = System.nanoTime();
        t[DYVP] = (double) (t2 - t1);
        min[DYVP] = dmin;
        return dmin;
    }

    private Distancia dyvplus(Punto p[], int i, int d) {
        if (d - i + 1 > 3) {
            int m = (i + d) / 2;
            Distancia di = dyvplus(p, i, m);
            Distancia dd = dyvplus(p, m + 1, d);
            Distancia dmin;
            if (dd.valor > di.valor) {
                dmin = di;
            } else {
                dmin = dd;
            }
            //sector izquierdo: p[i] -> p[m]
            int a = m;
            while (a >= i && dmin.valor > p[m + 1].x - p[a].x) {
                a--;
            }
            //franja izquierda: p[a+1] -> p[m]
            //sector derecho: p[m+1] -> p[dmin]
            int b = m + 1;
            while (b <= d && dmin.valor > p[b].x - p[m].x) {
                b++;
            }
            //franja derecha: p[m+1] -> p[b-1]
            int tam = (b - 1) - (a + 1) + 1;
            if (tam > 1) {
                Punto franja[] = new Punto[tam];
                for (int j = 0; j < tam; j++) {
                    franja[j] = new Punto(p[a + 1 + j]);
                }
                Punto.quicksort(franja, 'y');
                Distancia df = exhaustiva12(franja);
                if (dmin.valor > df.valor) {
                    dmin = df;
                }
            }
            return dmin;
        } else {
            Distancia dmin = exhaustivaDYVP(p, i, d);
            return dmin;
        }
    }

}
