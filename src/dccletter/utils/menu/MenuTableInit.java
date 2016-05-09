/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils.menu;

import agtp.dataBase.tables.Users2;
import agtp.myControl.tableView.MyColumnTable;
import static dccletter.DCCLetter.databaseHelper;
import agtp.dataBase.tables.Companies;
//import dccletter.fxml.letterInsert.RemoveButtonCellFactory;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Cursor;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author reza
 */
public class MenuTableInit {

    public static void actionsInit(String query, TextField textField, TableView<Users2> tableView) {
        MyColumnTable<Users2> removeButtonCellFactory = new MyColumnTable<Users2>(null, Cursor.DEFAULT);
        removeButtonCellFactory.setOnAddToMenu((Users2 s) -> {
            textField.setText(s.getName_fa());
        });
        FilteredList<Users2> filteredlist = removeButtonCellFactory.init(
                tableView, textField, databaseHelper.users2Dao.rawResults(query));

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getName_fa().contains(newValue));
            tableView.setPrefHeight(
                    tableView.getFixedCellSize() * (tableView.getItems().size() > 5 ? 5 : tableView.getItems().size()) + 4
            );
        });
        tableView.getSelectionModel().select(0);

    }

    public static void companiesInit(String query, TextField textField, TableView<Companies> tableView) {
        MyColumnTable<Companies> removeButtonCellFactory = new MyColumnTable<>(null, Cursor.DEFAULT);
        removeButtonCellFactory.setOnAddToMenu((Companies s) -> {
            textField.setText(s.getCompany_fa());
        });
        FilteredList<Companies> filteredlist = removeButtonCellFactory.init(
                tableView, textField, databaseHelper.companiesDao.rawResults(query));

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : s.getCompany_fa().contains(newValue));
            tableView.setPrefHeight(
                    tableView.getFixedCellSize() * (tableView.getItems().size() > 5 ? 5 : tableView.getItems().size()) + 4
            );

        });
        tableView.getSelectionModel().select(0);
    }

    public static void companiesInit(String query, TextField textField, TableView<Companies> tableView, boolean is_EN) {
        MyColumnTable<Companies> removeButtonCellFactory = new MyColumnTable<>(null, Cursor.DEFAULT);
        removeButtonCellFactory.setOnAddToMenu((Companies s) -> {
            textField.setText(is_EN ? s.getCompany_en() : s.getCompany_fa());

        });
        FilteredList<Companies> filteredlist = removeButtonCellFactory.init(
                tableView, textField, databaseHelper.companiesDao.rawResults(query));

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0)
                    ? true : is_EN ? s.getCompany_en().toLowerCase().contains(newValue.toLowerCase()) : s.getCompany_fa().toLowerCase().contains(newValue.toLowerCase()));

            tableView.setPrefHeight(
                    tableView.getFixedCellSize() * (tableView.getItems().size() > 5 ? 5 : tableView.getItems().size()) + 4
            );
        });
        tableView.getSelectionModel().select(0);
    }

    public static void disciplineInit(String query, TextField textField, TableView<Users2> tableView, boolean is_EN) {
        MyColumnTable<Users2> removeButtonCellFactory = new MyColumnTable<>(null, Cursor.DEFAULT);
        removeButtonCellFactory.is_EN = is_EN;
        removeButtonCellFactory.setOnAddToMenu((Users2 s) -> {
            textField.setText(is_EN ? s.getName_en() : s.getName_fa());
        });
        FilteredList<Users2> filteredlist = removeButtonCellFactory.init(
                //                tableView, textField, databaseHelper.groupsDao.rawResults(query));
                tableView, textField, databaseHelper.users2Dao.rawResults(query));

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredlist.setPredicate(s -> (newValue == null || newValue.length() == 0)
                    ? true : is_EN ? s.getName_en().toLowerCase().contains(newValue.toLowerCase()) : s.getName_fa().toLowerCase().contains(newValue.toLowerCase()));
            tableView.setPrefHeight(
                    tableView.getFixedCellSize() * (tableView.getItems().size() > 5 ? 5 : tableView.getItems().size()) + 4
            );
        });
        tableView.getSelectionModel().select(0);
    }

}
