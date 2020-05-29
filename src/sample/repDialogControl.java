package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class repDialogControl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setDisable(true);
    }

    @FXML
    private TextField bandStatID, repetitions;

    @FXML
    private TextArea alertLabel;

    @FXML
    private Button saveButton;

    @FXML
    private void showReps() {
        sceneNavigation.getInstance().repPage();
    }

    @FXML
    private void requiredFieldsCheck(){
        if (bandStatID.getText().isBlank() || repetitions.getText().isBlank()){
            saveButton.setDisable(true);
            alertLabel.setText("Please enter Band stat ID and repetitions");
        } else {
            saveButton.setDisable(false);
            alertLabel.setText("");
        }
    }

    @FXML
    private void saveData(){
        //trim() removes all leading and trailing whitespace
        bbDatabase tempDB = bbDatabase.getInstance();

        int index = tempDB.repetitionOnFile(Integer.valueOf(bandStatID.getText().trim()),
                Integer.valueOf(repetitions.getText().trim()));

        if (index >= 1){
            alertLabel.setText("These details are already on file");
        } else {
            index = tempDB.insertNewRepetition(Integer.valueOf(bandStatID.getText().trim()),
                    Integer.valueOf(repetitions.getText().trim()));
            alertLabel.setText("New rep added at " + index);
        }
    }
}
