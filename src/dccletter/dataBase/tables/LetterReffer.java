package dccletter.dataBase.tables;

import agtp.dataBase.tables.Base;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "letterreffer")
public class LetterReffer extends Base {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "letter_id")
    private Letters3 letterId;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "reffer_id")
    private Letters3 refferId;

    @DatabaseField(defaultValue = "0")
    private int sortOrder;

    public LetterReffer() {
    }

    public LetterReffer getThis() {
        return this;
    }

    @Override
    public String getCulomnValue() {
        return refferId.getLetterNumber();
    }

    @Override
    public String getStyle() {
        if (refferId.getFrom() == null) {
            return "-fx-background-color: -fx-table-cell-border-color, #FFEB78; -fx-border-color: transparent transparent #E4E4E4 transparent;";
        } else {
            return super.getStyle();
        }
    }

    @Override
    public String getToolTipValue() {
        if (refferId.getFrom() == null) {
            return refferId.getLetterNumber();
        } else {
            return refferId.getLetterNumber() + " - از " + refferId.getFrom().getCompany_fa();
        }
    }

    public LetterReffer(Letters3 letterId, Letters3 refferId) {
        this.letterId = letterId;
        this.refferId = refferId;
    }

    public LetterReffer(Letters3 letterId, String number) {
        this.letterId = letterId;
        this.refferId = new Letters3(number);
    }

    public Boolean isActive() {
        return refferId.getFrom() != null;
    }

    public String getLetterNumber() {
        return refferId.getLetterNumber();
    }

    public String getAnswerNumber() {
        return letterId.getLetterNumber();
    }

    public Integer getId() {
        return id;
    }

    public Letters3 getLetterId() {
        return letterId;
    }

    public Letters3 getRefferId() {
        return refferId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLetterId(Letters3 letterId) {
        this.letterId = letterId;
    }

    public void setRefferId(Letters3 refferId) {
        this.refferId = refferId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

}
