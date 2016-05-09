package dccletter.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "letterFull")
public class LetterFull {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField
    private String letterDate;

    @DatabaseField
    private String letterNumber;

    @DatabaseField
    private String subject;

    @DatabaseField
    private String from;

    @DatabaseField
    private String to;

    @DatabaseField
    private String orginal;

    @DatabaseField
    private String copies;

    @DatabaseField
    private Boolean annex;

    @DatabaseField
    private Boolean closed;

    @DatabaseField
    private String receiveDate;

    public LetterFull() {
    }

// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public String getLetterDate() {
        return letterDate;
    }

    public String getLetterNumber() {
        return letterNumber;
    }

    public String getSubject() {
        return subject;
    }

    public String getFrom() {
        return from == null ? "" : from;
    }

    public String getTo() {
        return to == null ? "" : to;
    }

    public String getOrginal() {
        return orginal == null ? "" : orginal;
    }

    public String getCopies() {
        return copies == null ? "" : copies;
    }

    public Boolean getAnnex() {
        return annex;
    }

    public Boolean getClosed() {
        return closed;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

}
