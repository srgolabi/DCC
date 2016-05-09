package document.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "definition")
public class Definition {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String title;

    @DatabaseField(defaultValue = "")
    private String description;

    @DatabaseField(defaultValue = "")
    private String key;

    public Definition() {
    }

    public Definition(String title, String description, String key) {
        this.title = title;
        this.description = description;
        this.key = key;
    }
    
////-----------------------------------------------------------------------------   
////-----------------------------------------------------------------------------   
////-----------------------------------------------------------------------------   

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
