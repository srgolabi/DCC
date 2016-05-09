package agtp.myControl.tableView;

import agtp.dataBase.tables.Base;
import agtp.myControl.MyButtonFont;
import dccletter.utils.menu.TableMouseClick;
import static java.lang.Integer.MAX_VALUE;
import java.util.List;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author reza
 */
public class MyColumnTable<S> extends Temmp<S, TableColumn<S, S>, TableCell<S, S>> implements TableMouseClick<S> {

    TableView<S> tableViewSelect;
    public boolean is_EN = false;

    public MyColumnTable(TableView<S> tableViewSelect, Cursor mouseIcon) {
        this.tableViewSelect = tableViewSelect;

        if (this.tableViewSelect != null) {
            this.tableViewSelect.setRowFactory(tv -> new TableRow<S>() {
                private Tooltip tooltip = new Tooltip();

                @Override
                protected void updateItem(S s, boolean empty) {
                    super.updateItem(s, empty);
                    setOnMouseClicked((MouseEvent event) -> {
                        if (getItem() != null && event.getClickCount() >= 2) {
                            try {
                                onDoubleClick(s);
                            } catch (Exception e) {
                            }
                        }
                    });
                    if (s == null) {
                        setCursor(Cursor.DEFAULT);
                        setTooltip(null);
                    } else {
                        setCursor(mouseIcon);
                        setStyle(getStyle() + "-fx-font-size : 13;");
                        tooltip.setText(((Base) s).getToolTipValue());
                        if (!tooltip.getText().isEmpty()) {
                            setTooltip(tooltip);
                        }
                    }

                }

            });

        }
    }

    @Override
    public FilteredList<S> init(TableView<S> tableViewMenu, TextField field, List<S> list) {

        FilteredList<S> filteredlist = super.init(tableViewMenu, field, list);

        tableViewMenu.setRowFactory(tv -> new TableRow<S>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            public void updateItem(S s, boolean empty) {
                super.updateItem(s, empty);
                if (s == null) {
                    setTooltip(null);
                    setCursor(Cursor.DEFAULT);
                } else {
                    setCursor(Cursor.CLOSED_HAND);

                    setStyle(getStyle() + "-fx-font-size : 13;");
                    tooltip.setText(((Base) s).getToolTipValue());
                    setTooltip(tooltip);
                    this.setOnMouseClicked((MouseEvent event) -> {
                        if (this.getItem() != null) {
                            field.requestFocus();
                            visibleB.set(false);
                            if (tableViewSelect != null) {
                                field.setText("");
                            }
                            addToMenu.action(tableViewMenu.getSelectionModel().getSelectedItem());
                        }
                    });
                }

            }
        });
        return filteredlist;
    }

    @Override
    public TableCell<S, S> call(TableColumn<S, S> param) {
        TableCell<S, S> cell = new TableCell<S, S>() {
            @Override
            protected void updateItem(S item, boolean empty) {
                if (item == getItem()) {
                    return;
                }
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle(null);
                    setText(null);
                    setGraphic(null);
                    return;
                }
                final HBox hbox = new HBox(5);
                hbox.setAlignment(Pos.CENTER_LEFT);
                
                setStyle(((Base) item).getStyle());
                MyButtonFont fileDelete = new MyButtonFont("trash", 14, "table-button");
                fileDelete.setOnAction((javafx.event.ActionEvent event) -> {
                    if (delete != null) {
                        delete.add(this.getTableView().getItems().get(this.getIndex()));
                    }
                    this.getTableView().getItems().remove(getIndex());
                });
                Label title = new Label(((Base) item).getCulomnValue());

                HBox.setHgrow(title, Priority.ALWAYS);
                title.setMaxWidth(MAX_VALUE);
                title.setAlignment(Pos.CENTER);
                final VBox vbox = new VBox(0);
                MyButtonFont fileUp = new MyButtonFont("up_open", 7, "table-button");

                MyButtonFont fileDown = new MyButtonFont("down_open", 7, "table-button");
                vbox.setAlignment(Pos.CENTER);

                vbox.getChildren().addAll(fileUp, fileDown);

                fileDown.setOnAction((ActionEvent event) -> {
                    if (getIndex() < getTableView().getItems().size() - 1) {
                        S temp = getTableView().getItems().get(getIndex());
                        getTableView().getItems().set(getIndex(), getTableView().getItems().get(getIndex() + 1));
                        getTableView().getItems().set(getIndex() + 1, temp);
                    }
                });
                fileUp.setOnAction((ActionEvent event) -> {
                    if (getIndex() > 0) {
                        S temp = getTableView().getItems().get(getIndex());
                        getTableView().getItems().set(getIndex(), getTableView().getItems().get(getIndex() - 1));
                        getTableView().getItems().set(getIndex() - 1, temp);
                    }
                });
                hbox.getChildren().addAll(fileDelete, title, vbox);
                setGraphic(hbox);
            }
        };
        return cell;
    }

    @Override
    public void handle(KeyEvent event) {
        super.handle(event);
        switch (event.getCode()) {
            case ENTER:
                addToMenu.action(tableViewMenu.getSelectionModel().getSelectedItem());
                break;
        }
    }

    @Override
    public void onDoubleClick(S s) {
    }
}
