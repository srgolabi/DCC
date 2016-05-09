package metting.dataBase.tables;

import agtp.dataBase.tables.Companies;
import dccletter.dataBase.tables.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "mettingCompany")
public class MettingCompany {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "metting_id")
    private Mettings metting_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "company_id")
    private Companies company_id;

    public MettingCompany() {

    }

    public MettingCompany(Mettings metting_id, Companies company_id) {
        this.metting_id = metting_id;
        this.company_id = company_id;
    }

////----------------------------------------------------------------------------
////----------------------------------------------------------------------------
////----------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public Mettings getMetting_id() {
        return metting_id;
    }

    public Companies getCompany_id() {
        return company_id;
    }

}
