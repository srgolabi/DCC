package dccletter.fxml.simpleSearch;

import agtp.dataBase.tables.Permission;
import agtp.myControl.MyButtonFont;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.users;
import dccletter.dataBase.tables.Letters3;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLSimpleSearchController implements Initializable {

    public interface OnAction {

        void ok(Letters3 l);
    }

    @FXML
    public TextField searchText;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton andicator_number;
    @FXML
    private RadioButton letter_number;
    @FXML
    private RadioButton letter_subject;
    @FXML
    public MyButtonFont searchNext;
    @FXML
    public MyButtonFont searchBack;
    @FXML
    public MyButtonFont first;
    @FXML
    public MyButtonFont end;
    @FXML
    private MyButtonFont cancel;
    @FXML
    private MyButtonFont submit;
    @FXML
    private Label searchResault;

    public Stage thisStage;
    public OnAction onAction;
    private ListProperty<Letters3> searchList = new SimpleListProperty<>();
    private IntegerProperty idSearchProperty = new SimpleIntegerProperty(0);
    private String subQuery = "";
    String base_query = "";

    /**
     * Initializes the controller class.
     */
    public void init(Stage s, Node parent) {
        thisStage = s;
        searchBack.init("left_dir", 15);
        searchNext.init("right_dir", 15);
        first.init("to_start", 15);
        end.init("to_end", 15);
        cancel.init("cancel_1", 14, "table-button");
        submit.init("level_down", 14);
        cancel.setOnAction((ActionEvent event) -> {
            parent.setVisible(false);
        });

        if (databaseHelper.users2Dao.rawResults(
                "SELECT users2.* FROM users2 \n"
                + "LEFT OUTER JOIN userPermission ON users2.id = userPermission.user_id\n"
                + "WHERE ((userPermission.permission_id = " + Permission.LETTER_VIEW_ALL + " AND userPermission.state = 1 )"
                + " OR users2.admin = 1) AND users2.id = " + users.getId()).isEmpty()) {
            base_query = "SELECT letters3.* FROM letters3 "
                    + "LEFT OUTER JOIN letteraction ON letteraction.letter_id = letters3.id "
                    + "LEFT OUTER JOIN userGroup ON  letteraction.action_id = userGroup.group_id "
                    + "LEFT OUTER JOIN userPermission ON userGroup.user_id = userPermission.user_id "
                    + "WHERE userGroup.id IS NOT NULL AND userPermission.state = 1 AND subQuery AND userGroup.user_id = loginUserId ".replace("loginUserId", users.getId() + " ")
                    + "GROUP BY letters3.id";

        } else {
            base_query = "SELECT * FROM letters3 WHERE subQuery";
        }

        subQuery = "id = '" + searchText.getText() + "'";
        end.disableProperty().bind(searchNext.disableProperty());
        first.disableProperty().bind(searchBack.disableProperty());
        end.setOnAction((ActionEvent event) -> {
            idSearchProperty.set(searchList.getSize() - 1);
            moving();
        });
        first.setOnAction((ActionEvent event) -> {
            idSearchProperty.set(0);
            moving();
        });
        searchNext.setOnAction((ActionEvent event) -> {
            idSearchProperty.set(idSearchProperty.get() + 1);
            moving();
        });
        searchBack.setOnAction((ActionEvent event) -> {
            idSearchProperty.set(idSearchProperty.get() - 1);
            moving();
        });
        submit.setOnAction((ActionEvent event) -> {
            if (toggleGroup.getSelectedToggle() == andicator_number) {
                try {
                    subQuery = " letters3.id = " + Integer.parseInt(searchText.getText()) ;
                } catch (Exception e) {
                    subQuery = " letters3.id = 0";
                }
            } else if (toggleGroup.getSelectedToggle() == letter_number) {
                subQuery = " letters3.letterNumber LIKE '%" + searchText.getText() + "%'";
            } else if (toggleGroup.getSelectedToggle() == letter_subject) {
                subQuery = " letters3.subject LIKE '%" + searchText.getText() + "%'";
            }
            ObservableList<Letters3> observableList = null;
//            if (subQuery.startsWith(" letters3.id")) {
//                System.out.println("adsd" + subQuery.startsWith(" letters3.id"));
//                observableList = FXCollections.observableArrayList(databaseHelper.letters3Dao.rawResults(base_query.replace("subQuery", " letters3.id > 0")));
//            } else {
                observableList = FXCollections.observableArrayList(databaseHelper.letters3Dao.rawResults(base_query.replace("subQuery", subQuery)));
//            }
            searchList = new SimpleListProperty<>(observableList);
            searchBack.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.lessThan(1)));
            searchNext.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.isEqualTo(searchList.getSize() - 1)));
            if (!searchList.isEmpty()) {
                if (searchList.isEmpty()) {
                    this.onAction.ok(null);
                } else {
//                    if (subQuery.startsWith(" letters3.id")) {
//                        idSearchProperty.set(Integer.parseInt(searchText.getText()) - 1);
//                    } else {
                        idSearchProperty.set(0);
//                    }
                    moving();
                }
            } else {
                searchResault.setText("موردی یافت نشد");
            }
        });
        searchList = new SimpleListProperty<>();
        searchBack.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.lessThan(1)));
        searchNext.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.isEqualTo(searchList.getSize() - 1)));

        letter_subject.setSelected(true);
        searchText.requestFocus();
    }

    private void moving() {
        this.onAction.ok(searchList.get(idSearchProperty.get()));
        searchResault.setText(idSearchProperty.get() + 1 + " از " + searchList.size());
    }

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
