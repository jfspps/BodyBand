package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import sample.model.bbDatabase;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class setController implements Initializable {

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Note that this page does not load is the table is empty (giving NullPointerException) hence the second catch
        record = bbDatabase.getInstance().getFirstSet();
        if (record == 0) {
            sceneNavigation.getInstance().showInfoAlert("Set", "Set DB empty", "Please add a new set " +
                    "to proceed");
            //prevent user access to each field
            setIDText.setDisable(true);
            exerciseIDText.setDisable(true);
            repIDText.setDisable(true);
            commentsText.setDisable(true);
            setDateText.setDisable(true);

            buttonNext.setDisable(true);
            buttonPrevious.setDisable(true);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else if (record > 0) {
            try {
                buttonPrevious.setDisable(true);
                setIDText.setText(String.valueOf(record));
                exerciseIDText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetExerciseIdINDEX));
                repIDText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetRepIdINDEX));
                commentsText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetCommentsINDEX));
                setDateText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetDateINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblSet to UI\n" + error.getMessage());
            } catch (NullPointerException nullError){
                System.out.println("SetPage NullPointerException: tblSet empty?\n" + nullError.getLocalizedMessage());
            }
        } else {
            sceneNavigation.getInstance().showInfoAlert("Set", "Problem with accessing Set DB", "");
            showMainPage();
        }
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

    // scene navigation --------------------------------------------------------------------------------

    @FXML
    private void showMainPage() {
        sceneNavigation.getInstance().mainPage();
    }

    @FXML
    private void addSet() {
        sceneNavigation.getInstance().addSet();
    }

    @FXML
    private void exitBB() {
        sceneNavigation.getInstance().exitBB();
    }

    @FXML
    private void bandStatScene() {
        sceneNavigation.getInstance().bandStatPage();
    }

    @FXML
    private void repScene() {
        sceneNavigation.getInstance().repPage();
    }

    @FXML
    private void exerciseScene() {
        sceneNavigation.getInstance().exercisePage();
    }

    // Previous and Next buttons --------------------------------------------------------------------------------

    @FXML
    private void onNextClicked() {
        record++;
        if (bbDatabase.getInstance().setOnFileKey(record) == null) {
            System.out.println("No set with id: " + record);
            setIDText.setText(String.valueOf(record));
            exerciseIDText.setText("");
            repIDText.setText("");
            commentsText.setText("");
            setDateText.setText("");

            setIDText.setDisable(true);
            repIDText.setDisable(true);
            commentsText.setDisable(true);
            setDateText.setDisable(true);

            buttonPrevious.setDisable(false);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try {
                buttonPrevious.setDisable(false);
                setIDText.setText(String.valueOf(record));
                exerciseIDText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetExerciseIdINDEX));
                repIDText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetRepIdINDEX));
                commentsText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetCommentsINDEX));
                setDateText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetDateINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblSet to UI\n" + error.getMessage());
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
        if (bbDatabase.getInstance().setOnFileKey(record) == null) {
            System.out.println("No set with id: " + record);
            setIDText.setText(String.valueOf(record));
            exerciseIDText.setText("");
            repIDText.setText("");
            commentsText.setText("");
            setDateText.setText("");

            setIDText.setDisable(true);
            repIDText.setDisable(true);
            commentsText.setDisable(true);
            setDateText.setDisable(true);

            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try {
                setIDText.setDisable(false);
                repIDText.setDisable(false);
                commentsText.setDisable(false);
                setDateText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                setIDText.setText(String.valueOf(record));
                exerciseIDText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetExerciseIdINDEX));
                repIDText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetRepIdINDEX));
                commentsText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetCommentsINDEX));
                setDateText.setText(bbDatabase.getInstance().setOnFileKey(record).getString(bbDatabase.SetDateINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblSet to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onUpdateClicked() {
        int outputInt = bbDatabase.getInstance().updateSet(
                record,
                commentsText.getText().trim(),
                setDateText.getText().trim()
        );
        System.out.println("Update code: " + outputInt);
    }

    @FXML
    private void onDeleteClicked(){

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
