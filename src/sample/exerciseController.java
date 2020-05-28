package sample;

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
    private BorderPane mainBorderPane;;
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
    private void showDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        //dialog boxes are automatically MODAL
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXML/Dialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException err) {
            System.out.println("Dialog not loading: " + err.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Add new exercise");
        dialog.setHeaderText("Add new exercise (header)");
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            controller.processData();
            System.out.println("Okay");
        } else {
            System.out.println("Cancelled");
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
