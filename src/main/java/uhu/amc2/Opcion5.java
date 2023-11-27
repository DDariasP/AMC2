package uhu.amc2;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Clase que muestra el autómata paso a paso.
 *
 * @author diego
 */
public class Opcion5 extends JFrame {

    public int i;
    public String cadena;
    public IProceso automata;
    public VisualizationViewer<String, String> grafo;
    public JFrame gFrame;

    /**
     * Constructor.
     *
     * @param a Autómata.
     * @param c String con la cadena de símbolos a comprobar.
     */
    public Opcion5(IProceso a, String c) {
        //inicializar los atributos
        i = -1;
        cadena = c;
        switch (a.getTipo()) {
            case 0:
                automata = new AFD(a);
                grafo = GrafoAFD.crear(automata);
                break;
            case 1:
                automata = new AFND(a);
                grafo = GrafoAFND.crear(automata);
                break;
        }

        //crear el JFrame y mostrar el grafo
        gFrame = new JFrame(Menu.fileName);
        gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gFrame.add(grafo);
        gFrame.setBounds(600, 50, 800, 800);
        gFrame.setVisible(true);
        initComponents();

        //actualizar la etiqueta cadena
        labelString.setText("Cadena: '" + c + "'");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonNext = new javax.swing.JButton();
        labelString = new javax.swing.JLabel();
        labelTran = new javax.swing.JLabel();
        labelChar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonNext.setText("Siguiente");
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        labelString.setText("Cadena:");

        labelTran.setText("Última transición:");

        labelChar.setText("Último carácter:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNext)
                    .addComponent(labelTran)
                    .addComponent(labelString)
                    .addComponent(labelChar))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(labelString)
                .addGap(18, 18, 18)
                .addComponent(labelTran)
                .addGap(18, 18, 18)
                .addComponent(labelChar)
                .addGap(34, 34, 34)
                .addComponent(buttonNext)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Avanza en el reconocimiento de la cadena cuando se pulsa el botón
     * "Siguiente".
     *
     * @param evt Acción sobre el botón.
     */
    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        //avanzar en la cadena
        i++;

        //si aún no ha terminado de analizar la cadena
        if (i < cadena.length()) {
            String s = String.valueOf(cadena.charAt(i));
            labelChar.setText("Último carácter: '" + s + "'");
            //actualizar el grafo
            boolean avanza = false; //true cuando encuentre una transición válida
            switch (automata.getTipo()) {
                case 0:
                    avanza = AFD.paso((AFD) automata, s);
                    labelTran.setText("Última transición: " + ((AFD) automata).ultima);
                    grafo = GrafoAFD.crear(automata);
                    break;
                case 1:
                    avanza = AFND.paso((AFND) automata, s);
                    labelTran.setText("Última transición: " + ((AFND) automata).ultimas);
                    grafo = GrafoAFND.crear(automata);
                    break;
            }
            //termina si no ha encontrado ninguna transición
            if (!avanza) {
                i = 100;
            }
            //muestra el grafo
            JPanel panel = new JPanel();
            panel.add(grafo);
            gFrame.add(grafo);
            gFrame.setVisible(false);
            gFrame.setVisible(true);

        } else { //si ha terminado el análisis
            //reconocer la cadena (true si ha llegado a algún estado final)
            boolean reconocida = false;
            String mensaje;
            switch (automata.getTipo()) {
                case 0:
                    reconocida = ((AFD) automata).verde.esFinal;
                    break;
                case 1:
                    reconocida = ((AFND) automata).esFinal(((AFND) automata).verde);
                    break;
            }
            if (reconocida) {
                mensaje = "Cadena '" + cadena + "' aceptada";
            } else {
                mensaje = "Cadena '" + cadena + "' rechazada";
            }
            //popup que informa sobre el reconocimiento de la cadena
            JOptionPane.showMessageDialog(this, mensaje, "Reconocer cadena", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_buttonNextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonNext;
    private javax.swing.JLabel labelChar;
    private javax.swing.JLabel labelString;
    private javax.swing.JLabel labelTran;
    // End of variables declaration//GEN-END:variables
}
