package sample.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        String test = "R_4_";
        assertTrue(bbDatabase.getInstance().checkRepString(test));
    }

    @Test
    void buildRepString() {
        String test = "R_32_1_";
        int newRepID = 67;
        assertEquals("R_32_1_76_", bbDatabase.getInstance().buildRepString(test, newRepID));
    }

    @Test
    void getRepListFromRepString() {
        List<bbRepetition> repList = bbDatabase.getInstance().getRepListFromRepString("R_71_21_31_41_");
        int listSize = repList.size();
        assertEquals(4, listSize);
    }

    @Test
    void checkRepStringOnFile() {
        fail("Test not implemented yet");
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
}