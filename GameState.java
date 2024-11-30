/**
 * The GameState class stores values for the current state of the game.
 *
 * @author Lukas Mannerstål
 * @version 2024-11-30
 */
public class GameState {

    private int bodyParts;
    private int applesEaten;
    private int appleX;
    private int appleY;
    char direction;
    boolean running;

    GameState() {
        this.bodyParts = 6;
        this.direction = 'R';
        this.running = false;
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

    public int getAppleX() {
        return appleX;
    }

    public void setAppleX(int appleX) {
        this.appleX = appleX;
    }

    public int getAppleY() {
        return appleY;
    }

    public void setAppleY(int appleY) {
        this.appleY = appleY;
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
