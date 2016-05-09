package dccletter.fxml.letterTableFull;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 *
 * @author reza
 */
public class FormattedTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    @SuppressWarnings("unchecked")
    public TableCell<S, T> call(TableColumn<S, T> p) {
        Tooltip tooltip = new Tooltip();
        CheckBox checkBox = new CheckBox();
        checkBox.setDisable(true);
        checkBox.setStyle("-fx-opacity: 1;-fx-font-size:11.5;");

        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            public void updateItem(Object item, boolean empty) {
                if (item == getItem()) {
                    return;
                }
                super.updateItem((T) item, empty);

                if (item == null) {
                    setTooltip(null);
                    super.setText(null);
                    super.setGraphic(null);
                } else if (item instanceof Boolean) {
                    checkBox.setSelected((boolean) item);
                    super.setText(null);
                    super.setGraphic(checkBox);
                } else {
                    tooltip.setText(item.toString());
                    setTooltip(tooltip);                    
                    super.setText(item.toString());
                    super.setGraphic(null);
                }
            }
        };
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setAlignment(Pos.CENTER);
        return cell;
    }
}
