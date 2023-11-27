package uhu.amc2;

/**
 * Interfaz para representar un autómata genérico. Contiene los métodos
 * compartidos por los AFD y los AFND.
 *
 * @author diego
 */
public interface IProceso {

    /**
     * Indica si el estado pasado como parámetro es final o no.
     *
     * @param estado Nombre del estado que se quiere comprobar.
     * @return true si es final o false en caso contrario.
     */
    public boolean esFinal(String estado);

    /**
     * Comprueba la cadena pasada e indica si el autómata la reconoce o no.
     *
     * @param cadena String con la cadena a comprobar.
     * @return true en caso de reconocer la cadena o false en caso contrario.
     */
    public boolean reconocer(String cadena);

    /**
     * Devuelve una representación en formato String de los estados y las
     * transiciones del autómata.
     *
     * @return String con los datos del autómata.
     */
    public String toString();

    /**
     * Devuelve el tipo de autómata.
     *
     * @return int que codifica el tipo del autómata.
     */
    public int getTipo();
}
