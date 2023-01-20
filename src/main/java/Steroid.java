import javafx.scene.paint.Color;

public enum Steroid {
    Testosteron, Proviron, Winstrol, MongolianSpecific;

    public double getDose(){
        return switch (this){
            case Testosteron -> 0.05;
            case Proviron -> 0.2;
            case Winstrol -> 0.75;
            case MongolianSpecific -> 0.99;
        };
    }

    public Color getColor(){
        return switch (this){
            case Testosteron -> Color.YELLOW;
            case Proviron -> Color.GOLD;
            case Winstrol -> Color.ORANGE;
            case MongolianSpecific -> Color.RED;
        };
    }
}
