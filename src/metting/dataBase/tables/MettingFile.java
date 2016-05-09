package metting.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "mettingFile")
public class MettingFile {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String address;

    @DatabaseField
    private String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "metting_id")
    private Mettings metting_id;

    public MettingFile() {
    }

    public MettingFile(String address, String name, Mettings metting_id) {
        this.address = address;
        this.name = name;
        this.metting_id = metting_id;
    }
////-----------------------------------------------------------------------------   
////-----------------------------------------------------------------------------   
////-----------------------------------------------------------------------------   

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Mettings getMetting_id() {
        return metting_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMetting_id(Mettings metting_id) {
        this.metting_id = metting_id;
    }

}
