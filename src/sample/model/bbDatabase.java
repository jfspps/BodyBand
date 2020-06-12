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
    public static final int ExerciseAnchorPositionINDEX = 4;
    public static final int ExerciseDescINDEX = 5;
    public static final int ExerciseVideoURLINDEX = 6;

    //tblRepetition indices
    public static final int RepetitionIdINDEX = 1;
    public static final int RepetitionTensionINDEX = 2;
    public static final int RepetitionRepsINDEX = 3;

    //tblSet indices
    public static final int SetIdINDEX = 1;
    public static final int SetExerciseIdINDEX = 2;
    public static final int SetCommentsINDEX = 3;
    public static final int SetDateINDEX = 4;
    public static final int SetRepIdSeqINDEX = 5;


    //General SQL queries -------------------------------------------------------------------
    //SELECT * queries
    public static final String querySelectAllExercises = "SELECT * FROM tblExercise";
    public static final String querySelectAllRepetitions = "SELECT * FROM tblRepetition";
    public static final String querySelectAllSets = "SELECT * FROM tblSet";

    //SELECT the first entry
    public static final String querySelectFirstExercise = "SELECT * FROM tblExercise LIMIT 1";
    public static final String querySelectFirstRepetition = "SELECT * FROM tblRepetition LIMIT 1";
    public static final String querySelectFirstSet = "SELECT * FROM tblSet LIMIT 1";

    //INSERT queries
    public static final String queryInsertExercise = "INSERT INTO tblExercise " +
            "(ExerciseName, MuscleGroup, AnchorPosition, Description, VideoURL) " +
            "VALUES(?, ?, ?, ?, ?)";
    public static final String queryInsertRepetition = "INSERT INTO tblRepetition " +
            "(Tension, Repetitions) VALUES(?, ?)";
    public static final String queryInsertSet = "INSERT INTO tblSet " +
            "(Exercise_id, Comments, SetDate, RepIdSeq) VALUES(?, ?, ?, ?)";

    //UPDATE queries (foreign keys cannot be updated)
    public static final String queryUpdateExercise = "UPDATE tblExercise SET ExerciseName = ?, MuscleGroup = ?, " +
            "AnchorPosition = ?, Description = ?, VideoURL = ? WHERE " +
            "idExercise = ?";
    public static final String queryUpdateRepetition = "UPDATE tblRepetition SET Tension = ?, Repetitions = ? WHERE " +
            "idRepetition = ?";
    public static final String queryUpdateSet = "UPDATE tblSet SET Exercise_id = ?, Comments = ?, SetDate = ?, " +
            "RepIdSeq = ? WHERE idSet = ?";
    public static final String queryUpdateSetDateAndRep = "UPDATE tblSet SET SetDate = ?, RepIdSeq = ? WHERE idSet = ?";

    //DELETE queries (records from tblRepetition and tblSet are deleted ON CASCADE)
    public static final String queryDeleteExercise = "DELETE FROM tblExercise WHERE idExercise = ?";
    public static final String queryDeleteRepetition = "DELETE FROM tblRepetition WHERE idRepetition = ?";
    public static final String queryDeleteSet = "DELETE FROM tblSet WHERE idSet = ?";

    //find particular records
    public static final String querySelectExercise = "SELECT ExerciseName, MuscleGroup, " +
            "AnchorPosition, Description, VideoURL FROM tblExercise WHERE ExerciseName = ? AND MuscleGroup = ? AND" +
            " AnchorPosition = ? AND Description = ? AND VideoURL = ?";
    public static final String querySelectRepetition = "SELECT Tension, Repetitions FROM tblRepetition " +
            "WHERE Tension = ? AND Repetitions = ?";
    public static final String querySelectSet = "SELECT Exercise_id, Comments, SetDate, RepIdSeq FROM tblSet WHERE " +
            "Exercise_id = ? AND Comments = ? AND SetDate = ? AND RepIdSeq = ?";

    //find particular records by primary key
    public static final String queryExerciseKey = "SELECT * FROM tblExercise WHERE idExercise = ?";
    public static final String queryRepetitionKey = "SELECT * FROM tblRepetition WHERE idRepetition = ?";
    public static final String querySetKey = "SELECT * FROM tblSet WHERE idSet = ?";

    //find record id
    public static final String querySelectExerciseId = "SELECT idExercise FROM tblExercise WHERE ExerciseName = ? AND" +
            " MuscleGroup = ? AND AnchorPosition = ? AND Description = ? " +
            "AND VideoURL = ?";
    public static final String querySelectRepetitionId = "SELECT idRepetition FROM tblRepetition WHERE Tension = " +
            "? AND Repetitions = ?";
    public static final String querySelectSetId = "SELECT idSet FROM tblSet WHERE Exercise_id = ? AND " +
            "Comments = ? AND SetDate = ? AND RepIdSeq = ?";
    public static final String querySelectSetByDateAndEx = "SELECT * FROM tblSet WHERE Exercise_id = ? AND " +
            "SetDate = ?";

    public static final String queryExercisesByDate = "SELECT idExercise, ExerciseName, MuscleGroup, AnchorPosition, " +
            "Description, VideoURL FROM tblSet JOIN tblExercise WHERE Exercise_id = idExercise AND SetDate = ?";

    //Precompiled SQL statements with PreparedStatement -------------------------------------
    private Connection conn;
    //Prepared statements
    private PreparedStatement insertExercise;
    private PreparedStatement insertRepetition;
    private PreparedStatement insertSet;
    private PreparedStatement selectExercise;
    private PreparedStatement selectRepetition;
    private PreparedStatement selectSet;
    private PreparedStatement selectExerciseKey;
    private PreparedStatement selectRepetitionKey;
    private PreparedStatement selectSetKey;
    private PreparedStatement selectExerciseId;
    private PreparedStatement selectRepetitionId;
    private PreparedStatement selectSetId;
    private PreparedStatement updateExercise;
    private PreparedStatement updateRepetition;
    private PreparedStatement updateSet;
    private PreparedStatement updateSet_DateRep;
    private PreparedStatement deleteExercise;
    private PreparedStatement deleteRepetition;
    private PreparedStatement deleteSet;
    private PreparedStatement selectSetDateAndEx;
    private PreparedStatement selectExerciseByDate;

    //bbDatabase admin routines -------------------------------------------------------------

    /**
     * Initialises the JDBC driver and all PrepareStatements
     */
    public boolean open() {
        try {
            //initiate the connection conn and all Statements/PreparedStatements here
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertExercise = conn.prepareStatement(queryInsertExercise);
            insertRepetition = conn.prepareStatement(queryInsertRepetition);
            insertSet = conn.prepareStatement(queryInsertSet);
            selectExercise = conn.prepareStatement(querySelectExercise);
            selectRepetition = conn.prepareStatement(querySelectRepetition);
            selectSet = conn.prepareStatement(querySelectSet);
            selectExerciseKey = conn.prepareStatement(queryExerciseKey);
            selectRepetitionKey = conn.prepareStatement(queryRepetitionKey);
            selectSetKey = conn.prepareStatement(querySetKey);
            selectExerciseId = conn.prepareStatement(querySelectExerciseId);
            selectRepetitionId = conn.prepareStatement(querySelectRepetitionId);
            selectSetId = conn.prepareStatement(querySelectSetId);
            updateExercise = conn.prepareStatement(queryUpdateExercise);
            updateRepetition = conn.prepareStatement(queryUpdateRepetition);
            updateSet = conn.prepareStatement(queryUpdateSet);
            updateSet_DateRep = conn.prepareStatement(queryUpdateSetDateAndRep);
            deleteExercise = conn.prepareStatement(queryDeleteExercise);
            deleteRepetition = conn.prepareStatement(queryDeleteRepetition);
            deleteSet = conn.prepareStatement(queryDeleteSet);
            selectSetDateAndEx = conn.prepareStatement(querySelectSetByDateAndEx);
            selectExerciseByDate = conn.prepareStatement(queryExercisesByDate);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection error:\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Frees all PrepareStatement resources and closes the JDBC connection
     */
    public boolean close() {
        try {
            //close in the order they were last instantiated
            if (insertExercise != null) {
                insertExercise.close();
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
            if (selectRepetition != null) {
                selectRepetition.close();
            }
            if (selectSet != null) {
                selectSet.close();
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
            if (selectRepetitionId != null) {
                selectRepetitionId.close();
            }
            if (selectSetId != null) {
                selectSetId.close();
            }
            if (updateExercise != null) {
                updateExercise.close();
            }
            if (updateRepetition != null) {
                updateRepetition.close();
            }
            if (updateSet != null) {
                updateSet.close();
            }
            if (updateSet_DateRep != null) {
                updateSet_DateRep.close();
            }
            if (deleteExercise != null) {
                deleteExercise.close();
            }
            if (deleteRepetition != null) {
                deleteRepetition.close();
            }
            if (deleteSet != null) {
                deleteSet.close();
            }
            if (selectSetDateAndEx != null) {
                selectSetDateAndEx.close();
            }
            if (selectExerciseByDate != null) {
                selectExerciseByDate.close();
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

    private bbDatabase() {
        //empty constructor
    }

    /**
     * Grants access to bbDatabase methods (call bbDatabase.getInstance().method_name)
     */
    public static bbDatabase getInstance() {
        return instance;
    }

    // General SQL queries (SQL to Java object) --------------------------------------------------

    //tblExercise ==================================================================================

    /**
     * Returns a List<> of all exercises and their fields on file. Returns null if none found.
     */
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

    /**
     * Returns a List<> of all exercises by a given date and their fields on file. Returns null if none found.
     */
    public List<bbExercise> listAllExercisesByDate(String setDate) {
        //note that conn is still open after this method returns
        //'statement' and 'results' are released on return
        try {
            selectExerciseByDate.setString(1, setDate);
            ResultSet results = selectExerciseByDate.executeQuery();

            List<bbExercise> exercises = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbExercise exercise = new bbExercise();
                exercise.setExerciseId(results.getInt(ExerciseIdINDEX));
                exercise.setExerciseName(results.getString(ExerciseNameINDEX));
                exercise.setMuscleGroup(results.getString(ExerciseMuscleGroupINDEX));
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

    /**
     * Returns the index of the first exercise on file. Returns 0 no records found and -1 if an exception was caught.
     */
    public int getFirstExercise() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstExercise)) {

            if (results.next()) {
                return results.getInt(1);
            }
            System.out.println("No exercises on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblExercises:\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * Returns the number of records with supplied fields, on file. Returns -1 if an exception was caught.
     */
    public int numberOfExercisesOnFile(String name, String muscleGroup,
                                       String anchorPosition,
                                       String desc, String videoURL) {
        try {
            selectExercise.setString(1, name);
            selectExercise.setString(2, muscleGroup);
            selectExercise.setString(3, anchorPosition);
            selectExercise.setString(4, desc);
            selectExercise.setString(5, videoURL);

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

    /**
     * Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.
     */
    public int getIDOfFirstExerciseOnFile(String name, String muscleGroup,
                                          String anchorPosition,
                                          String desc, String videoURL) {
        try {
            selectExerciseId.setString(1, name);
            selectExerciseId.setString(2, muscleGroup);
            selectExerciseId.setString(3, anchorPosition);
            selectExerciseId.setString(4, desc);
            selectExerciseId.setString(5, videoURL);

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
            System.out.println("getFirstExID problem querying tblExercise:\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * Returns a ResultSet of the exercise record with the supplied primary key idExercise. Returns null if none
     * found or if an exception is caught.
     */
    public ResultSet getExerciseSetWithKey(int idExercise) {
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

    /**
     * Returns a List<bbExercise> with the given primary key. Returns null if none
     * found or if an exception is caught.
     */
    public List<bbExercise> getExerciseListWithKey(int idExercise) {
        try {
            selectExerciseKey.setInt(ExerciseIdINDEX, idExercise);
            ResultSet resultSet = selectExerciseKey.executeQuery();

            List<bbExercise> exerciseArray = new ArrayList<>();
            if (resultSet.next()) {
                bbExercise tempEx = new bbExercise();
                tempEx.setExerciseId(resultSet.getInt(ExerciseIdINDEX)); //records and their PKs may get deleted over time
                tempEx.setExerciseName(resultSet.getString(ExerciseNameINDEX));
                tempEx.setMuscleGroup(resultSet.getString(ExerciseMuscleGroupINDEX));
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

    //tblRepetition ========================================================================================

    /**
     * Returns a List<> of all repetitions and their fields on file. Returns null if none found.
     */
    public List<bbRepetition> listAllRepetitions() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllRepetitions)) {
            List<bbRepetition> repetitions = new ArrayList<>();

            while (results.next()) {
                bbRepetition repetition = new bbRepetition();
                repetition.setRepetitionId(results.getInt(RepetitionIdINDEX));
                repetition.setTension(results.getFloat(RepetitionTensionINDEX));
                repetition.setReps(results.getInt(RepetitionRepsINDEX));
                repetitions.add(repetition);
            }
            return repetitions;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblRepetition:\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Returns the index of the first repetition on file. Returns 0 no records found and -1 if an exception was caught.
     */
    public int getFirstRepetition() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstRepetition)) {

            if (results.next()) {
                return results.getInt(1);
            }
            System.out.println("No repetitions on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblRepetitions:\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.
     */
    public int getIDOfFirstRepetitionOnFile(Float tension, Integer reps) {
        try {
            selectRepetitionId.setFloat(1, tension);
            selectRepetitionId.setInt(2, reps);

            //this returns a ResultSet with one field, idExercise
            ResultSet resultSet = selectRepetitionId.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                System.out.println("Repetition with given data not found");
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("getIDFirstRep problem querying tblRepetition:\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * Returns the number of records with supplied fields, on file.
     * Returns -1 if an exception was caught.
     */
    public int numberOfRepetitionsOnFile(float tension, int repetitions) {

        try {
            selectRepetition.setFloat(1, tension);
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

    /**
     * Returns a ResultSet of the repetition record with the supplied primary key idRepetition. Returns null if none
     * found or if an exception is caught.
     */
    public ResultSet getRepetitionSetWithKey(int idRepetition) {
//        System.out.println("Trying to find exercise with id = " + idRepetition);
        try {
            selectRepetitionKey.setInt(1, idRepetition);
            ResultSet resultSet = selectRepetitionKey.executeQuery();

            if (resultSet.next()) {
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

    /**
     * Determines if a repetition string sequence, repString, is formatted correctly. Returns the true if correctly
     * formatted or false if not.
     */
    public boolean checkRepString(String repString) {

        if (repString.isEmpty() || repString.isBlank()) {
            System.out.println("repString empty");
            return false;
        }

        //check empty repString then one with IDs
        if (repString.equals("R_")) {
            return true;
            //look for "R_xxxx_yyyy_zzzz_ etc.
            // caret is the string starting point, [] numeric with 0-9, * is zero or more (+ is one or more),
            // parenthesise groups XXXX_
        } else if (repString.matches("^R_([0-9]?[0-9]?[0-9]?[0-9]_)*")) {
            return true;
        } else {
            System.out.println("Incorrectly formatted repString received");
            return false;
        }
    }

    /**
     * Builds a string in the format R_x_x_x_ and so on...to store repetition IDs as x in a single field in bbSet.
     * Returns the same string if nothing built or format supplied was incorrect
     */
    public String buildRepString(String repBuild, int index) {
        StringBuilder newRepString = new StringBuilder();
        if (checkRepString(repBuild)) {
            newRepString.append(repBuild).append(index).append("_");
            System.out.println("New rep string: " + newRepString);
            return newRepString.toString();
        }
        return repBuild;
    }

    /**Removes a given set from the repString at a given index, returning the original string if a deletion cannot be
     *  carried out. Note that this method does not delete the repetition from the DB since it may be used later or
     *  is already referenced by other sets.*/
    public String deleteRep(String repDelete, int index) {
        if (checkRepString(repDelete)){
            String[] IDs = repDelete.split("_");
            StringBuilder newRepString = new StringBuilder();

            for (int i = 0; i < index; i++){
                newRepString.append(IDs[i]).append("_");
            }
            for (int i = index+1; i < IDs.length; i++){
                newRepString.append(IDs[i]).append("_");
            }
            return newRepString.toString();
        }
        return repDelete;
    }

    /**
     * Parses an repetition sequence string and returns a List<bbRepetition> related to the parsed string. Returns null
     * if a problem is encountered.
     *
     * Note here that missing repIDs from properly formatted repStrings are ignored and the method continues to parse
     * the string until the end.
     *
     * Use checkRepStringOnFile to begin identifying which rep records are missing.
     */
    public List<bbRepetition> getRepListFromRepString(String repString) {

        if (!checkRepString(repString)) {
            return null;
        } else {
            int repIndex;
            List<bbRepetition> repList = new ArrayList<>();

            //used to temporarily store a repID
            StringBuilder tempRepID = new StringBuilder();

            //skip to the first integer of the string
            for (int i = 2; i < repString.length(); i++) {
                if (repString.charAt(i) != '_') {
                    //build up the reversed integer (may be double or triple figured)
                    tempRepID.append(repString.charAt(i));
                } else if (repString.charAt(i) == '_') {
                    // stop processing repString for now, extract the index and add resultSet to the List<>
                    repIndex = Integer.parseInt(tempRepID.toString());
                    tempRepID.setLength(0);

                    try {
                        selectRepetitionKey.setInt(1, repIndex);
                        ResultSet tempRepSet = selectRepetitionKey.executeQuery();

                        while (tempRepSet.next()) {
                            bbRepetition tempRep = new bbRepetition();
                            tempRep.setRepetitionId(tempRepSet.getInt(1));
                            tempRep.setTension(tempRepSet.getFloat(2));
                            tempRep.setReps(tempRepSet.getInt(3));
                            repList.add(tempRep);
                        }
                    } catch (SQLException error) {
                        System.out.println("parseString error\n" + error.getMessage());
                        return null;
                    } catch (NullPointerException nullError) {
                        System.out.println("Dealing with an empty record at id: " + repIndex + "\n" + nullError.getCause());
                    }
                }
            }
            System.out.println("Number of reps in list: " + repList.size());
            return repList;
        }
    }


    /**
     * Determines if a repString's IDs are on tblRepetition. Returns record of missing record first encountered, 0 if
     * all records were found and -1 if an exception was caught or string formatted incorrectly
     */
    public int checkRepStringOnFile(String repString) {

        int length = repString.length();
        StringBuilder tempRepID = new StringBuilder();
        int repIndex = 0;

        if (checkRepString(repString)) {
            for (int i = 2; i < length; i++) {

                if (repString.charAt(i) != '_') {
                    //build up the reversed integer (may be double or triple figured)
                    tempRepID.append(repString.charAt(i));
                } else if (repString.charAt(i) == '_') {
                    // stop processing repString for now, extract the index and reset stringBuilder
                    repIndex = Integer.parseInt(tempRepID.toString());
                    tempRepID.setLength(0);

                    try {
                        selectRepetitionKey.setInt(1, repIndex);
                        if (!selectRepetitionKey.executeQuery().next()) {
                            System.out.println("Rep index " + repIndex + " missing");
                            return repIndex;
                        }
                    } catch (SQLException error) {
                        System.out.println("parseString error\n" + error.getMessage());
                        return -1;
                    } catch (NullPointerException nullError) {
                        System.out.println("Dealing with an empty record at id: " + repIndex + "\n" + nullError.getCause());
                        return -1;
                    }
                }
            }
        } else {
            //formatting problem...
            return -1;
        }
        //all good...
        return 0;
    }

    /**Updates a repString at the given index with a given repID. Index = 1 represents the first repID, index =
     * 2 represents the second repID and so on. Returns the updated String if successful or "R_" if not.*/
    public String updateRepString(int index, int newRepID, int oldRepID, String repString){
        if (checkRepString(repString)){
            //split the string up into an array and then replace the String at the given index. Then reassemble.
            String[] IDs = repString.split("_");

            if (IDs[index].equals(String.valueOf(oldRepID))){
                IDs[index] = String.valueOf(newRepID);
            } else {
                System.out.println("Given index " + index + " holds a value of " + IDs[index] + ", not " + oldRepID);
                return "R_";
            }

            //rebuild a new repString
            StringBuilder newRepText = new StringBuilder();
            for (String id : IDs) {
                newRepText.append(id).append("_");
            }
            System.out.println("New repText: " + newRepText);
            return newRepText.toString();
        }
        return "R_";
    }

    //tblSet =========================================================================================

    /**
     * Returns a List<> of all sets and their fields on file. Returns null if none found.
     */
    public List<bbSet> listAllSets() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectAllSets)) {
            List<bbSet> sets = new ArrayList<>();

            while (results.next()) {
                //build up each object bbExercise, transfer values from DB then add to the ArrayList
                bbSet set = new bbSet();
                set.setSetId(results.getInt(SetIdINDEX));
                set.setExerciseId(results.getInt(SetExerciseIdINDEX));
                set.setComments(results.getString(SetCommentsINDEX));
                set.setSetDate(results.getString(SetDateINDEX));
                set.setRepIdSequence(results.getString(SetRepIdSeqINDEX));
                sets.add(set);
            }
            return sets;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblSet:\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Returns the index of the first set on file. Returns 0 no records found and -1 if an exception was caught.
     */
    public int getFirstSet() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(querySelectFirstSet)) {

            if (results.next()) {
                return results.getInt(1);
            }
            System.out.println("No sets on record");
            return 0;
        } catch (SQLException e) {
            System.out.println("JDBC connection error to tblSets:\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * Returns the first id found from the supplied fields, ignoring all others. Returns 0 if none found and -1 if an
     * exception was caught.
     */
    public int getIDOfFirstSetOnFile(Integer exerciseID, String comments, String setDate, String repSeq) {
        try {
            selectSetId.setInt(1, exerciseID);
            selectSetId.setString(2, comments);
            selectSetId.setString(3, setDate);
            selectSetId.setString(4, repSeq);

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
            System.out.println("getFirstSetID problem querying tblSet:\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * Returns the number of records with supplied fields, on file.
     * Compared to most of the other query methods, numberOfSetsOnFile also checks tblRepetition and tblExercise using
     * repetitionOnFileKey and exerciseOnFileKey, respectively. Returns -1 if an DB record error occurred or
     * an exception was caught.
     */
    public int numberOfSetsOnFile(int exerciseId, String comments, String setDate, String repSeq) {
        try (ResultSet exercisePack = getExerciseSetWithKey(exerciseId)) {
            if (exercisePack == null) {
                System.out.println("The given exerciseId, " + exerciseId + ", was not found");
                return -1;
            }
        } catch (SQLException err) {
            System.out.println("Error with exerciseId in set parameter list\n" + err.getMessage());
        }

        if (checkRepStringOnFile(repSeq) == 0) {
            try {
                selectSet.setInt(1, exerciseId);
                selectSet.setString(2, comments);
                selectSet.setString(3, setDate);
                selectSet.setString(4, repSeq);

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
        } else {
            return -1;
        }
    }

    /**
     * Returns a ResultSet of the set record with the supplied primary key idSet. Returns null if none
     * found or if an exception is caught.
     */
    public ResultSet getSetSetWithKey(int idSet) {
//        System.out.println("Trying to find set with id = " + idSet);
        try {
            selectSetKey.setInt(1, idSet);
            ResultSet resultSet = selectSetKey.executeQuery();

            if (resultSet.next()) {
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

    /**
     * Returns a List<bbSet> of a set with the supplied SetDate and Exercise_id. Returns null if an
     * exception was caught.
     */
    public ResultSet getSetSetWithExerciseIDDate(int ExerciseID, String timeDate) {
        try {
            selectSetDateAndEx.setInt(1, ExerciseID);
            selectSetDateAndEx.setString(2, timeDate);
            ResultSet newSet = selectSetDateAndEx.executeQuery();

            if (newSet.next()) {
                return newSet;
            } else {
                System.out.println("newSet is empty");
                return null;
            }
        } catch (SQLException err) {
            System.out.println("SQLException of setOnFileDateEx\n" + err.getMessage());
            return null;
        }
    }

    // Insertion methods -------------return value is the index of the inserted record---------------------------------

    //the controller would read all given values on a form, verify the correct Java type and then assign "" to blank entries
    // these methods are the general user's equivalent of admin's updateXYZ methods, below. Essentially, the insert
    // methods try to locate the record. If the record does not exist, then one is created as opposed to updated. This
    // leaves the record available for all other records which may reference it.

    /**
     * Inserts a new exercise, first by checking of the supplied fields match any record on file. Returns the primary
     * key of the inserted record, current index if the exercise already exists, -2 if name is blank or null, and -1
     * if an
     * exception was caught
     */
    public int insertNewExercise(String name, String muscleGroup,
                                 String anchorPosition,
                                 String desc, String videoURL) {
        //check if the exercise already exists (returns 0 if none, and 1 if present)
        int index = getIDOfFirstExerciseOnFile(name, muscleGroup, anchorPosition, desc, videoURL);
        if (index >= 1) {
            System.out.println(name + " already exists on file. No further changes made.");
            return index;
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
            insertExercise.setString(3, anchorPosition);
            insertExercise.setString(4, desc);
            insertExercise.setString(5, videoURL);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertExercise.executeUpdate();

            if (insertedRecord != 1) {
                throw new SQLException("Could not insert exercise");
            }

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

    /**
     * Inserts a new repetition, first by checking of the supplied fields match any record on file. Returns the primary
     * key of the inserted record, current index if repetition already exists, -2 if
     * repetitions is 0, and -1 if an exception was caught.
     */
    public int insertNewRepetition(float tension, int repetitions) {

        //check if the Repetition already exists in tblRepetition (returns 0 if none, and 1 if present)
        int index = getIDOfFirstRepetitionOnFile(tension, repetitions);
        if (index >= 1) {
            System.out.println(repetitions + " with tension " + tension + " already exists on " +
                    "file, no further changes made.");
            return index;
        }

        //testing for null is eventually handled by the controller and used here for test purposes
        if (repetitions == 0) {
            System.out.println("Rep count needed ( >=1 )");
            return -2;
        }

        try {
            //PreparedStatements only allow for one value per placeholder ?
            insertRepetition.setFloat(1, tension);
            insertRepetition.setInt(2, repetitions);

            //store the expected return (1) if one row was inserted
            int insertedRecord = insertRepetition.executeUpdate();

            if (insertedRecord != 1) {
                throw new SQLException("Could not insert rep record");
            }

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

    /**
     * Inserts a new exercise, first by checking of the supplied fields match any record on file. The exercise
     * record with the supplied exerciseId is also verified on tblExercise. Returns the primary
     * key of the inserted record, current index if set already exists, -2 if exercise is missing or exerciseID is 0, -3
     * if repetition is missing, -4 is the date is missing, and -1 if an exception was caught.
     */
    public int insertNewSet(int exerciseId, String comments, String setDate, String repSeq) {
        //check if the set already exists in tblSet (returns 0 if none, and index if present)
        int index = getIDOfFirstSetOnFile(exerciseId, comments, setDate, repSeq);
        if (index >= 1) {
            System.out.println("This set dated " + setDate + " already on file, no further changes made.");
            return index;
        }

        //check if exerciseID is valid and record from tblExercise actually exists
        if (exerciseId == 0) {
            System.out.println("Exercise info needed ( >0 )");
            return -2;
        }

        try (ResultSet exercise = getExerciseSetWithKey(exerciseId)) {
            if (exercise == null) {
                System.out.println("The exercise with id " + exerciseId + " is not in its table");
                return -2;
            }
        } catch (SQLException err) {
            System.out.println("Insert set: SQL problem finding exercise with given exerciseID\n" + err.getMessage());
            return -1;
        }

        if (setDate == null) {
            System.out.println("Date of workout needed");
            return -4;
        }

        if (checkRepStringOnFile(repSeq) == 0){
            try {
                //PreparedStatements only allow for one value per placeholder ?
                insertSet.setInt(1, exerciseId);
                insertSet.setString(2, comments);
                insertSet.setString(3, setDate);
                insertSet.setString(4, repSeq);

                //store the expected return (1) if one row was inserted
                int insertedRecord = insertSet.executeUpdate();

                if (insertedRecord != 1) {
                    throw new SQLException("Could not insert set record");
                }

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
        return -3;
    }

    // update methods ---return value is the index of the updated record; parameter checking provided UI controls-----
    // these update methods are used by admin for which the PKs are known; the general user will use the above
    // insertXYZ methods as update methods

    /**
     * Updates the selected exercise. Returns the exerciseID if update successful, 0 if no changes needed or no record
     * found, and -1 if an exception was caught
     */
    public int updateExercise(Integer exerciseID, String name, String muscleGroup,
                              String anchorPosition,
                              String desc, String videoURL) {
        // check the index is already on the DB, return 0 if not

        if (getExerciseSetWithKey(exerciseID) == null) {
            System.out.println("Record with ID " + exerciseID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateExercise.setString(1, name);
                updateExercise.setString(2, muscleGroup);
                updateExercise.setString(3, anchorPosition);
                updateExercise.setString(4, desc);
                updateExercise.setString(5, videoURL);
                updateExercise.setInt(6, exerciseID);

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

    /**
     * Updates the selected repetition. Returns the repetitionID if update successful, 0 if no changes needed or no
     * record found, and -1 if an exception was caught
     */
    public int updateRepetition(Integer repID, Float tension, Integer reps) {
        // check the index is already on the DB, return 0 if not

        if (getRepetitionSetWithKey(repID) == null) {
            System.out.println("Record with ID " + repID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateRepetition.setFloat(1, tension);
                updateRepetition.setInt(2, reps);
                updateRepetition.setInt(3, repID);

                //should only return 1 row update (index = 1)
                int index = updateRepetition.executeUpdate();

                if (index != 1) {
                    System.out.println("No records updated");
                    return 0;
                }
                return repID;
            } catch (SQLException error) {
                System.out.println("Problem updating repetition\n" + error.getMessage());
                return -1;
            }
        }
    }

    /**
     * Updates the selected set. Returns the setID if update successful, 0 if no changes needed or no record
     * found, and -1 if an exception was caught
     */
    public int updateSet(Integer setID, Integer exerciseID, String comments, String setDate, String repSeq) {
        // check the index is already on the DB, return 0 if not

        if (getSetSetWithKey(setID) == null) {
            System.out.println("Record with ID " + setID + " not found. No changes made.");
            return 0;
        } else {
            try {
                updateSet.setInt(1, exerciseID);
                updateSet.setString(2, comments);
                updateSet.setString(3, setDate);
                updateSet.setString(4, repSeq);
                updateSet.setInt(5, setID);

                //should only return 1 row update (index = 1)
                int index = updateSet.executeUpdate();

                if (index != 1) {
                    System.out.println("No records updated");
                    return 0;
                }
                return setID;
            } catch (SQLException error) {
                System.out.println("Problem updating set\n" + error.getMessage());
                return -1;
            }
        }
    }

//    /**
//     * Updates the selected set's date and repetition sequence, repSeq. Returns the setID if update successful, 0 if no
//     * changes needed or no record found, and -1 if an exception was caught
//     */
//    public int updateSetDateRep(Integer setID, String setDate, String repSeq) {
//        // check the index is already on the DB, return 0 if not
//
//        if (getSetSetWithKey(setID) == null) {
//            System.out.println("Record with ID " + setID + " not found. No changes made.");
//            return 0;
//        } else {
//            try {
//                updateSet_DateRep.setString(1, setDate);
//                updateSet_DateRep.setString(2, repSeq);
//                updateSet_DateRep.setInt(3, setID);
//
//                //should only return 1 row update (index = 1)
//                int index = updateSet_DateRep.executeUpdate();
//
//                if (index != 1) {
//                    System.out.println("No records updated");
//                    return 0;
//                }
//                return setID;
//            } catch (SQLException error) {
//                System.out.println("Problem updating exercise\n" + error.getMessage());
//                return -1;
//            }
//        }
//    }

    // DELETE queries...a separate Dialog alert will be needed to confirm DELETE...deleted record ID is returned

    /**
     * Deletes the selected exercise. Returns the exerciseID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught
     */
    public int deleteExercise(int index) {
        if (getExerciseSetWithKey(index) == null) {
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

    /**
     * Deletes the selected repetition. Returns the repetitionID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught
     */
    public int deleteRepetition(int index) {
        if (getRepetitionSetWithKey(index) == null) {
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

    /**
     * Deletes the selected set. Returns the setID if delete successful, 0 if no record was
     * found or nothing deleted, and -1 if an exception was caught
     */
    public int deleteSet(int index) {
        if (getSetSetWithKey(index) == null) {
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

    // ---- Previous set record related queries -----------------------------------------------------------

}