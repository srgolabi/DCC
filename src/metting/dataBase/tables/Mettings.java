package metting.dataBase.tables;

import agtp.dataBase.tables.History;
import agtp.dataBase.tables.Users2;
import dccletter.dataBase.tables.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Collection;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "mettings")
public class Mettings {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String subject;

    @DatabaseField(defaultValue = "")
    private String mettingNumber;
    
    @DatabaseField(defaultValue = "")
    private String audience;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "metting_date_id")
    private History metting_date_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true , columnName = "receive_date_id")
    private History receive_date_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "insert_date_id")
    private History insert_date_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private Users2 user_id;

    @DatabaseField(defaultValue = "")
    private String letterHour;

    @DatabaseField(defaultValue = "0")
    private Integer pageNumber;

    @ForeignCollectionField
    private Collection<MettingFile> fileList;

    public Mettings() {
    }

////-----------------------------------------------------------------------------   
////-----------------------------------------------------------------------------   
////-----------------------------------------------------------------------------   
    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getMittingNumber() {
        return mettingNumber;
    }

    public History getMetting_date_id() {
        return metting_date_id;
    }

    public History getReceive_date_id() {
        return receive_date_id;
    }

    public History getInsert_date_id() {
        return insert_date_id;
    }

    public Users2 getUser_id() {
        return user_id;
    }

    public String getLetterHour() {
        return letterHour;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Collection<MettingFile> getFileList() {
        return fileList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMettingNumber(String mettingNumber) {
        this.mettingNumber = mettingNumber;
    }

    public void setMetting_date_id(History mitting_date) {
        this.metting_date_id = mitting_date;
    }

    public void setReceive_date_id(History receive_date) {
        this.receive_date_id = receive_date;
    }

    public void setInsert_date_id(History insert_date) {
        this.insert_date_id = insert_date;
    }

    public void setUser_id(Users2 user) {
        this.user_id = user;
    }

    public void setLetterHour(String letterHour) {
        this.letterHour = letterHour;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setFileList(Collection<MettingFile> fileList) {
        this.fileList = fileList;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getMettingNumber() {
        return mettingNumber;
    }

    public String getAudience() {
        return audience;
    }

}
