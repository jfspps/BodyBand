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
            statement.execute("CREATE TABLE IF NOT EXISTS tblSet (" +
                    "idSet INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Exercise_id INTEGER NOT NULL, " +
                    "Rep_id INTEGER NOT NULL, " +
                    "Comments TEXT, " +
                    "SetDate TEXT NOT NULL," +
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

            System.out.println("SQLite DB built");
            return true;
        } catch (SQLException e) {
            System.out.println("Could not connect to BodyBand db" + e.getMessage());
            return false;
        }
    }
}
