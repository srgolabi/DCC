package dccletter.fxml.searchPro;

import agtp.dataBase.tables.Permission;
import agtp.dataBase.tables.Users2;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.users;
import agtp.dataBase.tables.Companies;
import dccletter.dataBase.tables.SearchFields;
import dccletter.utils.MyComboBox;
import dccletter.utils.TextFiledLimited;
import dccletter.utils.menu.MenuTableInit;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLSearchProController implements Initializable {

    public Stage thisStage;
    private SimpleIntegerProperty integerProperty = new SimpleIntegerProperty(-1);

    private SimpleStringProperty fieldProperty = new SimpleStringProperty("");
    public String query;

    @FXML
    private ComboBox<SearchFields> searchFieldComboBox;
    @FXML
    private ComboBox<AbsrtactComboBoxValue> operatorComboBox;

    @FXML
    private MyComboBox companiesComboBox;
    @FXML
    private MyComboBox actionsComboBox;
    @FXML
    private TextField valueText;
    @FXML
    private ComboBox<AbsrtactComboBoxValue> booleanComboBox;
    @FXML
    private ComboBox<AbsrtactComboBoxValue> relationComboBox;
    @FXML
    private TableView<Companies> companyMenu;
    @FXML
    private TableView<Users2> actionMenu;

    @FXML
    private TableView<RowTable> tableView;
    @FXML
    private TableColumn<RowTable, Boolean> removeButtunColumn;
    @FXML
    private HBox dateLable;
    @FXML
    private HBox dateValue;

    @FXML
    private Label valueLable;
    @FXML
    private TextField dayValue;
    @FXML
    private TextField mounthValue;
    @FXML
    private TextField yearValue;
    @FXML
    private Button insert;
    @FXML
    private Button edit;
    @FXML
    private Button cancelEdit;
    @FXML
    private Button done;
    @FXML
    private Button remaoveAll;

    /**
     * Initializes the controller class.
     *
     * @param stage
     */
    public void init(Stage stage) {
        this.thisStage = stage;
        thisStage.setOnShowing((WindowEvent event) -> {
            query = null;
        });
        tableView.disableProperty().bind(Bindings.isEmpty(tableView.itemsProperty().get()));
        remaoveAll.disableProperty().bind(tableView.disableProperty());
        done.disableProperty().bind(tableView.disableProperty());
        valueLable.visibleProperty().bind(fieldProperty.isEqualTo("date").not());
        companiesComboBox.visibleProperty().bind(fieldProperty.isEqualTo("companies"));
        actionsComboBox.visibleProperty().bind(fieldProperty.isEqualTo("actions"));
        dateLable.visibleProperty().bind(fieldProperty.isEqualTo("date"));
        dateValue.visibleProperty().bind(dateLable.visibleProperty());
        booleanComboBox.visibleProperty().bind(fieldProperty.isEqualTo("closeORopen").or(fieldProperty.isEqualTo("attachORnot")));
        valueText.visibleProperty().bind(fieldProperty.isEqualTo("string").or(fieldProperty.isEqualTo("integer")));
        tableView.setFixedCellSize(30);
        TextFiledLimited.setEnterFocuse(searchFieldComboBox, operatorComboBox, searchFieldComboBox);
        TextFiledLimited.setEnterFocuse(valueText, relationComboBox, operatorComboBox);
        TextFiledLimited.setEnterFocuse(actionsComboBox.getTextField(), relationComboBox, operatorComboBox);
        TextFiledLimited.setEnterFocuse(companiesComboBox.getTextField(), relationComboBox, operatorComboBox);
        TextFiledLimited.setEnterFocuse(booleanComboBox, relationComboBox, operatorComboBox);
        TextFiledLimited.setEnterFocuse(insert, searchFieldComboBox, relationComboBox);
        TextFiledLimited.setNumberAndLengthLimit(yearValue, 2, mounthValue, operatorComboBox);
        TextFiledLimited.setNumberAndLengthLimit(mounthValue, 2, dayValue, yearValue);
        TextFiledLimited.setNumberAndLengthLimit(dayValue, 2, relationComboBox, mounthValue);
        fieldProperty.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Control tf = null;
            switch (newValue) {
                case "date":
                    tf = yearValue;
                    AbsrtactComboBoxValue.addAll(operatorComboBox,
                            "بزرگتر", " > 'value' ", "بزرگتر مساوی", " >= 'value' ",
                            "کوچکتر", " < 'value' ", "کوچکتر مساوی", " <= 'value' ",
                            "برابر", " = 'value' ", "نابرابر", " != 'value' ");
                    break;
                case "companies":
                    tf = companiesComboBox.getTextField();
                    AbsrtactComboBoxValue.addAll(operatorComboBox, "برابر", " = 'value' ", "نابرابر", " != 'value' ");
                    break;
                case "actions":
                    tf = actionsComboBox.getTextField();
                    AbsrtactComboBoxValue.addAll(operatorComboBox, "برابر", " = 'value' ");
                    break;
                case "attachORnot":
                    tf = booleanComboBox;
                    AbsrtactComboBoxValue.addAll(operatorComboBox, "برابر", " = 'value' ", "نابرابر", " != 'value' ");
                    AbsrtactComboBoxValue.addAll(booleanComboBox, "دارد", "1", "ندارد", "0");
                    break;
                case "closeORopen":
                    tf = booleanComboBox;
                    AbsrtactComboBoxValue.addAll(operatorComboBox, "برابر", " = 'value' ", "نابرابر", " != 'value' ");
                    AbsrtactComboBoxValue.addAll(booleanComboBox, "بسته", "1", "باز", "0");
                    break;
                case "integer":
                    tf = valueText;
                    AbsrtactComboBoxValue.addAll(operatorComboBox,
                            "بزرگتر", " > 'value' ", "بزرگتر مساوی", " >= 'value' ",
                            "کوچکتر", " < 'value' ", "کوچکتر مساوی", " <= 'value' ",
                            "برابر", " = 'value' ", "نابرابر", " != 'value' ");
                    break;
                case "string":
                    tf = valueText;
                    AbsrtactComboBoxValue.addAll(operatorComboBox, "شامل", " LIKE '" + "%" + "value" + "%' ");
                    break;
            }
            TextFiledLimited.setEnterFocuse(relationComboBox, insert, tf);
            TextFiledLimited.setEnterFocuse(operatorComboBox, tf, searchFieldComboBox);
        });
        searchFieldComboBox.valueProperty().addListener((ObservableValue<? extends SearchFields> observable, SearchFields oldValue, SearchFields newValue) -> {
            fieldProperty.setValue(newValue.getType());
        });  

        removeButtunColumn.setCellValueFactory(new PropertyValueFactory<>("removeAction"));
        removeButtunColumn.setCellFactory(new Callback<TableColumn<RowTable, Boolean>, TableCell<RowTable, Boolean>>() {
            @Override
            public TableCell<RowTable, Boolean> call(TableColumn<RowTable, Boolean> param) {
                TableCell<RowTable, Boolean> cell = new TableCell<RowTable, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        if (Objects.equals(item, getItem())) {
                            return;
                        }
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setGraphic(null);
                            return;
                        }
                        final HBox hbox = new HBox(5);
//                        Image image = new Image(CiyamDesktop.class.getResourceAsStream("fxml/search/funnel-icon.png"));
//                        Button button = new Button("حذف",new ImageView(image));
                        Button button = new Button("حذف");
                        final TableCell<RowTable, Boolean> c = this;
                        button.setOnAction((ActionEvent event) -> {
                            tableView.getItems().remove(c.getIndex());
                            if (tableView.getItems().size() == 0) {
                                stage.setHeight(205);
                            } else if (tableView.getItems().size() < 5 && tableView.getItems().size() > 1) {
                                stage.setHeight(stage.getHeight() - 30);
                            }
                        });
                        Button button1 = new Button("اصلاح");
                        button1.setOnAction((ActionEvent event) -> {
                            integerProperty.set(c.getIndex());

//                            rowEdit = c.getIndex();
//                            RowTable item1 = tableView.getItems().get(rowEdit);
                            RowTable item1 = tableView.getItems().get(integerProperty.get());
                            searchFieldComboBox.setValue(item1.getSearchFields());
                            operatorComboBox.setValue(item1.getOperatorInit());
                            relationComboBox.setValue(item1.getRelationColumn());
                            if (companiesComboBox.isVisible()) {
                                companiesComboBox.setText(item1.getValueColumn().txt);
                            }
                            if (actionsComboBox.isVisible()) {
                                actionsComboBox.setText(item1.getValueColumn().txt);
                            }
                            if (dateValue.isVisible()) {
                                String dd = item1.getValueColumn().txt;
                                yearValue.setText(dd.substring(0, 2));
                                mounthValue.setText(dd.substring(3, 5));
                                dayValue.setText(dd.substring(6, 8));
                            }
                            if (valueText.isVisible()) {
                                valueText.setText(item1.getValueColumn().txt);
                            }
                            if (booleanComboBox.isVisible()) {
                                booleanComboBox.setValue(item1.getValueColumn());
                            }
                        });

                        hbox.setAlignment(Pos.CENTER);
                        hbox.getChildren().addAll(button, button1);
                        setGraphic(hbox);
                    }
                };
                return cell;
            }
        });

        tableView.setRowFactory((TableView<RowTable> param) -> {
            TableRow<RowTable> row = new TableRow<>();
            row.disableProperty().bind(integerProperty.isNotEqualTo(-1).and(integerProperty.isNotEqualTo(row.getIndex())));
            return row;
        });

        String tempQuery = "SELECT * FROM searchfields WHERE active = 1 ";
        new ComboBoxCellFactory(searchFieldComboBox);
        searchFieldComboBox.getItems().addAll(databaseHelper.searchFieldsDao.rawResults(tempQuery));
        searchFieldComboBox.getSelectionModel().select(0);

        new ComboBoxCellFactory<AbsrtactComboBoxValue>(operatorComboBox);

        AbsrtactComboBoxValue.init(relationComboBox, "یا", "OR ", "و", "AND ");

        new ComboBoxCellFactory<AbsrtactComboBoxValue>(booleanComboBox);

        fieldProperty.setValue(searchFieldComboBox.getSelectionModel().getSelectedItem().getType());

        tempQuery = "SELECT * FROM companies WHERE active = 1 AND is_deleted = 0 ORDER BY company_fa ASC";
        MenuTableInit.companiesInit(tempQuery, companiesComboBox.getTextField(), companyMenu);

        tempQuery = "SELECT * FROM users2 WHERE active = 1 AND is_deleted = 0 ORDER BY name ASC";
        MenuTableInit.actionsInit(tempQuery, actionsComboBox.getTextField(), actionMenu);

        edit.visibleProperty().bind(integerProperty.isNotEqualTo(-1));
        insert.visibleProperty().bind(edit.visibleProperty().not());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void cancelAction(ActionEvent event) {
//        TextFiledLimited.setEnterFocuse(searchFieldComboBox, name, position, insert);;
        integerProperty.set(-1);
        searchFieldComboBox.getSelectionModel().select(0);
        companiesComboBox.setText("");
        actionsComboBox.setText("");
        yearValue.setText("");
        mounthValue.setText("");
        dayValue.setText("");
        valueText.setText("");
        relationComboBox.getSelectionModel().select(0);
        searchFieldComboBox.requestFocus();
        event.consume();
    }

    @FXML
    private void buttonAfzodanClick() {
        RowTable rowTable = null;
        AbsrtactComboBoxValue vc = null;
        if (valueText.isVisible() && !valueText.getText().equals("")) {
            vc = new AbsrtactComboBoxValue(valueText.getText(), valueText.getText());
        }
        if (companiesComboBox.isVisible() && !companiesComboBox.getText().equals("")) {
            Companies cTemp = databaseHelper.companiesDao.getFirst("company_fa", companiesComboBox.getText());
            if (cTemp != null) {
                vc = new AbsrtactComboBoxValue(companiesComboBox.getText(), cTemp.getId() + "");
            }
        }
        if (actionsComboBox.isVisible() && !actionsComboBox.getText().equals("")) {
            Users2 aTemp = databaseHelper.users2Dao.getFirst("name_fa", actionsComboBox.getText());
            if (aTemp != null) {
                vc = new AbsrtactComboBoxValue(actionsComboBox.getText(), aTemp.getId() + "");
            }
        }
        if (booleanComboBox.isVisible()) {
            vc = booleanComboBox.getValue();
        }
        if (dateValue.isVisible() && notEmpty(dayValue, mounthValue, yearValue)) {
            String strTemp = yearValue.getText() + "/" + mounthValue.getText() + "/" + dayValue.getText();
            vc = new AbsrtactComboBoxValue(strTemp, strTemp);
        }
        if (vc == null) {
            return;
        }
        rowTable = new RowTable(searchFieldComboBox.getValue(), operatorComboBox.getValue(), vc, relationComboBox.getValue());
        if (integerProperty.get() != -1) {
            tableView.getItems().set(integerProperty.get(), rowTable);
        } else {
            tableView.getItems().add(rowTable);
        }
        if (tableView.getItems().size() < 6 && tableView.getItems().size() > 2) {
            thisStage.setHeight((tableView.getItems().size() - 1) * 30 + 175);
        }
        cancelAction(new ActionEvent());
    }

    private boolean notEmpty(TextField... field) {
        for (TextField tf : field) {
            if (tf.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void buttonDoneClick() {

        String part1 = "SELECT letterFull.id , letterH.date letterDate , letterFull.letterNumber , letterFull.subject  , fromC.company_fa 'from' , GROUP_CONCAT(DISTINCT toC.company_fa) 'to' , \n"
                + "originalA.name_fa || ' (' ||originalA.semat || ')' orginal , GROUP_CONCAT(DISTINCT copyA.name_fa || '  (' ||copyA.semat || ')  ') copies , letterFull.annex , letterFull.closed  , receiveH.date receiveDate \n"
                + "FROM letters3 letterFull\n"
                + "LEFT OUTER JOIN history letterH ON letterFull.letter_date_id = letterH.id \n"
                + "LEFT OUTER JOIN history receiveH ON letterFull.receive_date_id = receiveH.id\n"
                + "LEFT OUTER JOIN companies fromC ON letterFull.from_id = fromC.id\n"
                + "LEFT OUTER JOIN users2 originalA ON letterFull.notification_id = originalA.id\n"
                + "LEFT OUTER JOIN letteraction copyLA ON letterFull.id = copyLA.letter_id\n"
                + "LEFT OUTER JOIN users2 copyA ON copyLA.action_id = copyA.id\n"
                + "LEFT OUTER JOIN userGroup ug ON  copyLA.action_id = ug.group_id \n"
                + "LEFT OUTER JOIN userPermission up ON up.user_id = ug.user_id\n"
                + "LEFT OUTER JOIN lettertocompany toLC ON letterFull.id = toLC.letter_id\n"
                + "LEFT OUTER JOIN companies toC ON toLC.company_id = toC.id\n"
                + "WHERE (letterFull.id is sub_query null OR (ug.user_id = " + users.getId()
                + " AND ((ug.group_id = ug.user_id AND up.permission_id = " + Permission.LETTER_VIEW_PERSON
                + " AND up.state = 1) OR (ug.group_id != ug.user_id AND up.permission_id = " + Permission.LETTER_VIEW_GROUP
                + " AND up.state = 1)))) SEARCH_QUERY \n"
                + "GROUP BY letterFull.id ORDER BY letterDate DESC";

        if (!databaseHelper.users2Dao.rawResults(
                "SELECT users2.* FROM users2 \n"
                + "LEFT OUTER JOIN userPermission ON users2.id = userPermission.user_id\n"
                + "WHERE ((userPermission.permission_id = " + Permission.LETTER_VIEW_ALL + " AND userPermission.state = 1 )"
                + " OR users2.admin = 1) AND users2.id = " + users.getId()).isEmpty()) {
            part1 = part1.replace("sub_query", "not");
        } else {
            part1 = part1.replace("sub_query", "");
        }

        String oldRelation = "";
        String part2 = "";
        for (RowTable rt : tableView.getItems()) {
            part2 = part2 + oldRelation + rt.getColumnName() + rt.getOperator().replace("value", rt.getValue());
            oldRelation = rt.getReletion();
        }
        query = part1.replace("SEARCH_QUERY", " AND " + part2);
        thisStage.close();
    }

    @FXML
    private void buttonRemoveAllClick() {
        tableView.getItems().remove(0, tableView.getItems().size());
        thisStage.setHeight(207);
    }
}
