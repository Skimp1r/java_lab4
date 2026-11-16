package lab4.histogram;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Сложное задание: гистограмма по данным из массива (JFreeChart).
 */
public class HistogramApp extends JFrame {

    private static final int[] VALUES = {12, 7, 19, 5, 14, 9, 22};
    private static final String[] CATEGORIES = {
            "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"
    };

    public HistogramApp() {
        super("Сложное задание: Гистограмма");
        JFreeChart chart = ChartFactory.createBarChart(
                "Продажи за неделю",
                "День",
                "Количество",
                createDataset(VALUES, CATEGORIES),
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 500));
        setContentPane(panel);
    }

    private static DefaultCategoryDataset createDataset(int[] values, String[] categories) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int length = Math.min(values.length, categories.length);
        for (int i = 0; i < length; i++) {
            dataset.addValue(values[i], "Продажи", categories[i]);
        }
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistogramApp app = new HistogramApp();
            app.pack();
            app.setLocationRelativeTo(null);
            app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            app.setVisible(true);
        });
    }
}
