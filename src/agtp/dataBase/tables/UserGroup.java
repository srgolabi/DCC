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
@DatabaseTable(tableName = "userGroup")
public class UserGroup {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private Users2 user_id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "group_id")
    private Users2 group_id;

    public UserGroup() {
    }

    public UserGroup(Users2 user_id) {
        this.user_id = user_id;
    }

    public UserGroup(Users2 user_id, Users2 group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }


    public String getUserName() {
        return user_id.getName_fa();
    }
    
    public String getNameBaSemat() {
        return user_id.getNameBaSemat();
    }
    
    
    public String getGroupName() {
        return group_id.getName_fa();
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

    public Users2 getGroup_id() {
        return group_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(Users2 user_id) {
        this.user_id = user_id;
    }

    public void setGroup_id(Users2 group_id) {
        this.group_id = group_id;
    }

}
