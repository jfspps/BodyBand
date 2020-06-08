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
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;

    //record is the index of each record in bbExercise, the PK of which is one-based
    private static int record;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Note that this page does not load is the table is empty (giving NullPointerException) hence the second catch
        record = bbDatabase.getInstance().getFirstExercise();
        if (record == 0) {
            sceneNavigation.getInstance().showInfoAlert("Exercise", "Exercise DB empty", "Please add a new exercise " +
                    "to proceed");
            //prevent user access to each field
            exerciseIDText.setDisable(true);
            exerciseNameText.setDisable(true);
            muscleGroupText.setDisable(true);
            anchorNeededText.setDisable(true);
            anchorHeightText.setDisable(true);
            anchorPositionText.setDisable(true);
            descriptionText.setDisable(true);
            videoURLText.setDisable(true);

            buttonNext.setDisable(true);
            buttonPrevious.setDisable(true);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else if (record > 0) {
            {
                try (ResultSet exerciseSet = bbDatabase.getInstance().exerciseOnFileKey(record)) {
                    buttonPrevious.setDisable(true);
                    exerciseIDText.setText(String.valueOf(record));

                    exerciseNameText.setText(exerciseSet.getString(bbDatabase.ExerciseNameINDEX));
                    muscleGroupText.setText(exerciseSet.getString(bbDatabase.ExerciseMuscleGroupINDEX));
                    anchorNeededText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorNeededINDEX));
                    anchorHeightText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorHeightINDEX));
                    anchorPositionText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorPositionINDEX));
                    descriptionText.setText(exerciseSet.getString(bbDatabase.ExerciseDescINDEX));
                    videoURLText.setText(exerciseSet.getString(bbDatabase.ExerciseVideoURLINDEX));
                } catch (SQLException error) {
                    System.out.println("Problem with pairing tblExercise to UI\n" + error.getMessage());
                } catch (NullPointerException nullError) {
                    System.out.println("ExercisePage NullPointerException: current tblExercise record is null\n" + nullError.getLocalizedMessage());
                }
            }
        } else {
            sceneNavigation.getInstance().showInfoAlert("Exercise", "Problem with accessing Exercise DB");
            showMainPage();
        }
    }

    // scene navigation --------------------------------------------------------------------------------

    @FXML
    private void showMainPage() {
        sceneNavigation.getInstance().showMainPage();
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
        sceneNavigation.getInstance().showBandStatPage();
    }

    @FXML
    private void repScene(){
        sceneNavigation.getInstance().showRepPage();
    }

    @FXML
    private void setScene(){
        sceneNavigation.getInstance().showSetPage();
    }

    // Previous and Next buttons --------------------------------------------------------------------------------

    @FXML
    private void onNextClicked() {
        record++;
        if (bbDatabase.getInstance().exerciseOnFileKey(record) == null) {
            System.out.println("No exercise with id: " + record);
            exerciseIDText.setText(String.valueOf(record));
            exerciseNameText.setText("");
            muscleGroupText.setText("");
            anchorNeededText.setText("");
            anchorHeightText.setText("");
            anchorPositionText.setText("");
            descriptionText.setText("");
            videoURLText.setText("");

            exerciseIDText.setDisable(true);
            exerciseNameText.setDisable(true);
            muscleGroupText.setDisable(true);
            anchorNeededText.setDisable(true);
            anchorHeightText.setDisable(true);
            anchorPositionText.setDisable(true);
            descriptionText.setDisable(true);
            videoURLText.setDisable(true);

            buttonPrevious.setDisable(false);
            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try (ResultSet exerciseSet = bbDatabase.getInstance().exerciseOnFileKey(record)) {
                exerciseIDText.setDisable(false);
                exerciseNameText.setDisable(false);
                muscleGroupText.setDisable(false);
                anchorNeededText.setDisable(false);
                anchorHeightText.setDisable(false);
                anchorPositionText.setDisable(false);
                descriptionText.setDisable(false);
                videoURLText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                buttonPrevious.setDisable(false);
                exerciseIDText.setText(String.valueOf(record));
                exerciseNameText.setText(exerciseSet.getString(bbDatabase.ExerciseNameINDEX));
                muscleGroupText.setText(exerciseSet.getString(bbDatabase.ExerciseMuscleGroupINDEX));
                anchorNeededText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorNeededINDEX));
                anchorHeightText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorHeightINDEX));
                anchorPositionText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorPositionINDEX));
                descriptionText.setText(exerciseSet.getString(bbDatabase.ExerciseDescINDEX));
                videoURLText.setText(exerciseSet.getString(bbDatabase.ExerciseVideoURLINDEX));
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
        if(record == 1){
            buttonPrevious.setDisable(true);
        }
        if (bbDatabase.getInstance().exerciseOnFileKey(record) == null) {
            System.out.println("No exercise with id: " + record);
            exerciseIDText.setText(String.valueOf(record));
            exerciseNameText.setText("");
            muscleGroupText.setText("");
            anchorNeededText.setText("");
            anchorHeightText.setText("");
            anchorPositionText.setText("");
            descriptionText.setText("");
            videoURLText.setText("");

            exerciseIDText.setDisable(true);
            exerciseNameText.setDisable(true);
            muscleGroupText.setDisable(true);
            anchorNeededText.setDisable(true);
            anchorHeightText.setDisable(true);
            anchorPositionText.setDisable(true);
            descriptionText.setDisable(true);
            videoURLText.setDisable(true);

            buttonUpdate.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            try (ResultSet exerciseSet = bbDatabase.getInstance().exerciseOnFileKey(record)) {
                exerciseIDText.setDisable(false);
                exerciseNameText.setDisable(false);
                muscleGroupText.setDisable(false);
                anchorNeededText.setDisable(false);
                anchorHeightText.setDisable(false);
                anchorPositionText.setDisable(false);
                descriptionText.setDisable(false);
                videoURLText.setDisable(false);

                buttonUpdate.setDisable(false);
                buttonDelete.setDisable(false);

                exerciseIDText.setText(String.valueOf(record));
                exerciseNameText.setText(exerciseSet.getString(bbDatabase.ExerciseNameINDEX));
                muscleGroupText.setText(exerciseSet.getString(bbDatabase.ExerciseMuscleGroupINDEX));
                anchorNeededText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorNeededINDEX));
                anchorHeightText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorHeightINDEX));
                anchorPositionText.setText(exerciseSet.getString(bbDatabase.ExerciseAnchorPositionINDEX));
                descriptionText.setText(exerciseSet.getString(bbDatabase.ExerciseDescINDEX));
                videoURLText.setText(exerciseSet.getString(bbDatabase.ExerciseVideoURLINDEX));
            } catch (SQLException error) {
                System.out.println("Problem with pairing tblExercise to UI\n" + error.getMessage());
            }
        }
    }

    @FXML
    private void onUpdateClicked(){
        int outputInt = bbDatabase.getInstance().updateExercise(
                record,
                exerciseNameText.getText().trim(),
                muscleGroupText.getText().trim(),
                anchorNeededText.getText().trim(),
                anchorHeightText.getText().trim(),
                anchorPositionText.getText().trim(),
                descriptionText.getText().trim(),
                videoURLText.getText().trim()
        );
        System.out.println("Update code: " + outputInt);
    }

    @FXML
    private void onDeleteClicked(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("All other records with this exercise will also be deleted");
        alert.setContentText("Click OK to confirm deletion");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int record = Integer.parseInt(exerciseIDText.getText());
                int deleted = bbDatabase.getInstance().deleteExercise(record);
                System.out.println("Record with id " + deleted + " deleted successfully");
                exerciseNameText.setText("");
                muscleGroupText.setText("");
                anchorNeededText.setText("");
                anchorHeightText.setText("");
                anchorPositionText.setText("");
                descriptionText.setText("");
                videoURLText.setText("");

                exerciseIDText.setDisable(true);
                exerciseNameText.setDisable(true);
                muscleGroupText.setDisable(true);
                anchorNeededText.setDisable(true);
                anchorHeightText.setDisable(true);
                anchorPositionText.setDisable(true);
                descriptionText.setDisable(true);
                videoURLText.setDisable(true);

                buttonUpdate.setDisable(true);
                buttonDelete.setDisable(true);
            }
        });
    }
}
