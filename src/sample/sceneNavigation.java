package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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

    // Testing and debugging only -----------------------------------------------------------------------

    public void testPage(){
        try {
            Parent testDialog = FXMLLoader.load(getClass().getResource("testPage.fxml"));
            Main.mainWindow.setTitle("BodyBand - test page");
            Main.mainWindow.setScene(new Scene(testDialog));
        } catch (IOException e) {
            System.out.println("Problem loading test scene:\n" + e.getCause());
        }
    }
}
