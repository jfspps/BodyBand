package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class setDialogControl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setDisable(true);
    }

    @FXML
    private TextField exerciseID, repetitionsID, comments, setDate;

    @FXML
    private TextArea alertLabel;

    @FXML
    private Button saveButton;

    @FXML
    private void showSets() {
        try {
            Parent setPage = FXMLLoader.load(getClass().getResource("FXML/SetPage.fxml"));
            Main.mainWindow.setTitle("BodyBand sets");
            Main.mainWindow.setScene(new Scene(setPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading exercise scene:\n" + e.getMessage());
        }
    }

    @FXML
    private void requiredFieldsCheck(){
        if (exerciseID.getText().isBlank() || repetitionsID.getText().isBlank() || setDate.getText().isBlank()){
            saveButton.setDisable(true);
            alertLabel.setText("Please enter IDs for the exercise and reps, with the date");
        } else {
            saveButton.setDisable(false);
            alertLabel.setText("");
        }
    }

    @FXML
    private void saveData(){
        //trim() removes all leading and trailing whitespace
        bbDatabase tempDB = bbDatabase.getInstance();

        int index = tempDB.setOnFile(Integer.valueOf(exerciseID.getText().trim()),
                Integer.valueOf(repetitionsID.getText().trim()), comments.getText().trim(), setDate.getText().trim());

        if (index >= 1){
            alertLabel.setText("These details are already on file");
        } else {
            index = tempDB.insertNewSet(Integer.valueOf(exerciseID.getText().trim()),
                    Integer.valueOf(repetitionsID.getText().trim()), comments.getText().trim(), setDate.getText().trim());
            alertLabel.setText("New set added at " + index);
        }
    }
}
