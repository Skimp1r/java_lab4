package lab4.snake;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * Специальное задание: игра «Змейка».
 * Управление: стрелки. Рестарт: пробел.
 */
public class SnakeApp extends JPanel implements ActionListener {

    private static final int TILE = 24;
    private static final int COLS = 24;
    private static final int ROWS = 20;
    private static final int DELAY_MS = 120;

    private final Deque<Point> snake = new ArrayDeque<>();
    private final Random random = new Random();
    private final Timer timer;

    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;
    private Point food;
    private int score;
    private boolean running;
    private boolean gameOver;

    public SnakeApp() {
        setPreferredSize(new Dimension(COLS * TILE, ROWS * TILE));
        setBackground(new Color(24, 24, 32));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        });
        timer = new Timer(DELAY_MS, this);
        startGame();
    }

    private void startGame() {
        snake.clear();
        snake.addFirst(new Point(5, 10));
        snake.addLast(new Point(4, 10));
        snake.addLast(new Point(3, 10));
        direction = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
        score = 0;
        running = true;
        gameOver = false;
        spawnFood();
        timer.start();
        requestFocusInWindow();
        repaint();
    }

    private void handleKey(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE && gameOver) {
            startGame();
            return;
        }
        Direction requested = switch (keyCode) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> Direction.UP;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> Direction.DOWN;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> Direction.LEFT;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> Direction.RIGHT;
            default -> null;
        };
        if (requested != null && !requested.isOpposite(direction)) {
            nextDirection = requested;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) {
            return;
        }
        direction = nextDirection;
        Point head = snake.peekFirst();
        Point next = new Point(head.x + direction.dx, head.y + direction.dy);

        if (next.x < 0 || next.x >= COLS || next.y < 0 || next.y >= ROWS || occupies(next)) {
            running = false;
            gameOver = true;
            timer.stop();
            repaint();
            return;
        }

        snake.addFirst(next);
        if (next.equals(food)) {
            score += 10;
            spawnFood();
        } else {
            snake.removeLast();
        }
        repaint();
    }

    private boolean occupies(Point point) {
        for (Point part : snake) {
            if (part.equals(point)) {
                return true;
            }
        }
        return false;
    }

    private void spawnFood() {
        do {
            food = new Point(random.nextInt(COLS), random.nextInt(ROWS));
        } while (occupies(food));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(220, 70, 70));
        g2.fillOval(food.x * TILE + 4, food.y * TILE + 4, TILE - 8, TILE - 8);

        boolean head = true;
        for (Point part : snake) {
            g2.setColor(head ? new Color(90, 210, 110) : new Color(50, 160, 80));
            g2.fillRoundRect(part.x * TILE + 2, part.y * TILE + 2, TILE - 4, TILE - 4, 8, 8);
            head = false;
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Счёт: " + score, 12, 22);

        if (gameOver) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 28));
            g2.drawString("Игра окончена", getWidth() / 2 - 110, getHeight() / 2 - 10);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
            g2.drawString("Пробел — начать заново", getWidth() / 2 - 100, getHeight() / 2 + 20);
        }
    }

    private enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        final int dx;
        final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        boolean isOpposite(Direction other) {
            return dx + other.dx == 0 && dy + other.dy == 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Специальное задание: Змейка");
            SnakeApp game = new SnakeApp();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(game);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            game.requestFocusInWindow();
        });
    }
}
