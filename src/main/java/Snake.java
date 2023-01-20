import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.max;

public class Snake {
    private Direction direction;
    private SnakeBody head = null;
    private SnakeBody tail = null;
    private int length = 1;
    private int width = 1;
    private double LD = 0.0;
    private boolean dead = false;


    Snake(){
        Random rand = new Random();
        direction = Direction.N;
        Vector2d initialPosition = new Vector2d(rand.nextInt() % GameConstants.N, rand.nextInt() % GameConstants.N);
        head = tail =  new SnakeBody(initialPosition, null, Direction.N);
    }

    private int velocity = 1;
    public void move(Direction directionInput){
        Direction newDirection = directionInput;
        Vector2d newPosition = head.getPosition().applyDirection(directionInput);
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
        }
    }

    public void reverse(){
        SnakeBody currCell = head;
        SnakeBody newNext = null;
        while (currCell != null){
            currCell.prev = currCell.next;
            currCell.next = newNext;
            newNext = currCell;
            currCell = currCell.prev;
        }
        SnakeBody tmp = head;
        head = tail;
        tail = tmp;
    }

    public boolean isDead(){return dead;}

    private void lengthen(){
        Direction newDirection = tail.direction;
        Vector2d modifier = newDirection.reverse().toUnitVector();
        Vector2d newPosition = Vector2d.add (tail.getPosition(), modifier);
        SnakeBody currCell = tail;
        for(int i = 0; i < GameConstants.BD; i ++){
            currCell = currCell.prev;
            currCell.prev = new SnakeBody(newPosition, null, newDirection);
        }
        tail = currCell.prev;
    }

    private void widen(){
        SnakeBody currCell = head;
        while (currCell != null){
            currCell.widen();
            currCell = currCell.next;
        }
    }


    public void deadLift(){
        lengthen();
        this.length += GameConstants.BD;
    }
    public void benchPress(){
        increaseVelocity();
    }

    private void increaseVelocity(){
        velocity = max(velocity + GameConstants.BP, GameConstants.VMax);
    }
    public void applySteroid(Steroid steroid){
        LD += steroid.getDose();
    }

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
