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
@DatabaseTable(tableName = "trans_doc")
public class Trans_Doc {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "documents_rev_id")
    private Documents_Rev documents_rev_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "transmittal_id")
    private Transmittal transmittal_id;

    @DatabaseField(defaultValue = "0")
    private int sortOrder;

    public Trans_Doc() {
    }

    public Trans_Doc(Documents_Rev document, Transmittal transmittal) {
        this.documents_rev_id = document;
        this.transmittal_id = transmittal;
    }

    /*
     ***************************************************************************
     ***************************************************************************
     ***************************************************************************
     */

    public Integer getId() {
        return id;
    }

    public Documents_Rev getDocuments_rev_id() {
        return documents_rev_id;
    }

    public Transmittal getTransmittal_id() {
        return transmittal_id;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDocuments_rev_id(Documents_Rev documents_rev_id) {
        this.documents_rev_id = documents_rev_id;
    }

    public void setTransmittal_id(Transmittal transmittal_id) {
        this.transmittal_id = transmittal_id;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    
}
