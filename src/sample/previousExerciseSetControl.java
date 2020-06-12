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

public class previousExerciseSetControl implements Initializable {
    //the following variables are needed to store as a new set of set+exercise+repetition

    //for exercises:
    private String exerciseName, exerciseAnchorPosition, exerciseDescription, exerciseVideoURL;
    private int currentExerciseID;

    //for sets: comments might prove redundant since Description can provide custom user remarks
    private final String comments = "";
    private int currentSetID;

    //for repetitions:
    private String repStringExSet, currentDateTime;
    private float TextFieldTension;
    private int TextFieldReps;

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
                try (ResultSet tempExercise =
                             bbDatabase.getInstance().getExerciseSetWithKey(exerciseHistoryControl.getCurrentExerciseID())) {
                    // the only records which the user can change are rep-related

                    currentExerciseID = tempExercise.getInt(bbDatabase.ExerciseIdINDEX);
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

                        currentDateTime = exerciseHistoryControl.getCurrentDateTime();
                        repStringExSet = exerciseHistoryControl.getRepString();
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
    private void toggleAddButton() {
        addButton.setDisable(tensionTextField.getText().isBlank() || repsTextField.getText().isBlank());
    }

    @FXML
    private void onClickedRow() {
        if (repTable.getSelectionModel().getSelectedItem() != null) {
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            System.out.println("Row " + repTable.getSelectionModel().getSelectedIndex() + " with rep ID " + repTable.getSelectionModel().getSelectedItem().getRepetitionId());
            repsTextField.setText(String.valueOf(repTable.getSelectionModel().getSelectedItem().getReps()));
            tensionTextField.setText(String.valueOf(repTable.getSelectionModel().getSelectedItem().getTension()));
        } else {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            repsTextField.clear();
            tensionTextField.clear();
        }
    }

    @FXML
    private void onClickedAdd() {
        // button is only enabled when both fields have a value

        // 1. exercise data initialised in Runnable (later will enable TextFields to be editable)
        // 2. verify that a repetition exists; if not, insert new repetition
        // 3. verify if the set exists, create one otherwise and then update repText

        // (2) Repetitions

        TextFieldTension = Float.parseFloat(tensionTextField.getText());
        TextFieldReps = Integer.parseInt(repsTextField.getText());

        int currentRepID = bbDatabase.getInstance().getIDOfFirstRepetitionOnFile(TextFieldTension, TextFieldReps);
        if (currentRepID <= 0) {
            currentRepID = bbDatabase.getInstance().insertNewRepetition(TextFieldTension, TextFieldReps);
        }

        // update the Rep string
        String updatedRepString = bbDatabase.getInstance().buildRepString(repStringExSet, currentRepID);
        if (repStringExSet.equals(updatedRepString)) {
            System.out.println("repString not updated");
        }

        // (3) update set records
        updateSetScene(updatedRepString);
    }

    @FXML
    private void onClickedUpdate() {
        if (repTable.getSelectionModel().getSelectedItem() != null) {
            //check with stored DB values to avoid unnecessary computation
            TextFieldTension = Float.parseFloat(tensionTextField.getText());
            TextFieldReps = Integer.parseInt(repsTextField.getText());

            // use the newly entered values to build a new repString
            int rowIndex = repTable.getSelectionModel().getSelectedIndex() + 1;
            int oldRepID = repTable.getSelectionModel().getSelectedItem().getRepetitionId();
            int newRepID = bbDatabase.getInstance().insertNewRepetition(TextFieldTension, TextFieldReps);

            //update...
            String updatedRepString = bbDatabase.getInstance().updateRepString(rowIndex, newRepID, oldRepID,
                    repStringExSet);
            if (updatedRepString.equals(repStringExSet)) {
                System.out.println("repString not updated");
            }

            //update this set with the new repString
            updateSetScene(updatedRepString);

            deleteButton.setDisable(true);
            updateButton.setDisable(true);
            repsTextField.clear();
            tensionTextField.clear();
        }
    }

    @FXML
    private void onClickedDelete() {
        if (repTable.getSelectionModel().getSelectedItem() != null) {
            int rowIndex = repTable.getSelectionModel().getSelectedIndex() + 1;

            //delete...
            String updatedRepString = bbDatabase.getInstance().deleteRep(repStringExSet, rowIndex);
            if (updatedRepString.equals(repStringExSet)) {
                System.out.println("record not deleted");
            }

            //update this set with the new repString
            updateSetScene(updatedRepString);

            deleteButton.setDisable(true);
            updateButton.setDisable(true);
            repsTextField.clear();
            tensionTextField.clear();
        }
    }

    private void updateSetScene(String updatedRepString){
        //update this set with the new repString
        currentSetID = exerciseHistoryControl.getCurrentSetID();
        if (currentSetID > 0) {
            currentSetID = bbDatabase.getInstance().updateSet(
                    currentSetID,
                    currentExerciseID,
                    comments,
                    currentDateTime,
                    updatedRepString);
            System.out.println("Using stored set with id " + currentSetID);
        } else {
            currentSetID = bbDatabase.getInstance().insertNewSet(
                    currentExerciseID,
                    comments,
                    currentDateTime,
                    updatedRepString);
            System.out.println("Inserted new set with id " + currentSetID);
        }

        //update both scene's repString's
        exerciseHistoryControl.setRepString(updatedRepString);
        repStringExSet = exerciseHistoryControl.getRepString();

        //refresh the table
        listPreviousRepetitionsRepString();
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
    public void listPreviousRepetitionsRepString() {
        Task<ObservableList<bbRepetition>> task = new GetAllPreviousRepetitionsForSet();

        //sync the FXML TableView with the data from GetAllExercisesTask
        repTable.itemsProperty().bind(task.valueProperty());

        //run a separate thread to populate the list after the UI is prepared
        new Thread(task).start();
    }
}

//// this class may be used during startup
//// and runs as a background thread independent of the UI thread
class GetAllPreviousRepetitionsForSet extends Task {

    @Override
    public ObservableList<bbRepetition> call() {
        // returns a List<> which is then pass to and converted to an ObservableList for data binding
        // purposes (bbExercise variables are defined as Simple Properties to enable data binding)
        return FXCollections.observableArrayList(bbDatabase.getInstance().getRepListFromRepString(exerciseHistoryControl.getRepString()));
    }
}
