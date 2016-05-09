/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.fxml.userManage;

import agtp.myControl.CheckBoxTreeCellMy;
import agtp.dataBase.tables.Permission;
import agtp.dataBase.tables.UserGroup;
import agtp.dataBase.tables.UserPermission;
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
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
 
/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLUserManageController extends ParentControl {

    @FXML
    private VBox step_1;
    @FXML
    private VBox step_2;
    @FXML
    private VBox step_3;
    @FXML
    private VBox step_4;
    @FXML
    private VBox step_5;

    @FXML
    private MyButtonFont add;
    @FXML
    private MyButtonFont change_pass;
    @FXML
    private MyButtonFont edit;
    @FXML
    private MyButtonFont review;
    @FXML
    private TableView<Users2> user_table;
    @FXML
    private TextField filter_user_table;

    @FXML
    private TextField user_name_fa;
    @FXML
    private TextField user_name_en;
    @FXML
    private TextField user_name;
    @FXML
    private TextField position;
    @FXML
    private TextField email;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField again_pass;
    @FXML
    private CheckBox is_deactive;
    @FXML
    private CheckBox is_action;
    @FXML
    private TreeView<Permission> permission_table;
    @FXML
    private Button show_user_group;
    @FXML
    private Button back_2;
    @FXML
    private Button submit_2;

    @FXML
    private TableView<Users2> allGroup_table;
    @FXML
    private TableView<UserGroup> groupUsers_table;
    @FXML
    private Button addGroup;
    @FXML
    private Button removeGroup;
    @FXML
    private TextField filter_allGroup_table;
    @FXML
    private TextField filter_groupUsers_table;
    @FXML
    private Button back_3;
    @FXML
    private Button submit_3;

    @FXML
    private PasswordField pass_4;
    @FXML
    private PasswordField again_pass_4;
    @FXML
    private Button back_4;
    @FXML
    private Button submit_4;

    @FXML
    private Label user_name_fa_Label;
    @FXML
    private Label user_name_en_Label;
    @FXML
    private Label user_name_Label;
    @FXML
    private Label position_Label;
    @FXML
    private Label email_Label;
    @FXML
    private Label is_deactive_Label;
    @FXML
    private Label is_action_Label;
    @FXML
    private TableView<UserGroup> groups_table_review;
    @FXML
    private TreeView<Permission> permission_table_review;
    @FXML
    private Button back_5;

    private Users2 new_user;
    private List<UserGroup> userGroup;
    private List<UserGroup> deleteGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        step_1_init();
        step_2_init();
        step_3_init();
        step_4_init();
        step_5_init();
    }

    private void step_1_init() {
        change_pass.disableProperty().bind(user_table.getSelectionModel().selectedItemProperty().isNull());
        edit.disableProperty().bind(change_pass.disableProperty());
        review.disableProperty().bind(change_pass.disableProperty());

        user_table.setRowFactory((TableView<Users2> param) -> {
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
        change_pass.init("lock_open_alt", 15);
        edit.init("pencil", 15);
        review.init("eye", 15);

        add.setOnAction((ActionEvent event) -> {
            step_1.setVisible(false);
            step_2.setVisible(true);
        });

        change_pass.setOnAction((ActionEvent event) -> {
            step_1.setVisible(false);
            step_4.setVisible(true);
        });

        edit.setOnAction((ActionEvent event) -> {
            step_1.setVisible(false);
            step_2.setVisible(true);
            new_user = user_table.getSelectionModel().getSelectedItem();
            user_name_fa.setText(new_user.getName_fa());
            user_name_en.setText(new_user.getName_en());
            user_name.setText(new_user.getUsername());
            position.setText(new_user.getSemat());
            email.setText(new_user.getEmail());
            pass.setText(new_user.getPassword());
            again_pass.setText(new_user.getPassword());
            is_deactive.setSelected(!new_user.getActive());
            is_action.setSelected(new_user.getType().equals(Users2.USERACTION));
            groupUsers_table_init(null, null, databaseHelper.userGroupDao.rawResults("SELECT * FROM userGroup WHERE user_id != group_id AND user_id = " + new_user.getId()));
            String query = "";

            for (UserGroup ug : groupUsers_table.getItems()) {
                query = query + " AND id != " + ug.getGroup_id().getId();
            }
            query = "SELECT * FROM users2 WHERE type LIKE '%GROUP%' AND is_deleted = 0" + query;
            allGroup_table_init(null, null, databaseHelper.users2Dao.rawResults(query));

            query = "SELECT permission.id , permission.name , permission.title , permission.icon , permission.iconColor , userPermission.state defaultValue , permission.parent , permission.sort_index\n"
                    + "FROM  permission\n"
                    + "LEFT JOIN userPermission\n"
                    + "ON userPermission.user_id = '" + new_user.getId() + "' AND permission.id = userPermission.permission_id";
            permission_table_init(permission_table, query);

        });

        review.setOnAction((ActionEvent event) -> {
            Users2 u2 = user_table.getSelectionModel().getSelectedItem();
            if (u2 != null) {
                step_5.setVisible(true);
                step_1.setVisible(false);
                user_name_fa_Label.setText(u2.getName_fa());
                user_name_en_Label.setText(u2.getName_en());
                user_name_Label.setText(u2.getUsername());
                position_Label.setText(u2.getSemat());
                email_Label.setText(u2.getEmail());
                is_action_Label.setText(u2.getType().equals(Users2.USERACTION) ? "اقدامگر" : "عادی");
                is_deactive_Label.setText(u2.getActive() ? "فعال" : "غیر فعال");

                groups_table_review.getItems().setAll(
                        databaseHelper.userGroupDao.rawResults(
                                "SELECT * FROM userGroup WHERE user_id != group_id AND user_id = " + u2.getId()
                        ));

                String query = "SELECT permission.id , permission.name , permission.title , permission.icon , permission.iconColor , userPermission.state defaultValue , permission.parent , permission.sort_index\n"
                        + "FROM  permission\n"
                        + "LEFT JOIN userPermission\n"
                        + "ON userPermission.user_id = '" + u2.getId() + "' AND permission.id = userPermission.permission_id";
                permission_table_init(permission_table_review, query);
            }
        });
        user_table_init();
    }

    private void step_2_init() {
        new_user = new Users2();
        userGroup = new ArrayList<>();
        deleteGroup = new ArrayList<>();

        permission_table.setCellFactory((TreeView<Permission> param) -> {
            CheckBoxTreeCellMy cc = new CheckBoxTreeCellMy();
            return cc;
        });

        show_user_group.setOnAction((ActionEvent event) -> {
            step_2.setVisible(false);
            step_3.setVisible(true);
        });

        permission_table_init(permission_table, "SELECT * FROM permission ");

        back_2.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_2.setVisible(false);
            new_user = new Users2();
            userGroup = new ArrayList<>();
            user_name_fa.setText("");
            user_name_en.setText("");
            user_name.setText("");
            position.setText("");
            email.setText("");
            pass.setText("");
            again_pass.setText("");
            filter_allGroup_table.setText("");
            filter_groupUsers_table.setText("");
            is_deactive.setSelected(false);
            is_action.setSelected(false);

            allGroup_table_init(null, null, databaseHelper.users2Dao.rawResults(
                    "SELECT * FROM users2 WHERE type LIKE '%GROUP%' AND is_deleted = 0"
            ));
            groupUsers_table_init(null, null, new ArrayList<>());
            permission_table_init(permission_table, "SELECT * FROM permission ");
        });

        submit_2.setOnAction((ActionEvent event) -> {

            ErrorCheck errorCheck = new ErrorCheck("نام", "نام کاربری", "رمز عبور", "تکرار رمز عبور");
            if (!pass.getText().equals(again_pass.getText())) {
                UtilsMsg.showMsg("رمزعبور با تکرار آن مطابق نیست.", "خطا", false, thisStage);
                return;
            }
            if (errorCheck.checked(false, "خطا", thisStage, user_name_fa, user_name, pass, again_pass) != -1) {
                return;
            }
            if (databaseHelper.users2Dao.getFirst("username", user_name.getText()) != null && new_user.getId() == null) {
                UtilsMsg.showMsg("این نام کاربری قبلا ثبت شده است.", "خطا", false, thisStage);
                return;
            }

            new_user.setName_fa(user_name_fa.getText());
            new_user.setName_en(user_name_en.getText());
            new_user.setUsername(user_name.getText());
            new_user.setSemat(position.getText());
            new_user.setEmail(email.getText());
            new_user.setPassword(pass.getText());
            new_user.setActive(!is_deactive.isSelected());
            new_user.setType(is_action.isSelected() ? Users2.USERACTION : Users2.ACTION);
            boolean b = new_user.getId() == null;
            databaseHelper.users2Dao.createOrUpdate(new_user);
            List<UserPermission> permissions = new ArrayList<>();
            for (UserGroup ug : userGroup) {
                ug.setUser_id(new_user);
            }
            if (b) {
                for (TreeItem<Permission> item : permission_table.getRoot().getChildren()) {
                    permissions.add(new UserPermission(new_user, item.getValue(), ((CheckBoxTreeItem) item).isSelected()));
                    if (!item.getChildren().isEmpty()) {
                        for (TreeItem<Permission> child : item.getChildren()) {
                            permissions.add(new UserPermission(new_user, child.getValue(), ((CheckBoxTreeItem) child).isSelected()));
                        }
                    }
                }
            } else {
                for (TreeItem<Permission> item : permission_table.getRoot().getChildren()) {
                    List<UserPermission> search = databaseHelper.userPermissionDao.rawResults(
                            "SELECT * FROM userPermission WHERE user_id = " + new_user.getId() + " AND permission_id = " + item.getValue().getId()
                    );
                    UserPermission up2 = search.isEmpty()
                            ? new UserPermission(new_user, item.getValue(), ((CheckBoxTreeItem) item).isSelected())
                            : search.get(0);
                    up2.setState(((CheckBoxTreeItem) item).isSelected());
                    permissions.add(up2);
                    if (!item.getChildren().isEmpty()) {
                        for (TreeItem<Permission> child : item.getChildren()) {
                            search = databaseHelper.userPermissionDao.rawResults(
                                    "SELECT * FROM userPermission WHERE user_id = " + new_user.getId() + " AND permission_id = " + child.getValue().getId()
                            );
                            up2 = search.isEmpty()
                                    ? new UserPermission(new_user, child.getValue(), ((CheckBoxTreeItem) child).isSelected())
                                    : search.get(0);
                            up2.setState(((CheckBoxTreeItem) child).isSelected());
                            permissions.add(up2);
                        }
                    }
                }
            }

            try {
                databaseHelper.userPermissionDao.insertList(permissions);
                databaseHelper.userGroupDao.delete(deleteGroup);
                if (b) {
                    databaseHelper.userGroupDao.createOrUpdate(new UserGroup(new_user, new_user));
                }
                databaseHelper.userGroupDao.insertList(userGroup);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLUserManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            back_2.getOnAction().handle(null);
            user_table_init();
        });
    }

    private void step_3_init() {

        addGroup.disableProperty().bind(allGroup_table.getSelectionModel().selectedItemProperty().isNull());
        removeGroup.disableProperty().bind(groupUsers_table.getSelectionModel().selectedItemProperty().isNull());

        allGroup_table_init(null, null, databaseHelper.users2Dao.rawResults(
                "SELECT * FROM users2 WHERE type LIKE '%GROUP%' AND is_deleted = 0"
        ));

        allGroup_table.setRowFactory((TableView<Users2> param) -> {
            TableRow<Users2> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() != 2) {
                    return;
                }
                if (allGroup_table.getSelectionModel().getSelectedIndex() != -1) {
                    addGroup.getOnAction().handle(null);
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
                    removeGroup.getOnAction().handle(null);
                }
            });
            return row;
        });

        addGroup.setOnAction((ActionEvent event) -> {
            Users2 u2 = allGroup_table.getSelectionModel().getSelectedItem();

            String strT1 = filter_allGroup_table.getText();
            filter_allGroup_table.setText("");
            allGroup_table_init(u2, null, allGroup_table.getItems());
            filter_allGroup_table.setText(strT1);

            String strT2 = filter_groupUsers_table.getText();
            filter_groupUsers_table.setText("");
            groupUsers_table_init(null, new UserGroup(null, u2), groupUsers_table.getItems());
            filter_groupUsers_table.setText(strT2);
        });

        removeGroup.setOnAction((ActionEvent event) -> {
            UserGroup temp = groupUsers_table.getSelectionModel().getSelectedItem();
            if (temp.getId() != null) {
                deleteGroup.add(temp);
            }

            String strT1 = filter_allGroup_table.getText();
            filter_allGroup_table.setText("");
            System.out.println("aa = " + temp.getGroup_id().getName_fa());
            allGroup_table_init(null, temp.getGroup_id(), allGroup_table.getItems());
            filter_allGroup_table.setText(strT1);

            String strT2 = filter_groupUsers_table.getText();
            filter_groupUsers_table.setText("");
            groupUsers_table_init(temp, null, groupUsers_table.getItems());
            filter_groupUsers_table.setText(strT2);
        });

        back_3.setOnAction((ActionEvent event) -> {
            step_2.setVisible(true);
            step_3.setVisible(false);
            groupUsers_table_init(null, null, userGroup);

            List<Users2> listTemp = databaseHelper.users2Dao.rawResults(
                    "SELECT * FROM users2 WHERE type LIKE '%GROUP%' AND is_deleted = 0"
            );

            boolean b = false;
            for (UserGroup ug : userGroup) {
                b = false;
                for (Users2 u2 : listTemp) {
                    if (ug.getGroup_id().getId() == u2.getId()) {
                        b = true;
                        break;
                    }
                    if (b) {
                        listTemp.remove(u2);
                    }
                }
            }
            allGroup_table_init(null, null, listTemp);
        });

        submit_3.setOnAction((ActionEvent event) -> {
            step_2.setVisible(true);
            step_3.setVisible(false);
            userGroup = new ArrayList<>(groupUsers_table.getItems());
        });

    }

    private void step_4_init() {
        submit_4.setOnAction((ActionEvent event) -> {
            ErrorCheck errorCheck = new ErrorCheck("رمز عبور", "تکرار رمز عبور");
            if (!pass_4.getText().equals(again_pass_4.getText())) {
                UtilsMsg.showMsg("رمزعبور با تکرار آن مطابق نیست.", "خطا", false, thisStage);
                return;
            }
            if (errorCheck.checked(false, "خطا", thisStage, pass_4, again_pass_4) != -1) {
                return;
            }
            Users2 u2 = user_table.getSelectionModel().getSelectedItem();
            u2.setPassword(pass_4.getText());
            databaseHelper.users2Dao.createOrUpdate(u2);
            user_table_init();
            back_4.getOnAction().handle(event);
        });

        back_4.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_4.setVisible(false);
            pass_4.setText("");
            again_pass_4.setText("");
        });
    }

    private void step_5_init() {
        groups_table_review.setRowFactory((TableView<UserGroup> param) -> {
            TableRow<UserGroup> row = new TableRow<>();
            row.setDisable(true);
            return row;
        });
        permission_table_review.setCellFactory((TreeView<Permission> param) -> {
            CheckBoxTreeCellMy cc = new CheckBoxTreeCellMy(true);
            return cc;
        });
        permission_table_review.setEditable(false);
        back_5.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_5.setVisible(false);
        });
    }

    private void permission_table_init(TreeView tv, String query) {
        CheckBoxTreeItem<Permission> root = new CheckBoxTreeItem<>(new Permission());
        List<Permission> permissionParent = databaseHelper.permissionDao.rawResults(
                query + " WHERE parent IS NULL ORDER BY sort_index ASC"
        );
        for (Permission cM : permissionParent) {
            CheckBoxTreeItem<Permission> parent = new CheckBoxTreeItem<>(cM);
            List<Permission> permissionChild = databaseHelper.permissionDao.rawResults(query + " WHERE parent = " + cM.getId() + " ORDER BY sort_index ASC");
            int i = 0;
            parent.setIndependent(true);
            for (Permission cMChild : permissionChild) {
                i = i + (cMChild.getDefaultValue() ? 1 : 0);
                CheckBoxTreeItem<Permission> child = new CheckBoxTreeItem<>(cMChild);
                child.setSelected(cMChild.getDefaultValue());
                parent.getChildren().add(child);
            }
            if (i == permissionChild.size()) {
                parent.setSelected(true);
            } else if (i > 0) {
                parent.setIndeterminate(true);
            }
            parent.setIndependent(false);
            root.getChildren().add(parent);
        }
        tv.setRoot(root);
        tv.refresh();
    }

    private void user_table_init() {
        filter_user_table.setText("");
        FilteredList<Users2> filteredListt = new PrepareTable<Users2>().init(user_table, databaseHelper.users2Dao.rawResults(
                "SELECT * FROM users2 WHERE type LIKE '%USER%' AND is_deleted = 0"
        ));
        filter_user_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : (s.getName_fa() + s.getName_en() + s.getUsername()).toLowerCase().contains(newValue.toLowerCase()));
        });
    }

    private void allGroup_table_init(Users2 remove, Users2 add, List<Users2> ll) {
        List<Users2> ttemp = new ArrayList<>(ll);
        if (remove != null) {
            ttemp.remove(remove);
        }
        if (add != null) {
            ttemp.add(add);
        }
        FilteredList<Users2> filteredListt = new PrepareTable<Users2>().init(allGroup_table, ttemp);
        filter_allGroup_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
