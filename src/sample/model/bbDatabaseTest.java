package sample.model;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class bbDatabaseTest {

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
        bbDatabase.getInstance().open();

        List<bbRepetition> repList = bbDatabase.getInstance().listAllRepetitions();
        int size = repList.size();
        assertEquals(5, size);

        bbDatabase.getInstance().close();
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
        bbDatabase.getInstance().open();

        //looking for idRepetition = 1;
        int index = 1;
        int tension = 0;
        int rep = 0;
        try(ResultSet resultSet = bbDatabase.getInstance().repetitionOnFileKey(index)){
            tension = resultSet.getInt(2);
            rep = resultSet.getInt(3);
        } catch (SQLException err){
            err.getMessage();
        }
        assertEquals(8.0, tension);
        assertEquals(9, rep);

        bbDatabase.getInstance().close();
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
        bbDatabase.getInstance().open();

        //looking for idRepetition = 1;
        String repString = "R_12_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().parseRepString(repString);
        assertTrue(list1.isEmpty());

        bbDatabase.getInstance().close();
    }

    @Test
    void parseRepString_R_1_() {
        bbDatabase.getInstance().open();

        //looking for idRepetition = 1;
        String repString = "R_1_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().parseRepString(repString);
        int size = list1.size();

        //should find one record with idRepetition 1
        assertEquals(1, size);

        bbDatabase.getInstance().close();
    }

    @Test
    void parseRepString_R_3_() {
        bbDatabase.getInstance().open();

        //looking for idRepetition = 1;
        String repString = "R_3_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().parseRepString(repString);
        int size = list1.size();
        assertEquals(1, size);

        bbDatabase.getInstance().close();
    }

    @Test
    void parseRepString_R_1_3_() {
        bbDatabase.getInstance().open();

        //looking for idRepetition = 1;
        String repString = "R_1_3_";

        List<bbRepetition> list1 = new ArrayList<>();
        list1 = bbDatabase.getInstance().parseRepString(repString);
        int size = list1.size();

        //should find two records, with idRepetition 3 and 1
        assertEquals(2, size);

        bbDatabase.getInstance().close();
    }

    @Test
    void updateSetDateRep() {
        fail("Test not implemented yet");
    }
}