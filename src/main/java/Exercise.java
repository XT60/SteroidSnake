import javafx.scene.paint.Color;

public enum Exercise {
    BenchPress, DeadLift;

    public Color getColor(){
        return switch (this){
            case BenchPress -> Color.BLUE;
            case DeadLift -> Color.VIOLET;
        };
    }
}
