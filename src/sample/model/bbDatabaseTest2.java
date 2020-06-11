package sample.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        fail("Test not implemented yet");
    }

    @Test
    void getFirstExercise() {
        fail("Test not implemented yet");
    }

    @Test
    void numberOfExercisesOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void getIDOfFirstExerciseOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void getExerciseSetWithKey() {
        fail("Test not implemented yet");
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
    void buildRepString() {
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
        fail("Test not implemented yet");
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
    void insertNewSet() {
        fail("Test not implemented yet");
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
    void updateSet() {
        fail("Test not implemented yet");
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
}