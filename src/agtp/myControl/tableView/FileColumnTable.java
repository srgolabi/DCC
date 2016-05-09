package agtp.myControl.tableView;

import agtp.myControl.MyButtonFont;
import static dccletter.DCCLetter.server;
import dccletter.dataBase.tables.LetterFile;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import agtp.utils.FilterMenuButton;

/**
 *
 * @author reza
 */
public class FileColumnTable extends Temmp<LetterFile, TableColumn<LetterFile, LetterFile>, TableCell<LetterFile, LetterFile>> {

    private boolean editable;

    public FileColumnTable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public TableCell<LetterFile, LetterFile> call(TableColumn<LetterFile, LetterFile> param) {
        TableCell<LetterFile, LetterFile> cell = new TableCell<LetterFile, LetterFile>() {
            @Override
            protected void updateItem(LetterFile item, boolean empty) {
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

                final HBox hbox = new HBox(0);
                hbox.setAlignment(Pos.CENTER_LEFT);

                MyButtonFont fileDownlaod = new MyButtonFont("download_1", 14, "table-button");

                fileDownlaod.setOnAction((javafx.event.ActionEvent event) -> {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files (*." + getTypeFile(item.getName()) + ")", "*." + getTypeFile(item.getName()));
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showSaveDialog(this.getScene().getWindow());
                    if (file == null) {
                        return;
                    }
                    try {
                        FileUtils.copyFile(new File(item.getAddress()), file);
                    } catch (IOException ex) {
                        Logger.getLogger(FileColumnTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                FilterMenuButton fileView = new FilterMenuButton(getTypeFile(item.getName()) + "-button");
                fileView.setOnAction((ActionEvent event) -> {
                    runFile(item.getAddress(), item.getId() != null);
                });

                if (editable) {
                    MyButtonFont fileDelete = new MyButtonFont("trash", 14, "table-button");

                    fileDelete.setOnAction((javafx.event.ActionEvent event) -> {
                        if (delete != null) {
                            delete.add(this.getTableView().getItems().get(this.getIndex()));
                        }
                        this.getTableView().getItems().remove(getIndex());
                    });

                    TextField text = new TextField(item.getTitle());
                    text.setAlignment(Pos.CENTER);
                    text.setStyle(text.getStyle() + "-fx-font-size : 12;");
                    text.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                        item.setTitle(text.getText());
                    });

                    HBox.setHgrow(text, Priority.ALWAYS);
                    final VBox Vbox = new VBox(0);
                    MyButtonFont fileUp = new MyButtonFont("up_open", 7, "table-button");

                    MyButtonFont fileDown = new MyButtonFont("down_open", 7, "table-button");

                    Vbox.setAlignment(Pos.CENTER);
                    Vbox.getChildren().addAll(fileUp, fileDown);

                    fileDown.setOnAction((ActionEvent event) -> {
                        if (getIndex() < getTableView().getItems().size() - 1) {
                            LetterFile temp = getTableView().getItems().get(getIndex());
                            getTableView().getItems().set(getIndex(), getTableView().getItems().get(getIndex() + 1));
                            getTableView().getItems().set(getIndex() + 1, temp);
                        }
                    });
                    fileUp.setOnAction((ActionEvent event) -> {
                        if (getIndex() > 0) {
                            LetterFile temp = getTableView().getItems().get(getIndex());
                            getTableView().getItems().set(getIndex(), getTableView().getItems().get(getIndex() - 1));
                            getTableView().getItems().set(getIndex() - 1, temp);
                        }
                    });

                    hbox.getChildren().addAll(fileDelete, fileDownlaod, text, fileView, Vbox);
                } else {
//                    HBox.setMargin(fileView, new Insets(0, 20, 0, 0));
                    Label title = new Label(item.getTitle());
                    HBox.setHgrow(title, Priority.ALWAYS);
                    title.setMaxWidth(Integer.MAX_VALUE);
                    title.setAlignment(Pos.CENTER);
                    title.setStyle(title.getStyle() + "-fx-font-size : 12;");
                    hbox.getChildren().addAll(fileDownlaod, fileView, title);
                }

                setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() >= 2) {
                        runFile(item.getAddress(), item.getId() != null);
                    }
                });

                setGraphic(hbox);

            }
        };
        return cell;
    }

    public String getTypeFile(String items) {
        try {
            return items.substring(items.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return null;
        }
    }

    public void runFile(String pach, boolean b) {
        try {
            if (b) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + server + pach);
            } else {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pach);
            }

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }
}
