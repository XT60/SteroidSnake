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
    private final HashMap<Vector2d, Steroid> steroids = new HashMap<Vector2d, Steroid>();
    private final HashMap<Vector2d, Exercise> exercises = new HashMap<Vector2d, Exercise>();
    private double highScore;
    private double currScore = 0;
    private final Snake snake;
    Gui gui;
    private final int sleepInterval = 300;
    int initialObjectCount = (int) Math.floor(Math.sqrt(GameConstants.N));



    Engine(){
        dumpHighScore();
        highScore = loadHighScore();
        snake = new Snake();
        addRandomObjects(initialObjectCount);
    }


    private void updateCollisionsWithObjects() {
        int currInteractions = 0;
        ArrayList<Vector2d> SteroidsPositionsToRemove = new ArrayList<Vector2d>();
        for (Map.Entry<Vector2d, Steroid> entry : steroids.entrySet()){
            if (snake.doesHeadCollide(entry.getKey().getUnitRect())){
                snake.storeSteroid(entry.getValue());
                SteroidsPositionsToRemove.add(entry.getKey());
                currInteractions += 1;
            }
        }
        for(Vector2d position : SteroidsPositionsToRemove){
            steroids.remove(position);
        }
        ArrayList<Vector2d> ExercisePositionsToRemove = new ArrayList<Vector2d>();
        for (Map.Entry<Vector2d, Exercise> entry : exercises.entrySet()){
            if (snake.doesHeadCollide(entry.getKey().getUnitRect())){
                switch (entry.getValue()){
                    case BenchPress -> snake.benchPress();
                    case DeadLift -> snake.deadLift();
                }
                ExercisePositionsToRemove.add(entry.getKey());
                currInteractions += 1;
            }
        }
        for(Vector2d position : ExercisePositionsToRemove){
            exercises.remove(position);
        }
        addRandomObjects(currInteractions);
    }

    public void run(){
        while (!snake.isDead()){
            updateCollisionsWithObjects();
            snake.updateVariables();
            updateScore();
            Platform.runLater(() -> {gui.draw();});
            try {
                TimeUnit.MILLISECONDS.sleep(sleepInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (currScore > highScore){
            highScore = currScore;
            dumpHighScore();
        }
        gui.informThatGameFinished(currScore, highScore);
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


            if (newObject instanceof Steroid){
                steroids.put(freePosition,(Steroid)newObject);
            }
            else if (newObject instanceof Exercise){
                exercises.put(freePosition,(Exercise)newObject);
            }
            else{
                throw new RuntimeException();
            }
        }
    }

    private ArrayList<Vector2d> findNewFreePositions(int objectCount){
        Random rand = new Random();
        ArrayList<Vector2d> freePositions = new ArrayList<Vector2d>();
        Set<Vector2d> takenPositions = new HashSet<>(getSnakeCellPositions());
        takenPositions.addAll(steroids.keySet());
        takenPositions.addAll(exercises.keySet());
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

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public ArrayList<Vector2d> getSnakeCellPositions() {
        return snake.getCellPositions();
    }

    public HashMap<Vector2d, Steroid> getSteroids() {
        return steroids;
    }

    public HashMap<Vector2d, Exercise> getExercises() {
        return exercises;
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
