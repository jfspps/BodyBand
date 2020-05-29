package sample;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.bbDatabase;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class exerciseController implements Initializable {

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    //stores current state of the dialog box state
    private boolean firstAttempt = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        record = 1;
        exerciseIDText.setText(String.valueOf(record));
        try {
            exerciseNameText.setText(bbDatabase.getInstance().exerciseOnFileKey(record).getString(bbDatabase.ExerciseNameINDEX));
        } catch (SQLException error) {
            System.out.println("Problem with pairing db to UI\n" + error.getMessage());
        }
        buttonPrevious.setDisable(true);
    }

    @FXML
    private TextArea exerciseIDText;
    @FXML
    private TextArea anchorNeededText;
    @FXML
    private TextArea exerciseNameText;
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

    // scene navigation --------------------------------------------------------------------------------

    @FXML
    private void showMainPage() {
        try {
            Parent mainPage = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
            Main.mainWindow.setTitle("BodyBand");
            Main.mainWindow.setScene(new Scene(mainPage));
        } catch (IOException e) {
            System.out.println("Problem loading main scene:\n" + e.getMessage());
        }
    }

    @FXML
    private void addExercise() {
        try {
            Parent exerciseDialog = FXMLLoader.load(getClass().getResource("FXML/exerciseDialog.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new exercise");
            Main.mainWindow.setScene(new Scene(exerciseDialog));
        } catch (IOException e) {
            System.out.println("Problem loading new exercise scene:\n" + e.getMessage());
        }
    }


    @FXML
    private void exitBB(){
        Platform.exit();
    }

    @FXML
    private void bandStatScene(){
        try {
            Parent bandStatPage = FXMLLoader.load(getClass().getResource("FXML/BandStatPage.fxml"));
            Main.mainWindow.setTitle("BodyBand band stats");
            Main.mainWindow.setScene(new Scene(bandStatPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading band stat scene:\n" + e.getMessage());
        }
    }

    @FXML
    private void repScene(){
        try {
            Parent repPage = FXMLLoader.load(getClass().getResource("FXML/RepPage.fxml"));
            Main.mainWindow.setTitle("BodyBand reps");
            Main.mainWindow.setScene(new Scene(repPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading rep scene:\n" + e.getMessage());
        }
    }

    @FXML
    private void setScene(){
        try {
            Parent setPage = FXMLLoader.load(getClass().getResource("FXML/SetPage.fxml"));
            Main.mainWindow.setTitle("BodyBand sets");
            Main.mainWindow.setScene(new Scene(setPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading set scene:\n" + e.getMessage());
        }
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
                System.out.println("Problem with pairing db to UI\n" + error.getMessage());
            }
        }

//        //example of running "background" processes on the JavaFX "UI thread" (separate, single thread) when Next is
//        // clicked
//        Runnable background = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                    //the UI is still operable since this is running on a thread separate to
//                    // UI thread, for now...(could be used as a countdown between sets)
//
//                    //once the background thread has passed, control is then assigned to UI thread by runLater() to
//                    run this:
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

        //print current date and time
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));
        LocalTime localTime = LocalTime.now();
        String time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(date + " at " + time);
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
                System.out.println("Problem with pairing db to UI\n" + error.getMessage());
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
