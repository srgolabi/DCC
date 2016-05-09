/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "history")
public class History {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private int year;

    @DatabaseField
    private int month;

    @DatabaseField
    private int day;

    @DatabaseField(unique = true)
    private String date;

    public History() {
    }

    public History(String year, String month, String day) {
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
        this.date = year + "/" + month + "/" + day;
    }

// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
