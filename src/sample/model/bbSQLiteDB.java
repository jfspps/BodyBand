package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class bbSQLiteDB {

    public Boolean buildDB(){
        //connect to SQLite via JDBC
        try {
            //we use DriverManager instead of DataSource objects (DataSource is more apt for Enterprise apps)
            Connection conn = DriverManager.getConnection("jdbc:sqlite:./bodyband.db");
            //Run SQL statements (semicolon not required) by instancing a Statement object:
            Statement statement = conn.createStatement();

            //use IF NOT EXISTS to retain previous table + data
            boolean tblExerciseBuilt = statement.execute("CREATE TABLE IF NOT EXISTS tblExercise (" +
                    "idExercise INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ExerciseName TEXT NOT NULL," +
                    "MuscleGroup TEXT DEFAULT   ," +
                    "AnchorPosition TEXT DEFAULT   ," +
                    "Description TEXT DEFAULT   ," +
                    "VideoURL TEXT DEFAULT   " +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS  tblRepetition  (" +
                    "idRepetition INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Tension REAL NOT NULL," +
                    "Repetitions INTEGER NOT NULL" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS  tblSet  (" +
                    "idSet INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Exercise_id INTEGER NOT NULL," +
                    "Comments TEXT DEFAULT   ," +
                    "SetDate TEXT NOT NULL DEFAULT   ," +
                    "RepIdSeq TEXT NOT NULL DEFAULT  R_ ," +
                    "FOREIGN KEY( Exercise_id ) REFERENCES  tblExercise ( idExercise ) ON DELETE CASCADE" +
                    ")");

            //introduces exercises if tblExercise was built
            if (tblExerciseBuilt){
                statement.execute("INSERT INTO  tblExercise  VALUES (1,'Bent-over Rowing','Lats','Floor','Develop the lats','https://www.youtube.com/watch?v=TE3v7CgXiiI')");
                statement.execute("INSERT INTO  tblExercise  VALUES (2,'Lying pulldown','Lats','Base','Models a lateral pulldown','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (3,'Standing Row','Lats','Chest','','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (4,'Standing cable pull','Lats','Top','','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (5,'Chest Press','Chest','Knee','Works the mid and upper chest','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (6,'Standing Fly','Chest','Chest','','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (7,'Standing Pushdown','Chest','Top','Works the lower chest','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (8,'Squat','Legs','Floor','Compound exercise for the thighs','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (9,'Calf Raise','Legs','Base','','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (10,'Seated Bicep Curl','Arms','Base','Works the biceps','')");
                statement.execute("INSERT INTO  tblExercise  VALUES (11,'Resisted Curl','Abs','Base','Challenge the core!','')");
            }

            //release resources; alternatively use try with resources to automate this necessary step
            statement.close();
            conn.close();

            System.out.println("SQLite DB built");
            return true;
        } catch (SQLException e) {
            System.out.println("Could not connect to BodyBand db" + e.getMessage());
            return false;
        }
    }
}
