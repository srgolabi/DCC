/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import agtp.AGTP;
import dccletter.DCCLetter;
import dccletter.fxml.warning.FXMLWarningController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author reza
 */
public class UtilsMsg {

    private Stage stage;
    private FXMLLoader loader;
    public Parent page;

//    public UtilsStage(String fxml, boolean resizable, String title, Modality modality, Window owner) {
    public UtilsMsg(String fxml, boolean resizable, String title, Modality modality, Window owner, Class clazz) {
        try {
//            loader = new FXMLLoader(DCCLetter.class.getResource("fxml/" + fxml));
            loader = new FXMLLoader(clazz.getResource("fxml/" + fxml));
            page = (Parent) loader.load();
            stage = new Stage();
            stage.setResizable(resizable);
            stage.setTitle(title);
            stage.initModality(modality);
            stage.initOwner(owner);
            stage.sizeToScene();
            stage.getIcons().add(new Image(DCCLetter.class.getResourceAsStream("resourse/app_icon.png")));
            Scene scene = new Scene(page);
            stage.setScene(scene);

        } catch (IOException ex) {
            Logger.getLogger(UtilsMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public UtilsStage(String fxml, String title, Modality modality, Window owner) {
//        this(fxml, false, title, modality, owner);
//    }
//
//    public UtilsStage(String fxml, String title, Window owner) {
//        this(fxml, false, title, Modality.APPLICATION_MODAL, owner);
//    }
    public UtilsMsg(String fxml, String title, Modality modality, Window owner) {
        this(fxml, false, title, modality, owner, DCCLetter.class);
    }

    public UtilsMsg(Modality modality, String fxml, String title, Window owner) {
        try {
            loader = new FXMLLoader(AGTP.class.getResource("fxml/" + fxml + "/FXML" + fxml + ".fxml"));
            page = (Parent) loader.load();
            stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);
            stage.initModality(modality);
            stage.initOwner(owner);
            stage.sizeToScene();
            stage.getIcons().add(new Image(DCCLetter.class.getResourceAsStream("resourse/app_icon.png")));
            Scene scene = new Scene(page);
            stage.setScene(scene);

        } catch (IOException ex) {
            Logger.getLogger(UtilsMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UtilsMsg(String fxml, String title, Modality modality, Window owner, Class clazz) {
        this(fxml, false, title, modality, owner, clazz);
    }

    public Stage getStage() {
        return stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public static boolean showMsg(String msg, String title, boolean okIsShow, Stage thisStage) {
        UtilsMsg utilsStage = new UtilsMsg("warning/FXMLWarning.fxml", title, Modality.APPLICATION_MODAL, thisStage.getOwner());
        FXMLWarningController controller = utilsStage.getLoader().getController();
        controller.init(utilsStage.getStage(), msg);
        controller.ok.setVisible(okIsShow);
        if (okIsShow) {
            controller.ok.setDefaultButton(true);
            controller.cancel.setCancelButton(true);
        } else {
            controller.cancel.setDefaultButton(true);
        }
        utilsStage.getStage().showAndWait();
        return controller.okClick;
    }

    public static boolean showMsgEn(String msg, String title, boolean okIsShow, Stage thisStage) {
        UtilsMsg utilsStage = new UtilsMsg("warning/FXMLWarningEN.fxml", title, Modality.APPLICATION_MODAL, thisStage.getOwner());
        FXMLWarningController controller = utilsStage.getLoader().getController();
        controller.init(utilsStage.getStage(), msg);
        controller.ok.setVisible(okIsShow);
        if (okIsShow) {
            controller.ok.setDefaultButton(true);
            controller.cancel.setCancelButton(true);
        } else {
            controller.cancel.setDefaultButton(true);
        }

        controller.ok.setText("Ok");
        controller.cancel.setText("Cancel");
        utilsStage.getStage().showAndWait();
        return controller.okClick;
    }

}
