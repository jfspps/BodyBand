package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.bbDatabase;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminSetControl implements Initializable {

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.getAdminMode()){
            menuRep.setDisable(false);
        } else {
            menuSet.setDisable(true);
        }
        menuSet.setDisable(true);
        setIDText.setDisable(true);

        //Note that this page does not load is the table is empty (giving NullPointerException) hence the second catch
        record = bbDatabase.getInstance().getFirstSet();
        if (record == 0) {
            sceneNavigation.getInstance().showInfoAlert("Set", "Set DB empty", "Please add a new set " +
                    "to proceed");
            //prevent user access to each field
            exerciseIDText.setDisable(true);
            repIDText.setDisable(true);
            commentsText.setDisable(true);
            setDateText.setDisable(true);

            buttonNext.setDisable(true);
            buttonPrevious.setDisable(true);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else if (record > 0) {
            try (ResultSet setSet = bbDatabase.getInstance().getSetSetWithKey(record)) {
                buttonPrevious.setDisable(true);
                setIDText.setText(String.valueOf(record));
                exerciseIDText.setText(setSet.getString(bbDatabase.SetExerciseIdINDEX));
                commentsText.setText(setSet.getString(bbDatabase.SetCommentsINDEX));
                setDateText.setText(setSet.getString(bbDatabase.SetDateINDEX));
                repIDText.setText(setSet.getString(bbDatabase.SetRepIdSeqINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblSet to UI\n" + error.getMessage());
            } catch (NullPointerException nullError){
                System.out.println("SetPage NullPointerException: current tblSet record is null\n" + nullError.getLocalizedMessage());
            }
        } else {
            sceneNavigation.getInstance().showInfoAlert("Set", "Problem with accessing Set DB");
            showMainPage();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                exerciseIDText.requestFocus();
            }
        });
    }

    @FXML
    private TextArea setIDText;
    @FXML
    private TextArea exerciseIDText;
    @FXML
    private TextArea repIDText;
    @FXML
    private TextArea commentsText;
    @FXML
    private TextArea setDateText;

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
    private void addSet() {
        sceneNavigation.getInstance().showAdminNewSetPage();
    }

    @FXML
    private void exitBB() {
        sceneNavigation.getInstance().exitBB();
    }

    @FXML
    private void repScene() {
        sceneNavigation.getInstance().showAdminRepPage();
    }

    @FXML
    private void exerciseScene() {
        sceneNavigation.getInstance().showAdminExPage();
    }

    // Previous and Next buttons --------------------------------------------------------------------------------

    @FXML
    private void onNextClicked() {
        record++;
        if (bbDatabase.getInstance().getSetSetWithKey(record) == null) {
            System.out.println("No set with id: " + record);
            setIDText.setText(String.valueOf(record));
            exerciseIDText.setText("");
            repIDText.setText("");
            commentsText.setText("");
            setDateText.setText("");

            exerciseIDText.setDisable(true);
            repIDText.setDisable(true);
            commentsText.setDisable(true);
            setDateText.setDisable(true);

            buttonPrevious.setDisable(false);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try (ResultSet setSet = bbDatabase.getInstance().getSetSetWithKey(record)) {
                exerciseIDText.setDisable(false);
                repIDText.setDisable(false);
                commentsText.setDisable(false);
                setDateText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                buttonPrevious.setDisable(false);
                setIDText.setText(String.valueOf(record));
                exerciseIDText.setText(setSet.getString(bbDatabase.SetExerciseIdINDEX));
                commentsText.setText(setSet.getString(bbDatabase.SetCommentsINDEX));
                setDateText.setText(setSet.getString(bbDatabase.SetDateINDEX));
                repIDText.setText(setSet.getString(bbDatabase.SetRepIdSeqINDEX));
                exerciseIDText.requestFocus();
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblSet to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onPreviousClicked() {
        record--;
        if(record == 1){
            buttonPrevious.setDisable(true);
        }
        if (bbDatabase.getInstance().getSetSetWithKey(record) == null) {
            System.out.println("No set with id: " + record);
            setIDText.setText(String.valueOf(record));
            exerciseIDText.setText("");
            repIDText.setText("");
            commentsText.setText("");
            setDateText.setText("");

            exerciseIDText.setDisable(true);
            repIDText.setDisable(true);
            commentsText.setDisable(true);
            setDateText.setDisable(true);

            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try (ResultSet setSet = bbDatabase.getInstance().getSetSetWithKey(record)) {
                exerciseIDText.setDisable(false);
                repIDText.setDisable(false);
                commentsText.setDisable(false);
                setDateText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                setIDText.setText(String.valueOf(record));
                exerciseIDText.setText(setSet.getString(bbDatabase.SetExerciseIdINDEX));
                commentsText.setText(setSet.getString(bbDatabase.SetCommentsINDEX));
                setDateText.setText(setSet.getString(bbDatabase.SetDateINDEX));
                repIDText.setText(setSet.getString(bbDatabase.SetRepIdSeqINDEX));
                exerciseIDText.requestFocus();
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblSet to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onUpdateClicked() {
        int outputInt = bbDatabase.getInstance().updateSet(
                record,
                Integer.parseInt(exerciseIDText.getText()),
                commentsText.getText().trim(),
                setDateText.getText().trim(),
                repIDText.getText().trim()
        );
        System.out.println("Update code: " + outputInt);
        exerciseIDText.requestFocus();
    }

    @FXML
    private void onDeleteClicked(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Click OK to confirm deletion");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int rec = Integer.parseInt(setIDText.getText());
                int deleted = bbDatabase.getInstance().deleteSet(rec);
                System.out.println("Record with id " + deleted + " deleted successfully");
                exerciseIDText.setText("");
                repIDText.setText("");
                commentsText.setText("");
                setDateText.setText("");

                exerciseIDText.setDisable(true);
                repIDText.setDisable(true);
                commentsText.setDisable(true);
                setDateText.setDisable(true);

                buttonUpdate.setDisable(true);
                buttonDelete.setDisable(true);
            }
        });
        exerciseIDText.requestFocus();
    }
}
