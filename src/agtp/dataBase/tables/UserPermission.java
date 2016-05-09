/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.dataBase.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author reza
 */
@DatabaseTable(tableName = "userPermission")
public class UserPermission {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private Users2 user_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "permission_id")
    private Permission permission_id;

    @DatabaseField(defaultValue = "false")
    private boolean state;

    public UserPermission() {
    }

    public UserPermission(Users2 user_id, Permission permission_id, boolean state) {
        this.user_id = user_id;
        this.permission_id = permission_id;
        this.state = state;
    }

    public String getPermissionName() {
        return permission_id.getTitle();
    }
    /*
     ***************************************************************************
     ***************************************************************************
     ***************************************************************************
     */

    public Integer getId() {
        return id;
    }

    public Users2 getUser_id() {
        return user_id;
    }

    public Permission getPermission_id() {
        return permission_id;
    }

    public boolean isState() {
        return state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(Users2 user_id) {
        this.user_id = user_id;
    }

    public void setPermission_id(Permission permission_id) {
        this.permission_id = permission_id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
