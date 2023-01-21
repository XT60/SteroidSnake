import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Engine implements Runnable{
    private final GameConstants constants = new GameConstants();
    private final HashMap<Vector2d, MapObject> mapObjects  = new HashMap<>();
    private double highScore;
    private double currScore = 0;
    private final Snake snake;
    private boolean interruptedStop = false;
    Gui gui;
    private final int sleepInterval = 300;
    int initialObjectCount = (int) Math.floor(Math.sqrt(GameConstants.N));



    Engine(){
        highScore = loadHighScore();
        snake = new Snake();
        addRandomObjects(initialObjectCount);
    }


    private void updateCollisionsWithObjects() {
        int currInteractions = 0;
        ArrayList<Vector2d> mapObjectPositionsToRemove = new ArrayList<Vector2d>();
        for (Map.Entry<Vector2d, MapObject> entry : mapObjects.entrySet()) {
            Vector2d position = entry.getKey();
            if (snake.doesHeadCollide(position.getUnitRect())) {
                MapObject mapObject = entry.getValue();
                if (mapObject.isSteroid()) {
                    snake.storeSteroid(mapObject.castToSteroid());
                } else if (mapObject.isExercise()) {
                    snake.doExercise(mapObject.castToExercise());
                }
                mapObjectPositionsToRemove.add(position);
                currInteractions += 1;
            }
        }
        for(Vector2d position : mapObjectPositionsToRemove){
            mapObjects.remove(position);
        }
        addRandomObjects(currInteractions);
    }

    public void run(){
        while (!snake.isDead() && !interruptedStop){
            updateCollisionsWithObjects();
            snake.updateVariables();
            updateScore();
            Platform.runLater(() -> {gui.draw();});
            try {
                TimeUnit.MILLISECONDS.sleep(sleepInterval / snake.getVelocity());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (!interruptedStop){
            if (currScore > highScore){
                highScore = currScore;
                dumpHighScore();
            }
            Platform.runLater(() -> {gui.informThatGameFinished(currScore, highScore);});
        }
    }

    private void addRandomObjects(int objectCount){
        Random rand = new Random();
        ArrayList<Vector2d> freePositions = findNewFreePositions(objectCount);
        for(int i = 0; i < objectCount; i++){
            Vector2d freePosition = freePositions.get(i);
            int val = rand.nextInt(50);
            Object newObject = switch (val % 6){
                case 0 -> Steroid.Testosteron;
                case 1 -> Steroid.Proviron;
                case 2 -> Steroid.Winstrol;
                case 3 -> Steroid.MongolianSpecific;
                case 4 -> Exercise.BenchPress;
                case 5 -> Exercise.DeadLift;
                default -> null;
            };

            MapObject newMapObject = new MapObject(newObject);
            mapObjects.put(freePosition,newMapObject);
        }
    }

    private ArrayList<Vector2d> findNewFreePositions(int objectCount){
        Random rand = new Random();
        ArrayList<Vector2d> freePositions = new ArrayList<Vector2d>();
        Set<Vector2d> takenPositions = new HashSet<>(getSnakeCellPositions());
        takenPositions.addAll(mapObjects.keySet());
        while (freePositions.size() < objectCount){
            Vector2d newPosition = new Vector2d(Math.abs(rand.nextInt()) % GameConstants.N,
                    Math.abs(rand.nextInt()) % GameConstants.N);
            if(!takenPositions.contains(newPosition)){
                freePositions.add(newPosition);
            }
        }
        return freePositions;
    }

    private void updateScore(){
        currScore = calculateScore();
    }
    private double calculateScore(){
        return snake.getLength() * snake.getWidth() * snake.getVelocity();
    };


    public double getCurrScore(){
        return currScore;
    }

    public double getHighScore(){
        return highScore;
    }

    private double loadHighScore(){
        File file = new File("./src/main/resources/highScore.txt");
        try {
            Scanner scanner = new Scanner(file);
            return Double.parseDouble(scanner.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        }
    }

    private void dumpHighScore(){
        File file = new File("./src/main/resources/highScore.txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(highScore);
            writer.close();
            System.out.println("File saved!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void stop(){
        interruptedStop = true;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public ArrayList<Vector2d> getSnakeCellPositions() {
        return snake.getCellPositions();
    }

    public HashMap<Vector2d, MapObject> getMapObjects() {
        return mapObjects;
    }

    public void turnSnakeLeft(){
        snake.turnLeft();
    }

    public void turnSnakeRight(){
        snake.turnRight();
    }

    public double getCurrLD(){
        return snake.getLD();
    }

}
