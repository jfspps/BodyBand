package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button buttonShowExercises;
    @FXML
    private Button buttonShowBandStats;

    @FXML
    public void viewExercises(){
        sceneNavigation.getInstance().showUserNewSet1A();
    }

    @FXML
    public void workoutHistory(){
        sceneNavigation.getInstance().showUserPrevDate2A();
    }

    @FXML
    public void onClickedOptions(){
        sceneNavigation.getInstance().showOptionsPage();
    }
}
