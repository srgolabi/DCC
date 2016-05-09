/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.fxml.searchPro;

import javafx.scene.control.ComboBox;

/**
 *
 * @author reza
 */
public class AbsrtactComboBoxValue {

    public String txt;
    public String value;

    public AbsrtactComboBoxValue(String txt, String value) {
        this.txt = txt;
        this.value = value;
    }

    public static void init(ComboBox cb, String... str) {
        new ComboBoxCellFactory<AbsrtactComboBoxValue>(cb);
        addAll(cb, str);
    }

    public static void addAll(ComboBox cb, String... str) {
        cb.getItems().clear();
        for (int i = 0; i < str.length; i += 2) {
            cb.getItems().add(new AbsrtactComboBoxValue(str[i], str[i + 1]));
        }
        cb.getSelectionModel().select(0);
    }
}
