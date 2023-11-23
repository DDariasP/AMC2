package uhu.amc2;

/**
 *
 * @author diego
 */
public class Distancia {

    public final double valor;
    public final Punto p1, p2;

    public Distancia(double a, Punto b, Punto c) {
        valor = a;
        p1 = new Punto(b);
        p2 = new Punto(c);
    }

    public Distancia(Distancia d) {
        valor = d.valor;
        p1 = new Punto(d.p1);
        p2 = new Punto(d.p2);
    }

    public Distancia(Punto a, Punto b) {
        valor = Punto.distancia(a, b);
        p1 = new Punto(b);
        p2 = new Punto(a);
    }

    @Override
    public String toString() {
        return (valor + " [" + p1 + ", " + p2 + "]");
    }

}
