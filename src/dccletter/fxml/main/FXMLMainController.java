package dccletter.fxml.main;

import agtp.AGTP;
import agtp.dataBase.tables.Permission;
import agtp.fxml.groupManage.FXMLGroupManageController;
import agtp.fxml.userManage.FXMLUserManageController;
import dccletter.DCCLetter;
import static dccletter.DCCLetter.databaseHelper;
import static dccletter.DCCLetter.onCloseApp;
import static dccletter.DCCLetter.users;
import dccletter.dataBase.tables.Letters3;
import agtp.fxml.actionsManage.FXMLactionsManageController;
import dccletter.fxml.changeUserPass.FXMLChangeUserPassController;
import agtp.fxml.companyManage.FXMLcompanyManageController;
import dccletter.fxml.letterInsert.FXMLLetterInsertController;
import dccletter.fxml.letterList.FXMLLtterListController;
import dccletter.fxml.letterViewer.FXMLLetterViewerController;
import dccletter.fxml.login.FXMLLoginController;
import dccletter.fxml.searchPro.FXMLSearchProController;
import dccletter.register.Register;
import dccletter.utils.ParentControl;
import dccletter.utils.PersianCalendar;
import dccletter.utils.UtilsMsg;
import document.DocumentSystem;
import document.fxml.transmitallInsert.FXMLTransmitallInsertController;
import document.fxml.print.transmital.FXMLPrintTransmitalController;
import document.fxml.transmittalOutGoing.FXMLTransmittalOutGoingController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import metting.MettingSystem;
import metting.fxml.mittingInsert.FXMLMettingInsertController;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLMainController implements Initializable {

    @FXML
    private Button letter_B;
    @FXML
    private Button doc_B;

    @FXML
    private VBox letter_layout;
    @FXML
    private VBox doc_layout;

    @FXML
    private Button letterInsert;
    @FXML
    private Button letterList;
    @FXML
    private Button search;
    @FXML
    private Button searchPro;

    @FXML
    private Label dayMessage;
    @FXML
    private Label timeAnimation;
    @FXML
    private Label logo;

    @FXML
    private MenuItem changeUserPass;
    @FXML
    private MenuItem manageUser;
    @FXML
    private MenuItem manageCompanies;
    @FXML
    private MenuItem manageActions;
    @FXML
    private MenuItem mettings;

    private boolean times = true;
    private Stage thisStage;
    FXMLLetterInsertController letterInsert_Controller;
    FXMLLtterListController letterList_Controller;
    FXMLLetterViewerController letterViewer_Controller;
    FXMLSearchProController searchPro_Controller;

    FXMLChangeUserPassController changeUserPass_Controller;
    FXMLUserManageController userManageController;
    FXMLGroupManageController groupManageController;
    FXMLcompanyManageController companyManage_Controller;
    FXMLactionsManageController actionsManageController;

    FXMLMettingInsertController mettingInsertController;
    FXMLTransmitallInsertController transmitallInsertController;
    FXMLTransmittalOutGoingController transmittalOutGoingController;
    FXMLPrintTransmitalController printTransmitalController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void stageToFront(ParentControl pc) {
        pc.thisStage.toFront();
        pc.thisStage.setIconified(false);
    }

    @FXML
    private void goToLayout(ActionEvent event) {
        letter_B.setDisable(false);
        doc_B.setDisable(false);
        ((Button) event.getSource()).setDisable(true);
    }

    public void init(Stage s) {
        thisStage = s;
        letter_layout.visibleProperty().bind(letter_B.disableProperty());
        doc_layout.visibleProperty().bind(doc_B.disableProperty());
        letter_B.setDisable(true);

        changeUserPass.setDisable(!Permission.isAcces(Permission.CHANGE_PASS));
        letterInsert.setDisable(!Permission.isAcces(Permission.LETTER_INSERT));
        letterList.setDisable(!Permission.isAcces(Permission.LETTER_GETREPORT));
        searchPro.setDisable(!Permission.isAcces(Permission.LETTER_GETREPORT));
        manageUser.setDisable(!Permission.isAcces(Permission.USER_VIEW));
        manageCompanies.setDisable(!users.getAdmin());
//        manageActions.setDisable(!users.getPermissions().contains(Permission.MANAGE_ACTIONS) );
        manageActions.setDisable(false);
        mettings.setDisable(!users.getAdmin());

        letterInsert.setOnAction((ActionEvent event) -> {
            letterInsert_Show(null);
        });
        search.setOnAction((ActionEvent event) -> {
            LetterView_Show(null);
        });
        letterList.setOnAction((ActionEvent event) -> {
            String querys = "SELECT letterFull.id , letterH.date letterDate , letterFull.letterNumber , letterFull.subject  , fromC.company_fa 'from' , GROUP_CONCAT(DISTINCT toC.company_fa) 'to' , \n"
                    + "originalA.name_fa || ' (' ||originalA.semat || ')' orginal , GROUP_CONCAT(DISTINCT copyA.name_fa || '  (' ||copyA.semat || ')  ') copies , letterFull.annex , letterFull.closed  , receiveH.date receiveDate \n"
                    + "FROM letters3 letterFull\n"
                    + "LEFT OUTER JOIN history letterH ON letterFull.letter_date_id = letterH.id \n"
                    + "LEFT OUTER JOIN history receiveH ON letterFull.receive_date_id = receiveH.id\n"
                    + "LEFT OUTER JOIN companies fromC ON letterFull.from_id = fromC.id\n"
                    + "LEFT OUTER JOIN users2 originalA ON letterFull.notification_id = originalA.id\n"
                    + "LEFT OUTER JOIN letteraction copyLA ON letterFull.id = copyLA.letter_id\n"
                    + "LEFT OUTER JOIN users2 copyA ON copyLA.action_id = copyA.id\n"
                    + "LEFT OUTER JOIN userGroup ug ON  copyLA.action_id = ug.group_id \n"
                    + "LEFT OUTER JOIN userPermission up ON up.user_id = ug.user_id\n"
                    + "LEFT OUTER JOIN lettertocompany toLC ON letterFull.id = toLC.letter_id\n"
                    + "LEFT OUTER JOIN companies toC ON toLC.company_id = toC.id\n"
                    + "WHERE letterFull.id is sub_query null OR (ug.user_id = " + users.getId()
                    + " AND ((ug.group_id = ug.user_id AND up.permission_id = " + Permission.LETTER_VIEW_PERSON
                    + " AND up.state = 1) OR (ug.group_id != ug.user_id AND up.permission_id = " + Permission.LETTER_VIEW_GROUP
                    + " AND up.state = 1)))\n"
                    + "GROUP BY letterFull.id ORDER BY letterDate DESC";
            if (!databaseHelper.users2Dao.rawResults(
                    "SELECT users2.* FROM users2 \n"
                    + "LEFT OUTER JOIN userPermission ON users2.id = userPermission.user_id\n"
                    + "WHERE ((userPermission.permission_id = " + Permission.LETTER_VIEW_ALL + " AND userPermission.state = 1 )"
                    + " OR users2.admin = 1) AND users2.id = " + users.getId()).isEmpty()) {
                querys = querys.replace("sub_query", "not");
            } else {
                querys = querys.replace("sub_query", "");
            }

            showListLetter(querys);
        });

        searchPro.setOnAction((ActionEvent event) -> {
            if (searchPro_Controller == null) {
                UtilsMsg utilsStage = new UtilsMsg("searchPro/FXMLSearchPro.fxml", "گزارش گیری", Modality.NONE, thisStage.getOwner());
                searchPro_Controller = utilsStage.getLoader().getController();
                searchPro_Controller.init(utilsStage.getStage());
                utilsStage.getStage().showAndWait();
                if (searchPro_Controller.query != null) {
                    showListLetter(searchPro_Controller.query);
                }
                searchPro_Controller = null;
            } else {
                searchPro_Controller.thisStage.setIconified(false);
                searchPro_Controller.thisStage.toFront();
            }
        });
        bindToTime();
        dayMessage.setText(DCCLetter.databaseHelper.manageDao.queryForId(1).getValue());
    }

    private void showListLetter(String query) {
        if (letterList_Controller == null) {
            UtilsMsg utilsStage = new UtilsMsg("letterList/FXMLLtterList.fxml", true, "لیست نامه ها", Modality.NONE, thisStage.getOwner(), DCCLetter.class);
            letterList_Controller = utilsStage.getLoader().getController();
            letterList_Controller.init(utilsStage.getStage(), query);
            utilsStage.getStage().showAndWait();
            int temp1 = letterList_Controller.editLetter;
            int temp2 = letterList_Controller.viewLetter;
            letterList_Controller = null;
            if (temp1 != -1) {
                letterInsert_Show(databaseHelper.letters3Dao.queryForId(temp1));
            }
            if (temp2 != -1) {
                LetterView_Show(databaseHelper.letters3Dao.queryForId(temp2));
            }
        } else {
            letterList_Controller.init(letterList_Controller.thisStage, query);
            letterList_Controller.thisStage.setIconified(false);
            letterList_Controller.thisStage.toFront();
        }

    }

    private void bindToTime() {
        PersianCalendar pc = new PersianCalendar();
        timeAnimation.setOnMouseClicked((MouseEvent event) -> {
            times = !times;
            if (times) {
                timeAnimation.setStyle("-fx-font-family:'B Yekan';");
                timeAnimation.setText(pc.nameOFdayFa() + " ، " + pc.get(Calendar.DAY_OF_MONTH) + " " + pc.nameOfMonthFA() + " ، " + pc.year() + "    (" + pc.year() + "/" + pc.month() + "/" + pc.day() + ")");
            } else {
                timeAnimation.setStyle("-fx-font-family:'Arial';");
                timeAnimation.setText("(" + pc.getNowMilladiDate() + ")     " + pc.nameOFdayEn() + " ، " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " " + pc.nameOfMonthEN() + " ، " + Calendar.getInstance().get(Calendar.YEAR));
            }
        });
        timeAnimation.setStyle("-fx-font-family:'B Yekan';");
        timeAnimation.setText(pc.nameOFdayFa() + " ، " + pc.get(Calendar.DAY_OF_MONTH) + " " + pc.nameOfMonthFA() + " ، " + pc.year() + "    (" + pc.year() + "/" + pc.month() + "/" + pc.day() + ")");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), (ActionEvent actionEvent) -> {
                    times = !times;
                    if (times) {
                        timeAnimation.setStyle("-fx-font-family:'B Yekan';");
                        timeAnimation.setText(pc.nameOFdayFa() + " ، " + pc.get(Calendar.DAY_OF_MONTH) + " " + pc.nameOfMonthFA() + " ، " + pc.year() + "    (" + pc.year() + "/" + pc.month() + "/" + pc.day() + ")");
                    } else {
                        timeAnimation.setStyle("-fx-font-family:'Arial';");
                        timeAnimation.setText("(" + pc.getNowMilladiDate() + ")     " + pc.nameOFdayEn() + " ، " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " " + pc.nameOfMonthEN() + " ، " + Calendar.getInstance().get(Calendar.YEAR));
                    }
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void changeUserPassAction() {
        if (changeUserPass_Controller == null) {
            UtilsMsg utilsStage = new UtilsMsg("changeUserPass/FXMLChangeUserPass.fxml", "تغییر رمز عبور", Modality.NONE, thisStage.getOwner());
            changeUserPass_Controller = utilsStage.getLoader().getController();
            changeUserPass_Controller.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            changeUserPass_Controller = null;
        } else {
            stageToFront(changeUserPass_Controller);
        }
    }

    @FXML
    private void exitFromUser() {
        thisStage.close();
        Register register = new Register();
        register.exitUser();
        UtilsMsg utilsStage = new UtilsMsg("login/FXMLLogin.fxml", "ورود", Modality.NONE, thisStage.getOwner());
        FXMLLoginController controller = utilsStage.getLoader().getController();
        controller.init(utilsStage.getStage());
        utilsStage.getStage().showAndWait();
        if (controller.isAccess) {
            users = controller.usersTemp;
            showMainStage(thisStage);
        } else {
            onCloseApp();
        }
    }

    @FXML
    private void companyManage() {

//        DirectoryChooser chooser = new DirectoryChooser();
//        File ff = chooser.showDialog(thisStage);
//        if (ff != null) {
//            System.out.println("" + ff);
//            getAll(ff);
//        }
        if (companyManage_Controller == null) {
            UtilsMsg utilsStage = new UtilsMsg(Modality.NONE, "companyManage", "شرکتها", thisStage.getOwner());

            companyManage_Controller = utilsStage.getLoader().getController();
            companyManage_Controller.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            companyManage_Controller = null;
        } else {
            stageToFront(companyManage_Controller);
        }
    }

    private void getAll(File ff) {
//        String ss = "00";
        if (ff.isDirectory()) {
            for (File ff2 : ff.listFiles()) {
                getAll(ff2);
            }
        } else if (ff.isFile()) {
            System.out.println("" + ff.getName());
            if (ff.getName().split("-").length > 3) {
                if (ff.getName().split("-")[3].length() == 2) {
                    try {
                        FileUtils.copyFile(ff, new File("map\\" + ff.getName().split("-")[3] + "\\" + ff.getName()));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @FXML
    private void actionsManage() {
        if (actionsManageController == null) {
            UtilsMsg utilsStage = new UtilsMsg(Modality.NONE, "actionsManage", "پرسنل", thisStage.getOwner());
            actionsManageController = utilsStage.getLoader().getController();
            actionsManageController.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            actionsManageController = null;
        } else {
            stageToFront(actionsManageController);
        }
    }

    private void letterInsert_Show(Letters3 l) {
        if (letterInsert_Controller == null) {
            UtilsMsg utilsStage = new UtilsMsg("letterInsert/FXMLLetterInsert.fxml", "ثبت نامه", Modality.NONE, thisStage.getOwner());
            letterInsert_Controller = utilsStage.getLoader().getController();
            letterInsert_Controller.setStage(utilsStage.getStage());
            letterInsert_Controller.loadLetterB(l);
            utilsStage.getStage().showAndWait();
            letterInsert_Controller = null;
        } else {
            letterInsert_Controller.loadLetterB(l);
            stageToFront(letterInsert_Controller);
        }
    }

    private void LetterView_Show(Letters3 l) {
        if (letterViewer_Controller == null) {
            UtilsMsg utilsStage = new UtilsMsg("letterViewer/FXMLLetterViewer.fxml", "جست و جو", Modality.NONE, thisStage.getOwner());
            letterViewer_Controller = utilsStage.getLoader().getController();
            letterViewer_Controller.setStage(utilsStage.getStage());
            if (l != null) {
                letterViewer_Controller.loadLetterB(l);
            }
            letterViewer_Controller.searcMenuAction();
            utilsStage.getStage().showAndWait();
            int temp = letterViewer_Controller.editLetter;
            letterViewer_Controller = null;
            if (temp != -1) {
                letterInsert_Show(databaseHelper.letters3Dao.queryForId(temp));
            }
        } else {
            letterViewer_Controller.thisStage.toFront();
            letterViewer_Controller.thisStage.setIconified(false);
        }
    }

    private void showForm(ParentControl pc, String fxml, String title) {
        if (pc == null) {
            UtilsMsg utilsStage = new UtilsMsg(fxml, title, Modality.NONE, thisStage.getOwner());
            pc = utilsStage.getLoader().getController();
            pc.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            pc = null;
        } else {
            pc.thisStage.toFront();
            pc.thisStage.setIconified(false);
        }
    }

    @FXML
    private void userManage() {
        if (userManageController == null) {
            UtilsMsg utilsStage = new UtilsMsg("userManage/FXMLUserManage.fxml", "مدیریت کاربران", Modality.NONE, thisStage.getOwner(), AGTP.class);
            userManageController = utilsStage.getLoader().getController();
            userManageController.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            userManageController = null;
        } else {
            stageToFront(userManageController);
        }
    }

    @FXML
    private void groupManage() {
        if (groupManageController == null) {
            UtilsMsg utilsStage = new UtilsMsg("groupManage/FXMLGroupManage.fxml", "مدیریت گروه ها", Modality.NONE, thisStage.getOwner(), AGTP.class);
            groupManageController = utilsStage.getLoader().getController();
            groupManageController.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            groupManageController = null;
        } else {
            stageToFront(groupManageController);
        }
    }

    @FXML
    private void exit() {
        onCloseApp();
    }

    @FXML
    private void mettingInsert() {
        if (mettingInsertController == null) {
            UtilsMsg utilsStage = new UtilsMsg("mittingInsert/FXMLMettingInsert.fxml", "سیستم ثبت صورتجلسات", Modality.NONE, thisStage.getOwner(), MettingSystem.class);
            mettingInsertController = utilsStage.getLoader().getController();
            mettingInsertController.setStage(utilsStage.getStage());
            mettingInsertController.setOnLetterView((Letters3 l) -> {
//                showLetterViewer(l);
            });
            utilsStage.getStage().showAndWait();
            mettingInsertController = null;
        } else {
            stageToFront(mettingInsertController);
        }
    }

    @FXML
    private void documentInsert() {
        if (transmitallInsertController == null) {
            UtilsMsg utilsStage = new UtilsMsg("transmitallInsert/FXMLTransmitallInsert.fxml", "سیستم ثبت ", Modality.NONE, thisStage.getOwner(), DocumentSystem.class);
            transmitallInsertController = utilsStage.getLoader().getController();
            transmitallInsertController.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            transmitallInsertController = null;
        } else {
            stageToFront(transmitallInsertController);
        }
    }
    
        @FXML
    private void transmittalOutgoing() {
        if (transmittalOutGoingController == null) {
            UtilsMsg utilsStage = new UtilsMsg("transmittalOutGoing/FXMLTransmittalOutGoing.fxml", "سیستم ثبت ", Modality.NONE, thisStage.getOwner(), DocumentSystem.class);
            transmittalOutGoingController = utilsStage.getLoader().getController();
//            printTransmitalController.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            transmittalOutGoingController = null;
        } else {
            stageToFront(transmittalOutGoingController);
        }
    }

    @FXML
    private void documentPrint() {
        if (printTransmitalController == null) {
            UtilsMsg utilsStage = new UtilsMsg("print/transmital/FXMLPrintPreview.fxml", "سیستم ثبت ", Modality.NONE, thisStage.getOwner(), DocumentSystem.class);
            printTransmitalController = utilsStage.getLoader().getController();
            printTransmitalController.setStage(utilsStage.getStage());
            utilsStage.getStage().showAndWait();
            printTransmitalController = null;
        } else {
            stageToFront(printTransmitalController);
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
}
