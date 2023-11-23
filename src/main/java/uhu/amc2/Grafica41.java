package uhu.amc2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica41 extends JFrame {

    private final double[][] d;

    public Grafica41(double[][] datos, boolean peor) {
        d = datos;

        //crear la grafica
        XYPlot plot = new XYPlot();

        if (peor) {
            //crear dmin0
            XYDataset setDist0 = createDist(0, "Exh");
            //caracteristicas de dmin0
            XYItemRenderer renderer0 = new XYLineAndShapeRenderer(true, true);
            renderer0.setSeriesShape(0, new Rectangle2D.Double(-3.0, 0.0, 6.0, 6.0));
            renderer0.setSeriesPaint(0, Color.CYAN);
            renderer0.setSeriesStroke(0, new BasicStroke(2.0f));
            //añadir dmin0 a la grafica
            plot.setDataset(0, setDist0);
            plot.setRenderer(0, renderer0);
        }

        //crear dmin1
        XYDataset setDist1 = createDist(1, "Poda");
        //caracteristicas de dmin1
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(true, true);
        renderer1.setSeriesShape(0, new Rectangle2D.Double(-3.0, 0.0, 6.0, 6.0));
        renderer1.setSeriesPaint(0, Color.ORANGE);
        renderer1.setSeriesStroke(0, new BasicStroke(2.0f));
        //añadir dmin1 a la grafica
        plot.setDataset(1, setDist1);
        plot.setRenderer(1, renderer1);

        //crear dmin2
        XYDataset setDist2 = createDist(2, "DyV");
        //caracteristicas de dmin2
        XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, true);
        renderer2.setSeriesShape(0, new Rectangle2D.Double(-3.0, 0.0, 6.0, 6.0));
        renderer2.setSeriesPaint(0, Color.GREEN);
        renderer2.setSeriesStroke(0, new BasicStroke(2.0f));
        //añadir dmin2 a la grafica
        plot.setDataset(2, setDist2);
        plot.setRenderer(2, renderer2);

        //crear dmin3
        XYDataset setDist3 = createDist(3, "DyVP");
        //caracteristicas de dmin3
        XYItemRenderer renderer3 = new XYLineAndShapeRenderer(true, true);
        renderer3.setSeriesShape(0, new Rectangle2D.Double(-3.0, 0.0, 6.0, 6.0));
        renderer3.setSeriesPaint(0, Color.YELLOW);
        renderer3.setSeriesStroke(0, new BasicStroke(2.0f));
        //añadir dmin3 a la grafica
        plot.setDataset(3, setDist3);
        plot.setRenderer(3, renderer3);

        //crear y añadir los ejes
        ValueAxis domain = new NumberAxis("Talla");
        ValueAxis range = new NumberAxis("Tiempo(ms)");
        plot.setDomainAxis(0, domain);
        plot.setRangeAxis(0, range);

        //crear el area de trazado
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        //crear la ventana 
        ChartPanel panel = new ChartPanel(chart);
        panel.setDomainZoomable(true);
        panel.setRangeZoomable(true);
        setContentPane(panel);
    }

    private XYDataset createDist(int tipo, String nombre) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        //distancia minima
        XYSeries series = new XYSeries(nombre);
        for (int i = 0; i < 5; i++) {
            series.add(d[i][0], d[i][tipo + 1]);
        }
        dataset.addSeries(series);
        return dataset;
    }
}
