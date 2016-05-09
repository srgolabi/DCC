package agtp.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "users2")
public class Users2 extends Base{

    public static String USERACTION = "USERACTION";
    public static String USER = "USER";
    public static String ACTION = "ACTION";
    public static String DISCIPLINE_GROUPE = "DISCIPLINE_GROUP";
    public static String GROUP = "GROUP";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(defaultValue = "")
    private String username;

    @DatabaseField(defaultValue = "")
    private String password;

    @DatabaseField(defaultValue = "")
    private String email;

    @DatabaseField(defaultValue = "")
    private String name_fa;

    @DatabaseField(defaultValue = "")
    private String name_en;

    @DatabaseField(defaultValue = "")
    private String summary;

    @DatabaseField(defaultValue = "false")
    private boolean admin;

    @DatabaseField(defaultValue = "true")
    private Boolean active;

    @DatabaseField(defaultValue = "true")
    private Boolean sending;

    @DatabaseField(defaultValue = "false")
    private Boolean use_group_permission;

    @DatabaseField(defaultValue = "USERACTION")
    private String type;

    @DatabaseField(defaultValue = "")
    private String description;

    @DatabaseField(defaultValue = "")
    private String semat;

    @DatabaseField(defaultValue = "false")
    private Boolean is_deleted;

    public Users2() {
    }

    public Users2(String username, String password, String email, String name_fa) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name_fa = name_fa;
    }

    public static Users2 newGroup(String summary, String name_en, String name_fa, String type) {
        Users2 u = new Users2();
        u.name_en = name_en;
        u.name_fa = name_fa;
        u.summary = summary;
        u.type = type;
        return u;
    }

    public String getNoeGroup() {
        return type.equals(DISCIPLINE_GROUPE) ? "دیسیپلین" : "معمولی";
    }

    public String getNameBaSemat() {
        return name_fa + (semat.equals("") ? "" : "  (" + semat + ")");
    }

    public Users2 getThis() {
        return this;
    }

    @Override
    public String getCulomnValue() {
        return getNameBaSemat();
    }

    @Override
    public String getToolTipValue() {
        return getNameBaSemat();
    }
    
    
    
    /*
     ***************************************************************************
     ***************************************************************************
     ***************************************************************************
     */

    public Integer getId() {
        return id;
    }

    public void setSemat(String semat) {
        this.semat = semat;
    }

    public String getSemat() {
        return semat;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public Boolean getUse_group_permission() {
        return use_group_permission;
    }

    public void setUse_group_permission(Boolean use_group_permission) {
        this.use_group_permission = use_group_permission;
    }

    public Boolean getSending() {
        return sending;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName_fa() {
        return name_fa;
    }

    public String getName_en() {
        return name_en;
    }

    public boolean getAdmin() {
        return admin;
    }

    public Boolean getActive() {
        return active;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName_fa(String name_fa) {
        this.name_fa = name_fa;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSending(Boolean sending) {
        this.sending = sending;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
