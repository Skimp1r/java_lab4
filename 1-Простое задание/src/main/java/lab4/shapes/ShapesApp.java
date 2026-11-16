package lab4.shapes;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Простое задание: рисует круг, квадрат и треугольник на форме.
 */
public class ShapesApp extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setFont(new Font("SansSerif", Font.BOLD, 16));

        g2.setColor(Color.RED);
        g2.fillOval(50, 80, 120, 120);
        g2.setColor(Color.BLACK);
        g2.drawString("Круг", 85, 230);

        g2.setColor(Color.BLUE);
        g2.fillRect(220, 80, 120, 120);
        g2.setColor(Color.BLACK);
        g2.drawString("Квадрат", 245, 230);

        g2.setColor(Color.GREEN);
        int[] xPoints = {420, 490, 350};
        int[] yPoints = {80, 200, 200};
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.setColor(Color.BLACK);
        g2.drawString("Треугольник", 385, 230);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Простое задание: Фигуры");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(560, 320);
            frame.setLocationRelativeTo(null);
            frame.add(new ShapesApp());
            frame.setVisible(true);
        });
    }
}
