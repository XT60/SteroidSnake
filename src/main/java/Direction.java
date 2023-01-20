

public enum Direction {
    N(0),
    E(1),
    S(2),
    W(3);

    private final static Direction[] clockWiseOrder = {
            Direction.N,
            Direction.E,
            Direction.S,
            Direction.W
    };
    private final int orderIndex;

    private Direction(int orderIndex){
        this.orderIndex = orderIndex;
    };

    @Override
    public String toString() {
        return switch (this) {
            case N -> "north";
            case E -> "east";
            case S -> "south";
            case W -> "west";
        };
    }

    public Direction turn(int moveValue){
        return clockWiseOrder[(this.orderIndex + moveValue + clockWiseOrder.length) % clockWiseOrder.length];
    }

    public Direction reverse(){
        return switch (this) {
            case N -> Direction.S;
            case E -> Direction.W;
            case S -> Direction.N;
            case W -> Direction.E;
        };
    }


    public Vector2d toUnitVector(){;
        return switch (this) {
            case N -> new Vector2d(0, -1);
            case E -> new Vector2d(1, 0);
            case S -> new Vector2d(0, 1);
            case W -> new Vector2d(-1, 0);
        };
    }

    public Vector2d getPerpendicularUnitVector(){
        return switch (this) {
            case N -> new Vector2d(-1, 0);
            case E -> new Vector2d(0, -1);
            case S -> new Vector2d(1, 0);
            case W -> new Vector2d(0, 1);
        };
    }

    public Direction rotateClockwise(){
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    public Direction rotateCounterClockwise(){
        return switch (this) {
            case N -> W;
            case E -> N;
            case S -> E;
            case W -> S;
        };
    }

}
