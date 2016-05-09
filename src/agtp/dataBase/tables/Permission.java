package agtp.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.users;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "permission")
public class Permission {

    public static int LETTER = 1;
    public static int LETTER_INSERT = 2;
    public static int LETTER_EDIT = 3;
    public static int LETTER_VIEW_ALL = 4;
    public static int LETTER_VIEW_GROUP = 5;
    public static int LETTER_VIEW_PERSON = 6;
    public static int LETTER_GETREPORT = 7;
    public static int LETTER_SEND = 17;
    public static int SETTING = 8;
    public static int USER_INSERT = 9;
    public static int USER_VIEW = 10;
    public static int CHANGE_PASS = 11;
    public static int GROUPS = 12;
    public static int GROUP_VIEW = 13;
    public static int GROUP_INSERT = 14;
    public static int GROUP_REMOVE = 15;
    public static int GROUP_USER = 16;
    
    //  17

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(defaultValue = "", unique = true)
    private String name;

    @DatabaseField(defaultValue = "")
    private String title;

    @DatabaseField(defaultValue = "")
    private String log_title;

    @DatabaseField(defaultValue = "eye")
    private String icon;

    @DatabaseField(defaultValue = "#000000")
    private String iconColor;

    @DatabaseField(defaultValue = "false")
    private Boolean defaultValue;

    @DatabaseField(index = true, foreign = true, foreignAutoRefresh = true, columnName = "parent")
    private Permission parent;

    @DatabaseField
    private int sort_index;

    public Permission() {
    }

    public Permission(Integer id, String name, String title, int sort_index, Permission parent) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.sort_index = sort_index;
        this.parent = parent;
    }

    public Permission(String name, String title, String log_title, Permission parent, int sort_index) {
        this.name = name;
        this.title = title;
        this.log_title = log_title;
        this.parent = parent;
        this.sort_index = sort_index;
    }

    public Permission(Integer id, String name, String title, Boolean defaultValue, int sort_index, Permission parent) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.defaultValue = defaultValue;
        this.sort_index = sort_index;
        this.parent = parent;
    }

    public static boolean isAcces(int permissionId) {
        return !databaseHelper.userPermissionDao.rawResults(
                "SELECT * FROM userPermission WHERE user_id = " + users.getId() + " AND state = 1 AND permission_id = " + permissionId
        ).isEmpty() || users.getAdmin();
    }
    /*
     ***************************************************************************
     ***************************************************************************
     ***************************************************************************
     */

    public Integer getId() {
        return id;
    }

    public void setLog_title(String log_title) {
        this.log_title = log_title;
    }

    public String getLog_title() {
        return log_title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getDefaultValue() {
        if (defaultValue == null) {
            return false;
        }
        return defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getSort_index() {
        return sort_index;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSort_index(int sort_index) {
        this.sort_index = sort_index;
    }

}
