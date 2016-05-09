package agtp.utils;

import javafx.scene.control.Button;

/**
 * A button that controls displaying the filter menu when clicked
 *
 * @author r.golabi
 *
 */
public class FilterMenuButton
        extends Button {


    public FilterMenuButton(String st) {
        getStyleClass().add("public-button");
        getStyleClass().add(st);

    }

}
