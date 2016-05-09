package agtp.myControl.tableView;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author reza
 */
public class Temmp<S, Z, W> implements EventHandler<KeyEvent>, Callback<Z, W> {

    public interface Delete<S> {

        void add(S s);
    }
    
    public interface AddToMenu<S>{
        void action(S s);
    }

    public void setOnDelete(Delete<S> d) {
        delete = d;
    }
    public void setOnAddToMenu(AddToMenu<S> atm) {
        addToMenu = atm;
    }
    public Delete<S> delete;
    public AddToMenu<S> addToMenu;
    public TableView<S> tableViewMenu;
    TextField field;
    SimpleBooleanProperty visible = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty visibleB = new SimpleBooleanProperty(true);

    public FilteredList<S> init(TableView<S> tableViewMenu, TextField field, List<S> list) {
        this.tableViewMenu = tableViewMenu;
        this.field = field;
        visible.bind((field.focusedProperty().or(tableViewMenu.focusedProperty())).and(visibleB));
        tableViewMenu.visibleProperty().bind(visible);
        tableViewMenu.setOnKeyPressed(this);

        ObservableList<S> observableList = FXCollections.observableArrayList(list);

        FilteredList<S> filteredlist = new FilteredList<>(observableList, p -> true);

        field.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                visibleB.set(true);
            }
        });
        field.setOnMouseClicked((MouseEvent event) -> {
            visibleB.set(true);
        });
        field.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ALT:
                    event.consume();
                    break;
                case DOWN:
                    if (tableViewMenu.getItems().size() != 0) {
                        visibleB.set(true);
                        tableViewMenu.getSelectionModel().select(0);
                        tableViewMenu.requestFocus();
                    }
                    break;
                case ESCAPE:
                    visibleB.set(false);
                    break;
                default:
                    visibleB.set(true);

            }
        });

        SortedList<S> sortedData = new SortedList<>(filteredlist);
        sortedData.comparatorProperty().bind(tableViewMenu.comparatorProperty());
        tableViewMenu.setItems(sortedData);

        ArrayList<TableColumn<S, ?>> sortOrder = new ArrayList<>(tableViewMenu.getColumns());
        tableViewMenu.getSortOrder().clear();
        tableViewMenu.getSortOrder().addAll(sortOrder);
        return filteredlist;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                if (tableViewMenu.getSelectionModel().getSelectedIndex() != -1) {
                    field.setText("");
                    field.requestFocus();
                    visibleB.set(false);
                }
                break;
            case UP:
                if (tableViewMenu.getSelectionModel().getSelectedIndex() <= 0) {
                    field.requestFocus();
                    visibleB.set(false);
                }
                break;
            case ESCAPE:
                field.requestFocus();
                visibleB.set(false);
                break;
        }
    }

    @Override
    public W call(Z param) {

        return null;
    }

}
