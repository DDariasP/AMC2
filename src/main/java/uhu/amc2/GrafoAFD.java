package uhu.amc2;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.*;

/**
 * Convierte un AFD en un grafo dirigido que puede visualizarse mediante la
 * librería JUNG.
 *
 * @author diego
 */
public class GrafoAFD {

    /**
     * Crea el grafo dirigido.
     *
     * @param a Autómata que es leído.
     * @return Grafo dirigido resultante.
     */
    public static VisualizationViewer<String, String> crear(IProceso a) {
        //typecast a AFD
        AFD automata = (AFD) a;

        //crear un grafo dirigido vacío
        DirectedSparseGraph<String, String> grafo = new DirectedSparseGraph<>();

        //añadir los vértices
        grafo.addVertex("inicio");
        for (int i = 0; i < automata.listaE.size(); i++) {
            Estado e = automata.listaE.get(i);
            grafo.addVertex(e.nombre);
        }

        //añadir las aristas
        grafo.addEdge("", "inicio", automata.inicial.nombre);
        //todas las transiciones
        for (int i = 0; i < automata.listaT.size(); i++) {
            Transicion ti = automata.listaT.get(i);
            String simbolo = ti.simbolo;
            //combina las que comparten origen y destino 
            for (int j = i + 1; j < automata.listaT.size(); j++) {
                Transicion tj = automata.listaT.get(j);
                if (ti.origen.equals(tj.origen) && ti.destino.equals(tj.destino)) {
                    simbolo = simbolo + "/" + tj.simbolo;
                }
            }
            String nombre = "{" + ti.origen.nombre + "," + simbolo + "," + ti.destino.nombre + "}";
            grafo.addEdge(nombre, ti.origen.nombre, ti.destino.nombre, EdgeType.DIRECTED);
        }

        //crear los visualizadores
        VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(grafo));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        //características de los vértices
        //posición
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        //forma y tamaño
        vv.getRenderContext().setVertexShapeTransformer(vertex -> {
            if (automata.esFinal(vertex)) {
                return new java.awt.geom.Rectangle2D.Double(-10, -10, 40, 40);
            } else {
                return new java.awt.geom.Ellipse2D.Double(-10, -10, 40, 40);
            }
        });
        //color
        vv.getRenderContext().setVertexFillPaintTransformer(vertex -> {
            if (vertex.equals("inicio")) {
                return Color.WHITE; //estado inicial en blanco
            } else if (automata.verde.nombre.equals(vertex)) {
                return Color.GREEN; //estado actual en verde
            } else {
                return Color.CYAN; //estados en cyan
            }
        });

        //devolver el grafo
        return vv;
    }

}
