package uhu.amc2;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author diego
 */
public class Punto {

    public final int id;
    public final double x, y;

    public Punto(int a, double b, double c) {
        id = a;
        x = b;
        y = c;
    }

    public Punto(Punto p) {
        id = p.id;
        x = p.x;
        y = p.y;
    }

    public static double distancia(Punto p1, Punto p2) {
        double a = Math.pow(p2.x - p1.x, 2);
        double b = Math.pow(p2.y - p1.y, 2);
        double d = Math.sqrt(a + b);
        return d;
    }

    public static void quicksort(Punto p[], char tipo) {
        quicksort(p, 0, p.length - 1, tipo);
    }

    private static void quicksort(Punto p[], int e, int d, char tipo) {
        if (e < d) {
            int q = partition(p, e, d, tipo);
            quicksort(p, e, q, tipo);
            quicksort(p, q + 1, d, tipo);
        }
    }

    private static int partition(Punto p[], int e, int d, char tipo) {
        Punto elem = p[e];
        int i = e - 1;
        int j = d + 1;
        while (true) {
            if (tipo == 'x') {
                while (elem.x < p[--j].x);
                while (p[++i].x < elem.x);
            }
            if (tipo == 'y') {
                while (elem.y < p[--j].y);
                while (p[++i].y < elem.y);
            }
            if (i >= j) {
                return j;
            }
            swap(p, i, j);
        }
    }

    private static void swap(Punto p[], int i, int j) {
        Punto temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }

    public static void rellenar(Punto p[], int talla, boolean peor) {
        int num, den;
        double x, y, aux1;
        Random r = new Random(System.nanoTime());
        if (peor) {
            for (int i = 0; i < talla; i++) {
                aux1 = r.nextInt() % 1000 + 7;
                y = aux1 / ((double) i + 1 + i * 0.1);
                num = r.nextInt() % 3;
                y += ((i % 500) - num * (r.nextInt() % 100));
                x = 1;
                p[i] = new Punto(i, x, y);
            }
        } else {
            for (int i = 0; i < talla; i++) {
                num = r.nextInt() % 4000 + 1;
                den = r.nextInt() % 11 + 7;
                x = num / ((double) den + 0.37);
                y = (r.nextInt() % 4000 + 1) / ((double) (r.nextInt() % 11 + 7) + 0.37);
                p[i] = new Punto(i + 1, x, y);
            }
        }
    }

    public static Punto[] copiar(Punto[] p) {
        Punto copia[] = new Punto[p.length];
        for (int i = 0; i < p.length; i++) {
            copia[i] = new Punto(p[i]);
        }
        return copia;
    }

    @Override
    public String toString() {
        DecimalFormat p = new DecimalFormat("#.00");
        return (id + " (" + p.format(x) + ", " + p.format(y) + ")");
    }
}
