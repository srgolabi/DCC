/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.fxml.searchPro;

import dccletter.dataBase.tables.SearchFields;

/**
 *
 * @author reza
 */
public class RowTable {

    private final SearchFields searchFields;
    private final AbsrtactComboBoxValue operator;
    private final AbsrtactComboBoxValue relation;
    private final AbsrtactComboBoxValue value;

    public RowTable(SearchFields searchFields, AbsrtactComboBoxValue operator, AbsrtactComboBoxValue value, AbsrtactComboBoxValue relation) {
        this.searchFields = searchFields;
        this.value = value;
        this.operator = operator;
        this.relation = relation;
    }

    public boolean isRemoveAction() {
        return true;
    }

    public SearchFields getSearchFields() {
        return searchFields;
    }

    public String getTitle() {
        return searchFields.getTitle();
    }

    public String getColumnName() {
        return searchFields.getColumnName();
    }

    public AbsrtactComboBoxValue getValueColumn() {
        return value;
    }

    public AbsrtactComboBoxValue getOperatorInit() {
        return operator;
    }

    public AbsrtactComboBoxValue getRelationColumn() {
        return relation;
    }

    public String getTxtField() {
        return searchFields.getTitle();
    }

    public String getTxtOperator() {
        return operator.txt;
    }

    public String getOperator() {
        return operator.value;
    }

    public String getTxtValue() {
        return value.txt;
    }

    public String getValue() {
        return value.value;
    }

    public String getTxtReletion() {
        return relation.txt;
    }

    public String getReletion() {
        return relation.value;
    }
}
