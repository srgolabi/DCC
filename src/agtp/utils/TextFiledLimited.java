/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.utils;

import agtp.dataBase.BaseRepo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author SR.Golabi
 */
public class TextFiledLimited {

    public static boolean clear_value(boolean show_msg, TextInputControl... textFields) {
        for (TextInputControl tf : textFields) {
            if (!tf.getText().isEmpty() && show_msg) {
                show_msg = false;
//                if(!UtilsMsg.show("آیا می خواهید فرم پاک سازی شود؟", "هشدار", true, new Stage())){
//                    return false;
//                }
            }
            tf.setText("");
        }
        return true;
    }

    public static void autoCompleteText_1(TextField tf, BaseRepo baseRepo, String query) {
        final ObservableList<String> items = FXCollections.observableArrayList(
                baseRepo.rawResultsString(query)
        );
        FilteredList<String> filtered = new FilteredList<>(items, p -> true);
        tf.setOnKeyReleased((KeyEvent event) -> {
            filtered.setPredicate(s -> s.startsWith(tf.getText()));
            if (filtered.isEmpty()) {
                tf.setStyle("-fx-text-fill: #ff0000;");
            } else {
                tf.setStyle("-fx-text-fill: #000000;");
                int se = tf.getText().length();
                tf.setText(filtered.get(0));
                tf.positionCaret(se);
                tf.selectNextWord();
            }
        });
    }

    public static void setEnterFocuse(Control... c) {
        c[0].setUserData(new Control[]{c[0], c[1]});
        setEnterFocuse(c[0]);
        for (int i = 1; i < c.length - 1; i++) {
            c[i].setUserData(new Control[]{c[i - 1], c[i + 1]});
            setEnterFocuse(c[i]);
        }
        c[c.length - 1].setUserData(new Control[]{c[c.length - 2], c[c.length - 2]});
        setEnterFocuse(c[c.length - 1]);
    }

    public static void setEnterFocuse(Control textField) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isShiftDown() && event.getCode() == TAB) {
                ((Control[]) textField.getUserData())[0].requestFocus();
                event.consume();
                return;
            }
            if ((event.isAltDown() || event.isControlDown()) && event.getCode() == TAB) {
                event.consume();
            } else if (event.getCode() == ENTER || event.getCode() == TAB) {
                ((Control[]) textField.getUserData())[1].requestFocus();
                event.consume();
            }
        });
    }

    public static void set_Number_Length_Date_Limit(TextField textField, int size) {
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (textField.getText().length() == 1 && !newValue) {
                textField.setText("0" + textField.getText());
            }
        });
        set_Number_Length_Limit(textField, size);
    }

    public static void set_Number_Length_Limit(TextField textField, int size) {
        set_Length_Limit(textField, size);
        set_Number_Limit(textField);
    }

    public static void set_Number_Length_Limit_Stop(TextField textField, int size) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if (textField.getSelection().getLength() > 0) {
                return;
            }
            if (textField.getText().length() >= size) {
                event.consume();
            }
            try {
                Integer.parseInt(event.getCharacter());
            } catch (Exception e) {
                event.consume();
            }
        });
    }

    public static void set_Number_Limit(TextField... textField) {
        for (TextField tf : textField) {
            set_Number_Limit(tf);
        }
    }

    public static void set_Number_Limit(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            try {
                Integer.parseInt(event.getCharacter());
            } catch (Exception e) {
                event.consume();
            }
        });
    }

    public static void set_Length_Limit(TextField textField, int size) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if (textField.getSelection().getLength() > 0) {
                return;
            }
            if (textField.getText().length() >= size) {
                event.consume();
            }
        });

        textField.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            switch (event.getCode()) {
                case RIGHT:
                case LEFT:
                case UP:
                case DOWN:
                case HOME:
                case END:
                    return;
            }
            if (textField.getSelection().getLength() > 0) {
                return;
            }
            if (textField.getText().length() >= size) {
                if (textField.getUserData() != null) {
                    ((Control[]) textField.getUserData())[1].requestFocus();
                }
                event.consume();
            }
        });
    }

    public static void set_Length_Limit_Stop(TextField textField, int size) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if (textField.getSelection().getLength() > 0) {
                return;
            }
            if (textField.getText().length() >= size) {
                event.consume();
            }
        });
    }

    public static void set_empty_textField(TextInputControl... textFields) {
        for (TextInputControl tf : textFields) {
            tf.setText("");
        }
    }

    public static void set_editable_textField(BooleanProperty editable, TextInputControl... textFields) {
        for (TextInputControl tf : textFields) {
            tf.editableProperty().bind(editable);
        }
    }
}
