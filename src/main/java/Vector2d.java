

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

    static Vector2d add(Vector2d that, Vector2d other){
        return new Vector2d(that.x + other.x, that.y + other.y);
    }

    Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    Vector2d opposite(){
        return new Vector2d(this.y, this.x);
    }

    public Vector2d reverse() {return new Vector2d(x * (-1), y* (-1));}

    public Vector2d applyDirection(Direction mapDirection){
        return Vector2d.add(mapDirection.toUnitVector(), this);
    }

    public Rect getUnitRect(){
        return new Rect(this.x, this.y, 1, 1);
    }


    public int hash;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return y == vector2d.y && x == vector2d.x && hash == vector2d.hash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x, hash);
    }
}
