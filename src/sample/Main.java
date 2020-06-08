package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.bbDatabase;

public class Main extends Application {

    //give access to the mainPage Stage
    static Stage mainWindow;

    // Initialise everything when the JavaFX dialog box loads (init() runs before start(); init() and stop() are
    // abstract by default)
    @Override
    public void init() throws Exception {
        super.init();       // fundamentally abstract, init() does nothing in JavaFX8 at least (leave it here in case
        // later versions change init())

        //attempt to connect to and verify PreparedStatements of "database"
        if (!bbDatabase.getInstance().open()) {
            System.out.println("Problem with opening DB queries");

            //force the UI to terminate if the DB does not load
            Platform.exit();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //assign whatever primaryStage has to mainWindow
        mainWindow = primaryStage;
        Parent mainPage = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setTitle("BodyBand");
        mainWindow.setScene(new Scene(mainPage, 335, 600));
        mainWindow.show();
    }

    //clear everything up on closing
    @Override
    public void stop() throws Exception {
        super.stop();   // fundamentally abstract, stop() does nothing in JavaFX8 at least

        //when done, close the database and all its resources...
        bbDatabase.getInstance().close();
    }

    public static void main(String[] args) {
        //JavaFX
        launch(args);
    }
}
