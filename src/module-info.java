module BodyBand {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens sample;

    //required for JavaFX to access BodyBand DB methods
    opens sample.model;
}