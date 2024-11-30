import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * The GamePanel class ensures functionality och visualisation of the game.
 *
 * @author Lukas Mannerstål
 * @version 2024-11-30
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * Constants
     */
    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 900;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    /**
     * Snake parts
     */
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    /**
     * Instances
     */
    Timer timer;
    Random random;
    GameState state;

    /**
     * Colors
     */
    static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
    static final Color HEAD_COLOR = new Color(239, 170, 33);
    static final Color BODY_COLOR = new Color(87, 45, 106);
    static final Color APPLE_COLOR = new Color(145, 39, 53);

    GamePanel() {
        random = new Random();
        state = new GameState();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        resetSnake();
        newApples(5);
        state.setRunning(true);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (state.getRunning()) {
            g.setColor(APPLE_COLOR);
            for (int[] apple : state.getApples().values()) {
                g.fillRect(apple[0], apple[1], UNIT_SIZE, UNIT_SIZE);
            }

            for (int i = 0; i < state.getBodyParts(); i++) {
                if (i == 0) {
                    g.setColor(HEAD_COLOR);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(BODY_COLOR);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else {
            gameOver();
        }
    }

    public void resetSnake() {
        for (int i = 0; i < state.getBodyParts(); i++) {
            x[i] = 0;
            y[i] = 0;
        }
    }

    public void newApples(int count) {
        state.getApples().clear();
        for (int i = 0; i < count; i++) {
            int appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            int appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            state.addApple(i, appleX, appleY);
        }
    }

    public void move() {
        for (int i = state.getBodyParts(); i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (state.getDirection()) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        ArrayList<Integer> applesToRemove = new ArrayList<>();

        for (Map.Entry<Integer, int[]> entry : state.getApples().entrySet()) {
            int id = entry.getKey();
            int[] apple = entry.getValue();

            if (x[0] == apple[0] && y[0] == apple[1]) {
                state.setBodyParts(state.getBodyParts() + 1);
                state.setApplesEaten(state.getApplesEaten() + 1);
                applesToRemove.add(id);
            }
        }

        for (int id : applesToRemove) {
            state.removeApple(id);
            int newAppleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            int newAppleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            state.addApple(id, newAppleX, newAppleY);
        }
    }

    public void checkCollisions() {
        for (int i = state.getBodyParts(); i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                state.setRunning(false);
            }
        }

        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            state.setRunning(false);
        }

        if (!state.getRunning()) {
            timer.stop();
        }
    }

    public void gameOver() {
        state = new GameState();
        timer.stop();
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (state.getRunning()) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (state.getDirection() != 'R')
                        state.setDirection('L');
                    break;
                case KeyEvent.VK_RIGHT:
                    if (state.getDirection() != 'L')
                        state.setDirection('R');
                    break;
                case KeyEvent.VK_UP:
                    if (state.getDirection() != 'D')
                        state.setDirection('U');
                    break;
                case KeyEvent.VK_DOWN:
                    if (state.getDirection() != 'U')
                        state.setDirection('D');
                    break;
                default:
                    break;
            }
        }
    }
}
