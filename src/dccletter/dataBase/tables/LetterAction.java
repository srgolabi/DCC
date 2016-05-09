/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.dataBase.tables;

import agtp.dataBase.tables.Base;
import agtp.dataBase.tables.Users2;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "letteraction")
public class LetterAction extends Base {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "letter_id")
    private Letters3 letterId;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "action_id")
    private Users2 actionId;

    @DatabaseField(defaultValue = "0")
    private int sortOrder;

    public LetterAction() {
    }

    public LetterAction(Letters3 letterId, Users2 actionId) {
        this.letterId = letterId;
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionId.getName_fa();
    }

    public String getNameBaSemat() {
        return actionId.getName_fa() + (actionId.getSemat().equals("") ? "" : "  (" + actionId.getSemat() + ")");
    }

    public LetterAction getThis() {
        return this;
    }

    @Override
    public String getCulomnValue() {
        return actionId.getNameBaSemat();
    }

    @Override
    public String getToolTipValue() {
        return actionId.getNameBaSemat();
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

    public Users2 getActionId() {
        return actionId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLetterId(Letters3 letterId) {
        this.letterId = letterId;
    }

    public void setActionId(Users2 actionId) {
        this.actionId = actionId;
    }

}
