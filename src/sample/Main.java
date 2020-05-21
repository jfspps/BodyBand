package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.bbDatabase;
import sample.model.bbExercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
                    "DoubledOrNot INTEGER NOT NULL," +
                    "Units TEXT" +
                    ")");

            statement.execute("DELETE FROM tblExercise");

            statement.execute("INSERT INTO tblExercise (" +
                    "ExerciseName, Description, VideoURL" +
                    ") VALUES(" +
                    "\"Bent-over Rowing\", \"Develop the lats\", \"https://www.youtube.com/watch?v=TE3v7CgXiiI\")");

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
        if (initialExercises == null) {
            System.out.println("No exercises on file");
            return;
        }
        for (bbExercise exercise : initialExercises) {
            System.out.println("ID = " + exercise.getExerciseId() + ", Name = " + exercise.getExerciseName()
            + " video: " + exercise.getVideoURL());
        }

        //try inserting data
        System.out.println("Attempting to insert new exercise");
        int index = database.insertNewExercise("Chest Press", null, null, null, null, "someURL");
        if(index > 0)
            System.out.println("New exercise uploaded, at index " + index);

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
