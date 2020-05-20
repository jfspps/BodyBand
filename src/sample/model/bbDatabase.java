package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bbDatabase {

    public static final String DB_NAME = "bodyband.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:./" + DB_NAME;

    //tblExercise indices
    public static final int ExerciseIdINDEX = 1;
    public static final int ExerciseNameINDEX = 2;
    public static final int ExerciseAnchorNeededINDEX = 3;
    public static final int ExerciseAnchorHeightINDEX = 4;
    public static final int ExerciseAnchorPositionINDEX = 5;
    public static final int ExerciseDescINDEX = 6;

    //tblExercise queries
    public static final String queryAllExercises = "SELECT * FROM tblExercise";

    //Prepared statments
    private PreparedStatement queryExercises;

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
            System.out.println("JDBC connection error:/n" + e.getMessage());
            return null;
        }
    }

}