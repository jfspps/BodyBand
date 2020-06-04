package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.bbDatabase;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class bandStatController implements Initializable {

    @FXML
    private TextArea bandStatIDText;
    @FXML
    private TextArea singleBandTensionText;
    @FXML
    private TextArea doubledOrNotText;
    @FXML
    private TextArea unitsText;

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

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Note that this page does not load is the table is empty (giving NullPointerException) hence the second catch
        record = bbDatabase.getInstance().getFirstBandStat();
        if (record == 0) {
            sceneNavigation.getInstance().showInfoAlert("Band Stat", "Band stat DB empty", "Please add a new band " +
                    "stat to proceed");
            //prevent user access to each field
            bandStatIDText.setDisable(true);
            singleBandTensionText.setDisable(true);
            doubledOrNotText.setDisable(true);
            unitsText.setDisable(true);

            buttonNext.setDisable(true);
            buttonPrevious.setDisable(true);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else if (record > 0) {
            try {
                buttonPrevious.setDisable(true);
                bandStatIDText.setText(String.valueOf(record));
                singleBandTensionText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatSingleBandTensionINDEX));
                doubledOrNotText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatDoubledOrNotINDEX));
                unitsText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatUnitsINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblBandStat to UI\n" + error.getMessage());
            } catch (NullPointerException nullError) {
                System.out.println("BandStatPage NullPointerException: tblBandStat empty?\n" + nullError.getLocalizedMessage());
            }
        } else {
            sceneNavigation.getInstance().showInfoAlert("Band stat", "Problem with accessing Band stat DB");
            showMainPage();
        }
    }

    // scene navigation --------------------------------------------------------------------------------

    @FXML
    public void showMainPage() {
        sceneNavigation.getInstance().mainPage();
    }

    @FXML
    private void addBandStat() {
        sceneNavigation.getInstance().addBandStat();
    }

    @FXML
    private void exitBB(){
        sceneNavigation.getInstance().exitBB();
    }

    @FXML
    private void repScene(){
        sceneNavigation.getInstance().repPage();
    }

    @FXML
    private void setScene(){
        sceneNavigation.getInstance().setPage();
    }

    @FXML
    private void exerciseScene(){
        sceneNavigation.getInstance().exercisePage();
    }

    // Previous and Next buttons --------------------------------------------------------------------------------

    @FXML
    private void onNextClicked() {
        record++;
        if (bbDatabase.getInstance().bandStatOnFileKey(record) == null) {
            System.out.println("No band stat with id: " + record);
            bandStatIDText.setText(String.valueOf(record));
            singleBandTensionText.setText("");
            doubledOrNotText.setText("");
            unitsText.setText("");

            bandStatIDText.setDisable(true);
            singleBandTensionText.setDisable(true);
            doubledOrNotText.setDisable(true);
            unitsText.setDisable(true);

            buttonPrevious.setDisable(false);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try {
                bandStatIDText.setDisable(false);
                singleBandTensionText.setDisable(false);
                doubledOrNotText.setDisable(false);
                unitsText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                buttonPrevious.setDisable(false);
                bandStatIDText.setText(String.valueOf(record));
                singleBandTensionText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatSingleBandTensionINDEX));
                doubledOrNotText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatDoubledOrNotINDEX));
                unitsText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatUnitsINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblBandStat to UI\n" + error.getMessage());
            }
        }

//        //example of running "background" processes on the JavaFX "UI thread" (separate, single thread)
//        Runnable background = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                    //background thread pauses for 10 seconds and ten runs runLater()
//
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            System.out.println("10 seconds have elapsed");
//                        }
//                    });
//                } catch (InterruptedException event) {
//                    //what happens if the background task is interrupted (independent of the UI controls)
//                    System.out.println("Some background task caused an interrupt: " + event.getMessage());
//                }
//            }
//        };
//        new Thread(background).start();

    }

    @FXML
    private void onPreviousClicked() {
        record--;
        if(record == 1){
            buttonPrevious.setDisable(true);
        }
        if (bbDatabase.getInstance().bandStatOnFileKey(record) == null) {
            System.out.println("No band stat with id: " + record);
            bandStatIDText.setText(String.valueOf(record));
            singleBandTensionText.setText("");
            doubledOrNotText.setText("");
            unitsText.setText("");

            bandStatIDText.setDisable(true);
            singleBandTensionText.setDisable(true);
            doubledOrNotText.setDisable(true);
            unitsText.setDisable(true);

            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try {
                bandStatIDText.setDisable(false);
                singleBandTensionText.setDisable(false);
                doubledOrNotText.setDisable(false);
                unitsText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                bandStatIDText.setText(String.valueOf(record));
                singleBandTensionText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatSingleBandTensionINDEX));
                doubledOrNotText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatDoubledOrNotINDEX));
                unitsText.setText(bbDatabase.getInstance().bandStatOnFileKey(record).getString(bbDatabase.BandStatUnitsINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblBandStat to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onUpdateClicked() {
        int outputInt = bbDatabase.getInstance().updateBandStat(
                record,
                Integer.valueOf(singleBandTensionText.getText()),
                doubledOrNotText.getText().trim(),
                unitsText.getText().trim()
        );
        System.out.println("Update code: " + outputInt);
    }

    @FXML
    private void onDeleteClicked(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("All other records with this band stat will also be deleted");
        alert.setContentText("Click OK to confirm deletion");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int deleted = bbDatabase.getInstance().deleteBandStat(Integer.valueOf(bandStatIDText.getText()));
                System.out.println("Record with id " + deleted + " deleted successfully");
                singleBandTensionText.setText("");
                doubledOrNotText.setText("");
                unitsText.setText("");

                bandStatIDText.setDisable(true);
                singleBandTensionText.setDisable(true);
                doubledOrNotText.setDisable(true);
                unitsText.setDisable(true);

                buttonUpdate.setDisable(true);
                buttonDelete.setDisable(true);
            }
        });
    }
}

//// this class may be used during startup or when the user specifically asks for the full list of bbExercise's
//// and runs as a background thread independent of the UI thread
//class GetAllExercises extends Task {
//
//    @Override
//    public ObservableList<bbExercise> call() {
//        // listAllExercises returns a List<> which is then pass to and converted to an ObservableList for data binding
//        // purposes (bbExercises are defined as Simple Properties to enable data binding)
//        return FXCollections.observableArrayList(bbDatabase.getInstance().listAllExercises());
//    }
//}
