package uhu.amc2;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.function.Function;

public class JungDirectedGraphWithEdgeLabels {

    public static void main(String[] args) {
        // Create a directed graph
        DirectedSparseGraph<String, String> graph = new DirectedSparseGraph<>();

        // Add vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        
        Collection<String> v = graph.getVertices(); 
        System.out.println(v);
        

        // Add directed edges with labels
        graph.addEdge("Edge1", "A", "B", EdgeType.DIRECTED);
        graph.addEdge("Edge2", "B", "C", EdgeType.DIRECTED);
        graph.addEdge("Edge3", "C", "A", EdgeType.DIRECTED);
        graph.addEdge("Edge4", "A", "C", EdgeType.DIRECTED);

        // Create a visualization viewer with a layout
        VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(graph));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        // Set vertex paint transformer (color)
        vv.getRenderContext().setVertexFillPaintTransformer(vertex -> {
            if (vv.getPickedVertexState().isPicked(vertex)) {
                return Color.YELLOW; // color for picked vertices
            } else {
                return Color.GREEN; // color for non-picked vertices
            }
        });
        
        // Set up the vertex shape and paint transformer using Java 8 Function
        Function<String, Paint> vertexPaint = vertex -> {
            // Customize the vertex color based on your logic
            if (vertex.equals("A")) {
                return Color.RED;
            } else if (vertex.equals("B")) {
                return Color.BLUE;
            } else {
                return Color.GREEN;
            }
        };

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint::apply);

        // Create a JFrame to display the graph
        JFrame frame = new JFrame("Jung Directed Graph with Edge Labels");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
