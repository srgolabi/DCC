/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp;

import agtp.dataBase.tables.Permission;
import agtp.dataBase.tables.UserGroup;
import agtp.dataBase.tables.UserPermission;
import agtp.dataBase.tables.Users2;
import static dccletter.DCCLetter.databaseHelper;
import dccletter.dataBase.tables.Letters3;
import agtp.dataBase.tables.Manage;
import dccletter.dataBase.tables.SearchFields;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reza
 */
public class PrepareUpdate {

    public void init1() {
//
        List<Permission> permissions = new ArrayList<>();

        Permission pLetter = new Permission(Permission.LETTER, "LETTER", "مکاتبات", 50, null);
        {
            databaseHelper.permissionDao.createOrUpdate(pLetter);
            permissions.add(pLetter);
        }
        permissions.add(new Permission(Permission.LETTER_INSERT, "LETTER_INSERT", "ثبت مکاتبات", 100, pLetter));
        permissions.add(new Permission(Permission.LETTER_SEND, "LETTER_SEND", "ارجاع مکاتبات", 150, pLetter));
        permissions.add(new Permission(Permission.LETTER_EDIT, "LETTER_EDIT", "اصلاح مکاتبات", 200, pLetter));
        permissions.add(new Permission(Permission.LETTER_VIEW_ALL, "LETTER_VIEW_ALL", "مشاهده کلیه مکاتبات", 300, pLetter));
        permissions.add(new Permission(Permission.LETTER_VIEW_GROUP, "LETTER_VIEW_GROUP", "مشاهده مکاتبات گروه", true, 350, pLetter));
        permissions.add(new Permission(Permission.LETTER_VIEW_PERSON, "LETTER_VIEW_PERSON", "مشاهده مکاتبات شخصی", true, 400, pLetter));
        permissions.add(new Permission(Permission.LETTER_GETREPORT, "LETTER_GETREPORT", "گزارش مکاتبات", 500, pLetter));

        Permission pSetting = new Permission(Permission.SETTING, "SETTING", "تنظیمات کاربری", 350, null);
        {
            databaseHelper.permissionDao.createOrUpdate(pSetting);
            permissions.add(pSetting);
        }
        permissions.add(new Permission(Permission.USER_VIEW, "USER_VIEW", "مشاهده کاربران", 300, pSetting));
        permissions.add(new Permission(Permission.USER_INSERT, "USER_INSERT", "ثبت کاربر", 400, pSetting));
        permissions.add(new Permission(Permission.CHANGE_PASS, "CHANGE_PASS", "تغییر رمز عبور", 500, pSetting));

        Permission pGROUPS = new Permission(Permission.GROUPS, "GROUPS", "گروه ها", 550, null);
        {
            databaseHelper.permissionDao.createOrUpdate(pGROUPS);
            permissions.add(pGROUPS);
        }
        permissions.add(new Permission(Permission.GROUP_VIEW, "GROUP_VIEW", "مشاهده گروه ها", 600, pGROUPS));
        permissions.add(new Permission(Permission.GROUP_INSERT, "GROUP_INSERT", "ثبت گروه", 700, pGROUPS));
        permissions.add(new Permission(Permission.GROUP_REMOVE, "GROUP_REMOVE", "حذف گروه", 800, pGROUPS));
        permissions.add(new Permission(Permission.GROUP_USER, "GROUP_USER", "تعریف کاربران گروه", 1000, pGROUPS));

        try {
            databaseHelper.permissionDao.insertList(permissions);
        } catch (SQLException ex) {
            Logger.getLogger(PrepareUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
//
//        if (databaseHelper.manageDao.getAll().size() != 14) {
//            databaseHelper.manageDao.createOrUpdate(new Manage(1, "پیام روز", "اگر شروع به قضاوت مردم کنید، وقتی برای دوست داشتن آنها نخواهید داشت."));
//            databaseHelper.manageDao.createOrUpdate(new Manage(2, "تعداد شرکت ها", "24"));
//            databaseHelper.manageDao.createOrUpdate(new Manage(3, "ورژن نرم افزار", "1.0.0"));
//            databaseHelper.manageDao.createOrUpdate(new Manage(4, "حتما باید نسخه جدید نصب شود", "false"));
//            databaseHelper.manageDao.createOrUpdate(new Manage(5, "پیغام قبل از اجرا", "false"));
//            databaseHelper.manageDao.createOrUpdate(new Manage(6, "نمایش فرم نظر سنجی", "94/11/01"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(1, "اندیکاتور", true, dccletter.register.Permission.LETTER_VIEW, "letterFull.id", "letters", "integer"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(2, "شماره نامه", true, dccletter.register.Permission.LETTER_VIEW, "letterFull.letterNumber", "letters", "string"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(3, "موضوع نامه", true, dccletter.register.Permission.LETTER_VIEW, "letterFull.subject", "letters", "string"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(4, "تاریخ رسید", true, dccletter.register.Permission.LETTER_VIEW, "receiveDate", "history", "date"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(5, "تاریخ نامه", true, dccletter.register.Permission.LETTER_VIEW, "letterDate", "history", "date"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(6, "فرستنده / از", true, dccletter.register.Permission.LETTER_VIEW, "fromC.id", "companies", "companies"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(7, "گیرنده / به", true, dccletter.register.Permission.LETTER_VIEW, "toC.id", "lettertocompany", "companies"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(8, "اصل", true, dccletter.register.Permission.LETTER_VIEW, "originalA.id", "actions", "actions"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(9, "رونوشت", true, dccletter.register.Permission.LETTER_VIEW, "copyA.id", "letteraction", "actions"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(10, "وضعیت نامه", true, dccletter.register.Permission.LETTER_VIEW, "letterFull.closed", "letters", "closeORopen"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(11, "پیوست", true, dccletter.register.Permission.LETTER_VIEW, "letterFull.annex", "letters", "attachORnot"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(12, "تعداد صفحات", true, dccletter.register.Permission.LETTER_VIEW, "letterFull.pageNumber", "letters", "integer"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(13, "کاربر", false, dccletter.register.Permission.REPORT_FIELDS, "u.id", "letters", "user"));
//            databaseHelper.searchFieldsDao.createOrUpdate(new SearchFields(14, "تاریخ ثبت/تغییر", false, dccletter.register.Permission.REPORT_FIELDS, "insertDate", "letters", "date"));
//        }
//        List<UserGroup> ll = new ArrayList<>();
//        List<UserPermission> listUserPermission = new ArrayList<>();
//
//        if (databaseHelper.users2Dao.getAll().isEmpty()) {
//            List<Actions> listActions = databaseHelper.actionsDao.getAll();
//            List<Users2> listUser = new ArrayList<>();
//            Users2 u2;
//            for (Actions a : listActions) {
//                u2 = new Users2();
//                u2.setId(a.getId());
//                u2.setSemat(a.getPosition());
//                u2.setActive(a.getActive());
//                u2.setType(Users2.ACTION);
//                u2.setName_fa(a.getName());
//                Users u = a.getUserId();
//                if (u != null) {
//                    u2.setUsername(u.getUsername());
//                    u2.setPassword(u.getPassword());
//                    u2.setEmail(u.getEmail());
//                    u2.setName_fa(u.getName());
//                    u2.setAdmin(u.isIsAdmin());
//                    u2.setUse_group_permission(Boolean.TRUE);
//                    u2.setType(Users2.USERACTION);
//                    ll.add(new UserGroup(u2, u2));
//                    listUserPermission.add(new UserPermission(u2, permissions.get(4), true));
//                    listUserPermission.add(new UserPermission(u2, permissions.get(5), true));
//                }
//                if (u2.getId() == 32) {
//                    u2.setType(Users2.GROUP);
//                }
//
//                listUser.add(u2);
//            }
//            try {
//                databaseHelper.users2Dao.insertList(listUser);
//                databaseHelper.userGroupDao.insertList(ll);
//                databaseHelper.userPermissionDao.insertList(listUserPermission);
//
//            } catch (SQLException ex) {
//                Logger.getLogger(PrepareUpdate.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("GE", "General", "عمومی", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("PR", "Process", "فرایند", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("CE", "Civil", "ساختمان", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("ST", "Structure", "سازه", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("BL", "Building", "معماری", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("IN", "Instrument", "ابزاردقیق", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("FE", "Fix Equipment", "تجهیزات ثابت", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("EL", "Electrical", "برق", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("RE", "Rotary Equipment", "ماشین آلات", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("HS", "HSE", "ایمنی", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("PI", "Piping", "لوله کشی", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("HV", "HVAC", "تهویه مطبوع", Users2.DISCIPLINE_GROUPE));
//            databaseHelper.users2Dao.createOrUpdate(Users2.newGroup("PJ", "Project", "پروژه", Users2.DISCIPLINE_GROUPE));
//
//        }
//
//        if (databaseHelper.letters3Dao.getAll().isEmpty()) {
//            List<Letters> listLetters = databaseHelper.lettersDao.getAll();
//            List<Letters3> listLetters3 = new ArrayList<>();
//
//            for (Letters l : listLetters) {
//                Letters3 l3 = new Letters3();
//                l3.setId(l.getId());
//                l3.setSubject(l.getSubject());
//                l3.setLetterNumber(l.getLetterNumber());
//                l3.setLetter_date(l.getLetter_date());
//                l3.setReceive_date(l.getReceive_date());
//                l3.setFrom(l.getFrom());
//                l3.setAnnex(l.getAnnex());
//                l3.setClosed(l.getClosed());
//                if (l.getNotification() != null) {
//                    l3.setNotification(databaseHelper.users2Dao.queryForId(l.getNotification().getId()));
//                }
//                if (l.getUser() != null) {
//                    int time = l.getInsert_date() == null ? 1 : l.getInsert_date().getId();
//                    l3.addToLog(l.getUser().getId() == 1 ? 6 : 53, Permission.LETTER_INSERT, time, l.getLetterHour() == null ? "00:00:00" : l.getLetterHour());
//                }
//                listLetters3.add(l3);
//            }
//            try {
//                databaseHelper.letters3Dao.insertList(listLetters3);
//            } catch (SQLException ex) {
//                Logger.getLogger(PrepareUpdate.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }
}
