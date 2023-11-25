package uhu.amc2;

/**
 *
 * @author diego
 */
class Transicion {

    public final Estado origen;
    public final String simbolo;
    public final Estado destino;

    Transicion(Estado a, String b, Estado c) {
        origen = a;
        simbolo = b;
        destino = c;
    }

    @Override
    public String toString() {
        return ("(" + origen + "," + simbolo + "," + destino + ")");
    }

}
