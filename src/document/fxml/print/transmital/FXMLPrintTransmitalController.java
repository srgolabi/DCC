/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.fxml.print.transmital;

import dccletter.utils.ParentControl;
import dccletter.utils.UtilsMsg;
import document.DocumentSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reza
 */
public class FXMLPrintTransmitalController extends ParentControl {

    @FXML
    private VBox container;
    @FXML
    private Button print;

    /**
     * Initializes the controller class.
     */
    @Override
    public void setStage(Stage s) {
        super.setStage(s);
        UtilsMsg utilsStage = new UtilsMsg("print/transmital/FXMLPrintTransmital.fxml", "سیستم ثبت ", Modality.NONE, thisStage.getOwner(), DocumentSystem.class);
//        utilsStage.getLoader().l.setPadding(Insets.EMPTY);
        container.getChildren().add(utilsStage.page);

        print.setOnAction((ActionEvent event) -> {
            PrinterJob printerJob = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());

            if (printerJob != null) {
                System.out.println("sdadsadsad");
                try {
                    System.out.println("q");
//                    FXMLLoader loader = new FXMLLoader(GateBass.class.getResource("fxml/print/FXMLKhodroBackPrint.fxml"));
//                    FXMLLoader loader = new FXMLLoader(GateBass.class.getResource("temporary/FXMLTemporaryCard.fxml"));
//                    Parent root = (Parent) loader.load();
//                    FXMLKhodroBackPrintController myController = loader.getController();
//
//                    Scene scene = new Scene(root);
//                    stage.setScene(scene);
                    System.out.println("" + printerJob);
                    Printer printer = Printer.getDefaultPrinter();
                    PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
                    printerJob.getJobSettings().setPageLayout(layout);
                    Boolean succes = printerJob.printPage(container);
                    if (succes) {
                        printerJob.endJob();
                    }

                } catch (Exception e) {
                }

            }
        });
    }

}
