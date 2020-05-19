package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("BodyBand");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        //connect to SQLite vis JDBC
        try {
            //we use DriverManager instead of DataSource objects (more apt for Enterprise apps)
            Connection conn = DriverManager.getConnection("jdbc:sqlite:./bodyband.db");
            //Run SQL statements (semicolon not required) by instancing a Statement object:
            Statement statement = conn.createStatement();
            if(statement.execute("SELECT * FROM tblExercise"))
                System.out.println("Query successful");
            else
                System.out.println("No records in tblExercise");

            //release resources; alternatively use try with resources to automate this necessary step
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Could not connect to BodyBand db" + e.getMessage());
        }
        launch(args);
    }
}
