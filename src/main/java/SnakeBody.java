import javax.lang.model.element.ModuleElement;
import java.util.ArrayList;

public class SnakeBody {
    private Vector2d position;
    public Direction direction;
    private int offCenterWidth = 0;
    public SnakeBody next = null;
    public SnakeBody prev = null;

    SnakeBody(Vector2d position, SnakeBody prev, Direction direction, int offCenterWidth){
        this(position, prev, direction);
        this.offCenterWidth = offCenterWidth;
    }

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
        return switch (direction){
            case N, S -> new Rect(position.x - offCenterWidth, position.y, offCenterWidth * 2 + 1, 1);
            case E, W -> new Rect(position.x, position.y - offCenterWidth, 1, 1 + offCenterWidth * 2);
        };

    }

    public Vector2d getPosition() {
        return new Vector2d(this.position);
    }

    public int getWidth() {
        return offCenterWidth * 2 + 1;
    }

    public ArrayList<Vector2d> getCellPositions(){
        ArrayList<Vector2d> cellPositions = new ArrayList<Vector2d>();

        cellPositions.add(position);
        Vector2d leftModifier = direction.getPerpendicularUnitVector();
        Vector2d rightModifier = leftModifier.reverse();
        Vector2d left = Vector2d.add(position, leftModifier);
        Vector2d right = Vector2d.add(position, rightModifier);
        for(int i = 0; i < offCenterWidth; i++){
            cellPositions.add(left);
            cellPositions.add(right);
            left = Vector2d.add(left, leftModifier);
            right = Vector2d.add(right, rightModifier);
        }

        return cellPositions;
    }

    public int getOffCenterWidth() {
        return offCenterWidth;
    }
}
