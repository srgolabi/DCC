package dccletter.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "searchfields")
public class SearchFields {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String title;
    @DatabaseField
    private boolean active;
    @DatabaseField
    private String permissionLevel;
    @DatabaseField
    private String columnName;
    @DatabaseField
    private String useForm;
    @DatabaseField
    private String type;

    public SearchFields() {
    }

    public SearchFields(Integer id, String title, boolean active, String permissionLevel, String columnName, String useForm, String type) {
        this.id = id;
        this.title = title;
        this.active = active;
        this.permissionLevel = permissionLevel;
        this.columnName = columnName;
        this.useForm = useForm;
        this.type = type;
    }
    
// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setTableName(String table) {
        this.useForm = table;
    }

    public void setType(String type) {
        this.type = type;
    }

}
