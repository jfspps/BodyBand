package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import sample.sceneNavigation;

public class MainPageControl {

    @FXML
    private Button buttonShowExercises;
    @FXML
    private Button buttonShowBandStats;

    @FXML
    private BorderPane borderPane;

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
