/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "letterFile")
public class LetterFile {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String address;

    @DatabaseField(defaultValue = "")
    private String name;

    @DatabaseField(defaultValue = "")
    private String title;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "letter_id")
    private Letters3 letterId;

    @DatabaseField(defaultValue = "0")
    private int sortOrder;

    public LetterFile() {
    }

    public LetterFile(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public LetterFile getThisFile() {
        return this;
    }

    /*
     ***************************************************************************
     ***************************************************************************
     ***************************************************************************
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Letters3 getLetterId() {
        return letterId;
    }

    public void setLetterId(Letters3 letterId) {
        this.letterId = letterId;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
