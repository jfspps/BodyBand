package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import sample.controller.*;

import java.io.IOException;

public class sceneNavigation {

    private static sceneNavigation instance = new sceneNavigation();
    private sceneNavigation(){
        //empty constructor
    }
    public static sceneNavigation getInstance(){
        return instance;
    }

    //main and admin pages ------------------------------------------------------------------------------------------

    public void showMainPage() {
        try {
            Parent mainPage = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
            Main.mainWindow.setTitle("BodyBand");
            Main.mainWindow.setScene(new Scene(mainPage));
        } catch (IOException e) {
            System.out.println("Problem loading main scene:\n" + e.getCause());
        }
    }

    public void showAdminExPage() {
        try {
            Parent exercisePage = FXMLLoader.load(getClass().getResource("FXML/adminExercise.fxml"));
            Main.mainWindow.setTitle("BodyBand exercises");
            Main.mainWindow.setScene(new Scene(exercisePage));
        } catch (
                IOException e) {
            System.out.println("Problem loading exercise scene:\n" + e.getCause());
        }
    }

    public void showAdminSetPage() {
        try {
            Parent setPage = FXMLLoader.load(getClass().getResource("FXML/adminSet.fxml"));
            Main.mainWindow.setTitle("BodyBand sets");
            Main.mainWindow.setScene(new Scene(setPage));
        } catch (
                IOException e) {
            System.out.println("Problem loading set scene:\n" + e.getCause());
        }
    }

    public void showAdminRepPage() {
        try {
            Parent repPage = FXMLLoader.load(getClass().getResource("FXML/adminRepetition.fxml"));
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

    public void showAdminNewExPage() {
        try {
            Parent addExercise = FXMLLoader.load(getClass().getResource("FXML/adminNewEx.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new exercise");
            Main.mainWindow.setScene(new Scene(addExercise));
        } catch (IOException e) {
            System.out.println("Problem loading new exercise scene:\n" + e.getCause());
        }
    }

    public void showAdminNewRepPage() {
        try {
            Parent addRep = FXMLLoader.load(getClass().getResource("FXML/adminNewRep.fxml"));
            Main.mainWindow.setTitle("BodyBand - add new rep");
            Main.mainWindow.setScene(new Scene(addRep));
        } catch (IOException e) {
            System.out.println("Problem loading new rep scene:\n" + e.getCause());
        }
    }

    public void showAdminNewSetPage() {
        try {
            Parent addSet = FXMLLoader.load(getClass().getResource("FXML/adminNewSet.fxml"));
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

    //Directs the user to the BodyBnad options page
    public void showOptionsPage(){
        try {
            FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("FXML/Options.fxml"));
            Parent newSet = optionsLoader.load();

            Main.mainWindow.setTitle("Options");
            Main.mainWindow.setScene(new Scene(newSet));
        } catch (IOException e) {
            System.out.println("Problem loading options scene:\n" + e.getCause());
        }
    }

    // 1A: user selects an exercise for a new workout...
    public void showUserNewSet1A(){
        try {
            FXMLLoader exerciseListLoader = new FXMLLoader(getClass().getResource("FXML/userNewSet1A.fxml"));
            Parent newSet = exerciseListLoader.load();
            userNewSetControl1A controller = exerciseListLoader.getController();
            controller.listMuscleGroupAndExercises();

            Main.mainWindow.setTitle("Exercise list");
            Main.mainWindow.setScene(new Scene(newSet));
        } catch (IOException e) {
            System.out.println("Problem loading 1A scene:\n" + e.getCause());
        }
    }

    // 1B: user sets tension and rep count for given exercise
    public void showUserNewRep1B() {
        try {
            FXMLLoader exerciseSetLoader = new FXMLLoader(getClass().getResource("FXML/userNewRep1B.fxml"));
            Parent newSet = exerciseSetLoader.load();
            userNewRepControl1B controller2 = exerciseSetLoader.getController();
            controller2.listRepetitionsRepString();

            Main.mainWindow.setTitle("Exercise and workout records");
            Main.mainWindow.setScene(new Scene(newSet));;
        } catch (IOException e) {
            System.out.println("Problem loading 1B scene:\n" + e.getCause());
        }
    }

    // shows the previous workout history for a given exercise
    public void showExerciseHistory(){
        try{
            FXMLLoader exerciseHistoryLoader = new FXMLLoader(getClass().getResource("FXML/userExerciseHistory.fxml"));
            Parent newHistorySet = exerciseHistoryLoader.load();
            userExerciseHistory controller3 = exerciseHistoryLoader.getController();
            controller3.listAllDates();

            Main.mainWindow.setTitle("Exercise history");
            Main.mainWindow.setScene(new Scene(newHistorySet));
        } catch (IOException e) {
            System.out.println("Problem loading exercise history scene:\n" + e.getCause());
        }
    }


    // 2A: user selects a previous date...
    public void showUserPrevDate2A(){
        try {
            FXMLLoader dateListLoader = new FXMLLoader(getClass().getResource("FXML/userPrevDate2A.fxml"));
            Parent newDateSet = dateListLoader.load();
            userPrevDateControl2A controller3 = dateListLoader.getController();
            controller3.listAllDates();

            Main.mainWindow.setTitle("Workout dates");
            Main.mainWindow.setScene(new Scene(newDateSet));
        } catch (IOException e) {
            System.out.println("Problem loading Previous Dates scene:\n" + e.getCause());
        }
    }

    // 2B: user then selects an exercise from the previous date...
    public void showUserPrevEx2B() {
        try {
            FXMLLoader exerciseSetLoader = new FXMLLoader(getClass().getResource("FXML/userPrevEx2B.fxml"));
            Parent newSet = exerciseSetLoader.load();
            userPrevExControl2B controller4 = exerciseSetLoader.getController();
            controller4.listMuscleGroupAndExercises();

            Main.mainWindow.setTitle("Exercises found");
            Main.mainWindow.setScene(new Scene(newSet));;
        } catch (IOException e) {
            System.out.println("Problem loading exercise set scene:\n" + e.getCause());
        }
    }

    // 2C: user then views the band tensions and rep counts for the given exercise at the given date
    public void showUserPrevRep2C() {
        try {
            FXMLLoader exerciseSetLoader = new FXMLLoader(getClass().getResource("FXML/userPrevRep2C.fxml"));
            Parent newSet = exerciseSetLoader.load();
            userPrevRepControl2C controller5 = exerciseSetLoader.getController();
            controller5.listPreviousRepetitionsRepString();

            Main.mainWindow.setTitle("Exercise and workout records");
            Main.mainWindow.setScene(new Scene(newSet));;
        } catch (IOException e) {
            System.out.println("Problem loading exercise set scene:\n" + e.getCause());
        }
    }

    // About page
    public void showAboutPage() {
        try {
            FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("FXML/About.fxml"));
            Parent aboutScene = aboutLoader.load();

            Main.mainWindow.setTitle("About BodyBand");
            Main.mainWindow.setScene(new Scene(aboutScene));;
        } catch (IOException e) {
            System.out.println("Problem loading About scene:\n" + e.getCause());
        }
    }
}
