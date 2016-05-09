package dccletter.fxml.letterTableFull;

import static dccletter.DCCLetter.databaseHelper;
import dccletter.dataBase.tables.LetterFull;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
public class FXMLLetterTableController implements Initializable {

    public interface OnSelectItemTable {

        void selected(LetterFull letters);
    }

    public interface OnChangeTableItems {

        void changed(String num);
    }

    private int columnCount = 0;
    private ObservableList<LetterFull> personList;

    private OnSelectItemTable onSelectItemTable;
    private OnChangeTableItems onChangeTableItems;

    @FXML
    public TableView<LetterFull> tableView;
    @FXML
    private TextField textField;
    @FXML
    private Button editMarkButton;

    /**
     * Initializes the controller class.
     */
    public void init(String query) {
        personList = FXCollections.observableArrayList(databaseHelper.letterFullDao.rawResults(query));
        tableView.setFixedCellSize(35d);
        tableView.setRowFactory(tv -> new TableRow<LetterFull>() {
            @Override
            protected void updateItem(LetterFull item, boolean empty) {
                super.updateItem(item, empty);
                setOnMouseClicked((MouseEvent event) -> {
                    if (item != null && event.getClickCount() >= 2) {
                        onSelectItemTable.selected(item);
                    }
                });
            }
        });

        prepareTable();
    }

    public String getSum() {
        return tableView.getItems().size() + "";
    }

    public void setOnSelectItemTable(OnSelectItemTable osit) {
        this.onSelectItemTable = osit;
    }

    public void setOnChangeTableItems(OnChangeTableItems octi) {
        this.onChangeTableItems = octi;
    }

    private void prepareTable() {
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tableFiltered(textFiltered(newValue));
        });

        tableView.addEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, (ColumnFilterEvent event) -> {
            tableFiltered(textFiltered(textField.getText()));
        });

        tableView.getItems().setAll(personList);
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
//        filterIntColumn(newData);
        filterBoolColumn(newData);
        filterBoolColumn(newData);
        filterDateColumn(newData);
//        filterDateColumn(newData);
//        columnCount++;
//        filterStringColumn(newData);
        columnCount++;
        tableView.getItems().setAll(newData);
        onChangeTableItems.changed(tableView.getItems().size() + "");
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
