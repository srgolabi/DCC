package dccletter.dataBase.tables;

import agtp.dataBase.tables.Companies;
import agtp.dataBase.tables.History;
import agtp.dataBase.tables.Users2;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dccletter.DCCLetter;
import java.util.List;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "letters3")
public class Letters3 {

    public static String spliter_A = "分"; // Minute
    public static String spliter_B = "離"; // from

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String subject;

    @DatabaseField(defaultValue = "")
    private String letterNumber;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "letter_date_id")
    private History letter_date;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "receive_date_id")
    private History receive_date;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "from_id")
    private Companies from;

    @DatabaseField(defaultValue = "false")
    private Boolean annex;

    @DatabaseField(defaultValue = "false")
    private Boolean closed;

//    @ForeignCollectionField
//    private Collection<Files> fileList;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "notification_id")
    private Users2 notification;

    /*
     mesal : 1分2分50分14:50:32分離5分6分11分10:10:02分離
     ba spliter_b(離) eghdamati ke userha roye name anjam dadehand moshakhas mishavad
     ba spliter_a(分 , meghdar1) useri ke roye name eghdam anjam dadeh moshakhas mishavad
     ba spliter_a(分 , meghdar2) kari ke user roye name anjam dadeh moshakhas mishavad
     ba spliter_a(分 , meghdar3) tarikhi ke user roye name eghdam anjam dadeh moshakhas mishavad
     ba spliter_a(分 , meghdar4) zamani ke user roye name eghdam anjam dadeh moshakhas mishavad
     */
    @DatabaseField(defaultValue = "") //ba 分. avali ferestande name ast baghie girandegan name hatand
    private String logs;

    public Letters3() {
    }

    public Letters3(Integer id) {
        this.id = id;
    }

    public Letters3(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public void addToLog(int userId, int actionId, int historyuId, String hour) {
        if (logs == null) {
            logs = "";
        }
        logs = logs + userId + spliter_A + actionId + spliter_A + historyuId + spliter_A + hour + spliter_A + spliter_B;
    }

    public String getFerestande() {
        if (from == null) {
            return "";
        } else {
            return from.getCompany_fa();
        }
    }

    public String getGirande() {
        List<LetterToCompany> list = DCCLetter.databaseHelper.letterToCompanyDao.getAll("letter_id", id);
        if (list.isEmpty()) {
            return "";
        } else {
            String str = "";
            for (LetterToCompany ltc : list) {
                str = str + ltc.getCompany_fa() + " ; ";
            }
            return str;
        }
    }

    public String getDastordande() {
        if (notification == null) {
            return "";
        } else {
            return notification.getName_fa();
        }
    }

    public String getTarikhname() {
        if (letter_date == null) {
            return "";
        } else {
            return letter_date.getDate();
        }
    }

    /*
     ***************************************************************************
     ***************************************************************************
     ***************************************************************************
     */
    public Boolean isActive() {
        return from == null;
    }

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getLetterNumber() {
        return letterNumber;
    }

    public History getLetter_date() {
        return letter_date;
    }

    public History getReceive_date() {
        return receive_date;
    }

    public Companies getFrom() {
        return from;
    }

    public Boolean getAnnex() {
        return annex;
    }

    public Boolean getClosed() {
        return closed;
    }

//    public Collection<Files> getFileList() {
//        return fileList;
//    }
    public Users2 getNotification() {
        return notification;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public void setLetter_date(History letter_date) {
        this.letter_date = letter_date;
    }

    public void setReceive_date(History receive_date) {
        this.receive_date = receive_date;
    }

    public void setFrom(Companies from) {
        this.from = from;
    }

    public void setAnnex(Boolean annex) {
        this.annex = annex;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

//    public void setFileList(Collection<Files> fileList) {
//        this.fileList = fileList;
//    }
    public void setNotification(Users2 notification) {
        this.notification = notification;
    }

}
