import java.util.HashMap;
import java.util.Map;

/**
 * The GameState class stores values for the current state of the game.
 *
 * @author Lukas Mannerstål
 * @version 2024-11-30
 */
public class GameState {

    private int bodyParts;
    private int applesEaten;
    private Map<Integer, int[]> apples;
    char direction;
    boolean running;

    GameState() {
        this.bodyParts = 6;
        this.direction = 'R';
        this.running = false;
        this.apples = new HashMap<>();
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public void setApplesEaten(int applesEaten) {
        this.applesEaten = applesEaten;
    }

    public Map<Integer, int[]> getApples() {
        return apples;
    }

    public void addApple(int id, int x, int y) {
        apples.put(id, new int[]{x, y});
    }

    public void removeApple(int id) {
        apples.remove(id);
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean getRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
