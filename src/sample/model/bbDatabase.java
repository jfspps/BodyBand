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
    //SELECT * queries
    public static final String querySelectAllExercises = "SELECT * FROM tblExercise";
    public static final String querySelectAllRepetitions = "SELECT * FROM tblRepetition";
    public static final String querySelectAllBandStats = "SELECT * FROM tblBandStat";
    public static final String querySelectAllSets = "SELECT * FROM tblSet";

    //INSERT queries
    public static final String queryInsertExercise = "INSERT INTO tblExercise " +
            "(ExerciseName, AnchorNeeded, AnchorHeight, AnchorPosition, Description, VideoURL) " +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String queryInsertBandStat = "INSERT INTO tblBandStat " +
            "(SingleBandTension, DoubledOrNot, Units) " +
            "VALUES(?, ?, ?)";
    public static final String queryInsertRepetition = "INSERT INTO tblRepetition " +
            "(BandStat_id, Repetitions) VALUES(?, ?)";
    public static final String queryInsertSet = "INSERT INTO tblSet " +
            "(Exercise_id, Rep_id, Comments, SetDate) VALUES(?, ?, ?, ?)";

    //find particular records
    public static final String querySelectExercise = "SELECT ExerciseName, AnchorNeeded, AnchorHeight, " +
            "AnchorPosition, Description, VideoURL FROM tblExercise WHERE ExerciseName = ? AND" +
            " AnchorNeeded = ? AND AnchorHeight = ? AND AnchorPosition = ? AND Description = ? AND VideoURL = ?";

    //Precompiled SQL statements with PreparedStatement -------------------------------------
    private Connection conn;
    //Prepared statements
    private PreparedStatement insertExercise;
    private PreparedStatement insertBandStat;
    private PreparedStatement insertRepetition;
    private PreparedStatement insertSet;
    private PreparedStatement selectExercise;

    //bbDatabase admin routines -------------------------------------------------------------
    //open() is called by main() first and sets up conn
    public boolean open() {
        try {
            //initiate the connection conn and all Statements/PreparedStatements here
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertExercise = conn.prepareStatement(queryInsertExercise);
            insertBandStat = conn.prepareStatement(queryInsertBandStat);
            insertRepetition = conn.prepareStatement(queryInsertRepetition);
            insertSet = conn.prepareStatement(queryInsertSet);
            selectExercise = conn.prepareStatement(querySelectExercise);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection error:\n" + e.getMessage());
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
            if (insertBandStat != null) {
                insertBandStat.close();
            }
            if (insertRepetition != null) {
                insertRepetition.close();
            }
            if (insertSet != null) {
                insertSet.close();
            }
            if (selectExercise != null){
                selectExercise.close();
            }
            //lastly, close conn
            if (conn != null){
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error closing connections://n" + e.getMessage());
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
            System.out.println("JDBC connection error to tblExercises:\n" + e.getMessage());
            return null;
        }
    }

    public int exerciseOnFile(String name, String anchorNeeded, String anchorHeight, String anchorPosition,
                                  String desc, String videoURL){
        try {
            selectExercise.setString(1, name);
            selectExercise.setString(2, anchorNeeded);
            selectExercise.setString(3, anchorHeight);
            selectExercise.setString(4, anchorPosition);
            selectExercise.setString(5, desc);
            selectExercise.setString(6, videoURL);

            //returns the rows with this record
            ResultSet resultSet = selectExercise.executeQuery();
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }
            System.out.println(rowCount + " records found");

            if(rowCount > 0){
                System.out.println("Exercise is already on file");
                resultSet.close();
                return rowCount;
            } else if(rowCount == 0)
            {
                System.out.println("Exercise not on file");
                resultSet.close();
                return 0;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("Problem with querying tblExercise:\n" + e.getMessage());
            return -1;
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
            System.out.println("JDBC connection error to tblRepetition:\n" + e.getMessage());
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
            System.out.println("JDBC connection error to tblBandStat://n" + e.getMessage());
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
            System.out.println("JDBC connection error to tblSet:\n" + e.getMessage());
            return null;
        }
    }

    // Insertion methods -------------return value is the index of the inserted record---------------------

    //the GUI would read all given values on a form, verify the correct Java type and then assign null to blank entries

    public int insertNewExercise(String name, String anchorNeeded, String anchorHeight, String anchorPosition,
                                  String desc, String videoURL) {
        //testing for null is eventually handled by the controller and used here for test purposes
        if (name == null) {
            System.out.println("Name of exercise needed");
            return -1;
        }

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

    public int insertNewBandStat(Integer singleBandTension, Integer doubledOrNot, String units) {
        //testing for null is eventually handled by the controller and used here for test purposes
        if (singleBandTension == null) {
            System.out.println("Band tension needed");
            return -1;
        }
        if (doubledOrNot == 0) {
            System.out.println("Doubling info needed");
            return -1;
        }

        //include code to check if the record already exists

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertBandStat.setInt(1, singleBandTension);
            insertBandStat.setInt(2, doubledOrNot);
            insertBandStat.setString(3, units);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertBandStat.executeUpdate();

            if(insertedRecord != 1){
                throw new SQLException("Could not insert Band stat");
            }

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertBandStat.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new band stat");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting record");
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }

    public int insertNewRepetition(Integer bandStatId, Integer repetitions) {
        //testing for null is eventually handled by the controller and used here for test purposes
        if (bandStatId == null) {
            System.out.println("Band stat info needed");
            return -1;
        }
        if (repetitions == 0) {
            System.out.println("Rep count needed");
            return -1;
        }

        //include code to check if the record already exists

        //include code which looks up the correct BandStat

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertRepetition.setInt(1, bandStatId);
            insertRepetition.setInt(2, repetitions);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertRepetition.executeUpdate();

            if(insertedRecord != 1){
                throw new SQLException("Could not insert rep record");
            }

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertRepetition.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new rep record");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting record");
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }

    public int insertNewSet(Integer exerciseId, Integer repId, String comments, String setDate) {
        //testing for null is eventually handled by the controller and used here for test purposes
        if (exerciseId == null) {
            System.out.println("Exercise info needed");
            return -1;
        }
        if (repId == 0) {
            System.out.println("Rep info needed");
            return -1;
        }
        if (setDate == null) {
            System.out.println("Date of workout needed");
            return -1;
        }

        //include code to check if the record already exists

        //include code which looks up the correct IDs for exercises and reps

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertSet.setInt(1, exerciseId);
            insertSet.setInt(2, repId);
            insertSet.setString(1, comments);
            insertSet.setString(2, setDate);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertSet.executeUpdate();

            if(insertedRecord != 1){
                throw new SQLException("Could not insert rep record");
            }

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertSet.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new set record");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting record");
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }
}