package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class testController {

    @FXML
    private Button buttonLeft;

    @FXML
    private Button buttonRight;

    @FXML
    private TextArea centralTextArea;

    @FXML
    private TextArea bottomTextArea;

    @FXML
    public void showMainPage(){
        sceneNavigation.getInstance().mainPage();
    }

    @FXML
    public void clickLeft(){
        System.out.println("Clicked left");
    }

    @FXML
    public void clickRight(){
        System.out.println("Clicked right");
    }
}
