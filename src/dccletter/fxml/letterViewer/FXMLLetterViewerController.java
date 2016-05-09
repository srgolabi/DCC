package dccletter.fxml.letterViewer;

import agtp.dataBase.tables.Permission;
import agtp.dataBase.tables.Users2;
import agtp.myControl.MyButtonFont;
import agtp.myControl.tableView.FileColumnTable;
import agtp.myControl.tableView.PrepareTable;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.users;
import dccletter.dataBase.tables.LetterFile;
import dccletter.dataBase.tables.LetterAction;
import dccletter.dataBase.tables.LetterReffer;
import dccletter.dataBase.tables.LetterToCompany;
import dccletter.dataBase.tables.Letters3;
import dccletter.fxml.simpleSearch.FXMLSimpleSearchController;
import dccletter.utils.ParentControl;
import dccletter.utils.UtilsMsg;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLLetterViewerController extends ParentControl {

    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;

    @FXML
    private TextField letter_Number;
    @FXML
    private Label letter_Date;
    @FXML
    private TextField subject;
    @FXML
    private TextField from;
    @FXML
    private TextField notification;
    @FXML
    private Label recieve_Date;
    @FXML
    private CheckBox letter_State;
    @FXML
    private Label andikator;
    @FXML
    private CheckBox attach;
    @FXML
    private Label user_name;

    @FXML
    private MyButtonFont edit;
    @FXML
    private MyButtonFont send_to_action;
    @FXML
    private MyButtonFont search_view;

    @FXML
    private TableView<LetterToCompany> toSlected;
    @FXML
    private TableView<LetterAction> actionSelected;
    @FXML
    private TableView<LetterReffer> refferSelected;
    @FXML
    private TableView<LetterReffer> answerSelected;
    @FXML
    private TableView<LetterFile> fileSelected;
    @FXML
    private TableColumn<LetterFile, LetterFile> fileColumnButton;

    @FXML
    private TableView<Users2> allUsers_table;
    @FXML
    private TableView<LetterAction> letterAction_table;
    @FXML
    private Button addUser;
    @FXML
    private Button removeUser;
    @FXML
    private TextField filter_allUsers_table;
    @FXML
    private TextField filter_groupUsers_table;
    @FXML
    private MyButtonFont erja_ok;
    @FXML
    private MyButtonFont erja_cancel;

    @FXML
    private VBox searchPane;
    @FXML
    private VBox mainPane;

    private List<LetterAction> deleteuser;

    public int editLetter = -1;
    public Letters3 letter_Loaded = null;

    public FXMLSimpleSearchController simpleSearchController;

    private void setRowFactory(TableView<LetterReffer> tv) {
        tv.setRowFactory((TableView<LetterReffer> param) -> {
            TableRow<LetterReffer> row = new TableRow<>();
            row.setCursor(Cursor.HAND);
            row.setOnMouseClicked((MouseEvent event) -> {
                if (row.getItem() != null && event.getClickCount() >= 2) {
                    simpleSearchController.onAction.ok(Objects.equals(tv, answerSelected) ? row.getItem().getLetterId() : row.getItem().getRefferId());
                }
            });
            return row;
        });
    }

    private void clearPage() {
        toSlected.getItems().clear();
        actionSelected.getItems().clear();
        refferSelected.getItems().clear();
        answerSelected.getItems().clear();
        fileSelected.getItems().clear();
        letter_Number.setText("");
        letter_Date.setText("----");
        subject.setText("");
        from.setText("");
        notification.setText("");
        recieve_Date.setText("----");
        letter_State.setSelected(false);
        attach.setSelected(false);
        andikator.setText("----");
        letter_Loaded = null;
    }

    public void loadLetterB(Letters3 l) {
        letter_Loaded = l;
        deleteuser = new ArrayList<>();
        if (l != null) {
            try {
                letter_Number.setText(l.getLetterNumber());
                letter_Date.setText(l.getLetter_date().getDate());
                subject.setText(l.getSubject());
                from.setText(l.getFrom().getCompany_fa());
                if (l.getNotification() != null) {
                    notification.setText(l.getNotification().getNameBaSemat());
                }
                recieve_Date.setText(l.getReceive_date().getDate());
                letter_State.setSelected(!l.getClosed());
                andikator.setText(l.getId() + "");
                attach.setSelected(l.getAnnex());

                toSlected.getItems().setAll(databaseHelper.letterToCompanyDao.rawResults(
                        "SELECT * FROM lettertocompany WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
                ));
                actionSelected.getItems().setAll(databaseHelper.letterActionDao.rawResults(
                        "SELECT * FROM letteraction WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
                ));
                refferSelected.getItems().setAll(databaseHelper.letterRefferDao.rawResults(
                        "SELECT * FROM letterreffer WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
                ));
                answerSelected.getItems().setAll(databaseHelper.letterRefferDao.rawResults(
                        "SELECT * FROM letterreffer WHERE reffer_id = " + l.getId() + " ORDER BY sortOrder ASC"
                ));
                fileSelected.getItems().setAll(databaseHelper.letterFileDao.rawResults(
                        "SELECT * FROM letterFile WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
                ));

                groupUsers_table_init(null, null, actionSelected.getItems());
                String query = "";
                for (LetterAction ug : letterAction_table.getItems()) {
                    query = query + " AND id != " + ug.getActionId().getId();
                }
                query = "SELECT * FROM users2 WHERE (type LIKE '%ACTION%' OR type LIKE '%GROUP%')" + query;
                allUsers_table_init(null, null, databaseHelper.users2Dao.rawResults(query));
            } catch (Exception e) {
            }
        }
    }

    private void step_2_init() {
        erja_ok.init("check", 16, "table-button");
        erja_cancel.init("cancel_1", 16, "table-button");

        erja_ok.setOnAction((ActionEvent event) -> {
            try {
                databaseHelper.letterActionDao.delete(deleteuser);
                databaseHelper.letterActionDao.insertList(letterAction_table.getItems());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLLetterViewerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            erja_cancel.getOnAction().handle(event);
        });

        erja_cancel.setOnAction((ActionEvent event) -> {
            vBox1.setVisible(false);
            loadLetterB(letter_Loaded);
        });

        addUser.disableProperty().bind(allUsers_table.getSelectionModel().selectedItemProperty().isNull());
        removeUser.disableProperty().bind(letterAction_table.getSelectionModel().selectedItemProperty().isNull());

        allUsers_table.setRowFactory((TableView<Users2> param) -> {
            TableRow<Users2> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() != 2) {
                    return;
                }
                if (allUsers_table.getSelectionModel().getSelectedIndex() != -1) {
                    addUser.getOnAction().handle(null);
                }
            });
            return row;
        });

        letterAction_table.setRowFactory((TableView<LetterAction> param) -> {
            TableRow<LetterAction> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() != 2) {
                    return;
                }
                if (letterAction_table.getSelectionModel().getSelectedIndex() != -1) {
                    removeUser.getOnAction().handle(null);
                }
            });
            return row;
        });

        addUser.setOnAction((ActionEvent event) -> {
            Users2 u2 = allUsers_table.getSelectionModel().getSelectedItem();

            String strT1 = filter_allUsers_table.getText();
            filter_allUsers_table.setText("");
            allUsers_table_init(u2, null, allUsers_table.getItems());
            filter_allUsers_table.setText(strT1);

            String strT2 = filter_groupUsers_table.getText();
            filter_groupUsers_table.setText("");
            groupUsers_table_init(null, new LetterAction(letter_Loaded, u2), letterAction_table.getItems());
            filter_groupUsers_table.setText(strT2);

        });

        removeUser.setOnAction((ActionEvent event) -> {

            LetterAction temp = letterAction_table.getSelectionModel().getSelectedItem();
            if (temp.getId() != null) {
                deleteuser.add(temp);
            }
            Users2 u2 = letterAction_table.getSelectionModel().getSelectedItem().getActionId();
            allUsers_table_init(null, u2, allUsers_table.getItems());
            groupUsers_table_init(temp, null, letterAction_table.getItems());
        });

    }

    private void allUsers_table_init(Users2 remove, Users2 add, List<Users2> ll) {
        List<Users2> ttemp = new ArrayList<>(ll);
        if (remove != null) {
            ttemp.remove(remove);
        }
        if (add != null) {
            ttemp.add(add);
        }
        FilteredList<Users2> filteredListt = new PrepareTable<Users2>().init(allUsers_table, ttemp);
        filter_allUsers_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : (s.getName_fa() + s.getName_en()).toLowerCase().contains(newValue.toLowerCase()));
        });
    }

    private void groupUsers_table_init(LetterAction remove, LetterAction add, List<LetterAction> ll) {
        List<LetterAction> ttemp = new ArrayList<>(ll);
        if (remove != null) {
            ttemp.remove(remove);
        }
        if (add != null) {
            ttemp.add(add);
        }
        FilteredList<LetterAction> filteredListt = new PrepareTable<LetterAction>().init(letterAction_table, ttemp);
        filter_groupUsers_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : (s.getActionId().getName_fa() + s.getActionId().getName_en()).toLowerCase().contains(newValue.toLowerCase()));
        });
    }

    @Override
    public void setStage(Stage s) {
        super.setStage(s);
        thisStage.getScene().setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case ESCAPE:
                    searchPane.setVisible(false);
                    break;
                case F2:
                    if (!edit.isDisable()) {
                        edit.getOnAction().handle(null);
                    }
                    break;
                case F3:
                    if (!send_to_action.isDisable()) {
                        send_to_action.getOnAction().handle(null);
                    }
                    break;
                case PAGE_DOWN:
                    if (!simpleSearchController.searchBack.isDisable()) {
                        simpleSearchController.searchBack.getOnAction().handle(null);
                    }
                    break;
                case PAGE_UP:
                    if (!simpleSearchController.searchNext.isDisable()) {
                        simpleSearchController.searchNext.getOnAction().handle(null);
                    }
                    break;
                case END:
                    if (!simpleSearchController.end.isDisable()) {
                        simpleSearchController.end.getOnAction().handle(null);
                    }
                    break;
                case HOME:
                    if (!simpleSearchController.first.isDisable()) {
                        simpleSearchController.first.getOnAction().handle(null);
                    }
                    break;
            }
        });
        thisStage.setOnShowing((WindowEvent event) -> {
            user_name.setText(users.getName_fa());
        });

        UtilsMsg utilsStage = new UtilsMsg("simpleSearch/FXMLSimpleSearch.fxml", "", Modality.WINDOW_MODAL, thisStage.getOwner());
        simpleSearchController = utilsStage.getLoader().getController();
        simpleSearchController.init(utilsStage.getStage(), searchPane);
        searchPane.getChildren().add(utilsStage.page);
        simpleSearchController.setOnAction((Letters3 l) -> {
            clearPage();
            loadLetterB(l);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        edit.init("edit", 16);
        send_to_action.init("erja", 16);
        search_view.init("search", 16, "table-button");

        step_2_init();
        vBox2.visibleProperty().bind(vBox1.visibleProperty());
        vBox1.setVisible(false);
        edit.setDisable(!Permission.isAcces(Permission.LETTER_INSERT));
        send_to_action.setDisable(!Permission.isAcces(Permission.LETTER_SEND));

        if (!edit.isDisable()) {
            edit.disableProperty().bind(Bindings.equal(andikator.textProperty(), "----"));
        }
        if (!send_to_action.isDisable()) {
            send_to_action.disableProperty().bind(edit.disableProperty());
        }

        edit.setOnAction((ActionEvent event) -> {
            editLetter = Integer.parseInt(andikator.getText());
            thisStage.close();
        });

        send_to_action.setOnAction((ActionEvent event) -> {
            vBox1.setVisible(true);
        });

        toSlected.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            event.consume();
        });
        actionSelected.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            event.consume();
        });

        setRowFactory(refferSelected);
        setRowFactory(answerSelected);

        FileColumnTable removeButtonCellFactory = new FileColumnTable(false);
        fileColumnButton.setCellFactory(removeButtonCellFactory);

    }

    @FXML
    public void searcMenuAction() {
        searchPane.setVisible(true);
        simpleSearchController.searchText.requestFocus();
    }

    @FXML
    private void close() {
        thisStage.close();
    }
}
