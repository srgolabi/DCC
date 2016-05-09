package dccletter.fxml.letterList;

import static dccletter.DCCLetter.server;
import dccletter.dataBase.tables.LetterFile;
import dccletter.fxml.letterInsert.FXMLLetterInsertController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLFileListController implements Initializable {

    public Stage thisStage;

    @FXML
    private TableView<LetterFile> fileSelected;

    /**
     * Initializes the controller class.
     */
    public void init(Stage s, List<LetterFile> list) {
        thisStage = s;
        fileSelected.setRowFactory((TableView<LetterFile> param) -> {
            TableRow<LetterFile> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                try {
                    if (row.getIndex() == fileSelected.getSelectionModel().getSelectedIndex() && event.getClickCount() >= 2) {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + server + fileSelected.getSelectionModel().getSelectedItem().getAddress());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FXMLLetterInsertController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
        fileSelected.getItems().setAll(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
