package agtp.fxml.actionsManage;

import agtp.dataBase.tables.Users2;
import agtp.myControl.MyButtonFont;
import agtp.myControl.tableView.PrepareTable;
import dccletter.DCCLetter;
import static dccletter.DCCLetter.databaseHelper;
import dccletter.utils.ErrorCheck;
import dccletter.utils.ParentControl;
import dccletter.utils.UtilsMsg;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLactionsManageController extends ParentControl {

    @FXML
    private VBox step_1;
    @FXML
    private VBox step_2;

    @FXML
    private MyButtonFont add;
    @FXML
    private MyButtonFont remove;
    @FXML
    private MyButtonFont edit;

    @FXML
    private TableView<Users2> user_table;
    @FXML
    private TextField filter_user_table;

    @FXML
    private CheckBox is_deactive;
    @FXML
    private TextField name;
    @FXML
    private TextField company;
    @FXML
    private TextField position;
    @FXML
    private Button back_2;
    @FXML
    private Button submit_2;

    private Users2 editActions;

    private void user_table_init() {
        filter_user_table.setText("");
        FilteredList<Users2> filteredListt = new PrepareTable<Users2>().init(user_table, databaseHelper.users2Dao.rawResults(
                "SELECT * FROM users2 WHERE type LIKE 'ACTION' AND is_deleted = 0 ORDER BY name_fa"
        ));
        filter_user_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : (s.getName_fa() + s.getName_en() + s.getUsername()).toLowerCase().contains(newValue.toLowerCase()));
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        step_1_init();
        step_2_init();
    }

    private void step_1_init() {
        remove.disableProperty().bind(user_table.getSelectionModel().selectedItemProperty().isNull());
        edit.disableProperty().bind(remove.disableProperty());

        add.init("plus", 15);
        remove.init("trash", 15);
        edit.init("pencil", 15);

        add.setOnAction((ActionEvent event) -> {
            step_1.setVisible(false);
            step_2.setVisible(true);
            editActions = new Users2();

        });

        remove.setOnAction((ActionEvent event) -> {
            Users2 u2 = user_table.getSelectionModel().getSelectedItem();
            if (!UtilsMsg.showMsg("آیا از حذف " + u2.getName_fa() + " مطمئن هستید؟", "هشدار", true, thisStage)) {
                return;
            }
            u2.setIs_deleted(Boolean.TRUE);
            databaseHelper.users2Dao.createOrUpdate(u2);
            user_table_init();
        });

        edit.setOnAction((ActionEvent event) -> {
            add.getOnAction().handle(event);
            editActions = user_table.getSelectionModel().getSelectedItem();
            is_deactive.setSelected(!editActions.getActive());
            name.setText(editActions.getName_fa());
            position.setText(editActions.getSemat());
        });

        user_table.setRowFactory((TableView<Users2> param) -> {
            TableRow<Users2> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() >= 2 && !edit.isDisable()) {
                    edit.getOnAction().handle(null);
                }
            });
            return row;
        });
        user_table_init();
    }

    private void step_2_init() {
        back_2.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_2.setVisible(false);
            name.setText("");
            company.setText("");
            position.setText("");
            is_deactive.setSelected(false);
            user_table_init();
        });

        submit_2.setOnAction((ActionEvent event) -> {
            ErrorCheck errorCheck = new ErrorCheck("نام");
            if (errorCheck.checked(false, "خطا", thisStage, name) != -1) {
                return;
            }
            if (editActions.getId() == null) {
                String query = "SELECT * FROM users2 WHERE name_fa LIKE '" + name.getText() + "' AND semat LIKE '" + position.getText() + "'";
                if (!DCCLetter.databaseHelper.users2Dao.rawResults(query).isEmpty()) {
                    UtilsMsg.showMsg("اطلاعات وارد شده تکراری می باشد.", "اخطار", false, thisStage);
                    return;
                }
            }
            editActions.setName_fa(name.getText());
            editActions.setSemat(position.getText());
            editActions.setActive(!is_deactive.isSelected());
            editActions.setType(Users2.ACTION);
            DCCLetter.databaseHelper.users2Dao.createOrUpdate(editActions);
            user_table_init();
            back_2.getOnAction().handle(event);
        });

    }

}
