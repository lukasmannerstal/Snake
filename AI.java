import java.util.Random;

public class AI {

    Random random = new Random();

    public char predictedDirection() {
        char prediction = 'R';
        switch (random.nextInt(4)) {
            case 0:
                prediction = 'R';
                break;
            case 1:
                prediction = 'L';
                break;
            case 2:
                prediction = 'U';
                break;
            case 3:
                prediction = 'D';
                break;
        }
        return prediction;
    }
}
