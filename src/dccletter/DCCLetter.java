package dccletter;

import agtp.PrepareUpdate;
import agtp.dataBase.DatabaseHelper;
import agtp.dataBase.tables.Users2;
import dccletter.fxml.login.FXMLLoginController;
import dccletter.fxml.main.FXMLMainController;
import dccletter.register.Register;
import dccletter.utils.UtilsMsg;
import static document.DocumentSystem.importFromExcel;
import static document.DocumentSystem.initDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author reza
 */
public class DCCLetter extends Application {

    public static DatabaseHelper databaseHelper;
    public static Users2 users;
    public static Register register;
    public static String version = "1.0.4";
//    public static String server = "\\\\DANESHJOO\\$Letter$\\";
//    public static String server = "\\\\DESKTOP-93BRJRT\\$Latter$\\";
    public static String server = "";

    public static String spliter = "分離器";

    @Override
    public void start(Stage stage) throws Exception {
        databaseHelper = new DatabaseHelper();

        if (!databaseHelper.manageDao.queryForId(3).getValue().equals(version)) {
            if (databaseHelper.manageDao.queryForId(4).getValue().contains("true")) {
                UtilsMsg.showMsg("نرم افزار بروزرسانی شده است.\nنسخه جدید آن را نصب کنید.", "هشدار", false, stage);
                onCloseApp();
            } else {
                UtilsMsg.showMsg("نسخه جدید نرم افزار آماده می باشد", "هشدار", false, stage);
            }
        }
        
        databaseHelper.init();
        PrepareUpdate prepareUpdate = new PrepareUpdate();
        prepareUpdate.init1();
        initDB();
//        importFromExcel();
//        List<LetterFull> lliisstt = databaseHelper.letterFullDao.rawResults("SELECT letterFull.id , letterH.date letterDate , letterFull.letterNumber , letterFull.subject  , fromC.company_fa 'from' , GROUP_CONCAT(DISTINCT toC.company_fa) 'to' , \n"
//                + "originalA.name || ' (' ||originalA.position || ')' orginal , GROUP_CONCAT(copyA.name || '  (' ||copyA.position || ')  ') copies , letterFull.pageNumber , letterFull.annex , letterFull.closed  , receiveH.date receiveDate ,\n"
//                + "insertH.date insertDate , letterFull.letterHour , u.name 'user' , insertH.date || ' - ' || letterFull.letterHour  AS insertTime FROM letters letterFull\n"
//                + "LEFT OUTER JOIN history letterH ON letterFull.letter_date_id = letterH.id \n"
//                + "LEFT OUTER JOIN history insertH ON letterFull.letter_insert_id = insertH.id \n"
//                + "LEFT OUTER JOIN history receiveH ON letterFull.receive_date_id = receiveH.id\n"
//                + "LEFT OUTER JOIN companies fromC ON letterFull.from_id = fromC.id\n"
//                + "LEFT OUTER JOIN users u ON letterFull.user_id = u.id\n"
//                + "LEFT OUTER JOIN actions originalA ON letterFull.notification_id = originalA.id\n"
//                + "LEFT OUTER JOIN letteraction copyLA ON letterFull.id = copyLA.letter_id\n"
//                + "LEFT OUTER JOIN actions copyA ON copyLA.action_id = copyA.id\n"
//                + "LEFT OUTER JOIN lettertocompany toLC ON letterFull.id = toLC.letter_id\n"
//                + "LEFT OUTER JOIN companies toC ON toLC.company_id = toC.id\n"
//                + "where letterFull.closed = 0 AND letterDate < '94/07/01'\n"
//                + "GROUP BY letterFull.id ORDER BY letterDate DESC");
//        for (LetterFull l : lliisstt) {
//            if (!l.getClosed()) {
//                Letters lll = databaseHelper.lettersDao.queryForId(l.getId());
//                lll.setClosed(true);
//                databaseHelper.lettersDao.createOrUpdate(lll);
//            }
//        }
//        for (Letters l : lliisstt) {
//            if (l.getInsert_date() != null) {
//                                    System.out.println("(asl tool == " + l.getInsert_date().getDate().length());
//
//                if (l.getInsert_date().getDate().length() != 8) {
//                    String dateTemp = l.getInsert_date().getDate();
//                    String split = dateTemp.substring(0, dateTemp.indexOf("/"));
//                    split = (split.length() == 2 ? split : "0" + split) + "/";
//                    String datee = split;
//                    split = dateTemp.substring(dateTemp.indexOf("/") + 1, dateTemp.lastIndexOf("/"));
//                    split = (split.length() == 2 ? split : "0" + split) + "/";
//                    datee = datee + split;
//                    split = dateTemp.substring(dateTemp.lastIndexOf("/") + 1);
//                    split = (split.length() == 2 ? split : "0" + split);
//                    datee = datee + split;
//                    History history = databaseHelper.historyDao.getFirst("date", datee);
//                    if (history == null) {
//                        history = new History(l.getInsert_date().getYear() + "", l.getInsert_date().getMonth() + "", l.getInsert_date().getDay() + "");
//                        databaseHelper.historyDao.createOrUpdate(history);
//                    }
//                    System.out.println("(asl == " + l.getInsert_date().getDate() + ")    (" + datee + ")");
//                    l.setInsert_date(history);
//                }
//            }
//            if (l.getLetterHour() != null) {
//                if (l.getLetterHour().length() != 8) {
//                    String dateTemp = l.getLetterHour();
//                    String split = dateTemp.substring(0, dateTemp.indexOf(":"));
//                    split = (split.length() == 2 ? split : "0" + split) + ":";
//                    String datee = split;
//                    split = dateTemp.substring(dateTemp.indexOf(":") + 1, dateTemp.lastIndexOf(":"));
//                    split = (split.length() == 2 ? split : "0" + split) + ":";
//                    datee = datee + split;
//                    split = dateTemp.substring(dateTemp.lastIndexOf(":") + 1);
//                    split = (split.length() == 2 ? split : "0" + split);
//                    datee = datee + split;
//                    System.out.println("(asl2 == " + l.getLetterHour() + ")    (" + datee + ")");
//                    l.setLetterHour(datee);
//                }
//            }
//            databaseHelper.lettersDao.createOrUpdate(l);
//        }

        register = new Register();
//        register.writeEncrype("luoinch", "2015111820160220111111110");
//        onCloseApp();
        if (register.checkRegister()) {
            if (register.showLoginStage) {
                showLoginStage(stage);
            } else {
                users = databaseHelper.users2Dao.queryForId(register.userID);

                showMainStage(stage);
            }

        } else {
            register.writeEncrype("luoinch", "asdfghjklkiuytre");
            UtilsMsg.showMsg("نرم افزار غیرفعال شده است.\nبا پشتیبان نرم افزار تماس حاصل نمایید.", "هشدار", false, stage);
            onCloseApp();
        }
    }

    public void showLoginStage(Stage stage) {
        UtilsMsg utilsStage = new UtilsMsg("login/FXMLLogin.fxml", "", Modality.NONE, stage.getOwner());
        FXMLLoginController controller = utilsStage.getLoader().getController();
        controller.init(utilsStage.getStage());
        utilsStage.getStage().showAndWait();
        if (controller.isAccess) {
            this.users = controller.usersTemp;
            showMainStage(stage);
        } else {
            onCloseApp();
        }
    }

    public void showMainStage(Stage stage) {
        UtilsMsg utilsStage = new UtilsMsg("main/FXMLMain.fxml", "", Modality.NONE, stage.getOwner());
        FXMLMainController controller = utilsStage.getLoader().getController();
        controller.init(utilsStage.getStage());
        utilsStage.getStage().show();
        utilsStage.getStage().setOnCloseRequest((WindowEvent event) -> {
            onCloseApp();
        });
    }

    public static void onCloseApp() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
