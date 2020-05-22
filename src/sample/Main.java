package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.bbDatabase;
import sample.model.bbExercise;
import sample.model.bbSQLiteDB;

import java.sql.*;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("BodyBand");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

        //build the SQLite db (comment out if needed)
        bbSQLiteDB sqlLiteDB = new bbSQLiteDB();
        sqlLiteDB.buildDB();

        //attempt to connect to and verify PreparedStatements of "database"
        bbDatabase database = new bbDatabase();
        if (!database.open()) {
            System.out.println("Problem with opening DB queries");
            return;
        }

        //work with "database" -------------------------------------------------------------------------------

        //view all exercises
        List<bbExercise> initialExercises = database.listAllExercises();
        if (initialExercises.isEmpty()) {
            System.out.println("No exercises on file");
        } else {
            for (bbExercise exercise : initialExercises) {
                System.out.println("ID = " + exercise.getExerciseId() + ", Name = " + exercise.getExerciseName()
                        + " video: " + exercise.getVideoURL());
            }
        }

        //add an exercise without checking (note the order: exercise, band stat, repetition then set
        database.insertNewExercise("Bent-over Rowing", "", "", "", "Develop the lats", "https://www.youtube.com/watch?v=TE3v7CgXiiI");
        database.insertNewBandStat(18, "n", "pounds");
        database.insertNewBandStat(13, "n", "pounds");
        database.insertNewRepetition(1, 8);
        database.insertNewRepetition(1, 7);
        database.insertNewRepetition(2, 9);
        database.insertNewSet(1, 1, "Need to work on form", "10.3.20");
        database.insertNewSet(1, 2, "", "10.3.20");
        database.insertNewSet(1, 3, "", "10.3.20");

        //try to insert a new exercise record (automatically checks if one exists)
        database.insertNewExercise("Chest Press", "", "", "", "", "someURL");

        //check a record exists by ID (key)
        int primaryKey = 99; //change as required
        try(ResultSet exerciseID = database.exerciseOnFileKey(primaryKey)) {
            if (exerciseID != null) {
                System.out.println("Record with id = " + primaryKey + " found.");
                System.out.println("Exercise name: " + exerciseID.getString("ExerciseName"));
                System.out.println("Anchor needed: " + exerciseID.getString("AnchorNeeded"));
                System.out.println("Anchor height: " + exerciseID.getString("AnchorHeight"));
                System.out.println("Anchor position: " + exerciseID.getString("AnchorPosition"));
                System.out.println("Description: " + exerciseID.getString("Description"));
                System.out.println("Video URL: " + exerciseID.getString("VideoURL"));
                exerciseID.close();
            }
        } catch (SQLException error){
            System.out.println("Problem with locating exercise record id = " + primaryKey + "\n" + error.getMessage());
        }

        //print them out again:
        System.out.println("Here is the latest list of exercises available:");
        List<bbExercise> finalExercises = database.listAllExercises();
        for (bbExercise exercise : finalExercises) {
            System.out.println("ID = " + exercise.getExerciseId() + ", Name = " + exercise.getExerciseName()
                    + " video: " + exercise.getVideoURL());
        }

        //when done, close the database and all its resources...
        database.close();

        //JavaFX
        launch(args);
    }
}
