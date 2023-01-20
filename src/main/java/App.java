import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Engine engine = new Engine();
        Gui gui = new Gui(engine, primaryStage);
        engine.setGui(gui);
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

}
