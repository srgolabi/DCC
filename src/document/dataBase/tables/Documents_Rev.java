package document.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dccletter.dataBase.tables.LetterFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "documents_rev")
public class Documents_Rev {

    public List<LetterFile> files_LIST;
    public boolean should_Save = false;

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "document_id")
    private Document document_id;

    @DatabaseField(defaultValue = "")
    private String rev;

    @DatabaseField(defaultValue = "")
    private String title;

    @DatabaseField(defaultValue = "")
    private String clas;

    @DatabaseField(defaultValue = "0")
    private Integer pages;

    @DatabaseField(defaultValue = "0")
    private Integer copies;

    @DatabaseField(defaultValue = "")
    private String size;

    @DatabaseField(defaultValue = "")
    private String poi;

    @DatabaseField(defaultValue = "false")
    private Boolean voiid;

    public Documents_Rev() {
        files_LIST = new ArrayList<>();
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public Integer getId() {
        return id;
    }

    public Document getDocument_id() {
        return document_id;
    }

    public String getRev() {
        return rev;
    }

    public String getTitle() {
        return title;
    }

    public String getClas() {
        return clas;
    }

    public Integer getPages() {
        return pages;
    }

    public Integer getCopies() {
        return copies;
    }

    public String getSize() {
        return size;
    }

    public String getPoi() {
        return poi;
    }

    public Boolean getVoiid() {
        return voiid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDocument_id(Document document_id) {
        this.document_id = document_id;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public void setVoiid(Boolean voiid) {
        this.voiid = voiid;
    }

}
