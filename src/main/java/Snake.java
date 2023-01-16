import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private Direction direction;
    private GameConstants constants = new GameConstants();
    private ArrayList<SnakeBody> head = new ArrayList<SnakeBody>();
    private ArrayList<SnakeBody> body = new ArrayList<SnakeBody>();
    private ArrayList<SnakeBody> abdomen = new ArrayList<SnakeBody>();
    private int length = 1;
    private int width = 1;
    private double LD = 0.0;
    private boolean dead = false;

    Snake(){
        Random rand = new Random();
        direction = Direction.N;
        Vector2d initialPosition = new Vector2d(rand.nextInt() % constants.N, rand.nextInt() % constants.N);
        head.add(new SnakeBody(initialPosition));
    }

    private int velocity = 1;
    public void move(){}
    public void selfInject(){}
    public void reverse(){}
    public boolean isDead(){return dead;}
    public void deadLift(){}
    public void benchPress(){}
    public void applySteroid(){}

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getVelocity() {
        return velocity;
    }
}
