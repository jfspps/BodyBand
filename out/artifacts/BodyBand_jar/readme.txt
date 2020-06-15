To get BodyBand.jar to run, first make sure that the latest version of JDK 11 is installed on your system. Verify installation and version by running "java -version" from the command line. You should get at least "java version "11.0.7" ....." as a response.

Download and extract JavaFX11 (https://gluonhq.com/products/javafx/), making a note of the path to /lib. The file bodyband.db which accompanies the BodyBand.jar file functions as the database record.

Run the following command, changing your path (mine happened to be "/home/james/Dev/javafx-sdk-11.0.2/lib") to the /lib directory of JavaFX files.

java --module-path /home/james/Dev/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar BodyBand.jar
