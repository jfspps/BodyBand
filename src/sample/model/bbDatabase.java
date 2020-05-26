package sample.model;

import javafx.collections.ObservableList;

import javax.xml.transform.Result;
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
    public static final String querySelectBandStat = "SELECT SingleBandTension, DoubledOrNot, Units FROM" +
            " tblBandStat WHERE SingleBandTension = ? AND DoubledOrNot = ? AND Units = ?";
    public static final String querySelectRepetition = "SELECT BandStat_id, Repetitions FROM tblRepetition " +
            "WHERE BandStat_id = ? AND Repetitions = ?";
    public static final String querySelectSet = "SELECT Exercise_id, Rep_id, Comments, SetDate FROM tblSet WHERE " +
            "Exercise_id = ? AND Rep_id = ? AND Comments = ? AND SetDate = ?";

    //find particular records by primary key
    public static final String queryBandStatKey = "SELECT * FROM tblBandStat WHERE idBandStat = ?";
    public static final String queryExerciseKey = "SELECT * FROM tblExercise WHERE idExercise = ?";
    public static final String queryRepetitionKey = "SELECT * FROM tblRepetition WHERE idRepetition = ?";

    //Precompiled SQL statements with PreparedStatement -------------------------------------
    private Connection conn;
    //Prepared statements
    private PreparedStatement insertExercise;
    private PreparedStatement insertBandStat;
    private PreparedStatement insertRepetition;
    private PreparedStatement insertSet;
    private PreparedStatement selectExercise;
    private PreparedStatement selectBandStat;
    private PreparedStatement selectRepetition;
    private PreparedStatement selectSet;
    private PreparedStatement selectBandStatKey;
    private PreparedStatement selectExerciseKey;
    private PreparedStatement selectRepetitionKey;

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
            selectBandStat = conn.prepareStatement(querySelectBandStat);
            selectRepetition = conn.prepareStatement(querySelectRepetition);
            selectSet = conn.prepareStatement(querySelectSet);
            selectBandStatKey = conn.prepareStatement(queryBandStatKey);
            selectExerciseKey = conn.prepareStatement(queryExerciseKey);
            selectRepetitionKey = conn.prepareStatement(queryRepetitionKey);
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
            if (selectExercise != null) {
                selectExercise.close();
            }
            if (selectBandStat != null) {
                selectBandStat.close();
            }
            if (selectRepetition != null) {
                selectRepetition.close();
            }
            if (selectSet != null) {
                selectSet.close();
            }
            if (selectBandStatKey != null) {
                selectBandStatKey.close();
            }
            if (selectExerciseKey != null) {
                selectExerciseKey.close();
            }
            if (selectRepetitionKey != null) {
                selectRepetitionKey.close();
            }
            //lastly, close conn
            if (conn != null) {
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error closing connections://n" + e.getMessage());
            return false;
        }
    }

    // Object instance which provides JavaFX access to bbDatabase methods ----------------------------------------
    // call any of the methods below from an external class (JavaFX controller) using bbDatabase.getInstance
    // .methodName()
    // this is a Singleton Pattern (static is needed), where multiple JavaFX Controllers can only access one instance
    // of bbDatabase

    // create the instance here instead of in the getter (this approach is more thread-safe than instantiating in
    // getInstance())
    private static bbDatabase instance = new bbDatabase();
    private bbDatabase(){
        //empty constructor
    }
    public static bbDatabase getInstance(){
        return instance;
    }

    // Object exercises which provides JavaFX access to all exercises on file
//    private ObservableList<bbExercise> exercises;
//    public  ObservableList<bbExercise> getExercises(){
//        return exercises;
//    }
    //can also implement add and remove exercises similarly



    // General SQL queries (SQL to Java object) --------------------------------------------------

    //tblExercise ==============================================
    public List<bbExercise> listAllExercises() {
        //note that conn is still open after this method returns
        //'statement' and 'results' are released on return
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllExercises)) {
            List<bbExercise> exercises = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbExercise exercise = new bbExercise();
                exercise.setExerciseId(results.getInt(ExerciseIdINDEX));
                exercise.setExerciseName(results.getString(ExerciseNameINDEX));
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
                              String desc, String videoURL) {
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

            if (rowCount > 0) {
//                System.out.println(name + " is already on file");
                resultSet.close();
                return rowCount;
            } else if (rowCount == 0) {
//                System.out.println(name + " not on file");
                resultSet.close();
                return 0;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("onFile problem querying tblExercise:\n" + e.getMessage());
            return -1;
        }
    }

    public ResultSet exerciseOnFileKey(int idExercise) {
//        System.out.println("Trying to find exercise with id = " + idExercise);
        try {
            selectExerciseKey.setInt(1, idExercise);
            ResultSet resultSet = selectExerciseKey.executeQuery();

            if (resultSet.next()) {
                System.out.println("Record with the key " + idExercise + " found");
                return resultSet;
            } else {
                System.out.println("No record with the key " + idExercise + " found");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Problem with querying tblExercise:\n" + e.getMessage());
            return null;
        }
    }

    public List<bbExercise> exerciseOnFileKeyList(int idExercise){
        try {
            selectExerciseKey.setInt(1, idExercise);
            ResultSet resultSet = selectExerciseKey.executeQuery();

            List<bbExercise> exerciseArray = new ArrayList<>();
            if (resultSet.next()) {
                bbExercise tempEx = new bbExercise();
                tempEx.setExerciseId(resultSet.getInt(1)); //records and their PKs may get deleted over time
                tempEx.setExerciseName(resultSet.getString(2));
                tempEx.setAnchorNeeded(resultSet.getString(3));
                tempEx.setAnchorHeight(resultSet.getString(4));
                tempEx.setAnchorPosition(resultSet.getString(5));
                tempEx.setExerciseDesc(resultSet.getString(6));
                tempEx.setVideoURL(resultSet.getString(7));
                exerciseArray.add(tempEx);
            } else {
                System.out.println("No record with the key " + idExercise + " found");
                return null;
            }
            return exerciseArray;
        } catch (SQLException e) {
            System.out.println("Problem with querying tblExercise:\n" + e.getMessage());
            return null;
        }
    }

    //tblBandStat ==========================================
    public List<bbBandStat> listAllBandstats() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllBandStats)) {
            List<bbBandStat> bandStats = new ArrayList<>();

            while (results.next()) {
                bbBandStat bandStat = new bbBandStat();
                bandStat.setBandStatId(results.getInt(BandStatIdINDEX));
                bandStat.setTension(results.getInt(BandStatSingleBandTensionINDEX));
                bandStat.setDoubledOrNot(results.getString(BandStatDoubledOrNotINDEX));
                bandStat.setUnits(results.getString(BandStatUnitsINDEX));
                bandStats.add(bandStat);
            }
            return bandStats;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblBandStat:\n" + e.getMessage());
            return null;
        }
    }

    public int bandStatOnFile(int tension, String doubledOrNot, String units) {
        try {
            selectBandStat.setInt(1, tension);
            selectBandStat.setString(2, doubledOrNot);
            selectBandStat.setString(3, units);

            //returns the rows with this record
            ResultSet resultSet = selectBandStat.executeQuery();
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
//                System.out.println("Band stat already on file");
                resultSet.close();
                return rowCount;
            } else if (rowCount == 0) {
//                System.out.println("Band stat not on file");
                resultSet.close();
                return 0;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("onFile problem querying tblBandStat:\n" + e.getMessage());
            return -1;
        }
    }

    public ResultSet bandStatOnFileKey(int idBandStat) {
//        System.out.println("Trying to find exercise with id = " + idBandStat);
        try {
            selectBandStatKey.setInt(1, idBandStat);
            ResultSet resultSet = selectBandStatKey.executeQuery();

            if (resultSet.next()) {
                System.out.println("Record with the key " + idBandStat + " found");
                return resultSet;
            } else {
                System.out.println("No record with the key " + idBandStat + " found");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Problem with querying tblBandStat:\n" + e.getMessage());
            return null;
        }
    }

    //tblRepetition ===========================================
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

    public int repetitionOnFile(int bandStatId, int repetitions) {
        //compared to other query methods, repetitionOnFile also checks tblBandStat using bandStatOnFileKey(int idBandStat)
        try (ResultSet bandStatPack = bandStatOnFileKey(bandStatId)) {
            if (bandStatPack == null) {
                System.out.println("The given bandStatId, " + bandStatId + ", is not found");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Error with bandStatId in repetition parameter list\n" + err.getMessage());
        }

        try {
            selectRepetition.setInt(1, bandStatId);
            selectRepetition.setInt(2, repetitions);

            //returns the rows with this record
            ResultSet resultSet = selectRepetition.executeQuery();
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
//                System.out.println("Repetitions already on file");
                resultSet.close();
                return rowCount;
            } else if (rowCount == 0) {
//                System.out.println("Repetitions not on file");
                resultSet.close();
                return 0;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("on File problem querying tblRepetitions:\n" + e.getMessage());
            return -1;
        }
    }

    public ResultSet repetitionOnFileKey(int idRepetition) {
//        System.out.println("Trying to find exercise with id = " + idRepetition);
        try {
            selectRepetitionKey.setInt(1, idRepetition);
            ResultSet resultSet = selectRepetitionKey.executeQuery();

            if (resultSet.next()) {
                System.out.println("Record with the key " + idRepetition + " found");
                return resultSet;
            } else {
                System.out.println("No record with the key " + idRepetition + " found");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Problem with querying tblRepetition:\n" + e.getMessage());
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

    public int setOnFile(int exerciseId, int repId, String comments, String setDate) {
        try {
            selectSet.setInt(1, exerciseId);
            selectSet.setInt(2, repId);
            selectSet.setString(3, comments);
            selectSet.setString(4, setDate);

            //returns the rows with this record
            ResultSet resultSet = selectSet.executeQuery();
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
//                System.out.println("Set is already on file");
                resultSet.close();
                return rowCount;
            } else if (rowCount == 0) {
//                System.out.println("Set not on file");
                resultSet.close();
                return 0;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("on File problem querying tblSet:\n" + e.getMessage());
            return -1;
        }
    }

    // Insertion methods -------------return value is the index of the inserted record---------------------------------

    //the controller would read all given values on a form, verify the correct Java type and then assign "" to blank entries

    public int insertNewExercise(String name, String anchorNeeded, String anchorHeight, String anchorPosition,
                                 String desc, String videoURL) {
        //check if the exercise already exists (returns 0 if none, and 1 if present)
        int index = exerciseOnFile(name, anchorNeeded, anchorHeight, anchorPosition, desc, videoURL);
        if (index == 1) {
            System.out.println(name + " already exists on file. No further changes made.");
            return -1;
        }

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

            if (insertedRecord != 1) {
                throw new SQLException("Could not insert exercise");
            }

//            System.out.println("New exercise added, getting the ID...");

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertExercise.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println(name + " id: " + generatedKeys.getInt(1));
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new exercise, " + name);
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting exercise\n" + err.getMessage());
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }

    public int insertNewBandStat(int singleBandTension, String doubledOrNot, String units) {
        //check if the BandStat already exists (returns 0 if none, and 1 if present)
        int index = bandStatOnFile(singleBandTension, doubledOrNot, units);
        if (index == 1) {
            System.out.println(singleBandTension + " " + units + ", doubled:" + doubledOrNot + ", already on" +
                    " file, no further changes made.");
            return -1;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (singleBandTension <= 0) {
            System.out.println("Band tension needed");
            return -1;
        }

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertBandStat.setInt(1, singleBandTension);
            insertBandStat.setString(2, doubledOrNot);
            insertBandStat.setString(3, units);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertBandStat.executeUpdate();

            if (insertedRecord != 1) {
                throw new SQLException("Could not insert Band stat");
            }
//            System.out.println("New band stat added, getting the ID...");

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertBandStat.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("New band stat id: " + generatedKeys.getInt(1));
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new band stat");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting new BandStat\n" + err.getMessage());
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }

    public int insertNewRepetition(int bandStatId, int repetitions) {
        //check if band stat from tblBandStat actually exists
        try (ResultSet band = bandStatOnFileKey(bandStatId)) {
            if (band == null) {
                System.out.println("The bandStat with id " + bandStatId + " is not in its table");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Problem finding bandStat in tblBandStat with given ID\n" + err.getMessage());
        }

        //check if the Repetition already exists in tblRepetition (returns 0 if none, and 1 if present)
        int index = repetitionOnFile(bandStatId, repetitions);
        if (index == 1) {
            System.out.println(repetitions + " with band stat " + bandStatId + " already exists on " +
                    "file, no further changes made.");
            return -1;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (bandStatId == 0) {
            System.out.println("Band stat info needed ( >=1 )");
            return -1;
        }
        if (repetitions == 0) {
            System.out.println("Rep count needed ( >=1 )");
            return -1;
        }

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertRepetition.setInt(1, bandStatId);
            insertRepetition.setInt(2, repetitions);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertRepetition.executeUpdate();

            if (insertedRecord != 1) {
                throw new SQLException("Could not insert rep record");
            }
//            System.out.println("New rep added, getting the ID...");

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertRepetition.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("New rep id: " + generatedKeys.getInt(1));
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new rep record");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting repetition\n" + err.getMessage());
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }

    public int insertNewSet(int exerciseId, int repId, String comments, String setDate) {
        //check if exercise from tblExercise actually exists
        try (ResultSet exercise = exerciseOnFileKey(exerciseId)) {
            if (exercise == null) {
                System.out.println("The exercise with id " + exerciseId + " is not in its table");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Problem finding exercise in tblExercise with given ID\n" + err.getMessage());
        }

        //check if repetition from tblRepetition actually exists
        try (ResultSet repetition = repetitionOnFileKey(repId)) {
            if (repetition == null) {
                System.out.println("The repetition with id " + repId + " is not in its table");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Problem finding repetition in tblRepetition with given ID\n" + err.getMessage());
        }

        //check if the set already exists in tblSet (returns 0 if none, and 1 if present)
        int index = setOnFile(exerciseId, repId, comments, setDate);
        if (index == 1) {
            System.out.println("This set dated " + setDate + " already on file, no further changes made.");
            return -1;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (exerciseId == 0) {
            System.out.println("Exercise info needed ( >0 )");
            return -1;
        }
        if (repId == 0) {
            System.out.println("Rep info needed ( >0 )");
            return -1;
        }
        if (setDate == null) {
            System.out.println("Date of workout needed");
            return -1;
        }

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertSet.setInt(1, exerciseId);
            insertSet.setInt(2, repId);
            insertSet.setString(3, comments);
            insertSet.setString(4, setDate);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertSet.executeUpdate();

            if (insertedRecord != 1) {
                throw new SQLException("Could not insert set record");
            }
//            System.out.println("New set added, getting the ID...");

            //find the key of the inserted record and return it
            ResultSet generatedKeys = insertSet.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("New set id: " + generatedKeys.getInt(1));
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Could not get ID for new set record");
            }
        } catch (SQLException err) {
            System.out.println("Error with inserting set\n" + err.getMessage());
            //one can conn.rollback() in another try-catch block
            return -1;
        }
    }
}