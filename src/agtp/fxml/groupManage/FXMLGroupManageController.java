/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.fxml.groupManage;

import agtp.dataBase.tables.UserGroup;
import agtp.dataBase.tables.Users2;
import agtp.myControl.MyButtonFont;
import static dccletter.DCCLetter.databaseHelper;
import dccletter.utils.ErrorCheck;
import dccletter.utils.ParentControl;
import dccletter.utils.UtilsMsg;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
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
public class FXMLGroupManageController extends ParentControl {

    @FXML
    private VBox step_1;
    @FXML
    private VBox step_2;
    @FXML
    private VBox step_4;

    @FXML
    private MyButtonFont add;
    @FXML
    private MyButtonFont remove;
    @FXML
    private MyButtonFont edit;
    @FXML
    private MyButtonFont review;
    @FXML
    private TableView<Users2> group_table;
    @FXML
    private TextField filter_group_table;

    @FXML
    private TextField group_name_fa;
    @FXML
    private TextField group_name_en;
    @FXML
    private TextField summray_name;
    @FXML
    private CheckBox is_discipline;
    @FXML
    private CheckBox is_deactive;

    @FXML
    private TableView<Users2> allUsers_table;
    @FXML
    private TableView<UserGroup> groupUsers_table;
    @FXML
    private Button addUser;
    @FXML
    private Button removeUser;
    @FXML
    private TextField filter_allUsers_table;
    @FXML
    private TextField filter_groupUsers_table;

    @FXML
    private Button back_2;
    @FXML
    private Button submit_2;

    @FXML
    private TextField group_name_fa_Label;
    @FXML
    private TextField group_name_en_Label;
    @FXML
    private TextField summray_name_Label;
    @FXML
    private CheckBox is_discipline_Label;
    @FXML
    private CheckBox is_deactive_Label;
    @FXML
    private TableView<UserGroup> users_table_review;
    @FXML
    private MyButtonFont edit4;
    @FXML
    private Button back_4;

    private Users2 new_group;
    private List<UserGroup> deleteuser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        is_deactive_Label.setStyle(is_deactive_Label.getStyle() + "-fx-opacity: 1;");
        is_discipline_Label.setStyle(is_discipline_Label.getStyle() + "-fx-opacity: 1;");
        step_1.setVisible(true);
        step_2.setVisible(false);
        step_4.setVisible(false);
        step_1_init();
        step_2_init();
        step_3_init();
        step_4_init();
    }

    private void step_1_init() {
        remove.disableProperty().bind(group_table.getSelectionModel().selectedItemProperty().isNull());
        edit.disableProperty().bind(remove.disableProperty());
        review.disableProperty().bind(remove.disableProperty());
        group_table.setRowFactory((TableView<Users2> param) -> {
            TableRow<Users2> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() != 2) {
                    return;
                }
                review.getOnAction().handle(null);
            });
            return row;
        });
        add.init("plus", 15);
        remove.init("trash", 15);
        edit.init("pencil", 15);
        edit4.init("pencil", 15);
        review.init("eye", 15);
        add.setOnAction((ActionEvent event) -> {
            step_1.setVisible(false);
            step_2.setVisible(true);
        });

        remove.setOnAction((ActionEvent event) -> {
            Users2 u2 = group_table.getSelectionModel().getSelectedItem();
            if (!UtilsMsg.showMsg("آیا از حذف گروه " + u2.getName_fa() + " مطمئن هستید؟", "هشدار", true, thisStage)) {
                return;
            }
            u2.setIs_deleted(Boolean.TRUE);
            databaseHelper.users2Dao.createOrUpdate(u2);
            group_table_init();
        });

        edit.setOnAction((ActionEvent event) -> {
                step_1.setVisible(false);
            step_2.setVisible(true);
            new_group = group_table.getSelectionModel().getSelectedItem();
            group_name_fa.setText(new_group.getName_fa());
            group_name_en.setText(new_group.getName_en());
            summray_name.setText(new_group.getSummary());
            is_deactive.setSelected(!new_group.getActive());
            is_discipline.setSelected(new_group.getType().equals(Users2.DISCIPLINE_GROUPE));
            groupUsers_table_init(null, null, databaseHelper.userGroupDao.getAll("group_id", new_group.getId()));
            String query = "";
            for (UserGroup ug : groupUsers_table.getItems()) {
                query = query + " AND id != " + ug.getUser_id().getId();
            }
            query = "SELECT * FROM users2 WHERE type LIKE '%USER%'" + query;
            allUsers_table_init(null, null, databaseHelper.users2Dao.rawResults(query));

        });
        edit4.setOnAction((ActionEvent event) -> {
            step_4.setVisible(false);
            edit.getOnAction().handle(null);
        });

        review.setOnAction((ActionEvent event) -> {
            Users2 u2 = group_table.getSelectionModel().getSelectedItem();
            if (u2 != null) {
                step_4.setVisible(true);
                step_1.setVisible(false);
                group_name_fa_Label.setText(u2.getName_fa());
                group_name_en_Label.setText(u2.getName_en());
                summray_name_Label.setText(u2.getSummary());
                is_deactive_Label.setSelected(!u2.getActive());
                is_discipline_Label.setSelected(u2.getType().equals(Users2.DISCIPLINE_GROUPE));
                users_table_review.getItems().setAll(
                        databaseHelper.userGroupDao.rawResults(
                                "SELECT * FROM userGroup WHERE group_id = " + u2.getId()
                        ));
            }
        });
        group_table_init();
    }

    private void step_2_init() {
        new_group = new Users2();
        deleteuser = new ArrayList<>();

        back_2.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_2.setVisible(false);
            new_group = new Users2();
            group_name_fa.setText("");
            group_name_en.setText("");
            summray_name.setText("");
            filter_allUsers_table.setText("");
            filter_groupUsers_table.setText("");
            is_deactive.setSelected(false);
            is_discipline.setSelected(false);
            allUsers_table_init(null, null, databaseHelper.users2Dao.rawResults(
                    "SELECT * FROM users2 WHERE type LIKE '%USER%'"
            ));
            groupUsers_table_init(null, null, new ArrayList<>());
        });

        submit_2.setOnAction((ActionEvent event) -> {
            ErrorCheck errorCheck = new ErrorCheck("نام گروه", "Group Name");
            if (is_discipline.isSelected() && summray_name.getText().trim().isEmpty()) {
                UtilsMsg.showMsg("فیلد نام مختصر را باید پر کنیید", "خطا", false, thisStage);
                return;
            }
            if (errorCheck.checked(false, "خطا", thisStage, group_name_fa, group_name_en) != -1) {
                return;
            }
            if (!databaseHelper.users2Dao.rawResults("SELECT * FROM users2 WHERE name_fa LIKE '" + group_name_fa.getText() + "' AND type LIKE '%GROUP%'").isEmpty() && new_group.getId() == null) {
                UtilsMsg.showMsg("نام " + group_name_fa.getText() + " تکراری است.", "خطا", false, thisStage);
                return;
            }

            if (!databaseHelper.users2Dao.rawResults("SELECT * FROM users2 WHERE name_en LIKE '" + group_name_en.getText() + "' AND type LIKE '%GROUP%'").isEmpty() && new_group.getId() == null) {
                UtilsMsg.showMsg("نام " + group_name_en.getText() + " تکراری است.", "خطا", false, thisStage);
                return;
            }

            new_group.setName_fa(group_name_fa.getText());
            new_group.setName_en(group_name_en.getText());
            new_group.setSummary(summray_name.getText());
            new_group.setActive(!is_deactive.isSelected());
            new_group.setType(is_discipline.isSelected() ? Users2.DISCIPLINE_GROUPE : Users2.GROUP);
            databaseHelper.users2Dao.createOrUpdate(new_group);
            for (UserGroup ug : groupUsers_table.getItems()) {
                ug.setGroup_id(new_group);
            }

            try {
                databaseHelper.userGroupDao.delete(deleteuser);
                databaseHelper.userGroupDao.insertList(groupUsers_table.getItems());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLGroupManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            back_2.getOnAction().handle(null);
            group_table_init();
        });
    }

    private void step_3_init() {

        addUser.disableProperty().bind(allUsers_table.getSelectionModel().selectedItemProperty().isNull());
        removeUser.disableProperty().bind(groupUsers_table.getSelectionModel().selectedItemProperty().isNull());

        allUsers_table_init(null, null, databaseHelper.users2Dao.rawResults(
                "SELECT * FROM users2 WHERE type LIKE '%USER%'"
        ));

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

        groupUsers_table.setRowFactory((TableView<UserGroup> param) -> {
            TableRow<UserGroup> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() != 2) {
                    return;
                }
                if (groupUsers_table.getSelectionModel().getSelectedIndex() != -1) {
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
            groupUsers_table_init(null, new UserGroup(u2), groupUsers_table.getItems());
            filter_groupUsers_table.setText(strT2);

        });

        removeUser.setOnAction((ActionEvent event) -> {
            UserGroup temp = groupUsers_table.getSelectionModel().getSelectedItem();
            if (temp.getId() != null) {
                deleteuser.add(temp);
            }
            Users2 u2 = groupUsers_table.getSelectionModel().getSelectedItem().getUser_id();
            allUsers_table_init(null, u2, allUsers_table.getItems());
            groupUsers_table_init(temp, null, groupUsers_table.getItems());
        });

    }

    private void step_4_init() {
        users_table_review.setRowFactory((TableView<UserGroup> param) -> {
            TableRow<UserGroup> row = new TableRow<>();
            row.setDisable(true);
            return row;
        });

        back_4.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_4.setVisible(false);
        });
    }

    private void group_table_init() {
        filter_group_table.setText("");
        FilteredList<Users2> filteredListt = new PrepareTable<Users2>().init(group_table, databaseHelper.users2Dao.rawResults(
                "SELECT * FROM users2 WHERE type LIKE '%GROUP%' AND is_deleted = 0"
        ));
        filter_group_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : (s.getName_fa() + s.getName_en()).toLowerCase().contains(newValue.toLowerCase()));
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
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getName_fa().contains(newValue));
        });
    }

    private void groupUsers_table_init(UserGroup remove, UserGroup add, List<UserGroup> ll) {
        List<UserGroup> ttemp = new ArrayList<>(ll);
        if (remove != null) {
            ttemp.remove(remove);
        }
        if (add != null) {
            ttemp.add(add);
        }
        FilteredList<UserGroup> filteredListt = new PrepareTable<UserGroup>().init(groupUsers_table, ttemp);
        filter_groupUsers_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getUser_id().getName_fa().contains(newValue));
        });
    }

    public class PrepareTable<S> {

        public FilteredList<S> init(TableView<S> tb, List<S> list) {
            ObservableList<S> observableList = FXCollections.observableArrayList(list);
            FilteredList<S> filteredlist = new FilteredList<>(observableList, p -> true);
            SortedList<S> sortedData = new SortedList<>(filteredlist);
            sortedData.comparatorProperty().bind(tb.comparatorProperty());
            tb.setItems(sortedData);

            ArrayList<TableColumn<S, ?>> sortOrder = new ArrayList<>(tb.getColumns());
            tb.getSortOrder().clear();
            tb.getSortOrder().addAll(sortOrder);

            return filteredlist;
        }
    }
}
