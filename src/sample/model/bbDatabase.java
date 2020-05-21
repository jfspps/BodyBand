package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bbDatabase {

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
    public static final int SetWorkoutDate = 5;

    //General SQL queries -------------------------------------------------------------------
    //tblExercise queries
    public static final String queryAllExercises = "SELECT * FROM tblExercise";
    public static final String queryAllRepetitions = "SELECT * FROM tblRepetition";
    public static final String queryAllBandStats = "SELECT * FROM tblBandStat";
    public static final String queryAllSets = "SELECT * FROM tblSet";

    //Precompiled SQL statements with PreparedStatement -------------------------------------
    //Prepared statments
    private PreparedStatement queryExercises;

    //bbDatabase admin routines -------------------------------------------------------------
    //open() checks if all general queries work
    public boolean open(){
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = conn.createStatement())
        {
            //dry run of queries, check if they are all valid for the given DB
            queryExercises = conn.prepareStatement(queryAllExercises);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection error:/n" + e.getMessage());
            return false;
        }
    }

    //close() ensures all resources are freed
    public boolean close(){
        try {
            //close in the order they were last instantiated
            if(queryExercises != null){
                queryExercises.close();
            }
            return true;
        } catch (SQLException e){
            System.out.println("Error closing connections:/n" + e.getMessage());
            return false;
        }
    }

    // General SQL queries (SQL to Java object) --------------------------------------------------

    //tblExercise
    public List<bbExercise> listAllExercises() {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryAllExercises))
        {
            List<bbExercise> exercises = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbExercise exercise = new bbExercise();
                exercise.setExerciseId(results.getInt(ExerciseIdINDEX));
                exercise.setExerciseName(results.getString(ExerciseNameINDEX));
                exercise.setAnchorNeeded(results.getBoolean(ExerciseAnchorNeededINDEX));
                exercise.setAnchorHeight(results.getString(ExerciseAnchorHeightINDEX));
                exercise.setAnchorPosition(results.getString(ExerciseAnchorPositionINDEX));
                exercise.setExerciseDesc(results.getString(ExerciseDescINDEX));
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
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryAllRepetitions))
        {
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
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryAllBandStats))
        {
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
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryAllSets))
        {
            List<bbSet> sets = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbSet set = new bbSet();
                set.setSetId(results.getInt(SetIdINDEX));
                set.setExerciseId(results.getInt(SetExerciseIdINDEX));
                set.setRepetitionId(results.getInt(SetExerciseIdINDEX));
                set.setComments(results.getString(SetCommentsINDEX));
                set.setWorkoutDate(results.getString(SetWorkoutDate));
                sets.add(set);
            }
            return sets;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblSet:/n" + e.getMessage());
            return null;
        }
    }

}