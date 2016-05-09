package document.fxml.document_insert;

import agtp.dataBase.DatabaseHelper;
import agtp.utils.ParentControl;
import agtp.utils.TextFiledLimited;
import static dccletter.DCCLetter.databaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class Fxml_Document_Insert extends ParentControl {

    @FXML
    private TextField project_no;
    @FXML
    private TextField phase_code;
    @FXML
    private TextField area_number;
    @FXML
    private TextField unit_number;
    @FXML
    private TextField document_type;
    @FXML
    private TextField discipline_code;
    @FXML
    private TextField sequential_no;
    @FXML
    private Button number_detail;

    @FXML
    private TableView revision_table;
    @FXML
    private Button revision_add;
    @FXML
    private Button revision_edit;
    @FXML
    private Button revision_remove;

    @FXML
    private TableView transmittal_table;
    @FXML
    private Button transmittal_add;

    @FXML
    private TableView file_table;
    @FXML
    private Button file_add;

    @FXML
    private Button quit;
    @FXML
    private Button save;
    @FXML
    private Button clear_main;
    @FXML
    private Button search;

    @FXML
    private HBox search_controls;
    @FXML
    private Label search_Resault;
    @FXML
    public Button search_Next;
    @FXML
    public Button search_Back;
    @FXML
    public Button search_first;
    @FXML
    public Button search_end;

    @FXML
    private VBox revision_page;
    @FXML
    private TextField rev;
    @FXML
    private TextField title;
    @FXML
    private TextField clas;
    @FXML
    private TextField pages;
    @FXML
    private TextField copies;
    @FXML
    private TextField size;
    @FXML
    private TextField poi;
    @FXML
    private Button revision_submit;
    @FXML
    private Button revision_back;
    @FXML
    private Button revision_clear;

    @FXML
    private VBox search_page;
    @FXML
    private TextField title_s;
    @FXML
    private TextField project_no_s;
    @FXML
    private TextField phase_code_s;
    @FXML
    private TextField area_number_s;
    @FXML
    private TextField unit_number_s;
    @FXML
    private TextField document_type_s;
    @FXML
    private TextField discipline_code_s;
    @FXML
    private TextField sequential_no_s;
    @FXML
    private TextField rev_s;
    @FXML
    private TextField clas_s;
    @FXML
    private TextField pages_s;
    @FXML
    private TextField copies_s;
    @FXML
    private TextField size_s;
    @FXML
    private TextField poi_s;
    @FXML
    private Button search_go;
    @FXML
    private Button search_back;
    @FXML
    private Button search_clear;

    @FXML
    private VBox number_concept_page;

    @Override
    public void setStage(Stage s) {
        super.setStage(s);
        TextFiledLimited.setEnterFocuse(
                project_no, phase_code, area_number, unit_number, document_type,
                discipline_code, sequential_no, number_detail, revision_table,
                revision_add, revision_edit, transmittal_table, transmittal_add,
                file_table, file_add, quit, save, clear_main, search
        );

        TextFiledLimited.autoCompleteText_1(project_no, databaseHelper.definitionCodeDao, "SELECT title FROM definition WHERE key LIKE 'Project No' ORDER BY title ASC");
        TextFiledLimited.autoCompleteText_1(phase_code, databaseHelper.definitionCodeDao, "SELECT title FROM definition WHERE key LIKE 'Phase Code' ORDER BY title ASC");
        TextFiledLimited.autoCompleteText_1(area_number, databaseHelper.definitionCodeDao, "SELECT title FROM definition WHERE key LIKE 'Area codes' ORDER BY title ASC");
        TextFiledLimited.autoCompleteText_1(unit_number, databaseHelper.definitionCodeDao, "SELECT title FROM definition WHERE key LIKE 'UnitCode' ORDER BY title ASC");
        TextFiledLimited.autoCompleteText_1(document_type, databaseHelper.definitionCodeDao, "SELECT title FROM definition WHERE key LIKE 'DocumentTyp' ORDER BY title ASC");
        TextFiledLimited.autoCompleteText_1(discipline_code, databaseHelper.definitionCodeDao, "SELECT title FROM definition WHERE key LIKE 'DisciplineCode' ORDER BY title ASC");

    }

}
