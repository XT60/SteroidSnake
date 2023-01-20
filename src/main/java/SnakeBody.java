import javax.lang.model.element.ModuleElement;

public class SnakeBody {
    private Vector2d position;
    public Direction direction;
    private int offCenterWidth = 0;
    public SnakeBody next = null;
    public SnakeBody prev = null;

    SnakeBody(Vector2d position, SnakeBody prev, Direction direction){
        this.position = new Vector2d(position);
        this.prev = prev;
        this.direction = direction;
    }

    public void widen(){
        offCenterWidth += 1;
    }

    public void updateMovementState(Direction newDirection, Vector2d newPosition){
        direction = newDirection;
        this.position = new Vector2d(newPosition);
    }

    public Rect getRect(){
        return new Rect(position.x - offCenterWidth, position.y, offCenterWidth * 2 + 1, 1);
    }

    public Vector2d getPosition() {
        return new Vector2d(this.position);
    }
}
