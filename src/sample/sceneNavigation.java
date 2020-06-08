package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;

public class sceneNavigation {

    private static sceneNavigation instance = new sceneNavigation();
    private sceneNavigation(){
        //empty constructor
    }
    public static sceneNavigation getInstance(){
        return instance;
    }

    //main pages ------------------------------------------------------------------------------------------
    
    public void mainPage() {
        try {
            Parent mainPage = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
            Main.mainWindow.setTitle("BodyBand");
            Main.mainWindow.setScene(new Scene(mainPage));
        } catch (IOException e) {
            System.out.println("Problem loading main scene:\n" + e.getCause());
        }
    }

    public void exercisePage() {
        try {
            Parent exercisePage = FXMLLoader.load(getClass().getResource("FXML/ExercisePage.fxml"));
            Main.mainWindow.setTitle("BodyBand exercises");
            Main.mainWindow.setScene(new Scene(exercisePage));
        } catch (
                IOException e) {
            System.out.println("Problem loading exercise scene:\n" + e.getCause());
        }
    }

    public void bandStatPage() {
        try {
            Parent bandStatPage = FXMLLoader.load(getClass().getResource("FXML/BandStatPage.fxml"));
            Main.mainWindow.setTitle("BodyBand band stats");
            Main.mainWindow.setScene(new Scene(bandStatPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading band stat scene:\n");
            e.printStackTrace();
        }
    }

    public void setPage() {
        try {
            Parent setPage = FXMLLoader.load(getClass().getResource("FXML/SetPage.fxml"));
            Main.mainWindow.setTitle("BodyBand sets");
            Main.mainWindow.setScene(new Scene(setPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading set scene:\n" + e.getCause());
        }
    }

    public void repPage() {
        try {
            Parent repPage = FXMLLoader.load(getClass().getResource("FXML/RepPage.fxml"));
            Main.mainWindow.setTitle("BodyBand reps");
            Main.mainWindow.setScene(new Scene(repPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading rep scene:\n" + e.getCause());
        }
    }

    public void exitBB(){
        Platform.exit();
    }

    // pages to add new records ----------------------------------------------------------------------------

    public void addBandStat() {
        try {
            Parent addBandStat = FXMLLoader.load(getClass().getResource("FXML/bandStatDialog.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new band stat");
            Main.mainWindow.setScene(new Scene(addBandStat));
        } catch (IOException e) {
            System.out.println("Problem loading new band stat scene:\n" + e.getCause());
        }
    }

    public void addExercise() {
        try {
            Parent addExercise = FXMLLoader.load(getClass().getResource("FXML/exerciseDialog.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new exercise");
            Main.mainWindow.setScene(new Scene(addExercise));
        } catch (IOException e) {
            System.out.println("Problem loading new exercise scene:\n" + e.getCause());
        }
    }

    public void addRep() {
        try {
            Parent addRep = FXMLLoader.load(getClass().getResource("FXML/repDialog.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new rep");
            Main.mainWindow.setScene(new Scene(addRep));
        } catch (IOException e) {
            System.out.println("Problem loading new rep scene:\n" + e.getCause());
        }
    }

    public void addSet() {
        try {
            Parent addSet = FXMLLoader.load(getClass().getResource("FXML/setDialog.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new set");
            Main.mainWindow.setScene(new Scene(addSet));
        } catch (IOException e) {
            System.out.println("Problem loading new set scene:\n" + e.getCause());
        }
    }

    // Dialog (alert) boxes

    public void showInfoAlert(String title, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.show();
    }

    public void showInfoAlert(String title, String header, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }

    // End-user UI -----------------------------------------------------------------------

    public void showMuscleExerciseList(){
        try {
            FXMLLoader exerciseListLoader = new FXMLLoader(getClass().getResource("FXML/exerciseChoice.fxml"));
            Parent newSet = exerciseListLoader.load();
            exerciseChoiceControl controller = exerciseListLoader.getController();
            controller.listMuscleGroupAndExercises();

            Main.mainWindow.setTitle("BodyBand - Muscle groups");
            Main.mainWindow.setScene(new Scene(newSet));
        } catch (IOException e) {
            System.out.println("Problem loading Muscle Group scene:\n" + e.getCause());
        }
    }

    public void showExerciseSet() {
        try {
            FXMLLoader exerciseSetLoader = new FXMLLoader(getClass().getResource("FXML/exerciseSet.fxml"));
            Parent newSet = exerciseSetLoader.load();
            exerciseSetControl controller = exerciseSetLoader.getController();

            Main.mainWindow.setTitle("BodyBand - set details");
            Main.mainWindow.setScene(new Scene(newSet));;
        } catch (IOException e) {
            System.out.println("Problem loading exercise set scene:\n" + e.getCause());
        }
    }
}
