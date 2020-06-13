package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class adminNewExControl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setDisable(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                exerciseName.requestFocus();
            }
        });
    }

    @FXML
    private TextField exerciseName, muscleGroup, anchorPosition, description, videoURL;

    @FXML
    private TextArea alertLabel;

    @FXML
    private Button saveButton;

    @FXML
    private void showExercises() {
        sceneNavigation.getInstance().showAdminExPage();
    }

    @FXML
    private void nameFieldCheck(){
        if (exerciseName.getText().isBlank()){
            saveButton.setDisable(true);
            alertLabel.setText("Please enter a name");
        } else {
            saveButton.setDisable(false);
            alertLabel.setText("");
        }
    }

    @FXML
    private void saveData(){
        //trim() removes all leading and trailing whitespace
        bbDatabase tempDB = bbDatabase.getInstance();

        int index = tempDB.getIDOfFirstExerciseOnFile(exerciseName.getText().trim(),
                muscleGroup.getText().trim(),
                anchorPosition.getText().trim(),
                description.getText().trim(),
                videoURL.getText().trim());

        if (index >= 1){
            alertLabel.setText("These details are already on file, id: " + index);
        } else {
            index = tempDB.insertNewExercise(exerciseName.getText().trim(),
                    muscleGroup.getText().trim(),
                    anchorPosition.getText().trim(),
                    description.getText().trim(),
                    videoURL.getText().trim());
            alertLabel.setText("New exercise added at " + index);
        }
    }
}
