package sample.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class bbDatabaseTest2 {

    @BeforeAll
    public static void setUp() {
        bbDatabase.getInstance().open();
    }

    @AfterAll
    public static void tearDown() {
        bbDatabase.getInstance().close();
    }

    @Test
    void listAllExercises() {
        List<bbExercise> tempList = bbDatabase.getInstance().listAllExercises();
        //pick INSERT INTO "tblExercise" VALUES (2,'Lying pulldown','Lats','Base','Models a lateral pulldown','');

        int ID = tempList.get(1).getExerciseId();
        String exerciseName = tempList.get(1).getExerciseName();
        String muscleGroup = tempList.get(1).getMuscleGroup();
        String position = tempList.get(1).getAnchorPosition();
        String description = tempList.get(1).getExerciseDesc();
        String URL = tempList.get(1).getVideoURL();

        assertEquals(ID, 2);
        assertEquals(exerciseName, "Lying pulldown");
        assertEquals(muscleGroup, "Lats");
        assertEquals(position, "Base");
        assertEquals(description, "Models a lateral pulldown");
        assertEquals(URL, "");
    }

    @Test
    void getFirstExercise() {
        assertEquals(1, bbDatabase.getInstance().getFirstExercise());
    }

    @Test
    void numberOfExercisesOnFile() {

        int numberOfExercises = bbDatabase.getInstance().numberOfExercisesOnFile("Lying pulldown", "Lats", "Base",
                "Models a lateral pulldown", "");
        assertEquals(1, numberOfExercises);
    }

    @Test
    void getIDOfFirstExerciseOnFile() {

        assertEquals(2, bbDatabase.getInstance().getIDOfFirstExerciseOnFile("Lying pulldown", "Lats", "Base",
                "Models a lateral pulldown", ""));
    }

    @Test
    void getExerciseSetWithKey() {
        ResultSet tempSet = bbDatabase.getInstance().getExerciseSetWithKey(2);
        try {
            assertEquals("Lying pulldown", tempSet.getString(bbDatabase.ExerciseNameINDEX));
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    @Test
    void getExerciseListWithKey() {
        fail("Test not implemented yet");
    }

    @Test
    void listAllRepetitions() {
        fail("Test not implemented yet");
    }

    @Test
    void getFirstRepetition() {
        fail("Test not implemented yet");
    }

    @Test
    void getIDOfFirstRepetitionOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void numberOfRepetitionsOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void getRepetitionSetWithKey() {
        fail("Test not implemented yet");
    }

    @Test
    void checkRepString() {
        String test = "R_12_13_14_15_16_17_19_20_1_";
        assertTrue(bbDatabase.getInstance().checkRepString(test));
    }

    @Test
    void checkRepString_LargeRepIDs() {
        String test = "R_12003_3413_14_1567_16_17_1999_20_1000_";
        assertTrue(bbDatabase.getInstance().checkRepString(test));
    }

    @Test
    void buildRepString() {
        String test = "R_32_1_";
        int newRepID = 67;
        assertEquals("R_32_1_67_", bbDatabase.getInstance().buildRepString(test, newRepID));
    }

    @Test
    void buildRepString2() {
        String test = "R_32_1_";
        int newRepID = 67;
        assertEquals("R_32_1_67_", bbDatabase.getInstance().buildRepString(test, newRepID));
    }

    @Test
    void getRepListFromRepString() {
        fail("Test not implemented yet");
    }

    @Test
    void checkRepStringOnFile() {
        int result = bbDatabase.getInstance().checkRepStringOnFile("R_17_12_13_14_");
        assertEquals(0, result);

    }

    @Test
    void checkRepStringOnFile_WRONG() {
        int result = bbDatabase.getInstance().checkRepStringOnFile("R_wrong_");
        assertEquals(-1, result);

    }

    @Test
    void listAllSets() {
        fail("Test not implemented yet");
    }

    @Test
    void getFirstSet() {
        fail("Test not implemented yet");
    }

    @Test
    void getIDOfFirstSetOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void numberOfSetsOnFile() {
        // pick INSERT INTO "tblSet" VALUES (2,8,'Getting there!','10 Mar 2020','R_3_');
        assertEquals(1, bbDatabase.getInstance().numberOfSetsOnFile(8, "Getting there!", "10 Mar 2020", "R_3_"));
    }

    @Test
    void numberOfSetsOnFile_WWRONG_Ex() {
        // pick INSERT INTO "tblSet" VALUES (2,8,'Getting there!','10 Mar 2020','R_3_');
        assertEquals(0, bbDatabase.getInstance().numberOfSetsOnFile(3, "Getting there!", "10 Mar 2020", "R_3_"));
    }

    @Test
    void numberOfSetsOnFile_WRONG_RepSeq() {
        // pick INSERT INTO "tblSet" VALUES (2,8,'Getting there!','10 Mar 2020','R_3_');
        assertEquals(-1, bbDatabase.getInstance().numberOfSetsOnFile(8, "Getting there!", "10 Mar 2020",
                "wrong_format"));
    }

    @Test
    void getSetSetWithKey() {
        fail("Test not implemented yet");
    }

    @Test
    void getSetSetWithExerciseIDDate() {
        fail("Test not implemented yet");
    }

    @Test
    void insertNewExercise() {
        fail("Test not implemented yet");
    }

    @Test
    void insertNewRepetition() {
        fail("Test not implemented yet");
    }

    @Test
    void insertNewSet_alreadyIn() {
        int currentSetIndex = 2;    //index of record found
        assertEquals(currentSetIndex, bbDatabase.getInstance().insertNewSet(8, "Getting there!", "10 Mar 2020", "R_3_"));
    }

    @Test
    void insertNewSet_missingEx() {
        int errorCode = -2;
        assertEquals(errorCode, bbDatabase.getInstance().insertNewSet(0, "Getting there!", "10 Mar 2020",
                "R_3_"));
    }

    @Test
    void insertNewSet_missingRepSeq() {
        int errorCode = -3;
        assertEquals(errorCode, bbDatabase.getInstance().insertNewSet(8, "Getting there!", "10 Mar 2020",
                ""));
    }

    //currently, BodyBand does not allow the user to add new sets with a user-based setDate "xx MMM yyyy"; see
    // updateSet()
    @Test
    void insertNewSet_nullDate() {
        int errorCode = -4;
        assertEquals(errorCode, bbDatabase.getInstance().insertNewSet(8, "Getting there!", "null",
                "R_3_"));
    }

    @Test
    void updateExercise() {
        fail("Test not implemented yet");
    }

    @Test
    void updateRepetition() {
        fail("Test not implemented yet");
    }

    @Test
    void updateSet_missingDate() {
        int errorCode = -2;
        assertEquals(errorCode, bbDatabase.getInstance().updateSet(2, 8, "Getting there!", "wrongDate",
                "R_3_"));
    }

    @Test
    void updateSetDateRep() {
        fail("Test not implemented yet");
    }

    @Test
    void deleteExercise() {
        fail("Test not implemented yet");
    }

    @Test
    void deleteRepetition() {
        fail("Test not implemented yet");
    }

    @Test
    void deleteSet() {
        fail("Test not implemented yet");
    }

    @Test
    void updateRepString() {
        int rowIndex = 3;   //first 3 of two 3s
        int oldRepID = 3;
        int newRepID = 14;
        String oldRepString = "R_1_4_3_3_9_";
        String newRepString = bbDatabase.getInstance().updateRepString(rowIndex, newRepID, oldRepID, oldRepString);
        assertEquals("R_1_4_14_3_9_", newRepString);
    }

    @Test
    void updateRepString_ERROR() {
        int rowIndex = 3;   //first 3 of two 3s
        int oldRepID = 3;
        int newRepID = 14;
        String oldRepString = "R_1_4_6_3_9_";
        String newRepString = bbDatabase.getInstance().updateRepString(rowIndex, newRepID, oldRepID, oldRepString);
        assertEquals("R_", newRepString);
    }

    @Test
    void updateRepString_doubleFig() {
        int rowIndex = 3;   //first 3 of two 3s
        int oldRepID = 37;
        int newRepID = 14;
        String oldRepString = "R_1_4_37_3_9_";
        String newRepString = bbDatabase.getInstance().updateRepString(rowIndex, newRepID, oldRepID, oldRepString);
        assertEquals("R_1_4_14_3_9_", newRepString);
    }

    @Test
    void updateRepString_tripleFig() {
        int rowIndex = 3;   //first 3 of two 3s
        int oldRepID = 976;
        int newRepID = 351;
        String oldRepString = "R_1_4_976_3_9_";
        String newRepString = bbDatabase.getInstance().updateRepString(rowIndex, newRepID, oldRepID, oldRepString);
        assertEquals("R_1_4_351_3_9_", newRepString);
    }

    @Test
    void deleteRep() {
        int rowIndex = 3;
        String oldRepIndex = "R_1_2_3_4_5_";
        String expected = "R_1_2_4_5_";
        assertEquals(expected, bbDatabase.getInstance().deleteRep(oldRepIndex, rowIndex));
    }

    @Test
    void deleteRep_doubleFig() {
        int rowIndex = 3;
        String oldRepIndex = "R_1_2_88_4_5_";
        String expected = "R_1_2_4_5_";
        assertEquals(expected, bbDatabase.getInstance().deleteRep(oldRepIndex, rowIndex));
    }

    @Test
    void listAllExercisesByDate() {
    }

    @Test
    void getSetDateRepSeqByExercise() {
    }

    @Test
    void checkDateFormatOrIsEmpty() {
        String correctDate = "25 Dec 1965";
        assertTrue(bbDatabase.getInstance().checkDateFormatOrIsEmpty(correctDate));
    }

    @Test
    void checkDateFormatOrIsEmpty_WRONG() {
        //note that checkDate does not look out for 32 Dec 1965 for example
        String correctDate = "ab 234 good";
        assertFalse(bbDatabase.getInstance().checkDateFormatOrIsEmpty(correctDate));
    }

    @Test
    void checkDateFormatOrIsEmpty_WRONG2() {
        //note that checkDate does not look out for 32 Dec 1965 for example
        String correctDate = "25Dec1965";
        assertFalse(bbDatabase.getInstance().checkDateFormatOrIsEmpty(correctDate));
    }

    @Test
    void insertNewSet() {
    }
}