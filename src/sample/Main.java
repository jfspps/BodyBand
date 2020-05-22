package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.bbDatabase;
import sample.model.bbExercise;

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

        //connect to SQLite via JDBC
        try {
            //we use DriverManager instead of DataSource objects (DataSource is more apt for Enterprise apps)
            Connection conn = DriverManager.getConnection("jdbc:sqlite:./bodyband.db");
            //Run SQL statements (semicolon not required) by instancing a Statement object:
            Statement statement = conn.createStatement();

            //use IF NOT EXISTS to retain previous table + data
            statement.execute("CREATE TABLE IF NOT EXISTS tblSet (" +
                    "idSet INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Exercise_id INTEGER NOT NULL, " +
                    "Rep_id INTEGER NOT NULL, " +
                    "Comments TEXT, " +
                    "SetDate NUMERIC NOT NULL," +
                    "FOREIGN KEY(Exercise_id) REFERENCES tblExercise(idExercise)," +
                    "FOREIGN KEY(Rep_id) REFERENCES tblRepetition(idRepetition)" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS tblExercise (" +
                    "idExercise INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    ExerciseName TEXT NOT NULL, " +
                    "AnchorNeeded TEXT, " +
                    "AnchorHeight TEXT, " +
                    "AnchorPosition TEXT, " +
                    "    Description TEXT," +
                    "    VideoURL TEXT" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS tblRepetition (" +
                    "idRepetition INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "BandStat_id INTEGER NOT NULL, " +
                    "Repetitions INTEGER NOT NULL," +
                    "FOREIGN KEY(BandStat_id) REFERENCES tblBandStat(idBandStat)" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS tblBandStat (" +
                    "idBandStat INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "SingleBandTension INTEGER NOT NULL, " +
                    "DoubledOrNot TEXT NOT NULL," +
                    "Units TEXT" +
                    ")");

            //release resources; alternatively use try with resources to automate this necessary step
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Could not connect to BodyBand db" + e.getMessage());
        }

        bbDatabase database = new bbDatabase();
        //attempt to connect to the database
        if (!database.open()) {
            System.out.println("Problem with opening DB queries");
            return;
        }

        //work with "database" ------------------------------------------------------------------

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

        //add an exercise without checking
        database.insertNewExercise("Bent-over Rowing", "", "", "", "Develop the lats", "https://www.youtube.com/watch?v=TE3v7CgXiiI");

        //check a record does not already exist
        int newExercise = database.exerciseOnFile("Chest Press", "", "", "", "", "someURL");
        System.out.println("Found " + newExercise);
        if(newExercise == 0){
            System.out.println("Attempting to insert new record");
            int index = database.insertNewExercise("Chest Press", "", "", "", "", "someURL");
            if(index > 0)
                System.out.println("New exercise uploaded, at index " + index);
        } else if (newExercise == -1) {
            System.out.println("Error with querying the database");
        }

        //check a record exists by ID (key)
        int primaryKey = 1; //change as required
        try(ResultSet exerciseID = database.exerciseOnFileKey(primaryKey)) {
            if (exerciseID.getInt("idExercise") > 0){
                System.out.println("Record with id = " + primaryKey + " found.");
                System.out.println("Exercise name: " + exerciseID.getString("ExerciseName"));
                System.out.println("Anchor needed: " + exerciseID.getString("AnchorNeeded"));
                System.out.println("Anchor height: " + exerciseID.getString("AnchorHeight"));
                System.out.println("Anchor position: " + exerciseID.getString("AnchorPosition"));
                System.out.println("Description: " + exerciseID.getString("Description"));
                System.out.println("Video URL: " + exerciseID.getString("VideoURL"));
                exerciseID.close();
            } else {
                System.out.println("No record with the id = " + primaryKey + " found");
                exerciseID.close();
            }
        } catch (SQLException error){
            System.out.println("Problem with reading record, by ID");
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
