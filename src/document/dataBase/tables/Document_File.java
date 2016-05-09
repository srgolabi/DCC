/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "document_file")
public class Document_File {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String address;

    @DatabaseField(defaultValue = "")
    private String name;

    @DatabaseField(defaultValue = "")
    private String title;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "documents_rev_id")
    private Documents_Rev documents_rev_id;

    @DatabaseField(defaultValue = "0")
    private int sortOrder;

    public Document_File() {
    }

    public Document_File(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public Document_File getThisFile() {
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

    public Documents_Rev getDocuments_rev_id() {
        return documents_rev_id;
    }

    public void setDocuments_rev_id(Documents_Rev documents_rev_id) {
        this.documents_rev_id = documents_rev_id;
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
