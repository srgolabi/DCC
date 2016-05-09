/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.myControl;

import agtp.AGTP;
import agtp.utils.AGTPFont;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 *
 * @author reza
 */
public class MyButtonFont extends Button {

    public MyButtonFont() {
    }

    public MyButtonFont(String text) {
        super(text);
    }

    public MyButtonFont(String text, Node graphic) {
        super(text, graphic);
    }

    
    
    public MyButtonFont(String text, int fontSize, String style) {
        Font font = Font.loadFont(AGTP.class.getResource("resourse/agtp_font.ttf").toExternalForm(), fontSize);
        this.setFont(font);
        this.setText(AGTPFont.Icons.valueOf(text).getIcon());
        getStyleClass().add(style);
    }

    public MyButtonFont(String text, int fontSize) {
        Font font = Font.loadFont(AGTP.class.getResource("resourse/agtp_font.ttf").toExternalForm(), fontSize);
        this.setFont(font);
        this.setText(AGTPFont.Icons.valueOf(text).getIcon());
        setStyle(getStyle() + "-fx-label-padding: -2;");
    }

    public void init(String text, int fontSize, String style) {
        Font font = Font.loadFont(AGTP.class.getResource("resourse/agtp_font.ttf").toExternalForm(), fontSize);
        this.setFont(font);
        this.setText(AGTPFont.Icons.valueOf(text).getIcon());
        getStyleClass().add(style);
    }
    
    public void init(String text, int fontSize) {
        Font font = Font.loadFont(AGTP.class.getResource("resourse/agtp_font.ttf").toExternalForm(), fontSize);
        this.setFont(font);
        this.setText(AGTPFont.Icons.valueOf(text).getIcon());
        setStyle(getStyle() + "-fx-label-padding: -2;");
    }
}
