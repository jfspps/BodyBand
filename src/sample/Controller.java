package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller {

    private int record;

    //declare arrays intended to store DB data here

    @FXML
    public void initialize() {
        //initialize any of the fields (and initialise arrays from the DB ready for use) on the JavaFX form
        exerciseNameText.setText("Click the arrow buttons below to cycle through records");
        record = 0;
        buttonPrevious.setDisable(true);
        buttonNext.setDisable(false);
    }

    //establish all JavaFX controls needed
    //tblExercise
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
    private void onNextClicked() {
        record++;
        if (record > 0) {
            buttonPrevious.setDisable(false);
            videoURLText.clear();
        }

        //example of running "background" processes on the JavaFX "UI thread" (separate, single thread) when Next is
        // clicked
        Runnable background = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    //the UI is still operable since this is running on a thread separate to
                    // UI thread, for now...(could be used as a countdown between sets)

                    //once the background thread has passed, control is then assigned to UI thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            descriptionText.setText("10 seconds have elapsed");
                        }
                    });
                } catch (InterruptedException event) {
                    //what happens if the background task is interrupted (independent of the UI controls)
                    System.out.println("Some background task caused an interrupt: " + event.getMessage());
                }
            }
        };
        new Thread(background).start();

        //print current date and time
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));
        LocalTime localTime = LocalTime.now();
        String time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        exerciseIDText.setText(date + " at " + time);

    }

    @FXML
    private void onPreviousClicked() {
        record--;
        if (record == 0) {
            buttonPrevious.setDisable(true);
            videoURLText.setText("Back at the beginning");
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

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            controller.processData();
            System.out.println("Okay");
        } else {
            System.out.println("Cancelled");
        }
    }
}
