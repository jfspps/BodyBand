package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbBandStat;
import sample.model.bbDatabase;
import sample.model.bbRepetition;
import sample.model.bbSet;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class exerciseSetControl implements Initializable {

    //exercise is accessed by calling exerciseChoiceControl.currentExercise.getInt(bbDatabase.ExerciseIdINDEX) and
    // related get___() functions
    private bbSet currentSet;
    private bbRepetition currentRep;
    private bbBandStat currentBandStat;

    //prove useful on exit
    private String exerciseName, exerciseAnchorHeight, exerciseAnchorPosition, exerciseDescription, exerciseVideoURL;

    @FXML
    private Label exerciseSetHeadLabel;

    @FXML
    private TextField anchorHeightField, anchorPositionField, videoURLField;

    @FXML
    private TextField repsTextField, tensionTextField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button addButton, updateButton, deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //build the exercise variables on a separate thread
        Runnable background = new Runnable() {
            @Override
            public void run() {
                try {
                    exerciseName = exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseNameINDEX);
                    exerciseAnchorHeight =
                            exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseAnchorHeightINDEX);
                    exerciseAnchorPosition =
                            exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseAnchorPositionINDEX);
                    exerciseDescription = exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseDescINDEX);
                    exerciseVideoURL =
                            exerciseChoiceControl.currentExercise.getString(bbDatabase.ExerciseVideoURLINDEX);
                } catch (SQLException sqlError) {
                    System.out.println("Problem initialising exerciseSetControl variables\n" + sqlError.getMessage());
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        exerciseSetHeadLabel.setText(exerciseName);
                        anchorHeightField.setText(exerciseAnchorHeight);
                        anchorPositionField.setText(exerciseAnchorPosition);
                        videoURLField.setText(exerciseVideoURL);
                        descriptionArea.setText(exerciseDescription);
                        addButton.setDisable(true);
                        updateButton.setDisable(true);
                        deleteButton.setDisable(true);
                    }
                });
            }
        };
        new Thread(background).start();
    }

    // interface related methods -----------------------------------------------------------------------------

    @FXML
    private void onClickChooseExercise() {
        sceneNavigation.getInstance().showMuscleExerciseList();
    }

    @FXML
    public void toggleAddButton() {
        if (!tensionTextField.getText().isBlank() && !repsTextField.getText().isBlank()) {
            addButton.setDisable(false);
        } else {
            addButton.setDisable(true);
        }
    }

    @FXML
    private void onClickedAdd(){
        // Build a new set object...


        // ...and query the database. If it already exists then update all relevant fields
    }

    @FXML
    private void onClickedUpdate(){

    }

    @FXML
    private void onClickedDelete(){

    }
}
