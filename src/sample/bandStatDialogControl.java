package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.bbDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class bandStatDialogControl implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        singleBandTension.setText("single");
        saveButton.setDisable(true);
    }

    @FXML
    private TextField singleBandTension, doubledOrNot, units;

    @FXML
    private TextArea alertLabel;

    @FXML
    private Button saveButton;

    @FXML
    private void showBandStats() {
        sceneNavigation.getInstance().bandStatPage();
    }

    @FXML
    private void requiredFieldsCheck(){
        if (singleBandTension.getText().isBlank() || doubledOrNot.getText().isBlank()){
            saveButton.setDisable(true);
            alertLabel.setText("Please enter a band tension with doubling info");
        } else {
            saveButton.setDisable(false);
            alertLabel.setText("");
        }
    }

    @FXML
    private void saveData(){
        //trim() removes all leading and trailing whitespace
        bbDatabase tempDB = bbDatabase.getInstance();

        int index = tempDB.bandStatOnFile(Integer.valueOf(singleBandTension.getText().trim()),
                doubledOrNot.getText().trim(),
                units.getText().trim());

        if (index >= 1){
            alertLabel.setText("These details are already on file");
        } else {
            index = tempDB.insertNewBandStat(Integer.valueOf(singleBandTension.getText().trim()),
                    doubledOrNot.getText().trim(),
                    units.getText().trim());
            alertLabel.setText("New band stat added at " + index);
        }
    }
}
