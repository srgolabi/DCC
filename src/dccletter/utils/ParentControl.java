/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 *
 * @author reza
 */
public class ParentControl implements Initializable {

    public Stage thisStage;

    public void setStage(Stage s) {
        this.thisStage = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void requestFocus(Control c) {
        Platform.runLater(c::requestFocus);
    }
}
