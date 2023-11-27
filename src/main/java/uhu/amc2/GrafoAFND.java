package uhu.amc2;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.*;

/**
 *
 * @author diego
 */
public class GrafoAFND {

    public static VisualizationViewer<String, String> crear(IProceso a) {
        //typecast a AFND
        AFND automata = (AFND) a;

        //crear un grafo dirigido vacío
        DirectedSparseGraph<String, String> grafo = new DirectedSparseGraph<>();

        //añadir los vértices
        grafo.addVertex("inicio");
        for (int i = 0; i < automata.listaE.size(); i++) {
            Estado e = automata.listaE.get(i);
            grafo.addVertex(e.nombre);
        }

        //añadir las aristas
        grafo.addEdge("", "inicio", automata.iniciales.getE(0).nombre);
        for (int i = 0; i < automata.listaT.size(); i++) {
            Transicion te = automata.listaT.get(i);
            String nombre = "{" + te.origen.nombre + ",";
            int j = 0;
            boolean encontrado = false;
            while (!encontrado && j < automata.listaL.size()) {
                Transicion tl = automata.listaL.get(j);
                if (te.origen.equals(tl.origen) && te.destino.equals(tl.destino)) {
                    nombre = nombre + te.simbolo + "/lambda," + te.destino + "}";
                    encontrado = true;
                }
                j++;
            }
            if (!encontrado) {
                nombre = nombre + te.simbolo + "," + te.destino + "}";
            }
            System.out.println(nombre);
            grafo.addEdge(nombre, te.origen.nombre, te.destino.nombre, EdgeType.DIRECTED);
        }
        for (int i = 0; i < automata.listaL.size(); i++) {
            Transicion t = automata.listaL.get(i);
            String nombre = "{" + t.origen.nombre + "," + t.simbolo + "," + t.destino.nombre + "}";
            grafo.addEdge(nombre, t.origen.nombre, t.destino.nombre, EdgeType.DIRECTED);
        }

        //crear los visualizadores
        VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(grafo));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        //características de los vértices
        //posición
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        //forma y tamaño
        vv.getRenderContext().setVertexShapeTransformer(vertex -> new java.awt.geom.Ellipse2D.Double(-10, -10, 40, 40)); // Set vertex size here
        //color
        vv.getRenderContext().setVertexFillPaintTransformer(vertex -> {
            if (vertex.equals("inicio")) {
                return Color.WHITE; //estado inicial blanco
            } else if (Estado.pertenece(vertex, automata.verde.lista) != -1) {
                return Color.GREEN; //estados actuales verde
            } else if (automata.esFinal(vertex)) {
                return Color.MAGENTA; //estados finales magenta
            } else {
                return Color.CYAN; //estados no finales cyan
            }
        });

        //devolver el grafo
        return vv;
    }

}
