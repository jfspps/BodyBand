package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.bbDatabase;
import sample.model.bbExercise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//this class handles exerciseChoice.fxml interfaces
public class exerciseChoiceControl {

    static int currentExerciseID;
    static String currentDateTime;
    static int currentSetID;
    static String repString;

    @FXML
    private TableView<bbExercise> exerciseTable;

    @FXML
    private Button buttonMainPage;

    @FXML
    private TableColumn muscleColumn, exerciseColumn;

    @FXML
    private void onClickedMainPage() {
        sceneNavigation.getInstance().showMainPage();
    }

    @FXML
    private void clickRow() {
        // check the table's selected item and get selected item
        if (exerciseTable.getSelectionModel().getSelectedItem() != null) {
            currentExerciseID = exerciseTable.getSelectionModel().getSelectedItem().getExerciseId();
            setRepStringAndSet();
            sceneNavigation.getInstance().showExerciseSetPage();
        }
    }

    //called by sceneNavigation
    public void listMuscleGroupAndExercises() {
        Task<ObservableList<bbExercise>> task = new GetAllExercisesTask();

        //sync the FXML TableView with the data from GetAllExercisesTask
        exerciseTable.itemsProperty().bind(task.valueProperty());

        //run a separate thread to populate the list after the UI is prepared
        new Thread(task).start();
    }

    private void setRepStringAndSet(){
        LocalDateTime dateTime = LocalDateTime.now();
        currentDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));
        try{
            ResultSet currentSet = bbDatabase.getInstance().getSetSetWithExerciseIDDate(currentExerciseID,
                    currentDateTime);

            // the next exerciseSet page would have to create a set if one does not exist
            // with a blank repString
            if (currentSet != null) {
                repString = currentSet.getString(bbDatabase.SetRepIdSeqINDEX);
                currentSetID = currentSet.getInt(bbDatabase.SetIdINDEX);
            } else {
                currentSetID = 0;
                repString = "R_";
            }
            System.out.println("Current exercise ID: " + currentExerciseID + ", date: " + currentDateTime + ", " +
                    "repString: " + repString + " with set ID: " + currentSetID);
        } catch (SQLException sqlError) {
            System.out.println("Problem with extracting set data\n" + sqlError.getMessage());
        } catch (NullPointerException nullError) {
            System.out.println("Null record processed\n" + nullError.getMessage());
        }
    }
}

//// this class may be used during startup or when the user specifically asks for the full list of bbExercise's
//// and runs as a background thread independent of the UI thread
class GetAllExercisesTask extends Task {

    @Override
    public ObservableList<bbExercise> call() {
        // listAllExercises returns a List<> which is then pass to and converted to an ObservableList for data binding
        // purposes (bbExercise variables are defined as Simple Properties to enable data binding)
        return FXCollections.observableArrayList(bbDatabase.getInstance().listAllExercises());
    }
}