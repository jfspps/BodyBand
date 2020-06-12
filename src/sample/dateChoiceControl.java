package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.bbDatabase;
import sample.model.bbSet;

//this class handles exerciseChoice.fxml interfaces
public class dateChoiceControl {

    private static String setDateTime;

    @FXML
    private TableView<bbSet> dateTable;

    @FXML
    private Button buttonMainPage;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private void onClickedMainPage() {
        sceneNavigation.getInstance().showMainPage();
    }

    @FXML
    private void clickRow() {
        // check the table's selected item and get selected item
        if (dateTable.getSelectionModel().getSelectedItem() != null) {
            setDateTime = dateTable.getSelectionModel().getSelectedItem().getSetDate();
            sceneNavigation.getInstance().showPreviousExerciseSetPage();
        }
    }

    //called by sceneNavigation
    public void listAllDates() {
        Task<ObservableList<bbSet>> task = new GetAllDatesTask();

        //sync the FXML TableView with the data from GetAllExercisesTask
        dateTable.itemsProperty().bind(task.valueProperty());

        //run a separate thread to populate the list after the UI is prepared
        new Thread(task).start();
    }

    public static String getSetDateTime() {
        return setDateTime;
    }
}

//// this class may be used during startup or when the user specifically asks for the list of bbSet's
//// and runs as a background thread independent of the UI thread
class GetAllDatesTask extends Task {

    @Override
    public ObservableList<bbSet> call() {
        return FXCollections.observableArrayList(bbDatabase.getInstance().listAllSets());
    }
}