package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sample.model.bbDatabase;
import sample.model.bbRepetition;
import sample.model.bbSet;

import java.util.List;

public class userExerciseHistory {

    @FXML
    private TableView<bbSet> dateTable;

    @FXML
    private TableView<bbRepetition> repTable;

    @FXML
    private Label repMaxLabel;

    @FXML
    private void onClickedMainPage() {
        sceneNavigation.getInstance().showMainPage();
    }

    @FXML
    private void onClickedGoBack() {
        sceneNavigation.getInstance().showUserNewRep1B();
    }

    @FXML
    private void onClickedDateRow() {
        if (dateTable.getSelectionModel().getSelectedItem() != null) {
           repTable.itemsProperty().setValue(getRepList());
        } else {
            repTable.itemsProperty().setValue(null);
        }
        repMaxLabel.setText("");
    }

    //this method estimates the 1 rep max from the selected repTable row
    @FXML
    private void onClickedRepTable(){
        if (repTable.getSelectionModel().getSelectedItem() != null){
            float tension = repTable.getSelectionModel().getSelectedItem().getTension();
            int reps = repTable.getSelectionModel().getSelectedItem().getReps();

            //use the Brzycki formula to estimate 1RM
            float oneRepMax = (tension / (1.0278f - (0.0278f * reps)));
            float fourRepMax = 0.92f * oneRepMax;
            float eightRepMax = 0.81f * oneRepMax;
            String oneRM = String.format("%.2f",oneRepMax);
            String fourRM = String.format("%.2f",fourRepMax);
            String eightRM = String.format("%.2f",eightRepMax);
            repMaxLabel.setText("1RM:  " + oneRM + "\n4RM:  " + fourRM + "\n8RM:  " + eightRM);
        } else {
            repMaxLabel.setText("");
        }
    }

    public void listAllDates() {
        Task<ObservableList<bbSet>> dateTask = new GetAllSetDatesForExerciseTask();

        //sync the FXML TableView with the data from GetAllRepetitionsForSet
        dateTable.itemsProperty().bind(dateTask.valueProperty());

        //run a separate thread to populate the list after the UI is prepared
        new Thread(dateTask).start();
    }

    private ObservableList<bbRepetition> getRepList(){
        //get the repString from dateTable and retrieve the List<>
        String repIndex = dateTable.getSelectionModel().getSelectedItem().getRepIdSequence();
        List<bbRepetition> tempRepList = bbDatabase.getInstance().getRepListFromRepString(repIndex);

        // send back an observable list
        return FXCollections.observableList(tempRepList);
    }
}

class GetAllSetDatesForExerciseTask extends Task {

    @Override
    public ObservableList<bbSet> call() {
        // returns a List<> which is then pass to and converted to an ObservableList for data binding
        // purposes (bbExercise variables are defined as Simple Properties to enable data binding)
        return FXCollections.observableArrayList(bbDatabase.getInstance().getSetDateRepSeqByExercise(userNewSetControl1A.getCurrentExerciseID()));
    }
}