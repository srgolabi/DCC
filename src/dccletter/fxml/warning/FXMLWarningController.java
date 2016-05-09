/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.fxml.warning;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLWarningController implements Initializable {

    private Stage thisStage;

    @FXML
    private Label label;
    @FXML
    public Button cancel;
    @FXML
    public Button ok;

    public boolean okClick = false;

    /**
     * Initializes the controller class.
     */
    public void init(Stage s, String txt) {
        thisStage = s;
        label.setText(txt);
    }

    @FXML
    private void okClick() {
        okClick = true;
        thisStage.close();
    }

    @FXML
    private void cancelClick() {
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
