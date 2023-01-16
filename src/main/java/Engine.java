import java.util.HashMap;
import java.util.Objects;

public class Engine {
    private final GameConstants constants = new GameConstants();
    private final HashMap<Vector2d, Objects> map = new HashMap<Vector2d, Objects>();
    private final double highScore = loadHighScore();
    private final Snake snake = new Snake();

    Engine(){
//        snake = new Snake;
    }

    void run(){}

    double calculateScore(){
        return snake.getLength() * snake.getWidth() * snake.getVelocity();
    };

    private double loadHighScore(){
        return 0.0;
    }

    private void dumpHighScore(){

    }
}
