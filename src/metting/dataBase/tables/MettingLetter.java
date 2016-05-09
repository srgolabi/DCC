package metting.dataBase.tables;

import dccletter.dataBase.tables.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "mettingLetter")
public class MettingLetter {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "metting_id")
    private Mettings metting_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "letter_id")
    private Letters3 letter_id;

    public MettingLetter() {

    }

    public MettingLetter(Mettings metting_id, Letters3 letter_id) {
        this.metting_id = metting_id;
        this.letter_id = letter_id;
    }

    public MettingLetter(Mettings metting_id, String number) {
        this.metting_id = metting_id;
        this.letter_id = new Letters3(number);
    }

    public String getLetterNumber() {
        return letter_id.getLetterNumber();
    }

    public boolean getRemoveButton() {
        return true;
    }
    
////--------------------------------------------------------------------------------------------------------------------
////------------------------------------------------------------------------------------------------------------------
////------------------------------------------------------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public Mettings getMetting_id() {
        return metting_id;
    }

    public Letters3 getLetter_id() {
        return letter_id;
    }

    public void setMetting_id(Mettings metting_id) {
        this.metting_id = metting_id;
    }

}
