package metting.fxml.simpleSearch;

import static dccletter.DCCLetter.databaseHelper;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import metting.dataBase.tables.Mettings;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLSimpleSearchController implements Initializable {

    public interface OnAction {

        void ok(Mettings m);
    }

    @FXML
    private TextField searchText;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton andicator_number;
    @FXML
    private RadioButton metting_number;
    @FXML
    private RadioButton metting_subject;
    @FXML
    private Button searchNext;
    @FXML
    private Button searchBack;
    @FXML
    private Button first;
    @FXML
    private Button end;
    @FXML
    private Button submit;
    @FXML
    private Label searchResault;

    public Stage thisStage;
    public OnAction onAction;
    private ListProperty<Mettings> searchList = new SimpleListProperty<>();
    private IntegerProperty idSearchProperty = new SimpleIntegerProperty(0);
    private String subQuery;

    /**
     * Initializes the controller class.
     */
    public void init(Stage s) {
        thisStage = s;
        
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
                subQuery = "id = '" + searchText.getText() + "'";
            } else if (toggleGroup.getSelectedToggle() == metting_number) {
                subQuery = "mettingNumber LIKE '%" + searchText.getText() + "%'";
            } else if (toggleGroup.getSelectedToggle() == metting_subject) {
                subQuery = "subject LIKE '%" + searchText.getText() + "%'";
            }
            ObservableList<Mettings> observableList = null;
            if (subQuery.startsWith("id = ")) {
                observableList = FXCollections.observableArrayList(databaseHelper.MettingsDao.rawResults("SELECT * FROM mettings"));
            } else {
                observableList = FXCollections.observableArrayList(databaseHelper.MettingsDao.rawResults("SELECT * FROM mettings WHERE " + subQuery));
            }
            searchList = new SimpleListProperty<>(observableList);
            searchBack.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.lessThan(1)));
            searchNext.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.isEqualTo(searchList.getSize() - 1)));
            if (!searchList.isEmpty()) {
                if (searchList.isEmpty()) {
                    this.onAction.ok(null);
                } else {
                    if (subQuery.startsWith("id = ")) {
                        idSearchProperty.set(Integer.parseInt(searchText.getText()) - 1);
                    } else {
                        idSearchProperty.set(0);
                    }
                    moving();
                }
            } else {
                searchResault.setText("موردی یافت نشد");
            }
        });
        searchList = new SimpleListProperty<>();
        searchBack.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.lessThan(1)));
        searchNext.disableProperty().bind(searchList.sizeProperty().lessThan(2).or(idSearchProperty.isEqualTo(searchList.getSize() - 1)));

        metting_subject.setSelected(true);
        searchText.requestFocus();
        searchText.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    if (!searchNext.isDisable()) {
                        searchNext.getOnAction().handle(new ActionEvent());
                    }
                    break;
                case DOWN:
                    if (!searchBack.isDisable()) {
                        searchBack.getOnAction().handle(new ActionEvent());
                    }
                    break;
            }
        });
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
