package document.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "document")
public class Document {

    public boolean should_Save = false;

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String documentNo;

    @DatabaseField(defaultValue = "")
    private String project_no;

    @DatabaseField(defaultValue = "")
    private String phase_code;

    @DatabaseField(defaultValue = "")
    private String area_number;

    @DatabaseField(defaultValue = "")
    private String unit_number;

    @DatabaseField(defaultValue = "")
    private String document_type;

    @DatabaseField(defaultValue = "")
    private String discipline_code;

    @DatabaseField(defaultValue = "")
    private String sequential_no;

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public Integer getId() {
        return id;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public String getProject_no() {
        return project_no;
    }

    public String getPhase_code() {
        return phase_code;
    }

    public String getArea_number() {
        return area_number;
    }

    public String getUnit_number() {
        return unit_number;
    }

    public String getDocument_type() {
        return document_type;
    }

    public String getDiscipline_code() {
        return discipline_code;
    }

    public String getSequential_no() {
        return sequential_no;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public void setPhase_code(String phase_code) {
        this.phase_code = phase_code;
    }

    public void setArea_number(String area_number) {
        this.area_number = area_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public void setDiscipline_code(String discipline_code) {
        this.discipline_code = discipline_code;
    }

    public void setSequential_no(String sequential_no) {
        this.sequential_no = sequential_no;
    }

}
