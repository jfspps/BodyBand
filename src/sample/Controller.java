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


    // end-user based user interface related ------------------------------------------------------------

    // this starts a sequence of scenes which enable the user to enter a new set (as they workout)
    @FXML
    public void showNewSet(){
        sceneNavigation.getInstance().showMuscleGroup();
    }

    // this starts a sequence of scenes which enables the user to review previous workouts (set targets for
    // themselves etc.)
    @FXML
    public void showPreviousSet(){

    }

}
