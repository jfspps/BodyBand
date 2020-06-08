package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    //prove useful on exit
    private String exerciseName, exerciseAnchorPosition, exerciseDescription, exerciseVideoURL;

    //adds a listener to a TextField and permits xxx.xx float values only
    private void setNumField(TextField field){
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // regex which firstly allows 0 to 3 digits before ".", followed by "." and thirdly 0 to 2 digits
                // after "."
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                    field.setText(oldValue);
                }
            }
        });
    }

    @FXML
    private Label exerciseSetHeadLabel;

    @FXML
    private TextField anchorPositionField, videoURLField, repsTextField, tensionTextField;

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
                        anchorPositionField.setText(exerciseAnchorPosition);
                        videoURLField.setText(exerciseVideoURL);
                        descriptionArea.setText(exerciseDescription);
                        addButton.setDisable(true);
                        updateButton.setDisable(true);
                        deleteButton.setDisable(true);

                        setNumField(tensionTextField);
                        setNumField(repsTextField);
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
    private void onClickedAdd() {
        // Build a new set object...format of each TextField is handled by a listener


        // ...and query the database. If it already exists then update all relevant fields
    }

    @FXML
    private void onClickedUpdate() {

    }

    @FXML
    private void onClickedDelete() {

    }
}
