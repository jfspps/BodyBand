package sample.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note that for many of the following tests, one needs to call open() and close() at the beginning and end of each
 * unit test block.
 */
class bbDatabaseTest {

    // run before the class bbDatabaseTest is instantiated (@BeforeEach is run before each test)
    // create one instance by applying static (bbDatabase uses a singleton pattern anyway)
    @BeforeAll
    public static void setup() {
        bbDatabase.getInstance().open();
    }

    @Test
    void open() {
        fail("Test not implemented yet");
    }

    @Test
    void close() {
        fail("Test not implemented yet");
    }

    @Test
    void getInstance() {
        fail("Test not implemented yet");
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
    void exerciseOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void exerciseOnFileId() {
        fail("Test not implemented yet");
    }

    @Test
    void exerciseOnFileKey() {
        fail("Test not implemented yet");
    }

    @Test
    void exerciseOnFileKeyList() {
        fail("Test not implemented yet");
    }

    @Test
    void listAllRepetitions() {
        List<bbRepetition> repList = bbDatabase.getInstance().listAllRepetitions();
        int size = repList.size();
        assertEquals(5, size);
    }

    @Test
    void getFirstRepetition() {
        fail("Test not implemented yet");
    }

    @Test
    void repetitionOnFileId() {
        fail("Test not implemented yet");
    }

    @Test
    void repetitionOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void repetitionOnFileKey() {
        //looking for idRepetition = 1;
        int index = 1;
        int tension = 0;
        int rep = 0;
        try (ResultSet resultSet = bbDatabase.getInstance().getRepetitionSetWithKey(index)) {
            tension = resultSet.getInt(2);
            rep = resultSet.getInt(3);
        } catch (SQLException err) {
            err.getMessage();
        }
        assertEquals(8.0, tension, 0);  // delta for float is the third parameter
        assertEquals(9, rep);
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
    void setOnFileId() {
        fail("Test not implemented yet");
    }

    @Test
    void setOnFile() {
        fail("Test not implemented yet");
    }

    @Test
    void setOnFileKey() {
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
    void buildRepString_R_() {
        int index = 17;

        String testOK = "R_";
        String result1 = bbDatabase.getInstance().buildRepString(testOK, index);
        assertEquals("R_71_", result1);
    }

    @Test
    void buildRepString_R_a() {
        int index = 17;

        String testNotOK = "R_a";
        String result2 = bbDatabase.getInstance().buildRepString(testNotOK, index);
        assertEquals("R_a", result2);
    }

    @Test
    void buildRepString_R_3_() {
        int index = 17;

        String testOK2 = "R_3_";
        String result3 = bbDatabase.getInstance().buildRepString(testOK2, index);
        assertEquals("R_3_71_", result3);
    }

    @Test
    void buildRepString_R_31_() {
        int index = 17;

        String testOK3 = "R_31_";
        String result4 = bbDatabase.getInstance().buildRepString(testOK3, index);
        assertEquals("R_31_71_", result4);
    }

    @Test
    void buildRepString_R_31_76_() {
        int index = 17;

        String testOK4 = "R_31_76_";
        String result5 = bbDatabase.getInstance().buildRepString(testOK4, index);
        assertEquals("R_31_76_71_", result5);
    }

    @Test
    void parseRepString_R_12_() {
        //looking for idRepetition = 1;
        String repString = "R_12_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().getRepListFromRepString(repString);
        assertTrue(list1.isEmpty());
    }

    @Test
    void parseRepString_R_1_() {
        //looking for idRepetition = 1;
        String repString = "R_1_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().getRepListFromRepString(repString);
        int size = list1.size();

        //should find one record with idRepetition 1
        assertEquals(1, size);
    }

    @Test
    void parseRepString_R_3_() {
        //looking for idRepetition = 1;
        String repString = "R_3_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().getRepListFromRepString(repString);
        int size = list1.size();
        assertEquals(1, size);
    }

    @Test
    void parseRepString_R_1_3_() {
        //looking for idRepetition = 1;
        String repString = "R_1_3_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().getRepListFromRepString(repString);
        int size = list1.size();

        //should find two records, with idRepetition 3 and 1
        assertEquals(2, size);
    }

    @Test
    void updateSetDateRep() {
        fail("Test not implemented yet");
    }

    @Test
    void buildRepString() {
        fail("Test not implemented yet");
    }

    @Test
    void parseRepString() {
        fail("Test not implemented yet");
    }

    @Test
    void setOnFileDateEx_knownRecord() {
        int exerciseID = 10;
        String timeDate = "09 Mar 2020";
        try (ResultSet result = bbDatabase.getInstance().getSetSetWithExerciseIDDate(exerciseID, timeDate)) {
            int index = result.getInt(bbDatabase.SetExerciseIdINDEX);
            System.out.println(index);
            assertEquals(10, index);
        } catch (SQLException err) {
            System.out.println(err);
        }
    }

    @Test
    void setOnFileDateEx_unknownRecord() {
        int exerciseID = 1;
        String timeDate = "09 Mar 2020";
        assertNull(bbDatabase.getInstance().getSetSetWithExerciseIDDate(exerciseID, timeDate));
    }

// =============================================================================================================

    // @AfterAll run after the class bbDatabaseTest is instantiated (@AfterEach is run after each test); tests not
    // necessarily
    // sequenced in the order they are provided in code
    @AfterAll
    public static void finish() {
        bbDatabase.getInstance().close();
    }

    @Test
    void checkRepString_correct() {
        String correct = "R_3_45_";
        String result = bbDatabase.getInstance().checkRepString(correct);
        assertEquals(correct, result);
    }

    @Test
    void checkRepString_incorrect() {
        String correct = "R_____";
        String result = bbDatabase.getInstance().checkRepString(correct);
        assertEquals("R_error", result);
    }

    @Test
    void checkRepStringOnFile_known1() {
        String knownIDs = "R_1_1_2_";
        assertEquals(0, bbDatabase.getInstance().checkRepStringOnFile(knownIDs));
    }

    @Test
    void checkRepStringOnFile_known2() {
        String knownIDs = "R_1_2_1_4_";
        assertEquals(0, bbDatabase.getInstance().checkRepStringOnFile(knownIDs));
    }

    @Test
    void checkRepStringOnFile_formatWrong() {
        String knownIDs = "R_1_1_2";
        assertEquals(-1, bbDatabase.getInstance().checkRepStringOnFile(knownIDs));
    }

    @Test
    void checkRepStringOnFile_formatWrong2() {
        //IDs not reversed (e.g. BodyBand does not accept 01 as 1 for formatting)
        String knownIDs = "R_10_10_20_";
        assertEquals(-1, bbDatabase.getInstance().checkRepStringOnFile(knownIDs));
    }

    @Test
    void checkRepStringOnFile_IDsWrong() {
        //ID 20 ("02" backwards) should be missing...
        String knownIDs = "R_01_1_02_4_";
        assertEquals(20, bbDatabase.getInstance().checkRepStringOnFile(knownIDs));
    }


    @Test
    void setOnFileDateEx() {
        fail("Test not implemented yet");
    }
}