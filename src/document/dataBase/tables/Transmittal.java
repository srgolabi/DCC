package document.dataBase.tables;

import agtp.dataBase.tables.Companies;
import agtp.dataBase.tables.History;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "transmittal")
public class Transmittal {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "transmittal_date_id")
    private History transmittal_date_id;

    @DatabaseField(defaultValue = "")
    private String att;

    @DatabaseField(defaultValue = "")
    private String tranc_number;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "companies_id")
    private Companies companies_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "discipline_id")
    private Definition discipline_id;

    @DatabaseField(defaultValue = "true")
    private Boolean isIncoming;

    public Transmittal() {
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public Integer getId() {
        return id;
    }

    public History getTransmittal_date() {
        return transmittal_date_id;
    }

    public void setIsIncoming(Boolean isIncoming) {
        this.isIncoming = isIncoming;
    }

    public Boolean getIsIncoming() {
        return isIncoming;
    }

    public String getAtt() {
        return att;
    }

    public String getTranc_number() {
        return tranc_number;
    }

    public Companies getFrom() {
        return companies_id;
    }

    public Definition getDiscipline() {
        return discipline_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTransmittal_date(History transmittal_date) {
        this.transmittal_date_id = transmittal_date;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public void setTranc_number(String tranc_number) {
        this.tranc_number = tranc_number;
    }

    public void setFrom(Companies from) {
        this.companies_id = from;
    }

    public void setDiscipline(Definition discipline) {
        this.discipline_id = discipline;
    }

}
