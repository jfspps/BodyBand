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

public class exerciseController implements Initializable {

    @FXML
    private TextArea exerciseIDText;
    @FXML
    private TextArea exerciseNameText;
    @FXML
    private TextArea muscleGroupText;
    @FXML
    private TextArea anchorNeededText;
    @FXML
    private TextArea anchorHeightText;
    @FXML
    private TextArea anchorPositionText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private TextArea videoURLText;
    @FXML
    private Button buttonPrevious;
    @FXML
    private Button buttonNext;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private MenuItem menuItemMainPage;

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Note that this page does not load is the table is empty (giving NullPointerException)!
        record = 1;
        exerciseIDText.setText(String.valueOf(record));
        try {
            exerciseNameText.setText(bbDatabase.getInstance().exerciseOnFileKey(record).getString(bbDatabase.ExerciseNameINDEX));
        } catch (SQLException error) {
            System.out.println("Problem with pairing tblExercise to UI\n" + error.getMessage());
        } catch (NullPointerException nullError){
            System.out.println("ExercisePage NullPointerException: tblExercise empty?\n" + nullError.getLocalizedMessage());
        }
        buttonPrevious.setDisable(true);
    }



    // scene navigation --------------------------------------------------------------------------------

    @FXML
    private void showMainPage() {
        sceneNavigation.getInstance().mainPage();
    }

    @FXML
    private void addExercise() {
        sceneNavigation.getInstance().addExercise();
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
    private void repScene(){
        sceneNavigation.getInstance().repPage();
    }

    @FXML
    private void setScene(){
        sceneNavigation.getInstance().setPage();
    }

    // Previous and Next buttons --------------------------------------------------------------------------------

    @FXML
    private void onNextClicked() {
        record++;
        exerciseIDText.setText(String.valueOf(record));
        if (record > 1) {
            buttonPrevious.setDisable(false);
        } else {
            buttonPrevious.setDisable(true);
            videoURLText.setText("");
        }
        if (bbDatabase.getInstance().exerciseOnFileKey(record) == null) {
            exerciseNameText.setText("No exercise with id: " + record);
        } else {
            try {
                exerciseNameText.setText(bbDatabase.getInstance().exerciseOnFileKey(record).getString(bbDatabase.ExerciseNameINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblExercise to UI\n" + error.getMessage());
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
        exerciseIDText.setText(String.valueOf(record));
        if (record <= 1) {
            buttonPrevious.setDisable(true);
            videoURLText.setText("Back at the beginning");
        } else {
            buttonPrevious.setDisable(false);
            videoURLText.setText("");
        }
        if (bbDatabase.getInstance().exerciseOnFileKey(record) == null) {
            exerciseNameText.setText("No exercise with id: " + record);
        } else {
            try {
                exerciseNameText.setText(bbDatabase.getInstance().exerciseOnFileKey(record).getString(bbDatabase.ExerciseNameINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblExercise to UI\n" + error.getMessage());
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
