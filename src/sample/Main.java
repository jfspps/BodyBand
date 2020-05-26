package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.bbDatabase;
//import sample.model.bbExercise;
//import sample.model.bbSQLiteDB;
//
//import java.sql.*;
//import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExercisePage.fxml"));
        primaryStage.setTitle("BodyBand");
        primaryStage.setScene(new Scene(root, 335, 600));
        primaryStage.show();
    }

    //Initialise everything when the JavaFX dialog box loads
    @Override
    public void init() throws Exception {
        super.init();

        //attempt to connect to and verify PreparedStatements of "database"
        if (!bbDatabase.getBbDB().open()) {
            System.out.println("Problem with opening DB queries");
            Platform.exit();
        }
    }

    //clear everything up on closing
    @Override
    public void stop() throws Exception {
        super.stop();
        //when done, close the database and all its resources...
        bbDatabase.getBbDB().close();
    }

    public static void main(String[] args) {

        //preliminary code to set up and test the database -------------------------------------------------

//        //build the SQLite db (comment out if needed)
//        bbSQLiteDB sqlLiteDB = new bbSQLiteDB();
//        sqlLiteDB.buildDB();
//
//        //view all exercises
//        List<bbExercise> initialExercises = bbDatabase.get.listAllExercises();
//        if (initialExercises.isEmpty()) {
//            System.out.println("No exercises on file");
//        } else {
//            for (bbExercise exercise : initialExercises) {
//                System.out.println("ID = " + exercise.getExerciseId() + ", Name = " + exercise.getExerciseName()
//                        + " video: " + exercise.getVideoURL());
//            }
//        }
//
//        //add an exercise without checking (note the order: exercise, band stat, repetition then set
//        bbDatabase.get.insertNewExercise("Bent-over Rowing", "", "", "", "Develop the lats", "https://www.youtube.com/watch?v=TE3v7CgXiiI");
//        bbDatabase.get.insertNewBandStat(18, "n", "pounds");
//        bbDatabase.get.insertNewBandStat(13, "n", "pounds");
//        bbDatabase.get.insertNewRepetition(1, 8);
//        bbDatabase.get.insertNewRepetition(1, 7);
//        bbDatabase.get.insertNewRepetition(2, 9);
//        bbDatabase.get.insertNewSet(1, 1, "Need to work on form", "10.3.20");
//        bbDatabase.get.insertNewSet(1, 2, "", "10.3.20");
//        bbDatabase.get.insertNewSet(1, 3, "", "10.3.20");
//
//        //try to insert a new exercise record (automatically checks if one exists)
//        bbDatabase.get.insertNewExercise("Chest Press", "", "", "", "", "someURL");
//
//        //check a record exists by ID (key)
//        int primaryKey = 99; //change as required
//        try(ResultSet exerciseID = bbDatabase.get.exerciseOnFileKey(primaryKey)) {
//            if (exerciseID != null) {
//                System.out.println("Record with id = " + primaryKey + " found.");
//                System.out.println("Exercise name: " + exerciseID.getString("ExerciseName"));
//                System.out.println("Anchor needed: " + exerciseID.getString("AnchorNeeded"));
//                System.out.println("Anchor height: " + exerciseID.getString("AnchorHeight"));
//                System.out.println("Anchor position: " + exerciseID.getString("AnchorPosition"));
//                System.out.println("Description: " + exerciseID.getString("Description"));
//                System.out.println("Video URL: " + exerciseID.getString("VideoURL"));
//                exerciseID.close();
//            }
//        } catch (SQLException error){
//            System.out.println("Problem with locating exercise record id = " + primaryKey + "\n" + error.getMessage());
//        }
//
//        //print them out again:
//        System.out.println("Here is the latest list of exercises available:");
//        List<bbExercise> finalExercises = bbDatabase.get.listAllExercises();
//        for (bbExercise exercise : finalExercises) {
//            System.out.println("ID = " + exercise.getExerciseId() + ", Name = " + exercise.getExerciseName()
//                    + " video: " + exercise.getVideoURL());
//        }

        //JavaFX
        launch(args);
    }
}
