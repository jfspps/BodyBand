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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class exerciseSetControl implements Initializable {

    private int currentExID, currentSetID, repIndex;

    //prove useful on exit
    private String exerciseName, exerciseAnchorPosition, exerciseDescription, exerciseVideoURL, repString;

    //adds a listener to a TextField and permits xxx.xx float values only
    private void setFloatField(TextField field) {
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

    private void setIntField(TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // regex which firstly allows 0 to 3 digits without a decimal
                if (!newValue.matches("\\d{0,3}?")) {
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
                    currentExID = exerciseChoiceControl.currentExercise.getInt(bbDatabase.ExerciseIdINDEX);
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

                        setFloatField(tensionTextField);
                        setIntField(repsTextField);
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
        addButton.setDisable(tensionTextField.getText().isBlank() || repsTextField.getText().isBlank());
    }

    @FXML
    private void onClickedAdd() {
        // Build a new set object...format of each TextField is handled by a listener;
        // button is only enabled when both fields have a value

        LocalDateTime dateTime = LocalDateTime.now();
        String timeDate = dateTime.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));


        float bandTension = Float.parseFloat(tensionTextField.getText());
        int reps = Integer.parseInt(repsTextField.getText());

        int repIndex = bbDatabase.getInstance().getIDOfFirstRepetitionOnFile(bandTension, reps);
        if (repIndex > 0) {
            System.out.println("Repetition found at id: " + repIndex);
            //check exercise and repetition ID
        } else {
            System.out.println("Repetition not found, adding to the database...");
            repIndex = bbDatabase.getInstance().insertNewRepetition(bandTension, reps);
            //add new set with new repetition
        }


        // ...and query the database. If it already exists then update all relevant fields
    }

    @FXML
    private void onClickedUpdate() {

    }

    @FXML
    private void onClickedDelete() {

    }
}
