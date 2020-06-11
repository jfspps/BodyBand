package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.bbDatabase;
import sample.model.bbRepetition;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class exerciseSetControl implements Initializable {

    //the following variables are needed to store as a new set of set+exercise+repetition

    //for exercises:
    private String exerciseName, exerciseAnchorPosition, exerciseDescription, exerciseVideoURL;

    //for sets: comments might prove redundant since Description can provide custom user remarks
    private final String comments = "";

    //for repetitions:
    private int currentRepID;

    @FXML
    private TableView<bbRepetition> repTable;

    @FXML
    private TableColumn tensionColumn, repsColumn;

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
                try (ResultSet tempExercise = bbDatabase.getInstance().getExerciseSetWithKey(exerciseChoiceControl.currentExerciseID)){
                    //get the exercise details
                    exerciseName = tempExercise.getString(bbDatabase.ExerciseNameINDEX);
                    exerciseAnchorPosition =
                            tempExercise.getString(bbDatabase.ExerciseAnchorPositionINDEX);
                    exerciseDescription = tempExercise.getString(bbDatabase.ExerciseDescINDEX);
                    exerciseVideoURL = tempExercise.getString(bbDatabase.ExerciseVideoURLINDEX);

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
        // button is only enabled when both fields have a value

        // 1. exercise data initialised in Runnable (later will enable TextFields to be editable)
        // 2. verify that a repetition exists; if not, insert new repetition
        // 3. currently, do not verify if a set exists, just use all above data to insert a new one

        // (2) Repetitions
        float bandTension = Float.parseFloat(tensionTextField.getText());
        int reps = Integer.parseInt(repsTextField.getText());

        currentRepID = bbDatabase.getInstance().getIDOfFirstRepetitionOnFile(bandTension, reps);
        if (currentRepID <= 0){
            currentRepID = bbDatabase.getInstance().insertNewRepetition(bandTension, reps);
        }

        // update the Rep string
        bbDatabase.getInstance().buildRepString(exerciseChoiceControl.repString, currentRepID);
        if (exerciseChoiceControl.repString.equals(exerciseChoiceControl.repString)){
            System.out.println("repString not updated");
        }

        // (3) if the set exists, update it otherwise create a new one
        if (exerciseChoiceControl.currentSetID > 0){
            exerciseChoiceControl.currentSetID = bbDatabase.getInstance().updateSet(
                    exerciseChoiceControl.currentSetID,
                    exerciseChoiceControl.currentExerciseID,
                    comments,
                    exerciseChoiceControl.currentDateTime,
                    exerciseChoiceControl.repString);
            System.out.println("Using stored set with id " + exerciseChoiceControl.currentSetID);
        } else {
            exerciseChoiceControl.currentSetID = bbDatabase.getInstance().insertNewSet(
                    exerciseChoiceControl.currentExerciseID,
                    comments,
                    exerciseChoiceControl.currentDateTime,
                    exerciseChoiceControl.repString);
            System.out.println("Inserted new set with id " + exerciseChoiceControl.currentSetID);
        }
    }

    @FXML
    private void onClickedUpdate() {

    }

    @FXML
    private void onClickedDelete() {

    }

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

    //called by sceneNavigation
    public void listRepetitionsRepString() {
        Task<ObservableList<bbRepetition>> task = new GetAllRepetitionsForSet();

        //sync the FXML TableView with the data from GetAllExercisesTask
        repTable.itemsProperty().bind(task.valueProperty());

        //run a separate thread to populate the list after the UI is prepared
        new Thread(task).start();
    }
}

//// this class may be used during startup
//// and runs as a background thread independent of the UI thread
class GetAllRepetitionsForSet extends Task {

    @Override
    public ObservableList<bbRepetition> call() {
        // returns a List<> which is then pass to and converted to an ObservableList for data binding
        // purposes (bbExercise variables are defined as Simple Properties to enable data binding)
        return FXCollections.observableArrayList(bbDatabase.getInstance().getRepListFromRepString(exerciseChoiceControl.repString));
    }
}
