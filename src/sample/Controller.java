package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button buttonShowExercises;
    @FXML
    private Button buttonShowBandStats;

    // Main (entry) page controller

    @FXML
    public void showExercises() {
        sceneNavigation.getInstance().exercisePage();
    }

    @FXML
    public void showBandStats() {
        sceneNavigation.getInstance().bandStatPage();
    }

    @FXML
    public void showSets() {
        sceneNavigation.getInstance().setPage();
    }

    @FXML
    public void showReps() {
        sceneNavigation.getInstance().repPage();
    }
}


//// this class may be used during startup or when the user specifically asks for the full list of bbExercise's
//// and runs as a background thread independent of the UI thread
//class GetAllExercises extends Task {
//
//    @Override
//    public ObservableList<bbExercise> call() {
//        // listAllExercises returns a List<> which is then pass to and converted to an ObservableList for data binding
//        // purposes (bbExercises are defined as Simple Properties to enable data binding)
//        return FXCollections.observableArrayList(bbDatabase.getInstance().listAllExercises());
//    }
//}
