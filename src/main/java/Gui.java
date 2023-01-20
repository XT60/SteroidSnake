import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gui {
    private final int windowSize = 600;
    private int cellSize = windowSize / GameConstants.N;

    private Engine engine;
    Stage stage;

    public Gui(Engine engine, Stage primaryStage){
        this.engine = engine;
        stage = primaryStage;
    }

    public void draw() {
        Canvas canvas = new Canvas(windowSize, windowSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw the snake
        drawSnake(gc);

        // Draw a consumable
        drawConsumables(gc);

        // Display the current score
        Text scoreText = new Text("Score: " + engine.getCurrScore());
        scoreText.setTranslateX(windowSize/2 - 27);
        scoreText.setTranslateY(-windowSize/2 + 20);

        // Display the overall high score
        Text highScoreText = new Text("High Score: " + engine.getHighScore());
        highScoreText.setTranslateX(windowSize/2 - 40);
        highScoreText.setTranslateY(-windowSize/2 + 40);

        // Display the overall high score
        Text LDtext = new Text("CurrLD: " + engine.getCurrLD());
        LDtext.setTranslateX(windowSize/2 - 30);
        LDtext.setTranslateY(-windowSize/2 + 60);

        // Draw the grid
        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= GameConstants.N; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, windowSize);
        }
        for (int i = 0; i <= GameConstants.N; i++) {
            gc.strokeLine(0, i * cellSize, windowSize, i * cellSize);
        }

        StackPane root = new StackPane();
        root.getChildren().addAll(canvas, scoreText, highScoreText, LDtext);

        Scene scene = new Scene(root, windowSize, windowSize);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                engine.turnSnakeRight();
            } else if (event.getCode() == KeyCode.A) {
                engine.turnSnakeLeft();
            }
        });
        stage.setScene(scene);
        stage.setTitle("Snake Game");
        stage.show();
    }

    public void drawConsumables(GraphicsContext gc){
        HashMap<Vector2d, Steroid> steroids = engine.getSteroids();
        HashMap<Vector2d, Exercise> exercises = engine.getExercises();

        for (Map.Entry<Vector2d, Steroid> entry : steroids.entrySet()){
            gc.setFill(entry.getValue().getColor());
            Vector2d worldPosition = ConvertToWorld(entry.getKey());
            gc.fillOval(worldPosition.x, worldPosition.y, cellSize, cellSize);
        }
        for (Map.Entry<Vector2d, Exercise> entry : exercises.entrySet()){
            gc.setFill(entry.getValue().getColor());
            Vector2d worldPosition = ConvertToWorld(entry.getKey());
            gc.fillOval(worldPosition.x, worldPosition.y, cellSize, cellSize);
        }

    }

    public Vector2d ConvertToWorld(Vector2d gridCords){
        return new Vector2d( gridCords.x * cellSize, gridCords.y * cellSize);
    }

    private void drawSnake(GraphicsContext gc){
        ArrayList<Vector2d> cellPositions = engine.getSnakeCellPositions();
        for(Vector2d position : cellPositions){
            Vector2d worldPosition = ConvertToWorld(position);
            gc.setFill(Color.GREEN);
            gc.fillRect(worldPosition.x, worldPosition.y, cellSize, cellSize);
        }
    }

    public void informThatGameFinished(double score, double highScore) {
        String title = "Game Over";
        String message = "You lost the game! Your score is: " + score + "\n" + " Your highscore is:" + highScore;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
