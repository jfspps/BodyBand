package sample;

import javafx.application.Platform;
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

public class repController implements Initializable {

    //record is the index of each record in bbExercise, the PK of which is one-based
    private int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        record = 1;
        repIDText.setText(String.valueOf(record));
        try {
            bandStatIDText.setText(bbDatabase.getInstance().repetitionOnFileKey(record).getString(bbDatabase.RepetitionBandStatIdINDEX));
        } catch (SQLException error) {
            System.out.println("Problem with pairing db to UI\n" + error.getMessage());
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
        dialog.setTitle("Add new repetition");
        dialog.setHeaderText("Add new repetition (header)");
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            controller.processData();
            System.out.println("Okay");
        } else {
            System.out.println("Cancelled");
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

    @FXML
    private void exerciseScene(){
        try {
            Parent exercisePage = FXMLLoader.load(getClass().getResource("FXML/ExercisePage.fxml"));
            Main.mainWindow.setTitle("BodyBand exercises");
            Main.mainWindow.setScene(new Scene(exercisePage));
        } catch (
                IOException e) {
            System.out.println("Problem loading exercise scene:\n" + e.getMessage());
        }
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
