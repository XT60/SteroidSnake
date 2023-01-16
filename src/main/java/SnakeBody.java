public class SnakeBody {
    public int x;
    public int y;
    public SnakeBody next;

    SnakeBody(Vector2d position){
        x = position.x;
        y = position.y;
    }
}
