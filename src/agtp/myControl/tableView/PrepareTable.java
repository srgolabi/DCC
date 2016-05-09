/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.myControl.tableView;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author reza
 */
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
