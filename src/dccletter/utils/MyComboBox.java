package dccletter.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Seyed Reza Golabi
 */
public class MyComboBox extends AnchorPane {

    private TextField textField;

    public MyComboBox() {
        Button button = new Button();
        button.setId("down-image");
        button.setOnAction((ActionEvent event) -> {
            textField.requestFocus();
        });
        button.setMaxHeight(Double.MAX_VALUE);
        AnchorPane.setRightAnchor(button, 0d);
        textField = new TextField();
        textField.setStyle("-fx-background-radius : 0;");
        textField.setPrefHeight(29);
//        textField.setFocusTraversable(false);
        AnchorPane.setLeftAnchor(textField, 0d);
        AnchorPane.setRightAnchor(textField, 28d);
        textField.setMaxHeight(Double.MAX_VALUE);
        this.getChildren().add(textField);
        this.getChildren().add(button);
    }
    
    public TextField getTextField() {
        return textField;
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String st) {
        textField.setText(st);
    }
}
