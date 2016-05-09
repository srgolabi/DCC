package agtp.fxml.companyManage;

import dccletter.DCCLetter;
import agtp.dataBase.tables.Companies;
import agtp.myControl.MyButtonFont;
import agtp.myControl.tableView.PrepareTable;
import static dccletter.DCCLetter.databaseHelper;
import dccletter.utils.ErrorCheck;
import dccletter.utils.ParentControl;
import dccletter.utils.UtilsMsg;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLcompanyManageController extends ParentControl {

    @FXML
    private VBox step_1;
    @FXML
    private VBox step_2;

    @FXML
    private MyButtonFont add;
    @FXML
    private MyButtonFont remove;
    @FXML
    private MyButtonFont edit;
    @FXML
    private TableView<Companies> company_table;
    @FXML
    private TextField filter_company_table;

    @FXML
    private CheckBox is_deactive;
    @FXML
    private TextField company_name_fa;
    @FXML
    private TextField company_name_en;
    @FXML
    private TextField summary;

    @FXML
    private Button back_2;
    @FXML
    private Button submit_2;

    private Companies editCompanies;

    /**
     * Initializes the controller class.
     */
    private void user_table_init() {
        filter_company_table.setText("");
        FilteredList<Companies> filteredListt = new PrepareTable<Companies>().init(company_table, databaseHelper.companiesDao.rawResults(
                "SELECT * FROM companies WHERE is_deleted = 0 ORDER BY company_fa"
        ));
        filter_company_table.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredListt.setPredicate(s -> (newValue == null || newValue.length() == 0) ? true : (s.getCompany_fa() + s.getCompany_en()).toLowerCase().contains(newValue.toLowerCase()));
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        step_1.setVisible(true);
        step_2.setVisible(false);
        step_1_init();
        step_2_init();
    }

    private void step_1_init() {
        remove.disableProperty().bind(company_table.getSelectionModel().selectedItemProperty().isNull());
        edit.disableProperty().bind(remove.disableProperty());

        add.init("plus", 15);
        remove.init("trash", 15);
        edit.init("pencil", 15);

        add.setOnAction((ActionEvent event) -> {
            step_1.setVisible(false);
            step_2.setVisible(true);
            editCompanies = new Companies();

        });

        remove.setOnAction((ActionEvent event) -> {
            Companies u2 = company_table.getSelectionModel().getSelectedItem();
            if (!UtilsMsg.showMsg("آیا از حذف " + u2.getCompany_fa() + " مطمئن هستید؟", "هشدار", true, thisStage)) {
                return;
            }
            u2.setIs_deleted(Boolean.TRUE);
            databaseHelper.companiesDao.createOrUpdate(u2);
            user_table_init();
        });

        edit.setOnAction((ActionEvent event) -> {
            add.getOnAction().handle(event);
            editCompanies = company_table.getSelectionModel().getSelectedItem();
            is_deactive.setSelected(!editCompanies.getActive());
            company_name_fa.setText(editCompanies.getCompany_fa());
            company_name_en.setText(editCompanies.getCompany_en());
            summary.setText(editCompanies.getSummary());
        });

        company_table.setRowFactory((TableView<Companies> param) -> {
            TableRow<Companies> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() >= 2 && !edit.isDisable()) {
                    edit.getOnAction().handle(null);
                }
            });
            return row;
        });
        user_table_init();
    }

    private void step_2_init() {
        back_2.setOnAction((ActionEvent event) -> {
            step_1.setVisible(true);
            step_2.setVisible(false);
            company_name_fa.setText("");
            company_name_en.setText("");
            summary.setText("");
            is_deactive.setSelected(false);
            user_table_init();
        });

        submit_2.setOnAction((ActionEvent event) -> {
            ErrorCheck errorCheck = new ErrorCheck("نام شرکت", "Company Name");
            if (errorCheck.checked(false, "خطا", thisStage, company_name_fa, company_name_en) != -1) {
                return;
            }
            if (editCompanies.getId() == null) {
                String query = "SELECT * FROM companies WHERE is_deleted = 0 AND ( company_fa LIKE '" + company_name_fa.getText() + "' OR company_en LIKE '" + company_name_en.getText() + "' )";
                if (!DCCLetter.databaseHelper.companiesDao.rawResults(query).isEmpty()) {
                    UtilsMsg.showMsg("اطلاعات وارد شده تکراری می باشد.", "اخطار", false, thisStage);
                    return;
                }
            }
            editCompanies.setCompany_fa(company_name_fa.getText());
            editCompanies.setCompany_en(company_name_en.getText());
            editCompanies.setSummary(summary.getText());
            editCompanies.setActive(!is_deactive.isSelected());
            if (editCompanies.getId() == null) {
                DCCLetter.databaseHelper.companiesDao.createOrUpdate(editCompanies);
                editCompanies.setFolder_name("F" + editCompanies.getId());
            }
            DCCLetter.databaseHelper.companiesDao.createOrUpdate(editCompanies);
            user_table_init();
            back_2.getOnAction().handle(event);
        });

    }

}
