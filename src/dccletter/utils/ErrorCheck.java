/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author reza
 */
public class ErrorCheck {

    private List<String> textFieldmsg = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    private String patrOfMsg;

    public ErrorCheck(String... str) {
        textFieldmsg = Arrays.asList(str);
    }

    public void setTextFieldMsg(String... str) {
    }

    public void addOtherMsg(String str) {
        errors.add(str);
    }

    public int checked(boolean okShow, String title, Stage stage, TextField... textFields) {
        int cont = 0;
        for (TextField tf : textFields) {
            if (getTxt(tf)) {
                errors.add(textFieldmsg.get(cont));
            }
            cont++;
        }
        if (!errors.isEmpty()) {
            String error = errors.size() > 1 ? "فیلدهای " : "فیلد ";
            cont = 0;
            for (String s : errors) {
                cont++;
                error = error + s + (cont == errors.size() ? "" : " ، ");
            }
            error = error + " را پر کنید.";
            return UtilsMsg.showMsg(error, title, okShow, stage) ? 1 : 0;

        }
        return -1;
    }

    public int checked_EN(boolean okShow, String title, Stage stage, TextField... textFields) {
        int cont = 0;
        for (TextField tf : textFields) {
            if (getTxt(tf)) {
                errors.add(textFieldmsg.get(cont));
            }
            cont++;
        }
        if (!errors.isEmpty()) {
            String fields = errors.size() > 1 ? " field's" : " field";
            String error = "";
            cont = 0;
            for (String s : errors) {
                cont++;
                error = error + s + (cont == errors.size() ? "" : " ، ");
            }
            error = "Fill in the " + error + fields;
            return UtilsMsg.showMsg(error, title, okShow, stage) ? 1 : 0;

        }
        return -1;
    }

    private boolean getTxt(TextField field) {
        return field.getText().trim().equals("");
    }

}
