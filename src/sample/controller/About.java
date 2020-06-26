package sample.controller;

import javafx.fxml.FXML;
import sample.sceneNavigation;

public class About {

    @FXML
    private void onClickedReturn(){
        sceneNavigation.getInstance().showOptionsPage();
    }
}
