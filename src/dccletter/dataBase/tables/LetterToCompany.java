/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.dataBase.tables;

import agtp.dataBase.tables.Companies;
import agtp.dataBase.tables.Base;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "lettertocompany")
public class LetterToCompany extends Base {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "letter_id")
    private Letters3 letterId;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "company_id")
    private Companies companyId;

    @DatabaseField(defaultValue = "0")
    private int sortOrder;

    public LetterToCompany() {

    }

    public String getCompany_fa() {
        return companyId.getCompany_fa();
    }

    public LetterToCompany(Letters3 letterId, Companies companyId) {
        this.letterId = letterId;
        this.companyId = companyId;
    }

    public LetterToCompany getThis() {
        return this;
    }

    @Override
    public String getCulomnValue() {
        return getCompany_fa();
    }

    @Override
    public String getToolTipValue() {
        return getCompany_fa();
    }

// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Letters3 getLetterId() {
        return letterId;
    }

    public Companies getCompanyId() {
        return companyId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLetterId(Letters3 letterId) {
        this.letterId = letterId;
    }

    public void setCompanyId(Companies companyId) {
        this.companyId = companyId;
    }

}
