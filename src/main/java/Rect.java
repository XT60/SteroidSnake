public class Rect {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rect(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean collideRect(Rect other){
        return !(other.x >= x + width ||
                other.x + other.width <= x ||
                other.y + other.height <= y ||
                other.y >= y + height);
    }
}
