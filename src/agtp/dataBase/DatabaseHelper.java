package agtp.dataBase;

import agtp.dataBase.tables.Permission;
import agtp.dataBase.tables.UserGroup;
import agtp.dataBase.tables.UserPermission;
import agtp.dataBase.tables.Users2;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import static dccletter.DCCLetter.server;
import agtp.dataBase.tables.Companies;
import dccletter.dataBase.tables.LetterFile;
import agtp.dataBase.tables.History;
import dccletter.dataBase.tables.LetterAction;
import dccletter.dataBase.tables.LetterFull;
import dccletter.dataBase.tables.LetterReffer;
import dccletter.dataBase.tables.LetterToCompany;
import agtp.dataBase.tables.Manage;
import dccletter.dataBase.tables.SearchFields;
import dccletter.dataBase.tables.Letters3;
import document.dataBase.tables.Definition;
import document.dataBase.tables.Document;
import document.dataBase.tables.Documents_Rev;
import document.dataBase.tables.Transmittal;
import document.dataBase.tables.Trans_Doc;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import metting.dataBase.tables.MettingCompany;
import metting.dataBase.tables.MettingLetter;
import metting.dataBase.tables.Mettings;

/**
 *
 * @author reza
 */
public class DatabaseHelper {

    public BaseRepo<Users2, Integer> users2Dao;

    public BaseRepo<Companies, Integer> companiesDao;
    public BaseRepo<LetterFile, Integer> letterFileDao;
    public BaseRepo<History, Integer> historyDao;
    public BaseRepo<LetterAction, Integer> letterActionDao;
    public BaseRepo<LetterToCompany, Integer> letterToCompanyDao;
    public BaseRepo<LetterReffer, Integer> letterRefferDao;
    public BaseRepo<Letters3, Integer> letters3Dao;
    public BaseRepo<LetterFull, Integer> letterFullDao;
    public BaseRepo<SearchFields, Integer> searchFieldsDao;
    public BaseRepo<Manage, Integer> manageDao;

    public BaseRepo<metting.dataBase.tables.MettingFile, Integer> mettingfilesDao;
    public BaseRepo<MettingCompany, Integer> MettingCompanyDao;
    public BaseRepo<MettingLetter, Integer> MettingLetterDao;
    public BaseRepo<Mettings, Integer> MettingsDao;

    public BaseRepo<Definition, Integer> definitionCodeDao;
    public BaseRepo<Documents_Rev, Integer> documents_REVDao;
    public BaseRepo<Document, Integer> documentsDao;
    public BaseRepo<Trans_Doc, Integer> transmittalDocument;
    public BaseRepo<Transmittal, Integer> transmittalDao;
    public BaseRepo<Permission, Integer> permissionDao;
    public BaseRepo<UserPermission, Integer> userPermissionDao;
    public BaseRepo<UserGroup, Integer> userGroupDao;

    public ConnectionSource connectionDb;

    public DatabaseHelper() {
        try {
//            String gateBassDBurl = "jdbc:sqlite:mcb.db";
//            String gateBassDBurl = "jdbc:sqlite://DANESHJOO/Shearing/mcb.db";
            String gateBassDBurl = "jdbc:sqlite:" + server + "mcb.db";
            connectionDb = new JdbcConnectionSource(gateBassDBurl);

            manageDao = new BaseRepo<>(connectionDb, Manage.class);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init() {

            users2Dao = new BaseRepo<>(connectionDb, Users2.class);

            letterFileDao = new BaseRepo<>(connectionDb, LetterFile.class);
            letterToCompanyDao = new BaseRepo<>(connectionDb, LetterToCompany.class);
            letterRefferDao = new BaseRepo<>(connectionDb, LetterReffer.class);
            companiesDao = new BaseRepo<>(connectionDb, Companies.class);
            historyDao = new BaseRepo<>(connectionDb, History.class);
            letters3Dao = new BaseRepo<>(connectionDb, Letters3.class);
            letterActionDao = new BaseRepo<>(connectionDb, LetterAction.class);
            letterFullDao = new BaseRepo<>(connectionDb, LetterFull.class);
            searchFieldsDao = new BaseRepo<>(connectionDb, SearchFields.class);

            mettingfilesDao = new BaseRepo<>(connectionDb, metting.dataBase.tables.MettingFile.class);
            MettingCompanyDao = new BaseRepo<>(connectionDb, MettingCompany.class);
            MettingLetterDao = new BaseRepo<>(connectionDb, MettingLetter.class);
            MettingsDao = new BaseRepo<>(connectionDb, Mettings.class);

            definitionCodeDao = new BaseRepo<>(connectionDb, Definition.class);
            documents_REVDao = new BaseRepo<>(connectionDb, Documents_Rev.class);
            documentsDao = new BaseRepo<>(connectionDb, Document.class);
            transmittalDocument = new BaseRepo<>(connectionDb, Trans_Doc.class);
            transmittalDao = new BaseRepo<>(connectionDb, Transmittal.class);
            permissionDao = new BaseRepo<>(connectionDb, Permission.class);
            userPermissionDao = new BaseRepo<>(connectionDb, UserPermission.class);
            userGroupDao = new BaseRepo<>(connectionDb, UserGroup.class);

    }

    public void prepare() {

    }
}
