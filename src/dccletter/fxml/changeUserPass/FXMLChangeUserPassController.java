package dccletter.fxml.changeUserPass;

import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.users;
import dccletter.utils.ParentControl;
import dccletter.utils.TextFiledLimited;
import dccletter.utils.UtilsMsg;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLChangeUserPassController extends ParentControl {

    @FXML
    public TextField pass;
    @FXML
    private TextField passAgain;
    @FXML
    private Button okButton;
    @FXML
    private Button closeButton;

    public boolean okClicked = false;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        okButton.disableProperty().bind(pass.textProperty().isEmpty().or(passAgain.textProperty().isEmpty()));
        TextFiledLimited.setEnterFocuse(pass, passAgain, okButton, closeButton);
    }

    @FXML
    private void close() {
        thisStage.close();
    }

    @FXML
    private void ok() {
        if (!pass.getText().equals(passAgain.getText())) {
            UtilsMsg.showMsg("رمزعبور با تکرار آن مطابق نیست.", "اخطار", false, thisStage);
            return;
        }
        okClicked = true;
        thisStage.close();
        users.setPassword(pass.getText());
        databaseHelper.users2Dao.createOrUpdate(users);
        UtilsMsg.showMsg("رمز عبور با موفقیت تغییر یافت.", "", false, thisStage);
    }
}
