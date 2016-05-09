package agtp.utils;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author reza
 */
public class ParentControl implements Initializable {

    public My_Action my_action;

    public void set_My_Action(My_Action<?> my_action) {
        this.my_action = my_action;
    }

    public BooleanProperty isClosed = new SimpleBooleanProperty(false);
    public Stage thisStage;

    public void setStage(Stage s) {
        this.thisStage = s;
        thisStage.setOnCloseRequest((WindowEvent event) -> {
            on_close();
        });
    }

    public void show_Front_Or_Wait() {
        thisStage.sizeToScene();
        if (!thisStage.isShowing()) {
            show_And_Wait();
        } else {
            show_On_Front();
        }
    }

    public void show_On_Front() {
        thisStage.toFront();
        thisStage.setIconified(false);
    }

    public void show_And_Wait() {
        thisStage.showAndWait();
    }

    public void on_close() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
