/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import static dccletter.DCCLetter.databaseHelper;
import agtp.dataBase.tables.History;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

/**
 *
 * @author reza
 */
public class MyTime {

    private TextField year;
    private TextField mount;
    private TextField day;

    public MyTime() {
    }

    public MyTime(TextField year, TextField mount, TextField day) {
        this.year = year;
        this.mount = mount;
        this.day = day;
    }

    public void setOrdert(Control c1, Control c2) {
        TextFiledLimited.setNumberAndLengthLimit(year, 2, mount, c1);
        TextFiledLimited.setNumberAndLengthLimit(mount, 2, day, year);
        TextFiledLimited.setNumberAndLengthLimit(day, 2, c2, mount);
    }

    public void setDate(History history) {
        if (history != null) {
            year.setText(addFirstZero(history.getYear()));
            mount.setText(addFirstZero(history.getMonth()));
            day.setText(addFirstZero(history.getDay()));
        }
    }

    private String addFirstZero(int field) {
        return field > 9 ? field + "" : "0" + field;
    }

    public boolean isFull() {
        if (getTxt(year) || getTxt(mount) || getTxt(day)) {
            return false;
        }
        return true;
    }

    private boolean getTxt(TextField field) {
        return field.getText().trim().equals("");
    }

    public History writeAndGet() {
        String tempHistory = year.getText() + "/" + mount.getText() + "/" + day.getText();
        History letterHistory = databaseHelper.historyDao.getFirst("date", tempHistory);
        if (letterHistory == null) {
            letterHistory = new History(year.getText(), mount.getText(), day.getText());
            databaseHelper.historyDao.create(letterHistory);
        }
        return letterHistory;
    }

    public History writeAndGetNow() {
        PersianCalendar calendar = new PersianCalendar();
        String yaer = calendar.year2dig();
        String month = calendar.month().length() == 2 ? calendar.month() : "0" + calendar.month();
        String day = calendar.day().length() == 2 ? calendar.day() : "0" + calendar.day();

        String tempHistory = yaer + "/" + month + "/" + day;
        System.out.println("a = " + tempHistory);
        History letterHistory = databaseHelper.historyDao.getFirst("date", tempHistory);
        if (letterHistory == null) {
            System.out.println("hooooooooooooooooooooooooo");
            letterHistory = new History(yaer, month, day);
            databaseHelper.historyDao.createOrUpdate(letterHistory);
        }
        return letterHistory;
    }

    public String getHourNow() {
        PersianCalendar calendar = new PersianCalendar();
        return calendar.hour() + ":" + calendar.minute() + ":" + calendar.second();
    }
}
