package dccletter.fxml.letterInsert;

import agtp.dataBase.tables.Permission;
import agtp.myControl.tableView.MyColumnTable;
import agtp.dataBase.tables.Users2;
import agtp.myControl.MyButtonFont;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.server;
import static dccletter.DCCLetter.users;
import agtp.dataBase.tables.Companies;
import dccletter.dataBase.tables.LetterFile;
import dccletter.dataBase.tables.LetterAction;
import dccletter.dataBase.tables.LetterReffer;
import dccletter.dataBase.tables.LetterToCompany;
import dccletter.fxml.simpleSearch.FXMLSimpleSearchController;
import dccletter.utils.ErrorCheck;
import dccletter.utils.MyTime;
import dccletter.utils.ParentControl;
import dccletter.utils.TextFiledLimited;
import dccletter.utils.UtilsMsg;
import dccletter.utils.menu.MenuTableInit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;
import agtp.myControl.tableView.FileColumnTable;
import dccletter.dataBase.tables.Letters3;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLLetterInsertController extends ParentControl {

    Node currentFucos;

    private MyTime letter_date;
    private MyTime receive_date;

    @FXML
    private TextField letter_date_yaer;
    @FXML
    private TextField letter_date_month;
    @FXML
    private TextField letter_date_day;

    @FXML
    private TextField letter_receives_yaer;
    @FXML
    private TextField letter_receive_month;
    @FXML
    private TextField letter_receive_day;

    @FXML
    private TextField number_letter;
    @FXML
    private TextField subject;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private TextField notification;
    @FXML
    private TextField actions;
    @FXML
    private TextField reffers;
    @FXML
    private MyButtonFont addReff;
    @FXML
    private CheckBox have_attach;
    @FXML
    private Label andicator;
    @FXML
    private MyButtonFont add_file;
    @FXML
    private TableView<Companies> fromMenu;
    @FXML
    private TableView<LetterToCompany> toMenu;
    @FXML
    private TableView<LetterToCompany> toSlected;
    @FXML
    private TableColumn<LetterToCompany, LetterToCompany> toColumnButton;
    @FXML
    private TableView<Users2> notificationMenu;
    @FXML
    private TableView<LetterAction> actionMenu;
    @FXML
    private TableView<LetterAction> actionSelected;
    @FXML
    private TableColumn<LetterAction, LetterAction> actionColumnButton;
    @FXML
    private TableView<LetterReffer> refferSelected;
    @FXML
    private TableColumn<LetterReffer, LetterReffer> reffColumnButton;
    @FXML
    private TableView<LetterFile> fileSelected;
    @FXML
    private TableColumn<LetterFile, LetterFile> fileColumnButton;
    @FXML
    private CheckBox askNeed;
    @FXML
    private MyButtonFont submit;
    @FXML
    private MyButtonFont clear;
    @FXML
    private MyButtonFont search_view;
    @FXML
    private Label userName;
    @FXML
    private VBox searchPane;
    @FXML
    private VBox mainPane;

    private Letters3 NEW_LETTERS;
    private boolean EDIT_MODE = false;

    private List<LetterToCompany> deleteToCompanies;
    private List<LetterAction> deleteActions;
    private List<LetterReffer> deleteReffers;
    private List<LetterFile> deleteFiles;
    private ListProperty<Letters3> searchList = new SimpleListProperty<>();
    private IntegerProperty idSearchProperty = new SimpleIntegerProperty(0);

    private FXMLSimpleSearchController simpleSearchController;

    /**
     * Initializes the controller class.
     *
     */
    private void toMenuInit() {

        mainPane.visibleProperty().bind(searchPane.visibleProperty().not());
        deleteToCompanies = new ArrayList<>();
        String query = "SELECT lettertocompany.id = null id , lettertocompany.letter_id = null letter_id  ,companies.id company_id\n"
                + "FROM  lettertocompany\n"
                + "left JOIN companies\n"
                + "WHERE companies.active = 1 AND companies.is_deleted = 0\n"
                + "group by companies.id order by companies.company_fa ASC";
        MyColumnTable<LetterToCompany> removeButtonCellFactory = new MyColumnTable<>(toSlected, Cursor.DEFAULT);
        removeButtonCellFactory.setOnDelete((LetterToCompany s) -> {
            if (s.getId() != null) {
                deleteToCompanies.add(s);
            }
        });
        removeButtonCellFactory.setOnAddToMenu((LetterToCompany s) -> {
            for (LetterToCompany c : toSlected.getItems()) {
                if (c.getCompanyId().equals(s.getCompanyId())) {
                    return;
                }
            }
            toSlected.getItems().add(new LetterToCompany(NEW_LETTERS, s.getCompanyId()));
        });
        FilteredList<LetterToCompany> filteredlist = removeButtonCellFactory.init(
                toMenu, to, databaseHelper.letterToCompanyDao.rawResults(query));

        toColumnButton.setCellFactory(removeButtonCellFactory);
        to.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getCompany_fa().toLowerCase().contains(newValue.toLowerCase()));
            toMenu.setPrefHeight(
                    toMenu.getFixedCellSize() * (toMenu.getItems().size() > 5 ? 5 : toMenu.getItems().size()) + 4
            );
        });
    }

    private void actionsMenuInit() {
        deleteActions = new ArrayList<>();
        String query = "SELECT letteraction.id = null id , letteraction.letter_id = null letter_id ,users2.id action_id "
                + "FROM  letteraction "
                + "LEFT JOIN users2 "
                + "WHERE users2.active = 1 AND users2.is_deleted = 0 AND ( users2.type LIKE '%ACTION%' OR users2.type LIKE '%GROUP%' ) "
                + "GROUP BY users2.id "
                + "ORDER BY users2.name_fa ASC";
        MyColumnTable<LetterAction> removeButtonCellFactory = new MyColumnTable<>(actionSelected, Cursor.DEFAULT);
        removeButtonCellFactory.setOnDelete((LetterAction s) -> {
            if (s.getId() != null) {
                deleteActions.add(s);
            }
        });
        removeButtonCellFactory.setOnAddToMenu((LetterAction s) -> {
            for (LetterAction c : actionSelected.getItems()) {
                if (c.getActionId().equals(s.getActionId())) {
                    return;
                }
            }
//            actionSelected.getItems().add(s);
            actionSelected.getItems().add(new LetterAction(NEW_LETTERS, s.getActionId()));
        });
        FilteredList<LetterAction> filteredlist = removeButtonCellFactory.init(
                actionMenu, actions, databaseHelper.letterActionDao.rawResults(query));
        actionColumnButton.setCellFactory(removeButtonCellFactory);
        actions.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getCulomnValue().toLowerCase().contains(newValue));
            actionMenu.setPrefHeight(
                    actionMenu.getFixedCellSize() * (actionMenu.getItems().size() > 5 ? 5 : actionMenu.getItems().size()) + 4
            );

        });
    }

    private void reffSelectedInit() {
        deleteReffers = new ArrayList<>();
        MyColumnTable<LetterReffer> removeButtonCellFactory = new MyColumnTable<LetterReffer>(refferSelected, Cursor.HAND) {
            @Override
            public void onDoubleClick(LetterReffer s) {
                if (s.getRefferId().getFrom() != null) {
                    simpleSearchController.onAction.ok(s.getRefferId());
                }
            }

        };

        reffColumnButton.setCellFactory(removeButtonCellFactory);
        reffers.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER:
                    refferADD();
                    break;
            }
        });
        addReff.setOnAction((ActionEvent event) -> {
            refferADD();
        });
        removeButtonCellFactory.setOnDelete((LetterReffer s) -> {
            deleteReffers.add(s);
        });
    }

    public void fileDragDrop(List<File> list) {
        for (File ff : list) {
            fileSelected.getItems().add(new LetterFile(ff.getAbsolutePath(), ff.getName()));
        }
    }

    private void refferADD() {
        Letters3 temp = databaseHelper.letters3Dao.getFirst("letterNumber", reffers.getText());
        if (temp == null && !reffers.getText().trim().isEmpty()) {
            if (UtilsMsg.showMsg("نامه ای با این شماره موجود نیست، آیا ادامه می دهید؟", "هشدار", true, thisStage)) {
                refferSelected.getItems().add(new LetterReffer(NEW_LETTERS, reffers.getText()));
            }
        } else if (!reffers.getText().equals("")) {
            refferSelected.getItems().add(new LetterReffer(NEW_LETTERS, temp));
        }
    }

    private void fileSelectedInit() {
        deleteFiles = new ArrayList<>();
        FileColumnTable removeButtonCellFactory = new FileColumnTable(true);
        fileColumnButton.setCellFactory(removeButtonCellFactory);
        removeButtonCellFactory.setOnDelete((LetterFile s) -> {
            if (s.getId() != null) {
                deleteFiles.add(s);
            }
        });
    }

    public void copyImageFile(String fileaddress, String dir, String name) {
        File srcFile = new File(fileaddress);
        File destFile = new File(dir + name + getFileExtension(fileaddress));
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException ex) {
            System.out.println("MY ERROR :: " + ex.getMessage());
            Logger.getLogger(FXMLLetterInsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getFileExtension(String fileaddress) {
        try {
            return fileaddress.substring(fileaddress.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    private void deleteListRefresh() {
        deleteActions = new ArrayList<>();
        deleteFiles = new ArrayList<>();
        deleteReffers = new ArrayList<>();
        deleteToCompanies = new ArrayList<>();
    }

    @FXML
    private void insertLetter() throws SQLException {
        ErrorCheck check = new ErrorCheck("شماره نامه", "موضوع نامه", "فرستنده(از)", "اصل");
        if (!letter_date.isFull()) {
            check.addOtherMsg("تاریخ نامه");
        }
        if (!receive_date.isFull()) {
            check.addOtherMsg("تاریخ رسید");
        }
        if (toSlected.getItems().isEmpty()) {
            check.addOtherMsg("گیرنده(به)");
        }
        if (check.checked(false, "اخطار", thisStage, number_letter, subject, from, notification) != -1) {
            return;
        }

        Companies companiesFROM = databaseHelper.companiesDao.getFirst("company_fa", from.getText());
        if (companiesFROM == null) {
            UtilsMsg.showMsg("فیلد فرستنده به درستی پر نگردیده است.", "اخطار", false, thisStage);
            return;
        }
        Users2 actionsTemp = databaseHelper.users2Dao.getFirst("name_fa", notification.getText());
        if (actionsTemp == null) {
            UtilsMsg.showMsg("فیلد اصل به درستی پر نگردیده است.", "اخطار", false, thisStage);
            return;
        }

        if (EDIT_MODE) {
            List<Letters3> exist = databaseHelper.letters3Dao.rawResults("SELECT * FROM letters3 WHERE letterNumber = '" + number_letter.getText() + "' AND id != '" + NEW_LETTERS.getId() + "'");
            if (!exist.isEmpty()) {
                UtilsMsg.showMsg("نامه ای با این شماره قبلا ثبت شده", "خطا", false, thisStage);
                return;
            }
        }

        NEW_LETTERS.setSubject(subject.getText());
        NEW_LETTERS.setLetterNumber(number_letter.getText());
        NEW_LETTERS.setLetter_date(letter_date.writeAndGet());
        NEW_LETTERS.setReceive_date(receive_date.writeAndGet());
        NEW_LETTERS.setFrom(companiesFROM);
        NEW_LETTERS.setAnnex(have_attach.isSelected());
        NEW_LETTERS.setClosed(!askNeed.isSelected());
        NEW_LETTERS.setNotification(actionsTemp);

        NEW_LETTERS.addToLog(
                users.getId(), EDIT_MODE ? Permission.LETTER_EDIT : Permission.LETTER_INSERT, new MyTime().writeAndGetNow().getId(), new MyTime().getHourNow()
        );
        databaseHelper.letters3Dao.createOrUpdate(NEW_LETTERS);

        String split = FileSystems.getDefault().getSeparator();
        int j = 0;
        for (LetterFile f : fileSelected.getItems()) {

            String dirDestination = server + "data" + split + toSlected.getItems().get(0).getCompanyId().getFolder_name() + split + companiesFROM.getFolder_name() + split;
            new File(dirDestination).mkdirs();

            String fileName = j == 0 ? "" : " (" + j + ")";
            if (f.getId() == null) {
                copyImageFile(f.getAddress(), dirDestination, NEW_LETTERS.getId() + fileName);
            } else {
                System.out.println("asdas = " + server + f.getAddress());
                new File(server + f.getAddress()).renameTo(new File(dirDestination + NEW_LETTERS.getId() + fileName + getFileExtension(f.getAddress())));
            }

            j++;
        }

        List<LetterFile> temp = new ArrayList<>(deleteFiles);
        temp.addAll(fileSelected.getItems());
        for (LetterToCompany ltc : deleteToCompanies) {
            for (LetterFile f : temp) {
                if (f.getId() != null) {
                    String dirDestination = server + "data" + split + ltc.getCompanyId().getFolder_name() + split + companiesFROM.getFolder_name() + split + f.getName();
                    File destFile = new File(dirDestination);
                    if (destFile.exists()) {
                        destFile.delete();
                    }
                }
            }
        }

        List<LetterToCompany> temp2 = new ArrayList<>(deleteToCompanies);
        temp2.addAll(toSlected.getItems());
        for (LetterToCompany ltc : temp2) {
            for (LetterFile f : deleteFiles) {
                if (ltc.getId() != null) {
                    String dirDestination = server + "data" + split + ltc.getCompanyId().getFolder_name() + split + companiesFROM.getFolder_name() + split + f.getName();
                    File destFile = new File(dirDestination);
                    if (destFile.exists()) {
                        destFile.delete();
                    }
                }
            }
        }
        int i = 0;
        for (LetterToCompany ltc : toSlected.getItems()) {
            ltc.setSortOrder(i++);
            ltc.setLetterId(NEW_LETTERS);
        }
        i = 0;
        for (LetterAction la : actionSelected.getItems()) {
            la.setSortOrder(i++);
            la.setLetterId(NEW_LETTERS);
        }
        databaseHelper.letterActionDao.delete(deleteActions);
        databaseHelper.letterToCompanyDao.delete(deleteToCompanies);
        databaseHelper.letterRefferDao.delete(deleteReffers);
        databaseHelper.letterFileDao.delete(deleteFiles);
        databaseHelper.letterActionDao.insertList(actionSelected.getItems());
        databaseHelper.letterToCompanyDao.insertList(toSlected.getItems());
        List<Letters3> reffer_list = new ArrayList<>();
        i = 0;
        for (LetterReffer lr : refferSelected.getItems()) {
            lr.setSortOrder(i++);
            Letters3 ref = lr.getRefferId();
            ref.setClosed(true);
            reffer_list.add(ref);
            lr.setLetterId(NEW_LETTERS);
        }
        databaseHelper.letters3Dao.insertList(reffer_list);
        databaseHelper.letterRefferDao.insertList(refferSelected.getItems());

        i = 0;
        for (LetterFile f : fileSelected.getItems()) {
            f.setSortOrder(i);
            String dir = "data" + split + toSlected.getItems().get(0).getCompanyId().getFolder_name() + split + companiesFROM.getFolder_name() + split;
            String attach = i == 0 ? "" : " (" + i + ")";
            String name = NEW_LETTERS.getId() + attach + getFileExtension(f.getAddress());
            f.setAddress(dir + name);
            f.setName(name);
            f.setLetterId(NEW_LETTERS);
            i++;
        }
        fileSelected.refresh();
        databaseHelper.letterFileDao.insertList(fileSelected.getItems());
        andicator.setText(NEW_LETTERS.getId() + "");

        UtilsMsg.showMsg(EDIT_MODE ? "تغییرات با موفقیت ثبت گردید" : "نامه با موفقیت ثبت گردید", "هشدار", false, thisStage);
        EDIT_MODE = true;
        number_letter.requestFocus();
        deleteListRefresh();
    }

    @FXML
    private void newLetter() {
        deleteListRefresh();
        EDIT_MODE = false;
        NEW_LETTERS = new Letters3();
        agtp.utils.TextFiledLimited.set_empty_textField(
                letter_date_yaer, letter_date_month, letter_date_day, letter_receives_yaer, letter_receive_month,
                letter_receive_day, number_letter, subject, from, to, notification, actions, reffers
        );
        toSlected.getItems().clear();
        actionSelected.getItems().clear();
        refferSelected.getItems().clear();
        have_attach.setSelected(false);
        askNeed.setSelected(true);
        fileSelected.getItems().clear();
        andicator.setText("----");
        number_letter.requestFocus();
    }

    @FXML
    private void zakhireAndClose() throws SQLException {
        insertLetter();
        thisStage.close();
    }

    @FXML
    private void zakhireAndnew() throws SQLException {
        insertLetter();
        newLetter();
    }

    @FXML
    private void about() {
    }

    @FXML
    private void close() throws SQLException {
        thisStage.close();
    }

    @FXML
    private void showSearch() {
        if (searchPane.isVisible()) {
            currentFucos.requestFocus();
            searchPane.setVisible(false);
        } else {
            searchPane.setVisible(true);
            currentFucos = thisStage.getScene().getFocusOwner();
            simpleSearchController.searchText.requestFocus();
        }
    }

    public boolean loadLetterB(Letters3 l) {
        newLetter();
        if (l != null) {
            EDIT_MODE = true;
            this.NEW_LETTERS = l;
            number_letter.setText(l.getLetterNumber());
            letter_date.setDate(l.getLetter_date());
            receive_date.setDate(l.getReceive_date());

            subject.setText(l.getSubject());
            try {
                from.setText(l.getFrom().getCompany_fa());
            } catch (Exception e) {
            }
            try {
                notification.setText(l.getNotification().getName_fa());
            } catch (Exception e) {
            }
            toSlected.getItems().setAll(databaseHelper.letterToCompanyDao.rawResults(
                    "SELECT * FROM lettertocompany WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
            ));
            actionSelected.getItems().setAll(databaseHelper.letterActionDao.rawResults(
                    "SELECT * FROM letteraction WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
            ));
            refferSelected.getItems().setAll(databaseHelper.letterRefferDao.rawResults(
                    "SELECT * FROM letterreffer WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
            ));
            fileSelected.getItems().setAll(databaseHelper.letterFileDao.rawResults(
                    "SELECT * FROM letterFile WHERE letter_id = " + l.getId() + " ORDER BY sortOrder ASC"
            ));

            askNeed.setSelected(!l.getClosed());
            have_attach.setSelected(l.getAnnex());
            andicator.setText(l.getId() + "");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentFucos = number_letter;
        number_letter.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (number_letter.getText().trim().isEmpty()) {
                return;
            }
            List<Letters3> letterList = databaseHelper.letters3Dao.rawResults(
                    "SELECT * FROM letters3 WHERE letterNumber = '" + number_letter.getText() + "'");
            if (!letterList.isEmpty() && !newValue && !EDIT_MODE) {
                if (letterList.get(0).getFrom() != null) {
                    UtilsMsg.showMsg("شماره نامه تکراری است ، آن را تغییر دهید.", "هشدار", false, thisStage);
                    number_letter.requestFocus();
                } else {
                    NEW_LETTERS = letterList.get(0);
                }
            }
        });
        submit.init("floppy", 16);
        clear.init("doc_new", 16);
        search_view.init("search", 16, "table-button");
        searchPane.visibleProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                currentFucos.requestFocus();
            }
        });
        toMenuInit();
        actionsMenuInit();
        String query = "SELECT * FROM companies WHERE active = 1 AND is_deleted = 0 ORDER BY company_fa ASC";
        MenuTableInit.companiesInit(query, from, fromMenu);
        query = "SELECT * FROM users2 WHERE active = 1 AND is_deleted = 0 AND ( users2.type LIKE '%ACTION%' OR users2.type LIKE '%GROUP%' ) ORDER BY name_fa ASC";
        MenuTableInit.actionsInit(query, notification, notificationMenu);

        reffSelectedInit();
        fileSelectedInit();
        TextFiledLimited.setEnterFocuse(number_letter, letter_date_yaer, number_letter);

        TextFiledLimited.setEnterFocuse(letter_receive_day, subject, from, to, notification, actions, reffers, addReff, askNeed, have_attach, add_file, submit, clear);

        TextFiledLimited.setEnterFocuse(toSlected, notification, to);
        TextFiledLimited.setEnterFocuse(actionSelected, reffers, actions);
        TextFiledLimited.setEnterFocuse(refferSelected, have_attach, reffers);
        TextFiledLimited.setTabFocuse(fileSelected, submit, add_file);
        TextFiledLimited.setTabFocuse(fromMenu, to, subject);
        TextFiledLimited.setTabFocuse(toMenu, notification, from);
        TextFiledLimited.setTabFocuse(notificationMenu, actions, to);
        TextFiledLimited.setTabFocuse(actionMenu, reffers, notification);

        add_file.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            List<File> fileList = fileChooser.showOpenMultipleDialog(thisStage);
            if (fileList != null) {
                for (File ff : fileList) {
                    fileSelected.getItems().add(new LetterFile(ff.getAbsolutePath(), ff.getName()));
                }
            }
        });

    }

    @Override
    public void setStage(Stage s) {
        super.setStage(s);

        NEW_LETTERS = new Letters3();

        addReff.init("plus", 16);
        add_file.init("plus", 16);
        letter_date = new MyTime(letter_date_yaer, letter_date_month, letter_date_day);
        letter_date.setOrdert(number_letter, letter_receives_yaer);

        receive_date = new MyTime(letter_receives_yaer, letter_receive_month, letter_receive_day);
        receive_date.setOrdert(letter_date_day, subject);

        thisStage.setOnShowing((WindowEvent event) -> {
            userName.setText("کاربر : " + users.getName_fa());
        });
        thisStage.setOnCloseRequest((WindowEvent event) -> {
            EDIT_MODE = true;
        });

        UtilsMsg utilsStage = new UtilsMsg("simpleSearch/FXMLSimpleSearch.fxml", "", Modality.NONE, thisStage.getOwner());
        simpleSearchController = utilsStage.getLoader().getController();
        simpleSearchController.init(utilsStage.getStage(), searchPane);
        searchPane.getChildren().add(utilsStage.page);

        thisStage.addEventFilter(KeyEvent.KEY_TYPED, (final KeyEvent event) -> {
            if (event.isAltDown()) {
                thisStage.requestFocus();
                event.consume();
            }
        });
        simpleSearchController.setOnAction((Letters3 l) -> {
            if (l != null) {
                loadLetterB(l);
            } else {
                newLetter();
            }
            simpleSearchController.searchText.requestFocus();
        });

        thisStage.getScene().setOnDragOver((DragEvent event1) -> {
            Dragboard db = event1.getDragboard();
            if (db.hasFiles()) {
                event1.acceptTransferModes(TransferMode.LINK);
            } else {
                event1.consume();
            }
        });
        thisStage.getScene().setOnDragDropped((DragEvent event1) -> {
            Dragboard db = event1.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                fileDragDrop(db.getFiles());
            }
            event1.setDropCompleted(success);
            event1.consume();
        });
    }

}
