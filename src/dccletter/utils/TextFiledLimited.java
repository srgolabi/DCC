/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author H.Daneshjoo
 */
public class TextFiledLimited {

//    public static void setEnterFocuse(Control textField, Control control) {
//        textField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
//            switch (event.getCode()) {
//                case TAB:
//                case ENTER:
//                    control.requestFocus();
//                case ALT:
//                    event.consume();
//                    break;
//
//            }
//        });
//    }
    public static void setEnterFocuse(Control... c) {
        setEnterFocuse(c[0], c[1], c[0]);
        for (int i = 1; i < c.length - 1; i++) {
            setEnterFocuse(c[i], c[i + 1], c[i - 1]);
        }
        setEnterFocuse(c[c.length - 1], c[c.length - 2], c[c.length - 2]);

    }

    public static void setEnterFocuse(Control textField, Control control, Control backcontrol) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isShiftDown() && event.getCode() == KeyCode.TAB) {
                backcontrol.requestFocus();
                event.consume();
                return;
            }
            switch (event.getCode()) {
                case TAB:
                case ENTER:
                    control.requestFocus();
                case ALT:
                    event.consume();
                    break;

            }
        });

    }

    public static void setTabFocuse(Control textField, Control control, Control backcontrol) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isShiftDown() && event.getCode() == KeyCode.TAB) {
                backcontrol.requestFocus();
                event.consume();
                return;
            }
            switch (event.getCode()) {
                case TAB:
                    control.requestFocus();
                case ALT:
                    event.consume();
                    break;

            }
        });
    }

    public static void setLengthLimit(TextField textField, int size) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if (textField.getText().length() >= size) {
                event.consume();
            }
        });
    }

    public static void setLengthLimit(TextField textField, int size, Control control) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if (textField.getText().length() >= size && textField.getSelection().getLength() == 0) {
                event.consume();
            }
        });

        textField.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (textField.getSelection().getLength() > 0) {
                return;
            }
            if (textField.getText().length() >= size) {
                control.requestFocus();
                event.consume();
            }
        });
    }

    public static void setNumberLimit(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            try {
                Integer.parseInt(event.getCharacter());
            } catch (Exception e) {
                event.consume();
            }
        });
    }

    public static void setNumberLimit(TextField textField, Control control, Control backcontrol) {
        setNumberLimit(textField);
        setEnterFocuse(textField, control, backcontrol);
    }

    public static void setNumberAndLengthLimit(TextField textField, int size, Control control, Control backcontrol) {
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (textField.getText().length() == 1 && !newValue) {
                textField.setText("0" + textField.getText());
            }
        });

        setLengthLimit(textField, size, control);
        setNumberLimit(textField);
        setEnterFocuse(textField, control, backcontrol);
    }

    public static void setNumberLimit(TextField textField, int size, Control control, Control backcontrol) {
        setLengthLimit(textField, size);
        setNumberLimit(textField);
        setEnterFocuse(textField, control, backcontrol);
    }
}
