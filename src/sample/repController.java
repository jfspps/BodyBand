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

public class repController implements Initializable {

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Note that this page does not load is the table is empty (giving NullPointerException)!
        record = 1;
        repIDText.setText(String.valueOf(record));
        try {
            bandStatIDText.setText(bbDatabase.getInstance().repetitionOnFileKey(record).getString(bbDatabase.RepetitionBandStatIdINDEX));
        } catch (SQLException error) {
            System.out.println("Problem with pairing tblRep to UI\n" + error.getMessage());
        } catch (NullPointerException nullError){
            System.out.println("RepPage NullPointerException: tblRep empty?\n" + nullError.getLocalizedMessage());
        }
        buttonPrevious.setDisable(true);
    }

    @FXML
    private TextArea repIDText;
    @FXML
    private TextArea bandStatIDText;
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

    // scene navigation --------------------------------------------------------------------------------

    @FXML
    private void showMainPage() {
        sceneNavigation.getInstance().mainPage();
    }

    @FXML
    private void addRep() {
        sceneNavigation.getInstance().addRep();
    }

    @FXML
    private void exitBB(){
        sceneNavigation.getInstance().exitBB();
    }

    @FXML
    private void bandStatScene(){
        sceneNavigation.getInstance().bandStatPage();
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
        repIDText.setText(String.valueOf(record));
        if (record > 1) {
            buttonPrevious.setDisable(false);
        } else {
            buttonPrevious.setDisable(true);
            repetitionsText.setText("");
        }
        if (bbDatabase.getInstance().repetitionOnFileKey(record) == null) {
            bandStatIDText.setText("No rep with id: " + record);
        } else {
            try {
                bandStatIDText.setText(bbDatabase.getInstance().repetitionOnFileKey(record).getString(bbDatabase.RepetitionBandStatIdINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblRep to UI\n" + error.getMessage());
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
        repIDText.setText(String.valueOf(record));
        if (record <= 1) {
            buttonPrevious.setDisable(true);
            repetitionsText.setText("Back at the beginning");
        } else {
            buttonPrevious.setDisable(false);
            repetitionsText.setText("");
        }
        if (bbDatabase.getInstance().repetitionOnFileKey(record) == null) {
            bandStatIDText.setText("No rep with id: " + record);
        } else {
            try {
                bandStatIDText.setText(bbDatabase.getInstance().repetitionOnFileKey(record).getString(bbDatabase.RepetitionBandStatIdINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblRep to UI\n" + error.getMessage());
            }
        }
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
