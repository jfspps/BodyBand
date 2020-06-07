package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbDatabase;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class exerciseSetControl implements Initializable {

    @FXML
    private Label exerciseSetHeadLabel;

    @FXML
    private TextField anchorHeightField, anchorPositionField, videoURLField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button addButton, updateButton, deleteButton;

    @FXML
    private void onClickChooseExercise(){
        sceneNavigation.getInstance().showMuscleExerciseList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            exerciseSetHeadLabel.setText(exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseNameINDEX));
            anchorHeightField.setText(exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseAnchorHeightINDEX));
            anchorPositionField.setText(exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseAnchorPositionINDEX));
            videoURLField.setText(exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseVideoURLINDEX));
            descriptionArea.setText(exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseDescINDEX));
            addButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        } catch (SQLException error){
            System.out.println("Problem retrieving bbDatabase for new exercise set\n" + error.getMessage());
        }
    }

}

