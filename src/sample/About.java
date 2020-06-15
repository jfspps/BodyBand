package sample;

import javafx.fxml.FXML;

public class About {

    @FXML
    private void onClickedReturn(){
        sceneNavigation.getInstance().showOptionsPage();
    }
}
