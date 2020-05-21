package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bbDatabase {

    // SQLite generates databases *.db automatically if they do not exist
    public static final String DB_NAME = "bodyband.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:./" + DB_NAME;

    //indices for ResultSet tables---------------------------------------------------------
    //tblExercise indices
    public static final int ExerciseIdINDEX = 1;
    public static final int ExerciseNameINDEX = 2;
    public static final int ExerciseAnchorNeededINDEX = 3;
    public static final int ExerciseAnchorHeightINDEX = 4;
    public static final int ExerciseAnchorPositionINDEX = 5;
    public static final int ExerciseDescINDEX = 6;
    public static final int ExerciseVideoURLINDEX = 7;

    //tblRepetition indices
    public static final int RepetitionIdINDEX = 1;
    public static final int RepetitionBandStatIdINDEX = 2;
    public static final int RepetitionRepsINDEX = 3;

    //tblBandStat indices
    public static final int BandStatIdINDEX = 1;
    public static final int BandStatSingleBandTensionINDEX = 2;
    public static final int BandStatDoubledOrNotINDEX = 3;
    public static final int BandStatUnitsINDEX = 4;

    //tblSet indices
    public static final int SetIdINDEX = 1;
    public static final int SetExerciseIdINDEX = 2;
    public static final int SetRepIdINDEX = 3;
    public static final int SetCommentsINDEX = 4;
    public static final int SetDateINDEX = 5;

    //General SQL queries -------------------------------------------------------------------
    //tblExercise queries
    public static final String querySelectAllExercises = "SELECT * FROM tblExercise";
    public static final String querySelectAllRepetitions = "SELECT * FROM tblRepetition";
    public static final String querySelectAllBandStats = "SELECT * FROM tblBandStat";
    public static final String querySelectAllSets = "SELECT * FROM tblSet";
    public static final String queryInsertExercise = "INSERT INTO tblExercise " +
            "(ExerciseName, AnchorNeeded, AnchorHeight, AnchorPosition, Description, VideoURL) " +
            "VALUES(?, ?, ?, ?, ?, ?)";

    //Precompiled SQL statements with PreparedStatement -------------------------------------
    private Connection conn;
    //Prepared statements
    private PreparedStatement insertExercise;

    //bbDatabase admin routines -------------------------------------------------------------
    //open() is called by main() first and sets up conn
    public boolean open() {
        try {
            //initiate the connection conn and all Statements/PreparedStatements here
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertExercise = conn.prepareStatement(queryInsertExercise);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection error:/n" + e.getMessage());
            return false;
        }
    }

    //close() is called at the end of main() and frees all resources
    public boolean close() {
        try {
            //close in the order they were last instantiated
            if (insertExercise != null) {
                insertExercise.close();
            }
            //lastly, close conn
            if (conn != null){
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error closing connections:/n" + e.getMessage());
            return false;
        }
    }

    // General SQL queries (SQL to Java object) --------------------------------------------------

    //tblExercise
    public List<bbExercise> listAllExercises() {
        //note that conn is still open after this method returns
        //'statement' and 'results' are released on return
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllExercises)) {
            List<bbExercise> exercises = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbExercise exercise = new bbExercise(results.getString(ExerciseNameINDEX));
                exercise.setExerciseId(results.getInt(ExerciseIdINDEX));
                exercise.setAnchorNeeded(results.getString(ExerciseAnchorNeededINDEX));
                exercise.setAnchorHeight(results.getString(ExerciseAnchorHeightINDEX));
                exercise.setAnchorPosition(results.getString(ExerciseAnchorPositionINDEX));
                exercise.setExerciseDesc(results.getString(ExerciseDescINDEX));
                exercise.setVideoURL(results.getString(ExerciseVideoURLINDEX));
                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblExercises:/n" + e.getMessage());
            return null;
        }
    }

    //tblRepetition
    public List<bbRepetition> listAllRepetitions() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllRepetitions)) {
            List<bbRepetition> repetitions = new ArrayList<>();

            while (results.next()) {
                bbRepetition repetition = new bbRepetition();
                repetition.setRepetitionId(results.getInt(RepetitionIdINDEX));
                repetition.setBandStatId(results.getInt(RepetitionBandStatIdINDEX));
                repetition.setReps(results.getInt(RepetitionRepsINDEX));
                repetitions.add(repetition);
            }
            return repetitions;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblRepetition:/n" + e.getMessage());
            return null;
        }
    }

    //tblBandStat
    public List<bbBandStat> listAllBandstats() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllBandStats)) {
            List<bbBandStat> bandStats = new ArrayList<>();

            while (results.next()) {
                bbBandStat bandStat = new bbBandStat();
                bandStat.setBandStatId(results.getInt(BandStatIdINDEX));
                bandStat.setTension(results.getInt(BandStatSingleBandTensionINDEX));
                bandStat.setDoubledOrNot(results.getInt(BandStatDoubledOrNotINDEX));
                bandStat.setUnits(results.getString(BandStatUnitsINDEX));
                bandStats.add(bandStat);
            }
            return bandStats;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblBandStat:/n" + e.getMessage());
            return null;
        }
    }

    //tblSet
    public List<bbSet> listAllSets() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllSets)) {
            List<bbSet> sets = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbSet set = new bbSet();
                set.setSetId(results.getInt(SetIdINDEX));
                set.setExerciseId(results.getInt(SetExerciseIdINDEX));
                set.setRepetitionId(results.getInt(SetRepIdINDEX));
                set.setComments(results.getString(SetCommentsINDEX));
                set.setSetDate(results.getString(SetDateINDEX));
                sets.add(set);
            }
            return sets;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblSet:/n" + e.getMessage());
            return null;
        }
    }

    // insertion methods ------------------------------------------------------------------------

    //the GUI would read all given values on a form, verify the correct Java type and then assign null to blank entries
    //aim to return the index of the inserted record
    public int insertNewExercise(String name, String anchorNeeded, String anchorHeight, String anchorPosition,
                                  String desc, String videoURL) {

        //testing for null is eventually handled by the controller and used here for test purposes
        if (name == null) {
            System.out.println("Name of exercise needed");
            return -1;
        }

        //include code to check if the record already exists

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertExercise.setString(1, name);
            insertExercise.setString(2, anchorNeeded);
            insertExercise.setString(3, anchorHeight);
            insertExercise.setString(4, anchorPosition);
            insertExercise.setString(5, desc);
            insertExercise.setString(6, videoURL);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertExercise.executeUpdate();

            if(insertedRecord != 1){
                throw new SQLException("Could not insert exercise");
            }

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertExercise.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new exercise");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting record");
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }
}