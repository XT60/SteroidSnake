import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;

public class Snake {
    private Direction direction;
    private SnakeBody head = null;
    private SnakeBody tail = null;
    private int length = 1;
    private double LD = 0.0;
    private boolean dead = false;


    Snake(){
        Random rand = new Random();
        direction = Direction.N;
        Vector2d initialPosition = new Vector2d(Math.abs(rand.nextInt()) % GameConstants.N,
                Math.abs(rand.nextInt()) % GameConstants.N);
        head = tail =  new SnakeBody(initialPosition, null, Direction.N);
    }

    private int velocity = 1;

    public void updateVariables(){
        move();
        updateSteroidsLevel();
    }

    public void move(){
        Direction newDirection = direction;
        Vector2d newPosition = head.getPosition().applyDirection(direction);
        if (!(0 <= newPosition.x && newPosition.x < GameConstants.N &&
                0 <= newPosition.y && newPosition.y < GameConstants.N)){
            reverse();
            return;
        }
        SnakeBody currCell = head;
        while (currCell != null){
            Vector2d tmpPosition = currCell.getPosition();
            Direction tmpDirection = currCell.direction;

            currCell.updateMovementState(newDirection, newPosition);

            newPosition = tmpPosition;
            newDirection = tmpDirection;
            currCell = currCell.next;
        }

    }

    private boolean isAbleToSelfInject(){
        int i = 0;
        SnakeBody currCell = tail;
        while (i < GameConstants.P && currCell != null){
            if (head.getRect().collideRect(currCell.getRect())){
                return true;
            }
            i++;
        }
        return false;
    }

    public void updateSteroidsLevel(){
        if (isAbleToSelfInject() && LD > 0){
            if (LD > 1){
                dead = true;
                return;
            }
            widen();
            if(LD > 0.5){
                lengthen();
                increaseVelocity();
            }
            LD = 0;
        }
    }

    public void reverse(){
        SnakeBody currCell = head;
        SnakeBody newNext = null;
        while (currCell != null){
            currCell.direction = currCell.direction.reverse();
            currCell.prev = currCell.next;
            currCell.next = newNext;
            newNext = currCell;
            currCell = currCell.prev;
        }
        SnakeBody tmp = head;
        head = tail;
        tail = tmp;
        direction = direction.reverse();
    }

    public boolean isDead(){return dead;}

    private void lengthen(){
        Direction newDirection = tail.direction;
        Vector2d modifier = newDirection.reverse().toUnitVector();
        Vector2d newPosition = Vector2d.add (tail.getPosition(), modifier);
        SnakeBody currCell = tail;
        for(int i = 0; i < GameConstants.BD; i ++){
            currCell.next = new SnakeBody(newPosition, null, newDirection, currCell.getOffCenterWidth());
            currCell.next.prev =  currCell;
            currCell = currCell.next;
        }
        tail = currCell;
        length += GameConstants.BD;
    }

    private void widen(){
        SnakeBody currCell = head;
        while (currCell != null){
            currCell.widen();
            currCell = currCell.next;
        }
    }

    public void doExercise(Exercise exercise){
        switch (exercise){
            case BenchPress -> benchPress();
            case DeadLift -> deadLift();
        }
    }

    public boolean doesHeadCollide(Rect rect){
        return this.head.getRect().collideRect(rect);
    }


    private void deadLift(){
        lengthen();
        this.length += GameConstants.BD;
    }
    private void benchPress(){
        increaseVelocity();
    }

    private void increaseVelocity(){
        velocity = Math.min(velocity + GameConstants.BP, GameConstants.VMax);
    }
    public void storeSteroid(Steroid steroid){
        LD += steroid.getDose();
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return head.getWidth();
    }

    public int getVelocity() {
        return velocity;
    }

    public ArrayList<Vector2d> getCellPositions() {
        ArrayList<Vector2d> cellPositions = new ArrayList<>();
        SnakeBody currCell = head;
        while (currCell != null){
            cellPositions.addAll(currCell.getCellPositions());
            currCell = currCell.next;
        }
        return cellPositions;
    }

    public void turnLeft(){
        direction = direction.rotateCounterClockwise();
    }

    public void turnRight(){
        direction = direction.rotateClockwise();
    }

    public double getLD() {
        return LD;
    }
}
