package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Options implements Initializable {

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private Button buttonShowSets, buttonShowReps;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.getAdminMode()){
            adminCheckBox.setSelected(true);
            buttonShowReps.setDisable(true);
            buttonShowSets.setDisable(true);
        } else {
            adminCheckBox.setSelected(false);
            buttonShowSets.setDisable(false);
            buttonShowReps.setDisable(false);
        }
    }

    @FXML
    public void onCheckBox(){
        if (adminCheckBox.isSelected()){
            Main.setAdminMode(true);
            buttonShowReps.setDisable(true);
            buttonShowSets.setDisable(true);
        } else {
            Main.setAdminMode(false);
            buttonShowSets.setDisable(false);
            buttonShowReps.setDisable(false);
        }
    }

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

    @FXML
    public void onClickedMainPage(){
        sceneNavigation.getInstance().showMainPage();
    }
}