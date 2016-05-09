package document.fxml.transmitallInsert;

import agtp.dataBase.tables.Users2;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.server;
import agtp.dataBase.tables.Companies;
import dccletter.dataBase.tables.LetterFile;
import dccletter.utils.ErrorCheck;
import dccletter.utils.MyTime;
import dccletter.utils.ParentControl;
import dccletter.utils.TextFiledLimited;
import dccletter.utils.UtilsMsg;
import dccletter.utils.menu.MenuTableInit;
import document.dataBase.tables.Definition;
import document.dataBase.tables.Documents_Rev;
import document.dataBase.tables.Transmittal;
import document.fxml.document_insert.Fxml_Document_Insert;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import agtp.myControl.tableView.FileColumnTable;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLTransmitallInsertController extends ParentControl {

    private MyTime transmitall_date;

    @FXML
    private HBox step_1;
    @FXML
    private VBox step_2;
    @FXML
    private VBox step_3;

    @FXML
    private TextField transmitall_date_yaer;
    @FXML
    private TextField transmitall_date_month;
    @FXML
    private TextField transmitall_date_day;
    @FXML
    private TextField trans_No;
    @FXML
    private TextField discipline;
    @FXML
    private TextField att;
    @FXML
    private TextField from;
    @FXML
    private Label commpany_Picture;
    @FXML
    private Label user_Tittle;
    @FXML
    private TableView<Users2> discipline_Menu;
    @FXML
    private TableView<Companies> from_Menu;
    @FXML
    private Button next_1;
    @FXML
    private Button cancel_1;

    @FXML
    private TableView<Documents_Rev> document_Table;
    @FXML
    private RadioButton impotr_from_Excel;
    @FXML
    private RadioButton add_Manual;
    @FXML
    private Button next_2;
    @FXML
    private Button cancel_2;
    @FXML
    private Button back_2;

    @FXML
    private TextField project_No;
    @FXML
    private TextField phase_Code;
    @FXML
    private TextField area_Number;
    @FXML
    private TextField unit_Number;
    @FXML
    private TextField document_Type;
    @FXML
    private TextField discipline_Code;
    @FXML
    private TextField sequential_No;
    @FXML
    private TextField rev;
    @FXML
    private TextField title;
    @FXML
    private ComboBox<String> class_Type;
    @FXML
    private TextField pages;
    @FXML
    private TextField copies;
    @FXML
    private ComboBox<String> size;
    @FXML
    private ComboBox<String> POI;
    @FXML
    private TableView<LetterFile> Document_Files;
    @FXML
    private TableColumn<LetterFile, LetterFile> fileColumnButton;
    @FXML
    private Button add_file_doc;
    @FXML
    private Button add_doc;
    @FXML
    private Button remove_doc;
    @FXML
    private Button next_doc;
    @FXML
    private Button back_doc;
    @FXML
    private Button finish;
    @FXML
    private Button cancel_3;
    @FXML
    private Button back_3;
    @FXML
    private Button print;

    Transmittal transmittal = new Transmittal();

    private List<LetterFile> deleteFiles;
    private ListProperty<Documents_Rev> list_Document = new SimpleListProperty<>(FXCollections.observableArrayList());
    private IntegerProperty activeDoc = new SimpleIntegerProperty(0);

    Fxml_Document_Insert documentInsertController;

    private BooleanBinding clear_form_bind() {
        BooleanBinding bb = activeDoc.isEqualTo(-1);
        TextField[] tfs = new TextField[]{
            project_No, phase_Code, area_Number, unit_Number, document_Type, discipline_Code, sequential_No,
            title, rev, class_Type.getEditor(), pages, copies, size.getEditor(), POI.getEditor()
        };
        for (TextField tf : tfs) {
            bb = bb.and(tf.textProperty().isEmpty());
        }
        bb = bb.and(Bindings.isEmpty(Document_Files.itemsProperty().get()));
        return bb;
    }

    @Override
    public void setStage(Stage s) {
        super.setStage(s);
        remove_doc.disableProperty().bind(clear_form_bind());
        activeDoc.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            remove_doc.setText(newValue.intValue() == -1 ? "Clear Form" : "Remove Document");
            add_doc.setText(newValue.intValue() == -1 ? "Add Document" : "Save Document");
        });
        activeDoc.set(-1);
        next_doc.disableProperty().bind(list_Document.sizeProperty().isEqualTo(0).or(activeDoc.isEqualTo(-1)).or(activeDoc.isEqualTo(list_Document.size() - 1)));
        back_doc.disableProperty().bind(list_Document.sizeProperty().isEqualTo(0).or(activeDoc.isEqualTo(0)));
        transmittal = new Transmittal();
//        list_Document = new ArrayList<>();
        transmitall_date = new MyTime(transmitall_date_yaer, transmitall_date_month, transmitall_date_day);
        transmitall_date.setOrdert(transmitall_date_yaer, trans_No);

//        TextFiledLimited.setTabFocuse(trans_No, discipline, transmitall_date_day);
//        TextFiledLimited.setTabFocuse(discipline, from, trans_No);
//        TextFiledLimited.setTabFocuse(from, att, discipline);
//        TextFiledLimited.setTabFocuse(att, next_1, from);
//        TextFiledLimited.setTabFocuse(next_1, cancel_1, att);
//        TextFiledLimited.setTabFocuse(cancel_1, transmitall_date_yaer, next_1);
        String query = "SELECT * FROM companies WHERE active = 1 AND company_en <> '' ORDER BY company_en ASC";
        MenuTableInit.companiesInit(query, from, from_Menu, true);

        query = "SELECT * FROM users2 WHERE active = 1 AND type LIKE 'DISCIPLINE_GROUP' ORDER BY name_en ASC";
        MenuTableInit.disciplineInit(query, discipline, discipline_Menu, true);

        TextFiledLimited.setTabFocuse(impotr_from_Excel, add_Manual, impotr_from_Excel);
        TextFiledLimited.setTabFocuse(add_Manual, next_2, impotr_from_Excel);
        TextFiledLimited.setTabFocuse(next_2, back_2, add_Manual);
        TextFiledLimited.setTabFocuse(back_2, cancel_2, next_2);
        TextFiledLimited.setTabFocuse(cancel_2, impotr_from_Excel, back_2);

        TextFiledLimited.setTabFocuse(project_No, phase_Code, project_No);
        TextFiledLimited.setTabFocuse(phase_Code, area_Number, project_No);
        TextFiledLimited.setTabFocuse(area_Number, unit_Number, phase_Code);

        autoCompleteText(project_No, "Project No");
        autoCompleteText(phase_Code, "Phase Code");
        autoCompleteText(area_Number, "Area codes");
        autoCompleteText(unit_Number, "UnitCode");
        autoCompleteText(document_Type, "DocumentTyp");
        autoCompleteText(discipline_Code, "DisciplineCode");
        set_upperCase(sequential_No);
        set_upperCase(rev);

        comboBox_init(POI, "POI");
        comboBox_init(size, "Size");
        comboBox_init(class_Type, "Class");

        Document_Files_Init();

        Document_Files.setOnDragOver((DragEvent event1) -> {
            Dragboard db = event1.getDragboard();
            File f = db.getFiles().get(0);
            if (db.hasFiles()) {
                event1.acceptTransferModes(TransferMode.LINK);
            } else {
                event1.consume();
            }
        });
        Document_Files.setOnDragDropped((DragEvent event1) -> {
            Dragboard db = event1.getDragboard();
            File f = db.getFiles().get(0);
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                addFileToTableView(db.getFiles());
            }
            event1.setDropCompleted(success);
            event1.consume();
        });

        add_file_doc.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            List<File> fileList = fileChooser.showOpenMultipleDialog(thisStage);
            if (fileList != null) {
                addFileToTableView(fileList);
            }
        });
    }

    private void addFileToTableView(List<File> list) {
        for (File ff : list) {
            Document_Files.getItems().add(new LetterFile(ff.getParent(), ff.getName()));
        }
        setDocShouldSave();
    }

    private void setDocShouldSave() {
        if (activeDoc.get() != -1) {
//            Documents temp = list_Document.get(activeDoc);
//            temp.should_Save = true;
//            list_Document.set(activeDoc, temp);
            list_Document.get(activeDoc.get()).should_Save = true;
        }
    }

    private void Document_Files_Init() {
        deleteFiles = new ArrayList<>();
        FileColumnTable removeButtonCellFactory = new FileColumnTable(true);
        fileColumnButton.setCellFactory(removeButtonCellFactory);
        removeButtonCellFactory.setOnDelete((LetterFile s) -> {
            if (s.getId() != null) {
                deleteFiles.add(s);
                setDocShouldSave();
            }
        });
    }

    private void comboBox_init(ComboBox<String> comboBox, String fieldSearch) {
        comboBox.getItems().setAll(databaseHelper.definitionCodeDao.rawResultsString("SELECT title FROM definition WHERE key LIKE '" + fieldSearch + "' ORDER BY title ASC"));
        final ObservableList<String> items = FXCollections.observableArrayList(comboBox.getItems());
        FilteredList<String> filtered = new FilteredList<>(items, p -> true);
        comboBox.getEditor().textProperty().addListener((ov, o, n) -> {
            comboBox.getEditor().setText(n.toUpperCase());

            if (n.equals(comboBox.getSelectionModel().getSelectedItem())) {
                comboBox.getEditor().setStyle("-fx-text-fill: #000000;");
                return;
            }

            comboBox.hide();

            filtered.setPredicate(s -> (n == null || n.length() == 0) ? true : s.toUpperCase().contains(n.toUpperCase()));

            if (filtered.isEmpty()) {
                comboBox.getEditor().setStyle("-fx-text-fill: #ff0000;");
                comboBox.getItems().setAll(items);
            } else {
                comboBox.getEditor().setStyle("-fx-text-fill: #000000;");
                comboBox.getItems().setAll(filtered);

                comboBox.show();
            }
        });
    }

    private void set_upperCase(TextField tf) {
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tf.setText(newValue.toUpperCase());
        });
    }

    private void autoCompleteText(TextField tf, String fieldSearch) {
        final ObservableList<String> items = FXCollections.observableArrayList(
                databaseHelper.definitionCodeDao.rawResultsString(
                        "SELECT title FROM definition WHERE key LIKE '" + fieldSearch + "' ORDER BY title ASC"
                )
        );

        FilteredList<String> filtered = new FilteredList<>(items, p -> true);
        tf.setOnKeyReleased((KeyEvent event) -> {
            filtered.setPredicate(s -> (tf.getText() == null || tf.getText().length() == 0) ? true : s.toUpperCase().startsWith(tf.getText().toUpperCase()));

            if (!event.getText().matches("[A-z0-9]")) {
                return;
            }

            if (filtered.isEmpty()) {
                tf.setStyle("-fx-text-fill: #ff0000;");
            } else {
                tf.setStyle("-fx-text-fill: #000000;");
                int se = tf.getText().length();
                tf.setText(filtered.get(0));
                tf.positionCaret(se);
                tf.selectNextWord();
            }
        });
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tf.setText(newValue.toUpperCase());
            filtered.setPredicate(s -> (tf.getText() == null || tf.getText().length() == 0) ? true : s.toUpperCase().startsWith(tf.getText().toUpperCase()));
            if (filtered.isEmpty()) {
                tf.setStyle("-fx-text-fill: #ff0000;");
            } else {
                tf.setStyle("-fx-text-fill: #000000;");
            }
        });
    }

    @FXML
    private void next_step() {
        if (step_1.isVisible()) {
            step_1.setVisible(false);
            step_2.setVisible(true);
        } else if (step_2.isVisible()) {
            if (impotr_from_Excel.isSelected()) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                        "Files (*.xls)", "*.xls");
                fileChooser.getExtensionFilters().add(extFilter);
                File fileList = fileChooser.showOpenDialog(thisStage);
                if (fileList != null) {
                    step_2.setVisible(false);
                    step_3.setVisible(true);
                }
            } else {
                step_2.setVisible(false);
                step_3.setVisible(true);
            }

        }
    }

    @FXML
    private void next_press_enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            next_step();
        }
    }

    @FXML
    private void back_step() {
        if (step_2.isVisible()) {
            step_2.setVisible(false);
            step_1.setVisible(true);
        } else if (step_3.isVisible()) {
            step_3.setVisible(false);
            step_2.setVisible(true);
        }
    }

    @FXML
    private void back_press_enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            back_step();
        }
    }

    @FXML
    private void cancel() {
        thisStage.close();
    }

    @FXML
    private void cancel_press_enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            cancel();
        }
    }

//    @FXML
//    private boolean save_Doc() {
//
//        if (checkStyle(
//                new TextField[]{
//                    project_No, phase_Code, area_Number, unit_Number, document_Type, discipline_Code,
//                    class_Type.getEditor(), size.getEditor(), POI.getEditor()
//                },
//                new String[]{
//                    "Project No", "Phase Code", "Area Number", "Unit Number", "Document Type", "Discipline Code",
//                    "Title", "Revesion", "POI"
//                }
//        )) {
//            return false;
//        }
//
//        if (checkEmpty(
//                new TextField[]{
//                    project_No, phase_Code, area_Number, unit_Number, document_Type, discipline_Code, sequential_No,
//                    title, rev, POI.getEditor()
//                },
//                new String[]{
//                    "Project No", "Phase Code", "Area Number", "Unit Number", "Document Type", "Discipline Code", "Sequential No",
//                    "Title", "Revesion", "POI"
//
//                }
//        )) {
//            return false;
//        }
//
//        String query_for_duplicate_base = get_query_patr1()
//                + " projectNo LIKE '" + project_No.getText()
//                + "' AND phaseCode LIKE '" + phase_Code.getText()
//                + "' AND areaNo LIKE '" + area_Number.getText()
//                + "' AND unitNo LIKE '" + unit_Number.getText()
//                + "' AND docType LIKE '" + document_Type.getText()
//                + "' AND disciplineCode LIKE '" + discipline_Code.getText()
//                + "' AND sequential LIKE '" + sequential_No.getText() + "'";
//
//        if (activeDoc.get() == -1) {
//            String query_for_duplicate = query_for_duplicate_base + " AND rev LIKE '" + rev.getText() + "'";
//            if (!databaseHelper.documents_REVDao.rawResults(query_for_duplicate).isEmpty()) {
//                UtilsStage.showMsgEn("The document No. is duplicate", "Error", false, thisStage);
//                return false;
//            }
//        }
//
//        List<Documents_Rev> revision_list = databaseHelper.documents_REVDao.rawResults(query_for_duplicate_base);
//        if (!revision_list.isEmpty()) {
//            String revesins = "";
//            for (Documents_Rev dc : revision_list) {
//                if (!dc.getVoiid()) {
//                    dc.setVoiid(true);
//                    databaseHelper.documents_REVDao.createOrUpdate(dc);
//                }
//                revesins = dc.getRev() + " , " + revesins;
//            }
//            revesins = revesins.substring(0, revesins.length() - 3);
//            if (!UtilsStage.showMsgEn("This document have old revision.\n old revesin: " + revesins, "Error", true, thisStage)) {
//                return false;
//            }
//        }
//
//        Documents_Rev new_Doc;
//        if (activeDoc.get() != -1) {
//            new_Doc = list_Document.get(activeDoc.get());
//            new_Doc.should_Save = isShouldSave(
//                    new TextField[]{
//                        project_No, phase_Code, area_Number, unit_Number, document_Type, discipline_Code, sequential_No,
//                        rev, class_Type.getEditor(), pages, copies, size.getEditor(), POI.getEditor()
//                    },
//                    new String[]{
//                        new_Doc.getProjectNo(), new_Doc.getPhaseCode(), new_Doc.getAreaNo(), new_Doc.getUnitNo(), new_Doc.getDocType(), new_Doc.getDisciplineCode(), new_Doc.getSequential(),
//                        new_Doc.getRev(), new_Doc.getClas(), new_Doc.getPageCount() + "", new_Doc.getCopyCount() + "", new_Doc.getPaperSize(), new_Doc.getPoi()
//                    }
//            );
//
//        } else {
//            new_Doc = new Documents_Rev();
//            new_Doc.should_Save = true;
//        }
//
//        if (!new_Doc.should_Save) {
//            return true;
//        }
//
//        new_Doc.setProjectNo(project_No.getText());
//        new_Doc.setPhaseCode(phase_Code.getText());
//        new_Doc.setAreaNo(area_Number.getText());
//        new_Doc.setUnitNo(unit_Number.getText());
//        new_Doc.setDocType(document_Type.getText());
//        new_Doc.setDisciplineCode(discipline_Code.getText());
//        new_Doc.setSequential(sequential_No.getText());
//        new_Doc.setTitle(title.getText());
//        new_Doc.setRev(rev.getText());
//        new_Doc.setClas(class_Type.getValue());
//        new_Doc.setPageCount(Integer.parseInt(pages.getText()));
//        new_Doc.setCopyCount(Integer.parseInt(copies.getText()));
//        new_Doc.setPaperSize(size.getValue());
//        new_Doc.setPoi(POI.getValue());
//        new_Doc.files_LIST = Document_Files.getItems();
//
//        if (activeDoc.get() != -1) {
//            list_Document.set(activeDoc.get(), new_Doc);
//        } else {
//            list_Document.add(new_Doc);
//        }
//        return true;
//    }

    @FXML
    private void delete_OR_clear_doc() {
        if (activeDoc.get() != -1) {
            if (!UtilsMsg.showMsgEn("Are you sure you want to remove this document?", "Warning", true, thisStage)) {
                return;
            }
//            for (File f : Files.getActualFiles(list_Document.get(activeDoc.get()).getFiles())) {
//                f.delete();
//            }
            databaseHelper.documents_REVDao.delete(list_Document.get(activeDoc.get()));
        }
        agtp.utils.TextFiledLimited.set_empty_textField(
                project_No, phase_Code, area_Number, unit_Number, document_Type,
                discipline_Code, sequential_No, title, rev, class_Type.getEditor(),
                pages, copies, size.getEditor(), POI.getEditor()
        );
        deleteFiles = new ArrayList<>();
        Document_Files.getItems().clear();
    }

    public String get_query_patr1() {
        if (activeDoc.get() != -1) {
            return "SELECT * FROM documents WHERE id != " + list_Document.get(activeDoc.get()).getId() + " AND";
        } else {
            return "SELECT * FROM documents WHERE";
        }
    }

    private boolean check_For_Save_Doc(TextField[] tfs) {
        if (!Document_Files.getItems().isEmpty()) {
            return true;
        }
        for (TextField tf : tfs) {
            if (!tf.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

//    @FXML
//    private void finish() {
//        if (check_For_Save_Doc(new TextField[]{
//            project_No, phase_Code, area_Number, unit_Number, document_Type, discipline_Code, sequential_No,
//            rev, class_Type.getEditor(), pages, copies, size.getEditor(), POI.getEditor()
//        })) {
//            if (!save_Doc()) {
//                return;
//            }
//        }
//        ErrorCheck check = new ErrorCheck("Trans No", "Discipline", "From", "Att");
//        if (!transmitall_date.isFull()) {
//            check.addOtherMsg("Date Receive");
//        }
//        if (discipline_Menu.getItems().isEmpty()) {
//            check.addOtherMsg("Discipline");
//        }
//        if (from_Menu.getItems().isEmpty()) {
//            check.addOtherMsg("From");
//        }
//        if (check.checked_EN(false, "Error", thisStage, trans_No, discipline, from, att) != -1) {
//            return;
//        }
//        Companies companiesFROM = databaseHelper.companiesDao.getFirst("company_en", from.getText());
//        if (companiesFROM == null) {
//            UtilsStage.showMsgEn("From Field is fault", "Error", false, thisStage);
//            return;
//        }
//
//        List<Definition> disciplinDEF = databaseHelper.definitionCodeDao.rawResults(
//                "SELECT * FROM definition WHERE key LIKE 'DisciplineCode' AND description LIKE '" + discipline.getText() + "'"
//        );
//        if (disciplinDEF.isEmpty()) {
//            UtilsStage.showMsgEn("Discipline Field is fault", "Error", false, thisStage);
//            return;
//        }
//        transmittal.setAtt(att.getText());
//        transmittal.setFrom(companiesFROM);
//        transmittal.setTranc_number(trans_No.getText());
//        transmittal.setTransmittal_date(transmitall_date.writeAndGet());
////        transmittal.setDiscipline(disciplinDEF.get(0));
//        databaseHelper.transmittalDao.createOrUpdate(transmittal);
//        String split = FileSystems.getDefault().getSeparator();
//
//        for (Documents_Rev docS : list_Document) {
////            docS.setTransmittal_id(transmittal);
//            String temp_files = "";
//
//            int i = 1;
//            String dirDestination = "data\\doc" + split + docS.getDisciplineCode() + split + transmittal.getId();
//            String fileNameBASE = docS.getProjectNo() + "-" + docS.getPhaseCode() + "-" + docS.getAreaNo() + "-" + docS.getUnitNo()
//                    + "-" + docS.getDocType() + "-" + docS.getDisciplineCode() + "-" + docS.getSequential() + "-" + docS.getRev();
//            for (LetterFile fls : docS.files_LIST) {
//                String fileName = fileNameBASE + (docS.files_LIST.size() == 1 ? "" : " (" + i + ")") + getFileExtension(fls.getAddress());
//                String destFile = server + dirDestination + split + fileName;
//                if (fls.getId() == null) {
//                    copyImageFile(fls.getAddress(), destFile);
//                } else {
//                    new File(server + fls.getAddress()).renameTo(new File(destFile));
//                }
//                fls.setName(fileName);
////                fls.setPath(dirDestination);
////                fls.setSaved(true);
////                temp_files = temp_files + fls.getAddressDB();
//                i++;
//            }
////            docS.setFiles(temp_files);
//            databaseHelper.documents_REVDao.createOrUpdate(docS);
//        }
//    }

    public void copyImageFile(String fileaddress, String desrDir) {
        File srcFile = new File(fileaddress);
        File destFile = new File(desrDir);
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

    private boolean isShouldSave(TextField[] tfs, String[] strs) {
        int i = 0;
        for (TextField tf : tfs) {
            if (!tf.getText().equals(strs[i])) {
                return true;
            }
            i++;
        }
        return false;
    }

    private boolean checkEmpty(TextField[] tfs, String[] st) {
        String msg = "";
        int i = 0;
        for (TextField tf : tfs) {
            if (tf.getText().trim().isEmpty()) {
                msg = msg + " \"" + st[i] + "\" ,";
            }
            i++;
        }
        if (msg.isEmpty()) {
            return false;
        } else {
            msg = msg + "\n" + "Should Not Empty.";
            UtilsMsg.showMsgEn(msg, "Error", false, thisStage);
            return true;
        }
    }

    private boolean checkStyle(TextField[] tfs, String[] st) {
        String msg = "";
        int i = 0;
        for (TextField tf : tfs) {
            if (!tf.getStyle().equals("-fx-text-fill: #000000;")) {
                msg = msg + " \"" + st[i] + "\" ,";
            }
            i++;
        }
        if (msg.isEmpty()) {
            return false;
        } else {
            msg = msg + "\n" + "Not Currect.";
            UtilsMsg.showMsgEn(msg, "Error", false, thisStage);
            return true;
        }
    }
}
