package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.bbDatabase;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminRepControl implements Initializable {

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.getAdminMode()){
            menuSet.setDisable(false);
        } else {
            menuSet.setDisable(true);
        }
        menuRep.setDisable(true);
        repIDText.setDisable(true);

        //Note that this page does not load is the table is empty (giving NullPointerException) hence the second catch
        record = bbDatabase.getInstance().getFirstRepetition();
        if (record == 0) {
            sceneNavigation.getInstance().showInfoAlert("Repetition", "Repetition DB empty", "Please add a new rep " +
                    "to proceed");
            //prevent user access to each field
            tensionText.setDisable(true);
            repetitionsText.setDisable(true);

            buttonNext.setDisable(true);
            buttonPrevious.setDisable(true);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else if (record > 0) {
            try (ResultSet repSet = bbDatabase.getInstance().getRepetitionSetWithKey(record)) {
                buttonPrevious.setDisable(true);
                repIDText.setText(String.valueOf(record));
                tensionText.setText(repSet.getString(bbDatabase.RepetitionTensionINDEX));
                repetitionsText.setText(repSet.getString(bbDatabase.RepetitionRepsINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblRep to UI\n" + error.getMessage());
            } catch (NullPointerException nullError){
                System.out.println("RepPage NullPointerException: current tblRep record is null\n" + nullError.getLocalizedMessage());
            }
        } else {
            sceneNavigation.getInstance().showInfoAlert("Repetitions", "Problem with accessing Repetition DB");
            showMainPage();
        }
    }

    @FXML
    private TextArea repIDText;
    @FXML
    private TextArea tensionText;
    @FXML
    private TextArea repetitionsText;

    @FXML
    private Button buttonPrevious;
    @FXML
    private Button buttonNext;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private MenuItem menuItemMainPage;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private MenuItem menuRep, menuSet;

    // scene navigation --------------------------------------------------------------------------------

    @FXML
    private void showMainPage() {
        sceneNavigation.getInstance().showMainPage();
    }

    @FXML
    private void showOptionsPage(){
        sceneNavigation.getInstance().showOptionsPage();
    }

    @FXML
    private void addRep() {
        sceneNavigation.getInstance().showAdminNewRepPage();
    }

    @FXML
    private void exitBB(){
        sceneNavigation.getInstance().exitBB();
    }

    @FXML
    private void setScene(){
        sceneNavigation.getInstance().showAdminSetPage();
    }

    @FXML
    private void exerciseScene(){
        sceneNavigation.getInstance().showAdminExPage();
    }

    // Previous and Next buttons --------------------------------------------------------------------------------

    @FXML
    private void onNextClicked() {
        record++;
        if (bbDatabase.getInstance().getRepetitionSetWithKey(record) == null) {
            System.out.println("No rep with id: " + record);
            repIDText.setText(String.valueOf(record));
            tensionText.setText("");
            repetitionsText.setText("");

            tensionText.setDisable(true);
            repetitionsText.setDisable(true);

            buttonPrevious.setDisable(false);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try (ResultSet repSet = bbDatabase.getInstance().getRepetitionSetWithKey(record)) {
                tensionText.setDisable(false);
                repetitionsText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                buttonPrevious.setDisable(false);
                repIDText.setText(String.valueOf(record));
                tensionText.setText(repSet.getString(bbDatabase.RepetitionTensionINDEX));
                repetitionsText.setText(repSet.getString(bbDatabase.RepetitionRepsINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblRep to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onPreviousClicked() {
        record--;
        if(record == 1){
            buttonPrevious.setDisable(true);
        }
        if (bbDatabase.getInstance().getRepetitionSetWithKey(record) == null) {
            System.out.println("No rep with id: " + record);
            repIDText.setText(String.valueOf(record));
            tensionText.setText("");
            repetitionsText.setText("");

            tensionText.setDisable(true);
            repetitionsText.setDisable(true);

            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try (ResultSet repSet = bbDatabase.getInstance().getRepetitionSetWithKey(record)) {
                tensionText.setDisable(false);
                repetitionsText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                repIDText.setText(String.valueOf(record));
                tensionText.setText(repSet.getString(bbDatabase.RepetitionTensionINDEX));
                repetitionsText.setText(repSet.getString(bbDatabase.RepetitionRepsINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblRep to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onUpdateClicked() {
        int outputInt = bbDatabase.getInstance().updateRepetition(
                record,
                Float.parseFloat(tensionText.getText()),
                Integer.parseInt(repetitionsText.getText())
        );
        System.out.println("Update code: " + outputInt);
    }

    @FXML
    private void onDeleteClicked(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Click OK to confirm deletion");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int rec = Integer.parseInt(repIDText.getText());
                int deleted = bbDatabase.getInstance().deleteRepetition(rec);
                System.out.println("Record with id " + deleted + " deleted successfully");
                tensionText.setText("");
                repetitionsText.setText("");

                tensionText.setDisable(true);
                repetitionsText.setDisable(true);

                buttonUpdate.setDisable(true);
                buttonDelete.setDisable(true);
            }
        });
    }
}
