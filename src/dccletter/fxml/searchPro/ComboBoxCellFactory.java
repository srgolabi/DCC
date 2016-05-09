/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.fxml.searchPro;

import dccletter.dataBase.tables.SearchFields;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author reza
 */
public class ComboBoxCellFactory<T> extends StringConverter<T> implements Callback<ListView<T>, ListCell<T>> {

    public ComboBoxCellFactory(ComboBox<T> comboBox , String... str) {
        comboBox.setCellFactory(this);
        comboBox.setConverter(this);
        
    }

    @Override
    public ListCell<T> call(ListView<T> param) {
        return new ListCell<T>() {

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else if (item instanceof AbsrtactComboBoxValue) {
                    setText(((AbsrtactComboBoxValue) item).txt);
                } else if (item instanceof SearchFields) {
                    setText(((SearchFields) item).getTitle());
                }
            }
        };
    }

    @Override
    public String toString(T object) {
        if (object == null) {
            return null;
        } else if (object instanceof AbsrtactComboBoxValue) {
            return (((AbsrtactComboBoxValue) object).txt);
        } else if (object instanceof SearchFields) {
            return(((SearchFields) object).getTitle());
        }
        return "";
    }

    @Override
    public T fromString(String string) {
        return null;
    }

}
