package uhu.amc2;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author diego
 */
public class Opcion5 extends javax.swing.JFrame {

    public int i;
    public String cadena;
    public IProceso automata;
    public VisualizationViewer<String, String> grafo;
    public JFrame gFrame;

    /**
     * Creates new form Opcion5
     */
    public Opcion5(IProceso a, String c) {
        //inicializar variables
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
        JPanel panel = new JPanel();
        panel.add(grafo);
        gFrame = new JFrame(Menu.fileName);
        gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gFrame.add(grafo);
        gFrame.setBounds(600, 50, 800, 800);
        gFrame.setVisible(true);
        initComponents();
        labelString.setText("Cadena: " + c);
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
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNext)
                    .addComponent(labelTran)
                    .addComponent(labelString)
                    .addComponent(labelChar))
                .addContainerGap(226, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(labelString)
                .addGap(42, 42, 42)
                .addComponent(labelTran)
                .addGap(28, 28, 28)
                .addComponent(labelChar)
                .addGap(50, 50, 50)
                .addComponent(buttonNext)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        //avanzar en la cadena
        i++;
        if (i < cadena.length()) {
            String s = String.valueOf(cadena.charAt(i));
            labelChar.setText("Último carácter: " + s);
            //actualizar el grafo
            switch (automata.getTipo()) {
                case 0:
                    AFD.paso((AFD) automata, s);
                    System.out.println("verde: " + ((AFD) automata).verde.toString());
                    grafo = GrafoAFD.crear(automata);
                    break;
                case 1:
                    AFND.paso((AFND) automata, s);
                    System.out.println("verde: " + ((AFND) automata).verde.toString());
                    grafo = GrafoAFND.crear(automata);
                    break;
            }
            JPanel panel = new JPanel();
            panel.add(grafo);
            gFrame.add(grafo);
            gFrame.setVisible(false);
            gFrame.setVisible(true);
            //actualizar la etiqueta
            labelTran.setText("Última transición: " + i);
        } else {
            //reconocer la cadena
            boolean reconocida = false;
            String mensaje;
            switch (automata.getTipo()) {
                case 0:
                    reconocida = ((AFD) automata).esFinal(((AFD) automata).verde.nombre);
                    break;
                case 1:
                    reconocida = ((AFND) automata).esFinal(((AFND) automata).verde);
                    break;
            }
            if (reconocida) {
                mensaje = "Cadena " + cadena + " aceptada";
            } else {
                mensaje = "Cadena " + cadena + " rechazada";
            }
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
