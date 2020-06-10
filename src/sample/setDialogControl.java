package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbDatabase;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class setDialogControl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDateTime dateTime = LocalDateTime.now();
        String timeDate = dateTime.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));
        System.out.println(timeDate);
        setDate.setText(timeDate);
        saveButton.setDisable(true);
        setDate.setDisable(true);
    }

    @FXML
    private TextField exerciseID, repetitionsID, comments, setDate;

    @FXML
    private TextArea alertLabel;

    @FXML
    private Button saveButton;

    @FXML
    private void showSets() {
        sceneNavigation.getInstance().showSetPage();
    }

    @FXML
    private void requiredFieldsCheck(){
        if (exerciseID.getText().isBlank()){
            saveButton.setDisable(true);
            alertLabel.setText("Please enter exercise ID");
        } else {
            saveButton.setDisable(false);
            alertLabel.setText("");
        }
    }

    @FXML
    private void saveData(){
        //trim() removes all leading and trailing whitespace
        bbDatabase tempDB = bbDatabase.getInstance();

        int index = tempDB.getIDOfFirstSetOnFile(Integer.parseInt(exerciseID.getText().trim()),
                comments.getText().trim(), setDate.getText().trim(), repetitionsID.getText().trim());

        if (index >= 1){
            alertLabel.setText("These details are already on file, id : " + index);
        } else {
            index = tempDB.insertNewSet(Integer.parseInt(exerciseID.getText().trim()),
                    comments.getText().trim(), setDate.getText().trim(), repetitionsID.getText().trim());
            alertLabel.setText("New set added at " + index);
        }
    }
}
