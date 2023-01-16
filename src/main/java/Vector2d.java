

import java.util.Objects;

public class Vector2d {
    public int y;
    public int x;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

//    public boolean
    public Vector2d(Vector2d vec){
        this.x = vec.x;
        this.y = vec.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", this.x, this.y);
    }

    boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
        }

    boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof Vector2d)){
            return false;
        }
        Vector2d that = (Vector2d) other;
        if (this.x != that.x){
            return false;
        }
        if (this.y != that.y){
            return false;
        }
        return true;
    }

    Vector2d opposite(){
        return new Vector2d(this.y, this.x);
    }

    public Vector2d applyDirection(Direction mapDirection){
        return this.add(mapDirection.toUnitVector());
    }

}
