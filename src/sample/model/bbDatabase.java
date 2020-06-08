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
    public static final int ExerciseMuscleGroupINDEX = 3;
    public static final int ExerciseAnchorNeededINDEX = 4;
    public static final int ExerciseAnchorHeightINDEX = 5;
    public static final int ExerciseAnchorPositionINDEX = 6;
    public static final int ExerciseDescINDEX = 7;
    public static final int ExerciseVideoURLINDEX = 8;

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

    //SELECT the first entry
    public static final String querySelectFirstExercise = "SELECT * FROM tblExercise LIMIT 1";
    public static final String querySelectFirstRepetition = "SELECT * FROM tblRepetition LIMIT 1";
    public static final String querySelectFirstBandStat = "SELECT * FROM tblBandStat LIMIT 1";
    public static final String querySelectFirstSet = "SELECT * FROM tblSet LIMIT 1";

    //INSERT queries
    public static final String queryInsertExercise = "INSERT INTO tblExercise " +
            "(ExerciseName, MuscleGroup, AnchorNeeded, AnchorHeight, AnchorPosition, Description, VideoURL) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String queryInsertBandStat = "INSERT INTO tblBandStat " +
            "(SingleBandTension, DoubledOrNot, Units) " +
            "VALUES(?, ?, ?)";
    public static final String queryInsertRepetition = "INSERT INTO tblRepetition " +
            "(BandStat_id, Repetitions) VALUES(?, ?)";
    public static final String queryInsertSet = "INSERT INTO tblSet " +
            "(Exercise_id, Rep_id, Comments, SetDate) VALUES(?, ?, ?, ?)";

    //UPDATE queries (foreign keys cannot be updated)
    public static final String queryUpdateExercise = "UPDATE tblExercise SET ExerciseName = ?, MuscleGroup = ?, " +
            "AnchorNeeded = ?, AnchorHeight = ?, AnchorPosition = ?, Description = ?, VideoURL = ? WHERE " +
            "idExercise = ?";
    public static final String queryUpdateBandStat = "UPDATE tblBandStat SET SingleBandTension = ?, DoubledOrNot =" +
            " ?, Units = ? WHERE idBandStat = ?";
    public static final String queryUpdateRepetition = "UPDATE tblRepetition SET Repetitions = ? WHERE idRepetition =" +
            " ?";
    public static final String queryUpdateSet = "UPDATE tblSet SET Comments = ?, SetDate = ? WHERE idSet = ?";

    //DELETE queries (records from tblRepetition and tblSet are deleted ON CASCADE)
    public static final String queryDeleteExercise = "DELETE FROM tblExercise WHERE idExercise = ?";
    public static final String queryDeleteBandStat = "DELETE FROM tblBandStat WHERE idBandStat = ?";
    public static final String queryDeleteRepetition = "DELETE FROM tblRepetition WHERE idRepetition = ?";
    public static final String queryDeleteSet = "DELETE FROM tblSet WHERE idSet = ?";

    //find particular records
    public static final String querySelectExercise = "SELECT ExerciseName, MuscleGroup, AnchorNeeded, AnchorHeight, " +
            "AnchorPosition, Description, VideoURL FROM tblExercise WHERE ExerciseName = ? AND MuscleGroup = ? AND" +
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
    public static final String querySetKey = "SELECT * FROM tblSet WHERE idSet = ?";

    //find record id
    public static final String querySelectExerciseId = "SELECT idExercise FROM tblExercise WHERE ExerciseName = ? AND" +
            " MuscleGroup = ? AND AnchorNeeded = ? AND AnchorHeight = ? AND AnchorPosition = ? AND Description = ? " +
            "AND VideoURL = ?";
    public static final String querySelectBandStatId = "SELECT idBandStat FROM tblBandStat WHERE SingleBandTension = " +
            "? AND DoubledOrNot = ? AND Units = ?";
    public static final String querySelectRepetitionId = "SELECT idRepetition FROM tblRepetition WHERE BandStat_id = " +
            "? AND Repetitions = ?";
    public static final String querySelectSetId = "SELECT idSet FROM tblSet WHERE Exercise_id = ? AND Rep_id = ? AND " +
            "Comments = ? AND SetDate = ?";

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
    private PreparedStatement selectSetKey;
    private PreparedStatement selectExerciseId;
    private PreparedStatement selectBandStatId;
    private PreparedStatement selectRepetitionId;
    private PreparedStatement selectSetId;
    private PreparedStatement updateExercise;
    private PreparedStatement updateBandStat;
    private PreparedStatement updateRepetition;
    private PreparedStatement updateSet;
    private PreparedStatement deleteExercise;
    private PreparedStatement deleteBandStat;
    private PreparedStatement deleteRepetition;
    private PreparedStatement deleteSet;

    //bbDatabase admin routines -------------------------------------------------------------
    /**Initialises the JDBC driver and all PrepareStatements */
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
            selectSetKey = conn.prepareStatement(querySetKey);
            selectExerciseId = conn.prepareStatement(querySelectExerciseId);
            selectBandStatId = conn.prepareStatement(querySelectBandStatId);
            selectRepetitionId = conn.prepareStatement(querySelectRepetitionId);
            selectSetId = conn.prepareStatement(querySelectSetId);
            updateExercise = conn.prepareStatement(queryUpdateExercise);
            updateBandStat = conn.prepareStatement(queryUpdateBandStat);
            updateRepetition = conn.prepareStatement(queryUpdateRepetition);
            updateSet = conn.prepareStatement(queryUpdateSet);
            deleteExercise = conn.prepareStatement(queryDeleteExercise);
            deleteBandStat = conn.prepareStatement(queryDeleteBandStat);
            deleteRepetition = conn.prepareStatement(queryDeleteRepetition);
            deleteSet = conn.prepareStatement(queryDeleteSet);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection error:\n" + e.getMessage());
            return false;
        }
    }

    /**Frees all PrepareStatement resources and closes the JDBC connection*/
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
            if (selectSetKey != null) {
                selectSetKey.close();
            }
            if (selectExerciseId != null) {
                selectExerciseId.close();
            }
            if (selectBandStatId != null) {
                selectBandStatId.close();
            }
            if (selectRepetitionId != null) {
                selectRepetitionId.close();
            }
            if (selectSetId != null) {
                selectSetId.close();
            }
            if (updateExercise != null) {
                updateExercise.close();
            }
            if (updateBandStat != null) {
                updateBandStat.close();
            }
            if (updateRepetition != null) {
                updateRepetition.close();
            }
            if (updateSet != null) {
                updateSet.close();
            }
            if (deleteExercise != null) {
                deleteExercise.close();
            }
            if (deleteBandStat != null) {
                deleteBandStat.close();
            }
            if (deleteRepetition != null) {
                deleteRepetition.close();
            }
            if (deleteSet != null){
                deleteSet.close();
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

    /**Grants access to bbDatabase methods (call bbDatabase.getInstance().method_name)*/
    public static bbDatabase getInstance(){
        return instance;
    }

    // General SQL queries (SQL to Java object) --------------------------------------------------

    //tblExercise ==================================================================================

    /**Returns a List<> of all exercises and their fields on file. Returns null if none found.*/
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
                exercise.setMuscleGroup(results.getString(ExerciseMuscleGroupINDEX));
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

    /**Returns the index of the first exercise on file. Returns 0 no records found and -1 if an exception was caught.*/
    public int getFirstExercise(){
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstExercise)) {

            if(results.next()) {
                return results.getInt(1);
            }
            System.out.println("No exercises on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblExercises:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the number of records with supplied fields, on file. Returns -1 if an exception was caught.*/
    public int exerciseOnFile(String name, String muscleGroup, String anchorNeeded, String anchorHeight,
                              String anchorPosition,
                              String desc, String videoURL) {
        try {
            selectExercise.setString(1, name);
            selectExercise.setString(2, muscleGroup);
            selectExercise.setString(3, anchorNeeded);
            selectExercise.setString(4, anchorHeight);
            selectExercise.setString(5, anchorPosition);
            selectExercise.setString(6, desc);
            selectExercise.setString(7, videoURL);

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
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("onFile problem querying tblExercise:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.*/
    public int exerciseOnFileId(String name, String muscleGroup, String anchorNeeded, String anchorHeight,
                              String anchorPosition,
                              String desc, String videoURL) {
        try {
            selectExerciseId.setString(1, name);
            selectExerciseId.setString(2, muscleGroup);
            selectExerciseId.setString(3, anchorNeeded);
            selectExerciseId.setString(4, anchorHeight);
            selectExerciseId.setString(5, anchorPosition);
            selectExerciseId.setString(6, desc);
            selectExerciseId.setString(7, videoURL);

            //this returns a ResultSet with one field, idExercise
            ResultSet resultSet = selectExerciseId.executeQuery();

            if (resultSet.next()) {
                System.out.println("Exercise found with id " + resultSet.getString(1));
                return resultSet.getInt(1);
            } else {
                System.out.println("Exercise not found");
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("onFileId problem querying tblExercise:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns a ResultSet of the exercise record with the supplied primary key idExercise. Returns null if none
     * found or if an exception is caught.*/
    public ResultSet exerciseOnFileKey(int idExercise) {
//        System.out.println("Trying to find exercise with id = " + idExercise);
        try {
            selectExerciseKey.setInt(ExerciseIdINDEX, idExercise);
            ResultSet resultSet = selectExerciseKey.executeQuery();

            if (resultSet.next()) {
//                System.out.println("Record with the key " + idExercise + " found");
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

    /**Returns a List<bbExercise> with the given primary key. Returns null if none
     * found or if an exception is caught.*/
    public List<bbExercise> exerciseOnFileKeyList(int idExercise){
        try {
            selectExerciseKey.setInt(ExerciseIdINDEX, idExercise);
            ResultSet resultSet = selectExerciseKey.executeQuery();

            List<bbExercise> exerciseArray = new ArrayList<>();
            if (resultSet.next()) {
                bbExercise tempEx = new bbExercise();
                tempEx.setExerciseId(resultSet.getInt(ExerciseIdINDEX)); //records and their PKs may get deleted over time
                tempEx.setExerciseName(resultSet.getString(ExerciseNameINDEX));
                tempEx.setMuscleGroup(resultSet.getString(ExerciseMuscleGroupINDEX));
                tempEx.setAnchorNeeded(resultSet.getString(ExerciseAnchorNeededINDEX));
                tempEx.setAnchorHeight(resultSet.getString(ExerciseAnchorHeightINDEX));
                tempEx.setAnchorPosition(resultSet.getString(ExerciseAnchorPositionINDEX));
                tempEx.setExerciseDesc(resultSet.getString(ExerciseDescINDEX));
                tempEx.setVideoURL(resultSet.getString(ExerciseVideoURLINDEX));
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

    //tblBandStat ========================================================================================

    /**Returns a List<> of all band stats and their fields on file. Returns null if none found.*/
    public List<bbBandStat> listAllBandStats() {
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

    /**Returns the index of the first band stat on file. Returns 0 no records found and -1 if an exception was caught.*/
    public int getFirstBandStat(){
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstBandStat)) {

            if(results.next()) {
                return results.getInt(1);
            }
            System.out.println("No band stats on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblBandStats:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the number of records with supplied fields, on file. Returns -1 if an exception was caught.*/
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
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("onFile problem querying tblBandStat:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.*/
    public int bandStatOnFileId(Integer tension, String doubled, String units) {
        try {
            selectBandStatId.setInt(1, tension);
            selectBandStatId.setString(2, doubled);
            selectBandStatId.setString(3, units);

            //this returns a ResultSet with one field, idExercise
            ResultSet resultSet = selectBandStatId.executeQuery();

            if (resultSet.next()) {
                System.out.println("Band stat found with id " + resultSet.getString(1));
                return resultSet.getInt(1);
            } else {
                System.out.println("Band stat not found");
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("onFileId problem querying tblBandStat:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns a ResultSet of the band stat record with the supplied primary key idBandStat. Returns null if none
     * found or if an exception is caught.*/
    public ResultSet bandStatOnFileKey(int idBandStat) {
//        System.out.println("Trying to find exercise with id = " + idBandStat);
        try {
            selectBandStatKey.setInt(1, idBandStat);
            ResultSet resultSet = selectBandStatKey.executeQuery();

            if (resultSet.next()) {
//                System.out.println("Record with the key " + idBandStat + " found");
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

    /**Returns a List<> of all repetitions and their fields on file. Returns null if none found.*/
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

    /**Returns the index of the first repetition on file. Returns 0 no records found and -1 if an exception was caught.*/
    public int getFirstRepetition(){
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstRepetition)) {

            if(results.next()) {
                return results.getInt(1);
            }
            System.out.println("No repetitions on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblRepetitions:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.*/
    public int repetitionOnFileId(Integer bandStatID, Integer reps) {
        try {
            selectRepetitionId.setInt(1, bandStatID);
            selectRepetitionId.setInt(2, reps);

            //this returns a ResultSet with one field, idExercise
            ResultSet resultSet = selectRepetitionId.executeQuery();

            if (resultSet.next()) {
                System.out.println("Repetition found with id " + resultSet.getString(1));
                return resultSet.getInt(1);
            } else {
                System.out.println("Repetition not found");
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("onFileId problem querying tblRepetition:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the number of records with supplied fields, on file.
     * Compared to most of the other query methods, repetitionOnFile also checks tblBandStat using bandStatOnFileKey
     * (int idBandStat). Returns -1 if an exception was caught.
     */
    public int repetitionOnFile(int bandStatId, int repetitions) {

        try (ResultSet bandStatPack = bandStatOnFileKey(bandStatId)) {
            if (bandStatPack == null) {
                System.out.println("The given bandStatId, " + bandStatId + ", was not found");
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
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("on File problem querying tblRepetitions:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns a ResultSet of the repetition record with the supplied primary key idRepetition. Returns null if none
     * found or if an exception is caught.*/
    public ResultSet repetitionOnFileKey(int idRepetition) {
//        System.out.println("Trying to find exercise with id = " + idRepetition);
        try {
            selectRepetitionKey.setInt(1, idRepetition);
            ResultSet resultSet = selectRepetitionKey.executeQuery();

            if (resultSet.next()) {
//                System.out.println("Record with the key " + idRepetition + " found");
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

    //tblSet =========================================================================================

    /**Returns a List<> of all sets and their fields on file. Returns null if none found.*/
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

    /**Returns the index of the first set on file. Returns 0 no records found and -1 if an exception was caught.*/
    public int getFirstSet(){
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstSet)) {

            if(results.next()) {
                return results.getInt(1);
            }
            System.out.println("No sets on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblSets:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.*/
    public int setOnFileId(Integer exerciseID, Integer repID, String comments, String setDate) {
        try {
            selectSetId.setInt(1, exerciseID);
            selectSetId.setInt(2, repID);
            selectSetId.setString(3, comments);
            selectSetId.setString(4, setDate);

            //this returns a ResultSet with one field, idExercise
            ResultSet resultSet = selectSetId.executeQuery();

            if (resultSet.next()) {
                System.out.println("Set found with id " + resultSet.getString(1));
                return resultSet.getInt(1);
            } else {
                System.out.println("Set not found");
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("onFileId problem querying tblSet:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns the number of records with supplied fields, on file.
     * Compared to most of the other query methods, setOnFile also checks tblRepetition and tblExercise using
     * repetitionOnFileKey and exerciseOnFileKey, respectively. Returns -1 if an exception was caught. */
    public int setOnFile(int exerciseId, int repId, String comments, String setDate) {
        try (ResultSet exercisePack = exerciseOnFileKey(exerciseId)) {
            if (exercisePack == null) {
                System.out.println("The given exerciseId, " + exerciseId + ", was not found");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Error with exerciseId in set parameter list\n" + err.getMessage());
        }

        try (ResultSet repetitionPack = repetitionOnFileKey(repId)) {
            if (repetitionPack == null) {
                System.out.println("The given repetition, " + repId + ", was not found");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Error with repetitionId in set parameter list\n" + err.getMessage());
        }

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
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("on File problem querying tblSet:\n" + e.getMessage());
            return -1;
        }
    }

    /**Returns a ResultSet of the set record with the supplied primary key idSet. Returns null if none
     * found or if an exception is caught.*/
    public ResultSet setOnFileKey(int idSet) {
//        System.out.println("Trying to find set with id = " + idSet);
        try {
            selectSetKey.setInt(1, idSet);
            ResultSet resultSet = selectSetKey.executeQuery();

            if (resultSet.next()) {
//                System.out.println("Record with the key " + idSet + " found");
                return resultSet;
            } else {
                System.out.println("No record with the key " + idSet + " found");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Problem with querying tblSet:\n" + e.getMessage());
            return null;
        }
    }

    // Insertion methods -------------return value is the index of the inserted record---------------------------------

    //the controller would read all given values on a form, verify the correct Java type and then assign "" to blank entries

    /**Inserts a new exercise, first by checking of the supplied fields match any record on file. Returns the primary
     *  key of the inserted record, 0 if the exercise already exists, -2 if name is blank or null, and -1 if an
     *  exception was caught*/
    public int insertNewExercise(String name, String muscleGroup, String anchorNeeded, String anchorHeight,
                                 String anchorPosition,
                                 String desc, String videoURL) {
        //check if the exercise already exists (returns 0 if none, and 1 if present)
        int index = exerciseOnFile(name, muscleGroup, anchorNeeded, anchorHeight, anchorPosition, desc, videoURL);
        if (index == 1) {
            System.out.println(name + " already exists on file. No further changes made.");
            return 0;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (name == null || name.isBlank()) {
            System.out.println("Name of exercise needed");
            return -1;
        }

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertExercise.setString(1, name);
            insertExercise.setString(2, muscleGroup);
            insertExercise.setString(3, anchorNeeded);
            insertExercise.setString(4, anchorHeight);
            insertExercise.setString(5, anchorPosition);
            insertExercise.setString(6, desc);
            insertExercise.setString(7, videoURL);

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

    /**Inserts a new band stat, first by checking of the supplied fields match any record on file. Returns the primary
     *  key of the inserted record, 0 if the band stat already exists, -2 if singleBandTension is <= 0, and -1 if an
     *  exception was caught*/
    public int insertNewBandStat(int singleBandTension, String doubledOrNot, String units) {
        //check if the BandStat already exists (returns 0 if none, and 1 if present)
        int index = bandStatOnFile(singleBandTension, doubledOrNot, units);
        if (index == 1) {
            System.out.println(singleBandTension + " " + units + ", doubled:" + doubledOrNot + ", already on" +
                    " file, no further changes made.");
            return 0;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (singleBandTension <= 0) {
            System.out.println("Band tension needed");
            return -2;
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

    /**Inserts a new repetition, first by checking of the supplied fields match any record on file. The bandStat
     * record with the supplied bandStatId is also verified on tblBandStat. Returns the primary
     *  key of the inserted record, 0 if repetition already exists, -3 if bandStatId is 0 or missing, -2 if
     *  repetitions is 0, and -1 if an exception was caught.*/
    public int insertNewRepetition(int bandStatId, int repetitions) {
        //check if band stat from tblBandStat actually exists
        try (ResultSet band = bandStatOnFileKey(bandStatId)) {
            if (band == null) {
                System.out.println("The bandStat with id " + bandStatId + " is not in its table");
                return -3;
            }
        } catch (SQLException err) {
            System.out.println("Problem finding bandStat in tblBandStat with given ID\n" + err.getMessage());
            return -1;
        }

        //check if the Repetition already exists in tblRepetition (returns 0 if none, and 1 if present)
        int index = repetitionOnFile(bandStatId, repetitions);
        if (index == 1) {
            System.out.println(repetitions + " with band stat " + bandStatId + " already exists on " +
                    "file, no further changes made.");
            return 0;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (bandStatId == 0) {
            System.out.println("Band stat info needed ( >=1 )");
            return -3;
        }
        if (repetitions == 0) {
            System.out.println("Rep count needed ( >=1 )");
            return -2;
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

    /**Inserts a new exercise, first by checking of the supplied fields match any record on file. The exercise
     * record with the supplied exerciseId is also verified on tblExercise. Returns the primary
     *  key of the inserted record, 0 if set already exists, -2 if exercise is missing or 0, -3 if repetition
     *  is missing or 0, -4 is the date is missing, and -1 if an exception was caught.*/
    public int insertNewSet(int exerciseId, int repId, String comments, String setDate) {
        //check if exercise from tblExercise actually exists
        try (ResultSet exercise = exerciseOnFileKey(exerciseId)) {
            if (exercise == null) {
                System.out.println("The exercise with id " + exerciseId + " is not in its table");
                return -2;
            }
        } catch (SQLException err) {
            System.out.println("Problem finding exercise in tblExercise with given ID\n" + err.getMessage());
            return -1;
        }

        //check if repetition from tblRepetition actually exists
        try (ResultSet repetition = repetitionOnFileKey(repId)) {
            if (repetition == null) {
                System.out.println("The repetition with id " + repId + " is not in its table");
                return -3;
            }
        } catch (SQLException err) {
            System.out.println("Problem finding repetition in tblRepetition with given ID\n" + err.getMessage());
            return -1;
        }

        //check if the set already exists in tblSet (returns 0 if none, and 1 if present)
        int index = setOnFile(exerciseId, repId, comments, setDate);
        if (index == 1) {
            System.out.println("This set dated " + setDate + " already on file, no further changes made.");
            return 0;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (exerciseId == 0) {
            System.out.println("Exercise info needed ( >0 )");
            return -2;
        }
        if (repId == 0) {
            System.out.println("Rep info needed ( >0 )");
            return -3;
        }
        if (setDate == null) {
            System.out.println("Date of workout needed");
            return -4;
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

    // update methods ------------------return value is the index of the updated record ------------------------

    /**Updates the selected exercise. Returns the exerciseID if update successful, 0 if no changes needed or no record
     * found, and -1 if an exception was caught*/
    public int updateExercise(Integer exerciseID, String name, String muscleGroup, String anchorNeeded, String anchorHeight,
                              String anchorPosition,
                              String desc, String videoURL){
        // check the index is already on the DB, return 0 if not
        // (new records would not have NULL values by default, uninitialised records would have NULL fields but this
        // check would prevent the passing of NULL)
        if (exerciseOnFileKey(exerciseID) == null) {
            System.out.println("Record with ID " + exerciseID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateExercise.setString(1, name);
                updateExercise.setString(2, muscleGroup);
                updateExercise.setString(3, anchorNeeded);
                updateExercise.setString(4, anchorHeight);
                updateExercise.setString(5, anchorPosition);
                updateExercise.setString(6, desc);
                updateExercise.setString(7, videoURL);
                updateExercise.setInt(8, exerciseID);

                //should only return 1 row update (index = 1)
                int index = updateExercise.executeUpdate();

                if (index != 1) {
                    System.out.println("No records updated");
                    return 0;
                }
                return exerciseID;
            } catch (SQLException error) {
                System.out.println("Problem updating exercise\n" + error.getMessage());
                return -1;
            }
        }
    }

    /**Updates the selected band stat. Returns the bandStatID if update successful, 0 if no changes needed or no record
     * found, and -1 if an exception was caught*/
    public int updateBandStat(Integer bandStatID, int singleBandTension, String doubledOrNot, String units){
        // check the index is already on the DB, return 0 if not
        // (new records would not have NULL values by default, uninitialised records would have NULL fields but this
        // check would prevent the passing of NULL)
        if (bandStatOnFileKey(bandStatID) == null) {
            System.out.println("Record with ID " + bandStatID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateBandStat.setInt(1, singleBandTension);
                updateBandStat.setString(2, doubledOrNot);
                updateBandStat.setString(3, units);
                updateBandStat.setInt(4, bandStatID);

                //should only return 1 row update (index = 1)
                int index = updateBandStat.executeUpdate();

                if (index != 1) {
                    System.out.println("No records updated");
                    return 0;
                }
                return bandStatID;
            } catch (SQLException error) {
                System.out.println("Problem updating exercise\n" + error.getMessage());
                return -1;
            }
        }
    }

    /**Updates the selected repetition. Returns the repetitionID if update successful, 0 if no changes needed or no
     * record found, and -1 if an exception was caught*/
    public int updateRepetition(Integer repID, Integer reps){
        // check the index is already on the DB, return 0 if not
        // (new records would not have NULL values by default, uninitialised records would have NULL fields but this
        // check would prevent the passing of NULL)
        if (repetitionOnFileKey(repID) == null) {
            System.out.println("Record with ID " + repID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateRepetition.setInt(1, reps);
                updateRepetition.setInt(2, repID);

                //should only return 1 row update (index = 1)
                int index = updateRepetition.executeUpdate();

                if (index != 1) {
                    System.out.println("No records updated");
                    return 0;
                }
                return repID;
            } catch (SQLException error) {
                System.out.println("Problem updating exercise\n" + error.getMessage());
                return -1;
            }
        }
    }

    /**Updates the selected set. Returns the setID if update successful, 0 if no changes needed or no record
     * found, and -1 if an exception was caught*/
    public int updateSet(Integer setID, String comments, String setDate){
        // check the index is already on the DB, return 0 if not
        // (new records would not have NULL values by default, uninitialised records would have NULL fields but this
        // check would prevent the passing of NULL)
        if (setOnFileKey(setID) == null) {
            System.out.println("Record with ID " + setID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateSet.setString(1, comments);
                updateSet.setString(2, setDate);
                updateSet.setInt(3, setID);

                //should only return 1 row update (index = 1)
                int index = updateSet.executeUpdate();

                if (index != 1) {
                    System.out.println("No records updated");
                    return 0;
                }
                return setID;
            } catch (SQLException error) {
                System.out.println("Problem updating exercise\n" + error.getMessage());
                return -1;
            }
        }
    }

    // DELETE queries...a separate Dialog alert will be needed to confirm DELETE...deleted record ID is returned

    /**Deletes the selected exercise. Returns the exerciseID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught*/
    public int deleteExercise(int index){
        if (exerciseOnFileKey(index) == null) {
            System.out.println("Record with ID " + index + " not found. No deletion carried out.");
            return 0;
        } else {
            try {
                deleteExercise.setInt(1, index);

                int result = deleteExercise.executeUpdate();

                if (result != 1) {
                    System.out.println("No records deleted");
                    return 0;
                }
                return index;
            } catch (SQLException err) {
                System.out.println("Problem with exercise deletion attempt");
                return -1;
            }
        }
    }

    /**Deletes the selected band stat. Returns the bandStatID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught*/
    public int deleteBandStat(int index){
        if (bandStatOnFileKey(index) == null) {
            System.out.println("Record with ID " + index + " not found. No deletion carried out.");
            return 0;
        } else {
            try {
                deleteBandStat.setInt(1, index);

                int result = deleteBandStat.executeUpdate();

                if (result != 1) {
                    System.out.println("No records deleted");
                    return 0;
                }
                return index;
            } catch (SQLException err) {
                System.out.println("Problem with band stat deletion attempt");
                return -1;
            }
        }
    }

    /**Deletes the selected repetition. Returns the repetitionID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught*/
    public int deleteRepetition(int index){
        if (repetitionOnFileKey(index) == null) {
            System.out.println("Record with ID " + index + " not found. No deletion carried out.");
            return 0;
        } else {
            try {
                deleteRepetition.setInt(1, index);

                int result = deleteRepetition.executeUpdate();

                if (result != 1) {
                    System.out.println("No records deleted");
                    return 0;
                }
                return index;
            } catch (SQLException err) {
                System.out.println("Problem with repetition deletion attempt");
                return -1;
            }
        }
    }

    /**Deletes the selected set. Returns the setID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught*/
    public int deleteSet(int index){
        if (setOnFileKey(index) == null) {
            System.out.println("Record with ID " + index + " not found. No deletion carried out.");
            return 0;
        } else {
            try {
                deleteSet.setInt(1, index);

                int result = deleteSet.executeUpdate();

                if (result != 1) {
                    System.out.println("No records deleted");
                    return 0;
                }
                return index;
            } catch (SQLException err) {
                System.out.println("Problem with set deletion attempt");
                return -1;
            }
        }
    }
}