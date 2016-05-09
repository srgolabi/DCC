package dccletter.fxml.login;

import agtp.dataBase.tables.Users2;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.register;
import dccletter.utils.UtilsMsg;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLLoginController implements Initializable {

    private Stage thisStage;
    public boolean isAccess = false;
    public Users2 usersTemp;

    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button okButton;
    @FXML
    private CheckBox remember;

    /**
     * Initializes the controller class.
     *
     * @param s
     */
    public void init(Stage s) {
        this.thisStage = s;

        okButton.disableProperty().bind(userName.textProperty().isEmpty());

        userName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {
                if (!userName.getText().equals("") && userName.getEffect() != null) {
                    userName.setEffect(null);
                }
            }
        });
        Users2 uu = databaseHelper.users2Dao.queryForId(register.userID);
        if (uu != null) {
            userName.setText(uu.getUsername());
        }
        password.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (oldValue) {
                if (!password.getText().equals("") && password.getEffect() != null) {
                    password.setEffect(null);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void actionOk() throws SQLException {

        if (databaseHelper.users2Dao.getFirst("username", userName.getText()) == null) {
            UtilsMsg.showMsg("نام کاربری صحیح نمی باشد.", "هشدار", false, thisStage);
            userName.requestFocus();
            return;
        }

        usersTemp = databaseHelper.users2Dao.queryBuilder().where().eq("username", userName.getText())
                .and().eq("password", password.getText()).queryForFirst();

        if (usersTemp == null) {
            UtilsMsg.showMsg("رمز عبور صحیح نمی باشد.", "اخطار", false, thisStage);
            password.requestFocus();
            return;
        }

        if (!usersTemp.getActive()) {
            UtilsMsg.showMsg("این نام کاربری غیرفعال شده است.", "اخطار", false, thisStage);
            return;
        }
//        Register register = new Register();
        register.userID = usersTemp.getId();
        register.checkLogin(remember.isSelected());
        isAccess = true;
        thisStage.close();
    }

    @FXML
    public void actionCancel() {
        thisStage.close();
    }
}
