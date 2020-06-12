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
        sceneNavigation.getInstance().showAdminExPage();
    }

    @FXML
    public void showSets() {
        sceneNavigation.getInstance().showAdminSetPage();
    }

    @FXML
    public void showReps() {
        sceneNavigation.getInstance().showAdminRepPage();
    }


    // end-user based user interface related ------------------------------------------------------------

    // this starts a sequence of scenes which enable the user to enter a new set (as they workout)
    @FXML
    public void showNewSet(){

        // insert code here which compares the current date with the latest date of the selected exercise: if they
        // match then the user is automatically directed to the same set (this will allow the user to change exercise
        // or other records and then resume their workout)

        sceneNavigation.getInstance().showUserNewSet1A();
    }

    // this starts a sequence of scenes which enables the user to review previous workouts (set targets for
    // themselves etc.)
    @FXML
    public void showPreviousSet(){
        sceneNavigation.getInstance().showUserPrevDate2A();
    }

}
