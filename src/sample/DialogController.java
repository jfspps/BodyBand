package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    private TextField someDesc;

    public void processData(){
        //trim() removes all leading and trailing whitespace
        String shortDesc = someDesc.getText().trim();

        System.out.println("Data received:\n" + shortDesc);

        //pass the String to the DB...(possibly defined in bbDatabase)

    }
}
