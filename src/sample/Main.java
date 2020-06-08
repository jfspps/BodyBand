package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.bbDatabase;

public class Main extends Application {

    //give access to the mainPage Stage
    static Stage mainWindow;

    // Initialise everything when the JavaFX dialog box loads (init() runs before start(); init() and stop() are
    // abstract by default)
    @Override
    public void init() throws Exception {
        super.init();       // fundamentally abstract, init() does nothing in JavaFX8 at least (leave it here in case
        // later versions change init())

        //attempt to connect to and verify PreparedStatements of "database"
        if (!bbDatabase.getInstance().open()) {
            System.out.println("Problem with opening DB queries");

            //force the UI to terminate if the DB does not load
            Platform.exit();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //assign whatever primaryStage has to mainWindow
        mainWindow = primaryStage;
        Parent mainPage = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setTitle("BodyBand");
        mainWindow.setScene(new Scene(mainPage, 335, 600));
        mainWindow.show();
    }

    //clear everything up on closing
    @Override
    public void stop() throws Exception {
        super.stop();   // fundamentally abstract, stop() does nothing in JavaFX8 at least

        //when done, close the database and all its resources...
        bbDatabase.getInstance().close();
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
