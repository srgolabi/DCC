package dccletter.fxml.letterList;

import agtp.dataBase.tables.Permission;
import agtp.myControl.MyButtonFont;
import agtp.myControl.tableView.FileColumnTable;
import dccletter.DCCLetter;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.server;
import static dccletter.DCCLetter.users;
import dccletter.dataBase.tables.LetterFile;
import dccletter.dataBase.tables.LetterFull;
import dccletter.utils.UtilsMsg;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;
import org.google.jhsheets.filtered.tablecolumn.ColumnFilterEvent;
import org.google.jhsheets.filtered.tablecolumn.FilterableBooleanTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableIntegerTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableLongTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableStringTableColumn;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLLtterListController implements Initializable {

    public Stage thisStage;
    private FXMLFileListController fileListController;

    @FXML
    private MyButtonFont edit;
    @FXML
    private MyButtonFont review;
    @FXML
    private MyButtonFont export_to_excel;
    @FXML
    private MyButtonFont download_file;

    @FXML
    private Label userName;
    @FXML
    private Label sum;

    @FXML
    public TableView<LetterFull> tableView;
    @FXML
    private TextField textField;

    private int columnCount = 0;
    private ObservableList<LetterFull> personList;
    public int editLetter = -1;
    public int viewLetter = -1;

    /**
     * Initializes the controller class.
     *
     * @param s
     */
    public void init(Stage s, String query) {
        thisStage = s;
        thisStage.setMinHeight(700);
        thisStage.setMinWidth(800);
        thisStage.getScene().setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER:
                    if (!review.isDisable()) {
                        viewFile(tableView.getSelectionModel().getSelectedItem().getId());
                    }
                    break;
            }
        });

        edit.setDisable(!Permission.isAcces(Permission.LETTER_INSERT));
        review.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        if (!edit.isDisabled()) {
            edit.disableProperty().bind(review.disableProperty());
        }
        download_file.disableProperty().bind(review.disableProperty());

        edit.init("pencil", 15);
        review.init("eye", 15);
        download_file.init("download_1", 15);
        export_to_excel.init("export", 15);

        edit.setOnAction((ActionEvent event) -> {
            editLetter = tableView.getSelectionModel().getSelectedItem().getId();
            thisStage.close();
        });
        review.setOnAction((ActionEvent event) -> {
            viewLetter = tableView.getSelectionModel().getSelectedItem().getId();
            thisStage.close();
        });
        download_file.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(thisStage);
            if (file == null) {
                return;
            }
            try {
                file.mkdirs();
                List<LetterFile> files_list = databaseHelper.letterFileDao.getAll("letter_id", databaseHelper.letters3Dao.queryForId(tableView.getSelectionModel().getSelectedItem().getId()));
                for (LetterFile f : files_list) {
                    FileUtils.copyFileToDirectory(new File(f.getAddress()), file);
                }
            } catch (IOException ex) {
                Logger.getLogger(FileColumnTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        userName.setText(users.getName_fa());

        personList = FXCollections.observableArrayList(databaseHelper.letterFullDao.rawResults(query));
        tableView.setFixedCellSize(35d);
        tableView.setRowFactory(tv -> new TableRow<LetterFull>() {
            @Override
            protected void updateItem(LetterFull item, boolean empty) {
                super.updateItem(item, empty);
                setOnMouseClicked((MouseEvent event) -> {
                    if (item != null && event.getClickCount() >= 2) {
                        viewFile(item.getId());
                    }
                });
            }

        });

        prepareTable();
        Platform.runLater(() -> {
            thisStage.setMaximized(true);
        });
    }

    private void viewFile(int id) {
        List<LetterFile> list = DCCLetter.databaseHelper.letterFileDao.getAll("letter_id", databaseHelper.letters3Dao.queryForId(id));
        if (list.isEmpty()) {
            UtilsMsg.showMsg("فایل نامه موجود نمی باشد.", "هشدار", false, thisStage);
            return;
        }
        try {
            if (list.size() > 1) {
                UtilsMsg utilsStage = new UtilsMsg("letterList/FXMLFileList.fxml", "فایل های نامه", Modality.APPLICATION_MODAL, thisStage.getOwner());
                fileListController = utilsStage.getLoader().getController();
                fileListController.init(utilsStage.getStage(), list);
                fileListController.thisStage.show();
                return;
            }
            if (!new File(server + list.get(0).getAddress()).exists()) {
                UtilsMsg.showMsg("ارتباط با سرور قطع شده است.", "هشدار", false, thisStage);
            }
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + server + list.get(0).getAddress());
        } catch (Exception ex) {
        }
    }

    private void prepareTable() {
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tableFiltered(textFiltered(newValue));
        });

        tableView.addEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, (ColumnFilterEvent event) -> {
            tableFiltered(textFiltered(textField.getText()));
        });

        tableView.getItems().setAll(personList);
        sum.setText(tableView.getItems().size() + "");
    }

    private void tableFiltered(ObservableList<LetterFull> newData) {
        columnCount = 0;
        filterIntColumn(newData);
        filterDateColumn(newData);
        filterStringColumn(newData);
        filterStringColumn(newData);
        filterStringColumn(newData);
        filterStringColumn(newData);
        filterStringColumn(newData);
        filterStringColumn(newData);
        filterBoolColumn(newData);
        filterBoolColumn(newData);
        filterDateColumn(newData);
        tableView.getItems().setAll(newData);
        sum.setText(tableView.getItems().size() + "");
    }

    private ObservableList<LetterFull> textFiltered(String newValue) {
        final List<LetterFull> remove = new ArrayList<>();
        final ObservableList<LetterFull> newData = FXCollections.concat(personList);
        if (!newValue.trim().equals("")) {
            newData.stream().filter((item) -> (!item.getSubject().contains(newValue) && !item.getLetterNumber().contains(newValue))).forEach((item) -> {
                remove.add(item);
            });
            newData.removeAll(remove);
        }
        return newData;
    }

    @FXML
    private void SaveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("انتقال به اکسل");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Excel(*.xls)", "*.xls"));
        File file = fileChooser.showSaveDialog(thisStage);
        if (file == null) {
            return;
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("گزارش");

            // index from 0,0... cell A1 is cell(0,0)
            int cellCount = 1;
            HSSFRow row = worksheet.createRow(0);
            row.createCell(0).setCellValue("ردیف");
            for (TableColumn tc : tableView.getColumns()) {
                if (tc.isVisible()) {
                    HSSFCell cell = row.createCell(cellCount);
                    cell.setCellValue(tc.getText() + "");
                    cellCount++;
                }
            }
            int rowCount = 1;
            for (LetterFull lf : tableView.getItems()) {
                row = worksheet.createRow(rowCount);
                cellCount = 1;
                row.createCell(0).setCellValue(rowCount + "");

                for (TableColumn tc : tableView.getColumns()) {
                    if (tc.isVisible()) {
                        HSSFCell cell = row.createCell(cellCount);
                        try {
                            cell.setCellValue(tc.getCellObservableValue(rowCount - 1).getValue() + "");
                        } catch (Exception e) {
                        }
                        cellCount++;
                    }
                }
                rowCount++;
            }

            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void exit() {
        thisStage.close();
    }

    private void filterIntColumn(ObservableList<LetterFull> newData) {
        final List<LetterFull> remove = new ArrayList<>();
        final ObservableList<NumberOperator<Integer>> filters;
        try {
            filters = ((FilterableIntegerTableColumn) tableView.getColumns().get(columnCount)).getFilters();
        } catch (Exception e) {
            return;
        }
        for (NumberOperator<Integer> filter : filters) {
            if (filter.getValue() == null) {
                continue;
            }
            for (LetterFull item : newData) {

                int itemV = getIntValue(item);
                if (itemV == -1) {
                    remove.add(item);
                    continue;
                }
                if (filter.getType() == NumberOperator.Type.EQUALS) {
                    if (!Objects.equals(itemV, filter.getValue())) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
                    if (Objects.equals(itemV, filter.getValue())) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
                    if (itemV <= filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
                    if (itemV < filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
                    if (itemV >= filter.getValue()) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
                    if (itemV > filter.getValue()) {
                        remove.add(item);
                    }
                }
            }
        }
        newData.removeAll(remove);
        columnCount++;
    }

    private void filterStringColumn(ObservableList<LetterFull> newData) {
        final List<LetterFull> remove = new ArrayList<>();
        final ObservableList<StringOperator> filters;
        try {
            filters = ((FilterableStringTableColumn) tableView.getColumns().get(columnCount)).getFilters();
        } catch (Exception e) {
            return;
        }
        for (StringOperator filter : filters) {
            for (LetterFull item : newData) {
                String itemV = getStringValue(item);

                if (filter.getType() == StringOperator.Type.EQUALS) {
                    if (!itemV.equals(filter.getValue())) {
                        remove.add(item);
                    }
                } else if (filter.getType() == StringOperator.Type.NOTEQUALS) {
                    if (itemV.equals(filter.getValue())) {
                        remove.add(item);
                    }
                } else if (filter.getType() == StringOperator.Type.CONTAINS) {
                    if (!itemV.contains(filter.getValue())) {
                        remove.add(item);
                    }
                } else if (filter.getType() == StringOperator.Type.STARTSWITH) {
                    if (!itemV.startsWith(filter.getValue())) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.ENDSWITH) {
                    if (!itemV.endsWith(filter.getValue())) {
                        remove.add(item);
                    }
                }
            }
        }
        newData.removeAll(remove);
        columnCount++;

    }

    private void filterDateColumn(ObservableList<LetterFull> newData) {
        final List<LetterFull> remove = new ArrayList<>();
        final ObservableList<NumberOperator<Long>> filters;
        try {
            filters = ((FilterableLongTableColumn) tableView.getColumns().get(columnCount)).getFilters();
        } catch (Exception e) {
            return;
        }
        for (NumberOperator<Long> filter : filters) {
            try {
                filter.getValue().toString();
            } catch (Exception e) {
                continue;
            }
            for (LetterFull item : newData) {

                long itemV = getDateValue(item);
                if (itemV == -1) {
                    remove.add(item);
                    continue;
                }

                long filterV = Long.parseLong(filter.getValue().toString().replace("/", ""));
                if (filter.getType() == NumberOperator.Type.EQUALS) {
                    if (!Objects.equals(itemV, filterV)) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
                    if (Objects.equals(itemV, filterV)) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
                    if (itemV <= filterV) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
                    if (itemV < filterV) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
                    if (itemV >= filterV) {
                        remove.add(item);
                    }
                } else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
                    if (itemV > filterV) {
                        remove.add(item);
                    }
                }
            }
        }
        newData.removeAll(remove);
        columnCount++;
    }

    private void filterBoolColumn(ObservableList<LetterFull> newData) {
        final List<LetterFull> remove = new ArrayList<>();
        final ObservableList<BooleanOperator> filters;
        try {
            filters = ((FilterableBooleanTableColumn) tableView.getColumns().get(columnCount)).getFilters();
        } catch (Exception e) {
            return;
        }
        for (BooleanOperator filter : filters) {
            for (LetterFull item : newData) {
                boolean itemV = getBoolValue(item);

                if (filter.getType() == BooleanOperator.Type.TRUE) {
                    if (!itemV) {
                        remove.add(item);
                    }
                } else if (filter.getType() == BooleanOperator.Type.FALSE) {
                    if (itemV) {
                        remove.add(item);
                    }
                }
            }
        }
        newData.removeAll(remove);
        columnCount++;

    }

    private long getDateValue(LetterFull item) {
        String st = "";
        switch (tableView.getColumns().get(columnCount).getText()) {
            case "تاریخ نامه":
                st = item.getLetterDate();
                break;
            case "تاریخ رسید نامه":
                st = item.getReceiveDate();
                break;
        }
        try {
            return Long.parseLong(st.replace("/", ""));
        } catch (Exception e) {
            return -1;
        }
    }

    private String getStringValue(LetterFull item) {
        String st = "";
        switch (tableView.getColumns().get(columnCount).getText()) {
            case "شماره نامه":
                st = item.getLetterNumber();
                break;
            case "موضوع نامه":
                st = item.getSubject();
                break;
            case "فرستنده":
                st = item.getFrom();
                break;
            case "گیرنده":
                st = item.getTo();
                break;
            case "اصل":
                st = item.getOrginal();
                break;
            case "رونوشت":
                st = item.getCopies();
                break;
        }
        try {
            return st;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean getBoolValue(LetterFull item) {
        boolean st = false;
        switch (tableView.getColumns().get(columnCount).getText()) {
            case "بسته شده":
                st = item.getClosed();
                break;
            case "پیوست":
                st = item.getAnnex();
                break;
        }
        return st;
    }

    private int getIntValue(LetterFull item) {
        int st = -1;
        switch (tableView.getColumns().get(columnCount).getText()) {
            case "اندیکاتور":
                st = item.getId();
                break;
        }
        return st;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
