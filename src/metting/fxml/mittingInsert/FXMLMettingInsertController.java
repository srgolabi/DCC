package metting.fxml.mittingInsert;

import agtp.dataBase.tables.Users2;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.server;
import static dccletter.DCCLetter.users;
import agtp.dataBase.tables.Companies;
import agtp.dataBase.tables.History;
import dccletter.dataBase.tables.Letters3;
import dccletter.utils.ErrorCheck;
import dccletter.utils.MyTime;
import dccletter.utils.ParentControl;
import dccletter.utils.PersianCalendar;
import dccletter.utils.TextFiledLimited;
import dccletter.utils.UtilsMsg;
import dccletter.utils.menu.LetterView;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import metting.MettingSystem;
import metting.dataBase.tables.MettingFile;
import metting.dataBase.tables.MettingCompany;
import metting.dataBase.tables.MettingLetter;
import metting.dataBase.tables.Mettings;
import metting.fxml.simpleSearch.FXMLSimpleSearchController;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLMettingInsertController extends ParentControl {

    private MyTime metting_date;
    private MyTime receive_date;

    @FXML
    private TextField metting_date_yaer;
    @FXML
    private TextField metting_date_month;
    @FXML
    private TextField metting_date_day;
    @FXML
    private TextField metting_receives_yaer;
    @FXML
    private TextField metting_receive_month;
    @FXML
    private TextField metting_receive_day;
    @FXML
    private TextField number_metting;
    @FXML
    private TextField subject;
    @FXML
    private TextField compamie_presents;
    @FXML
    private TextField action_presents;
    @FXML
    private TextField reffers;
    @FXML
    private Button addReff;
    @FXML
    private TextField page_number;
    @FXML
    private Label andicator;
    @FXML
    private Button files;
    @FXML
    private TableView<Companies> company_presents_menu;
    @FXML
    private TableView<Companies> company_presents_selected;
    @FXML
    private TableColumn<Companies, Boolean> company_presents_ColumnButton;
    @FXML
    private TableView<Users2> action_presents_menu;
    @FXML
    private TableView<Users2> action_presents_selected;
    @FXML
    private TableColumn<Users2, Boolean> action_presents_ColumnButton;
    @FXML
    private TableView<MettingLetter> letter_selected;
    @FXML
    private TableColumn<MettingLetter, Boolean> letter_ColumnButton;
    @FXML
    private TableView<MettingFile> file_selected;
    @FXML
    private TableColumn<MettingFile, Boolean> file_ColumnButton;
    @FXML
    private Button submit;
    @FXML
    private Button clear;
    @FXML
    private Label userName;

    private Mettings NEW_MITTINGS;
    private boolean EDIT_MODE = false;

    private List<MettingCompany> deleteCompanies;
    private List<MettingLetter> deleteReffers;
    private List<MettingFile> deleteFiles;
    private ListProperty<Mettings> searchList = new SimpleListProperty<>();
    private IntegerProperty idSearchProperty = new SimpleIntegerProperty(0);

    private FXMLSimpleSearchController simpleSearchController;

    private LetterView letterView;

    /**
     * Initializes the controller class.
     *
     * @param list
     */
    public void fileDragDrop(List<File> list) {
        for (File f : list) {
            file_selected.getItems().add(new MettingFile(f.getAbsolutePath(), f.getName(), null));
        }
    }

    public void setOnLetterView(LetterView lv) {
        this.letterView = lv;
    }

    private void companyMenuInit() {
        deleteCompanies = new ArrayList<>();
        String query = "SELECT * FROM companies WHERE active = 1 ORDER BY company_fa ASC";

//        RemoveButtonCellFactory<Companies, Boolean> removeButtonCellFactory = new RemoveButtonCellFactory<>(company_presents_selected, Cursor.DEFAULT);
//        removeButtonCellFactory.setOnDelete((Companies s) -> {
//            if (NEW_MITTINGS != null) {
//                deleteCompanies.addAll(databaseHelper.MettingCompanyDao.rawResults("SELECT * FROM mettingCompany WHERE metting_id = '" + NEW_MITTINGS.getId() + "' AND company_id = '" + s.getId() + "'"));
//            }
//        });
//        removeButtonCellFactory.setOnAddToMenu((Companies s) -> {
//            for (Companies c : company_presents_selected.getItems()) {
//                if (c.getCompany_fa().equals(s.getCompany_fa())) {
//                    return;
//                }
//            }
//            company_presents_selected.getItems().add(s);
//        });
//        FilteredList<Companies> filteredlist = removeButtonCellFactory.init(
//                company_presents_menu, compamie_presents, databaseHelper.companiesDao.rawResults(query));
//        company_presents_ColumnButton.setCellFactory(removeButtonCellFactory);
//        compamie_presents.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getCompany_fa().contains(newValue));
//        });
//        company_presents_menu.getSelectionModel().select(0);
    }

    private void actionsMenuInit() {

        String query = "SELECT * FROM actions WHERE active = 1 ORDER BY name ASC";
//        RemoveButtonCellFactory<Actions, Boolean> removeButtonCellFactory = new RemoveButtonCellFactory<>(action_presents_selected, Cursor.DEFAULT);
//
//        removeButtonCellFactory.setOnAddToMenu((Actions s) -> {
//            for (Actions c : action_presents_selected.getItems()) {
//                if (c.getId() != null) {
//                    if (c.getId().equals(s.getId())) {
//                        return;
//                    }
//                }
//            }
//            action_presents_selected.getItems().add(s);
//        });
//        FilteredList<Actions> filteredlist = removeButtonCellFactory.init(
//                action_presents_menu, action_presents, databaseHelper.actionsDao.rawResults(query));
//        action_presents_ColumnButton.setCellFactory(removeButtonCellFactory);
//        action_presents.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getName().contains(newValue));
//            if (action_presents_menu.getItems().isEmpty()) {
//                action_presents.setStyle("-fx-text-fill: red;");
//            } else {
//                action_presents.setStyle("-fx-text-fill: black;");
//            }
//        });
        action_presents.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.TAB) {
                reffers.requestFocus();
                return;
            }

            if (event.getCode() == KeyCode.ENTER) {
                if (action_presents.getText().trim().isEmpty()) {
                    reffers.requestFocus();
                    return;
                } else {
//                    Users2 aa = new Users2(action_presents.getText(), "", true);
//                    action_presents_selected.getItems().add(aa);
//                    action_presents.setText("");
                    event.consume();
                    return;
                }
            }
        });

//        action_presents.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                removeButtonCellFactory.visibleB.set(false);
//                event.consume();
//            }
//        });
    }

    private void reffSelectedInit() {
        deleteReffers = new ArrayList<>();
//        RemoveButtonCellFactory<MettingLetter, Boolean> removeButtonCellFactory = new RemoveButtonCellFactory<MettingLetter, Boolean>(letter_selected, Cursor.HAND) {
//            @Override
//            public void onDoubleClick(MettingLetter s) {
//                letterView.view(s.getLetter_id());
//            }
//        };
//        letter_ColumnButton.setCellFactory(removeButtonCellFactory);
//        reffers.setOnKeyPressed((KeyEvent event) -> {
//            switch (event.getCode()) {
//                case ENTER:
//                    refferADD();
//                    break;
//            }
//        });
//        addReff.setOnAction((ActionEvent event) -> {
//            refferADD();
//        });
//        removeButtonCellFactory.setOnDelete((MettingLetter s) -> {
//            deleteReffers.add(s);
//        });
    }

    private void refferADD() {
        Letters3 temp = databaseHelper.letters3Dao.getFirst("letterNumber", reffers.getText());
        if (temp == null && !reffers.getText().trim().isEmpty()) {
            UtilsMsg.showMsg("نامه ای با این شماره موجود نیست", "هشدار", true, thisStage);
        } else if (!reffers.getText().equals("")) {
            letter_selected.getItems().add(new MettingLetter(NEW_MITTINGS, temp));
        }
    }

    private void fileSelectedInit() {
        deleteFiles = new ArrayList<>();
//        RemoveButtonCellFactory<MettingFile, Boolean> removeButtonCellFactory = new RemoveButtonCellFactory<MettingFile, Boolean>(file_selected, Cursor.HAND);
//        file_ColumnButton.setCellFactory(removeButtonCellFactory);
//        file_selected.setRowFactory((TableView<MettingFile> param) -> {
//            TableRow<MettingFile> row = new TableRow<>();
//            row.setOnMouseClicked((MouseEvent event) -> {
//                try {
//                    if (row.getIndex() == file_selected.getSelectionModel().getSelectedIndex() && event.getClickCount() >= 2) {
//                        if (file_selected.getSelectionModel().getSelectedItem().getId() == null) {
//                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file_selected.getSelectionModel().getSelectedItem().getAddress());
//                        } else {
//                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + server + file_selected.getSelectionModel().getSelectedItem().getAddress());
//                        }
//                    }
//                } catch (Exception ex) {
//                    Logger.getLogger(FXMLMettingInsertController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            return row;
//        });
//        removeButtonCellFactory.setOnDelete((MettingFile s) -> {
//            if (s.getId() != null) {
//                deleteFiles.add(s);
//            }
//        });
    }

    public void copyImageFile(String fileaddress, String dir, String name) {
        File srcFile = new File(fileaddress);
        File destFile = new File(dir + name + getFileExtension(fileaddress));
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException ex) {
            System.out.println("MY ERROR :: " + "har do file yeki ast");
        }
    }

    private String getFileExtension(String fileaddress) {
        try {
            return fileaddress.substring(fileaddress.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    private boolean getTxt(TextField field) {
        return field.getText().trim().equals("");
    }

    private void deleteListRefresh() {
        deleteFiles = new ArrayList<>();
        deleteReffers = new ArrayList<>();
        deleteCompanies = new ArrayList<>();
    }

    @FXML
    private void insertLetter() throws SQLException {
        ErrorCheck check = new ErrorCheck("شماره صورتجلسه", "موضوع جلسه");
        if (!metting_date.isFull()) {
            check.addOtherMsg("تاریخ صورتجلسه");
        }
        if (!receive_date.isFull()) {
            check.addOtherMsg("تاریخ رسید");
        }

        if (check.checked(false, "اخطار", thisStage, number_metting, subject) != -1) {
            return;
        }

        if (EDIT_MODE) {
            List<Mettings> exist = databaseHelper.MettingsDao.rawResults("SELECT * FROM mettings WHERE mettingNumber = '" + number_metting.getText() + "' AND id != '" + NEW_MITTINGS.getId() + "'");
            if (!exist.isEmpty()) {
                UtilsMsg.showMsg("نامه ای با این شماره قبلا ثبت شده", "خطا", false, thisStage);
                return;
            }
        } else if (NEW_MITTINGS == null) {
            NEW_MITTINGS = new Mettings();
        }
        if (getTxt(page_number)) {
            page_number.setText("1");
        }

        NEW_MITTINGS.setMetting_date_id(metting_date.writeAndGet());

        NEW_MITTINGS.setReceive_date_id(receive_date.writeAndGet());

        NEW_MITTINGS.setMettingNumber(number_metting.getText());
        NEW_MITTINGS.setSubject(subject.getText());

        databaseHelper.MettingsDao.createOrUpdate(NEW_MITTINGS);
        databaseHelper.MettingCompanyDao.delete(deleteCompanies);
        for (Companies c : company_presents_selected.getItems()) {
            if (databaseHelper.MettingCompanyDao.rawResults("SELECT * FROM mettingCompany WHERE metting_id = '" + NEW_MITTINGS.getId() + "' AND company_id = '" + c.getId() + "'").isEmpty()) {
                databaseHelper.MettingCompanyDao.createOrUpdate(new MettingCompany(NEW_MITTINGS, c));
            }
        }
        String audience = "";
        for (Users2 c : action_presents_selected.getItems()) {
            audience = audience + c.getNameBaSemat() + "众";
        }
        NEW_MITTINGS.setAudience(audience);
        databaseHelper.MettingLetterDao.delete(deleteReffers);
        for (MettingLetter ml : letter_selected.getItems()) {
            ml.setMetting_id(NEW_MITTINGS);
            databaseHelper.MettingLetterDao.createOrUpdate(ml);
        }

        NEW_MITTINGS.setPageNumber(Integer.parseInt(page_number.getText()));

        PersianCalendar calendar = new PersianCalendar();
        String tempHistory = calendar.year2dig()
                + "/" + (calendar.month().length() == 2 ? calendar.month() : "0" + calendar.month())
                + "/" + (calendar.day().length() == 2 ? calendar.day() : "0" + calendar.day());
        History letterHistory = databaseHelper.historyDao.getFirst("date", tempHistory);
        if (letterHistory == null) {
            letterHistory = new History(calendar.year2dig(), calendar.month(), calendar.day());
            databaseHelper.historyDao.create(letterHistory);
        }
        NEW_MITTINGS.setInsert_date_id(letterHistory);

        if (NEW_MITTINGS.getUser_id() == null) {
            NEW_MITTINGS.setUser_id(databaseHelper.users2Dao.queryForId(1));
        }

        NEW_MITTINGS.setLetterHour(calendar.hour() + ":" + calendar.minute() + ":" + calendar.second());
        databaseHelper.MettingsDao.createOrUpdate(NEW_MITTINGS);
        String split = FileSystems.getDefault().getSeparator();
        databaseHelper.mettingfilesDao.delete(deleteFiles);
        for (MettingFile f : deleteFiles) {
            File destFile = new File(server + f.getAddress());
            if (destFile.exists()) {
                destFile.delete();
            }
        }

        for (Companies c : company_presents_selected.getItems()) {
            int i = 0;
            for (MettingFile f : file_selected.getItems()) {
                String dirDestination = server + "data" + split + "M" + split;
                String fileName = i == 0 ? "" : " (" + i + ")";
                if (f.getId() == null) {
                    copyImageFile(f.getAddress(), dirDestination, NEW_MITTINGS.getId() + fileName);
                } else {
                    new File(server + f.getAddress()).renameTo(new File(dirDestination + NEW_MITTINGS.getId() + fileName + getFileExtension(f.getAddress())));
                }
                i++;
            }
        }
        int i = 0;
        for (MettingFile f : file_selected.getItems()) {
            String dir = "data" + split + "M" + split;
            String attach = i == 0 ? "" : " (" + i + ")";
            String name = NEW_MITTINGS.getId() + attach + getFileExtension(f.getAddress());
            f.setAddress(dir + name);
            f.setName(name);
            f.setMetting_id(NEW_MITTINGS);
            i++;
        }
        file_selected.refresh();
        databaseHelper.mettingfilesDao.insertList(file_selected.getItems());
        andicator.setText(NEW_MITTINGS.getId() + "");

        UtilsMsg.showMsg(EDIT_MODE ? "تغییرات با موفقیت ثبت گردید" : "صورتجلسه با موفقیت ثبت گردید", "هشدار", false, thisStage);
        EDIT_MODE = true;
        number_metting.requestFocus();
        deleteListRefresh();
    }

    @FXML
    private void newLetter() {
        deleteListRefresh();
        EDIT_MODE = false;
        NEW_MITTINGS = null;
        number_metting.setText("");
        metting_date_yaer.setText("");
        metting_date_month.setText("");
        metting_date_day.setText("");
        metting_receives_yaer.setText("");
        metting_receive_month.setText("");
        metting_receive_day.setText("");
        subject.setText("");
        compamie_presents.setText("");
        company_presents_selected.getItems().clear();
        action_presents.setText("");
        action_presents_selected.getItems().clear();
        reffers.setText("");
        letter_selected.getItems().clear();
        page_number.setText("");
        file_selected.getItems().clear();
        andicator.setText("----");
        number_metting.requestFocus();
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
    private void loadLetter() {
        if (number_metting.getText().trim().isEmpty()) {
            return;
        }
        String temp = number_metting.getText();
        if (!loadLetterB(databaseHelper.MettingsDao.getFirst("mettingNumber", number_metting.getText()))) {
            UtilsMsg.showMsg("صورتجلسه ای با این شماره وجود ندارد", "اخطار", false, thisStage);
        }
        number_metting.setText(temp);
    }

    @FXML
    private void showSearch() {
        simpleSearchController.thisStage.show();
        simpleSearchController.thisStage.toFront();
        simpleSearchController.thisStage.setIconified(false);
    }

    private boolean loadLetterB(Mettings m) {
        newLetter();
        if (m != null) {
            EDIT_MODE = true;
            this.NEW_MITTINGS = m;
            number_metting.setText(m.getMittingNumber());

            metting_date.setDate(m.getMetting_date_id());
            receive_date.setDate(m.getReceive_date_id());

            subject.setText(m.getSubject());
            List<MettingCompany> mettingCompanyList = databaseHelper.MettingCompanyDao.getAll("metting_id", m);
            for (MettingCompany mc : mettingCompanyList) {
                company_presents_selected.getItems().add(mc.getCompany_id());
            }
            String[] audience = m.getAudience().split("众");
            for (String aud : audience) {
//                action_presents_selected.getItems().add(new Users2(aud, "", true));
            }

            List<MettingLetter> refferList = databaseHelper.MettingLetterDao.getAll("metting_id", m);
            letter_selected.getItems().addAll(refferList);
            List<MettingFile> filesList = databaseHelper.mettingfilesDao.getAll("metting_id", m);
            file_selected.getItems().addAll(filesList);
            page_number.setText(m.getPageNumber() + "");
            andicator.setText(m.getId() + "");
            return true;
        } else {
            return false;
        }
    }

    private String addFirstZero(int field) {
        return field > 9 ? field + "" : "0" + field;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        number_metting.requestFocus();
        number_metting.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (number_metting.getText().trim().isEmpty()) {
                return;
            }
            List<Mettings> mettingList = databaseHelper.MettingsDao.rawResults(
                    "SELECT * FROM mettings WHERE mettingNumber = '" + number_metting.getText() + "'");
            if (!mettingList.isEmpty() && !newValue && !EDIT_MODE) {
                if (mettingList.get(0).getSubject() != null) {
                    UtilsMsg.showMsg("شماره صورتجلسه تکراری است ، آن را تغییر دهید.", "هشدار", false, thisStage);
                    number_metting.requestFocus();
                } else {
                    NEW_MITTINGS = mettingList.get(0);
                }
            }
        });
        companyMenuInit();
        actionsMenuInit();
        reffSelectedInit();
        fileSelectedInit();
        TextFiledLimited.setEnterFocuse(
                number_metting, metting_date_yaer, metting_date_month, metting_date_day,
                metting_receives_yaer, metting_receive_month, metting_receive_day,
                subject, compamie_presents
        );
        TextFiledLimited.setEnterFocuse(
                reffers, addReff, page_number, files, submit, clear
        );

        TextFiledLimited.setEnterFocuse(compamie_presents, action_presents, subject);
        TextFiledLimited.setEnterFocuse(reffers, addReff, action_presents);

        TextFiledLimited.setNumberAndLengthLimit(page_number, 3, files, addReff);

        TextFiledLimited.setTabFocuse(file_selected, submit, files);
        TextFiledLimited.setTabFocuse(letter_selected, page_number, addReff);
        TextFiledLimited.setTabFocuse(company_presents_selected, action_presents, compamie_presents);
        TextFiledLimited.setTabFocuse(company_presents_menu, action_presents, compamie_presents);
        TextFiledLimited.setTabFocuse(action_presents_selected, reffers, action_presents);
        TextFiledLimited.setTabFocuse(action_presents_menu, reffers, action_presents);

        files.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            List<File> fileList = fileChooser.showOpenMultipleDialog(thisStage);
            if (fileList != null) {
                for (File f : fileList) {
                    file_selected.getItems().add(new MettingFile(f.getAbsolutePath(), f.getName(), null));
                }
            }
        });
    }

    @Override
    public void setStage(Stage s) {
        super.setStage(s);
        metting_date = new MyTime(metting_date_yaer, metting_date_month, metting_date_day);
        metting_date.setOrdert(number_metting, metting_receives_yaer);

        receive_date = new MyTime(metting_receives_yaer, metting_receive_month, metting_receive_day);
        receive_date.setOrdert(metting_date_day, subject);

        thisStage.setOnShowing((WindowEvent event) -> {
            userName.setText("کاربر : " + users.getName_fa());
        });
        thisStage.setOnCloseRequest((WindowEvent event) -> {
            EDIT_MODE = true;
            simpleSearchController.thisStage.close();
        });
        UtilsMsg utilsStage = new UtilsMsg("simpleSearch/FXMLSimpleSearch.fxml", "", Modality.WINDOW_MODAL, thisStage.getOwner(), MettingSystem.class);
        simpleSearchController = utilsStage.getLoader().getController();
        simpleSearchController.init(utilsStage.getStage());
        thisStage.addEventFilter(KeyEvent.KEY_TYPED, (final KeyEvent event) -> {
            if (event.isAltDown()) {
                thisStage.requestFocus();
                event.consume();
            }
        });
        simpleSearchController.setOnAction((Mettings m) -> {
            if (m != null) {
                loadLetterB(m);
            } else {
                newLetter();
            }
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
