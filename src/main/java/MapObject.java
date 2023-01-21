import javafx.scene.paint.Color;

public class MapObject {
    protected Object object;

    public MapObject(Object object){
        this.object = object;
    }
    public boolean isSteroid(){
        return object instanceof Steroid;
    }

    public boolean isExercise(){
        return object instanceof Exercise;
    }

    public Color getColor(){
        if (isSteroid()){
            return ((Steroid)object).getColor();
        } else if (isExercise()) {
            return ((Exercise)object).getColor();
        }
        return null;
    };

    public Steroid castToSteroid(){
        if (isSteroid()){
            return (Steroid)object;
        }
        return null;
    }

    public Exercise castToExercise(){
        if (isExercise()){
            return (Exercise)object;
        }
        return null;
    }
}
