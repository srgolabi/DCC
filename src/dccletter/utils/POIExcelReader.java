/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import static dccletter.DCCLetter.databaseHelper;
import agtp.dataBase.tables.Companies;
import agtp.dataBase.tables.History;
import agtp.dataBase.tables.Users2;
import dccletter.dataBase.tables.LetterAction;
import dccletter.dataBase.tables.LetterToCompany;
import document.dataBase.tables.Documents_Rev;
import document.dataBase.tables.Transmittal;
import document.dataBase.tables.Trans_Doc;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import sun.util.calendar.LocalGregorianCalendar;

/**
 *
 * @author reza
 */
public class POIExcelReader {

    /**
     * Creates a new instance of POIExcelReader
     */
    public POIExcelReader() {
    }

    /**
     * 41 This method is used to display the Excel content to command line. 42 *
     *
     * @param xlsPath
     */
    
//    
//    @SuppressWarnings("unchecked")
//    public void displayFromExcel(String xlsPath) {
//        InputStream inputStream = null;
//
//        try {
//            inputStream = new FileInputStream(xlsPath);
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found in the specified path.");
//            e.printStackTrace();
//        }
//        List<Documents_Rev> doc_list = new ArrayList<>();
//        List<Transmittal> trans_list = new ArrayList<>();
//        List<TransmittalDocument> doc_trans_list = new ArrayList<>();
//
//        POIFSFileSystem fileSystem = null;
//
//        try {
//            fileSystem = new POIFSFileSystem(inputStream);
//
//            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
//            HSSFSheet sheet = workBook.getSheetAt(0);
//            Iterator rows = sheet.rowIterator();
//
//            while (rows.hasNext()) {
//                HSSFRow row = (HSSFRow) rows.next();
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
//                Transmittal transmittal = null;
//                Documents_Rev documents = new Documents_Rev();
////                Letters letters = null;
//
////                Companies to = null;
////                Actions response1 = null;
////                Actions response2 = null;
////                Actions response3 = null;
//// display row number in the console.
//                System.out.println("Row No.: " + row.getRowNum());
//
//// once get a row its time to iterate through cells.
//                Iterator cells = row.cellIterator();
//
//                while (cells.hasNext()) {
//                    HSSFCell cell = (HSSFCell) cells.next();
//
//                    System.out.println("Cell No.: " + cell.getCellNum());
//
//                    /*
//                     * Now we will get the cell type and display the values
//                     * accordingly.
//                     */
//                    switch (cell.getCellNum()) {
//                        case 0:
//                            transmittal = databaseHelper.transmittalDao.getFirst("tranc_number", cell.getRichStringCellValue().getString());
//                            if (transmittal == null) {
//                                transmittal = new Transmittal();
//                                transmittal.setTranc_number(cell.getRichStringCellValue().getString());
//                            }
//                            break;
//                        case 1:
//                            break;
//                        case 2:
//                            transmittal.setAtt(cell.getRichStringCellValue().getString());
//                            break;
//                        case 3:
//                            // from
//                            Companies from = databaseHelper.companiesDao.getFirst("company_en", cell.getRichStringCellValue().getString());
//                            if (from != null) {
//                                transmittal.setFrom(from);
//                            }
//                            break;
//
//                        case 4:
//                            Users2 disipline = databaseHelper.users2Dao.getFirst("name_en", cell.getRichStringCellValue().getString());
//                            if (disipline != null) {
//                                transmittal.setDiscipline(disipline);
//                            }
//                            break;
//                        case 5:
//                            String[] doc_number_B = cell.getRichStringCellValue().getString().split("-");
//                            documents.setDocumentNo(cell.getRichStringCellValue().getString());
//                            documents.setProjectNo(doc_number_B[0]);
//                            documents.setPhaseCode(doc_number_B[1]);
//                            documents.setAreaNo(doc_number_B[2]);
//                            documents.setUnitNo(doc_number_B[3]);
//                            if (doc_number_B[0].equals("4865")) {
//                                documents.setDisciplineCode(doc_number_B[5]);
//                                documents.setDocType(doc_number_B[4]);
//                            } else {
//                                documents.setDocType(doc_number_B[5]);
//                                documents.setDisciplineCode(doc_number_B[4]);
//                            }
//                            documents.setSequential(doc_number_B[6]);
//                            break;
//                        case 7:
//                            documents.setTitle(cell.getRichStringCellValue().getString());
//                            break;
//                        case 8:
//                            documents.setClas(cell.getRichStringCellValue().getString());
//                            break;
//                        case 9:
//                            documents.setRev(cell.getRichStringCellValue().getString());
//                            break;
//                        case 10:
//                            documents.setPoi(cell.getRichStringCellValue().getString());
//                            break;
//                        case 11:
//                            try {
//                                documents.setPageCount((int) cell.getNumericCellValue());
//                            } catch (Exception e) {
//                            }
//                            break;
//                        case 12:
//                            try {
//                                documents.setCopyCount((int) cell.getNumericCellValue());
//                            } catch (Exception e) {
//                            }
//                            break;
//                        case 13:
//                            documents.setPaperSize(cell.getRichStringCellValue().getString());
//                            break;
//                        case 14:
//                            if (cell.getDateCellValue().equals(null)) {
//                                break;
//                            }
//
//                            Date dd = cell.getDateCellValue();
//                            History history = databaseHelper.historyDao.getFirst("date", (dd.getYear() + "").substring(1)
//                                    + "/" + (dd.getMonth() > 8 ? (dd.getMonth() + 1) : "0" + (dd.getMonth() + 1))
//                                    + "/" + (dd.getDate() > 9 ? dd.getDate() : "0" + dd.getDate()));
//
//                            if (history == null) {
//                                history = new History(
//                                        (dd.getYear() + "").substring(1),
//                                        (dd.getMonth() > 8 ? (dd.getMonth() + 1) : "0" + (dd.getMonth() + 1)) + "",
//                                        (dd.getDate() > 9 ? dd.getDate() : "0" + dd.getDate()) + ""
//                                );
//                                databaseHelper.historyDao.createOrUpdate(history);
//                            }
//
////                            documents.setDocument_date(history);
//                            break;
//                    }
//                    switch (cell.getCellType()) {
//                        case HSSFCell.CELL_TYPE_NUMERIC: {
//                            // cell type numeric.
//                            System.out.println("Numeric value: " + cell.getNumericCellValue());
//                            break;
//                        }
//
//                        case HSSFCell.CELL_TYPE_STRING: {
//                            // cell type string.
//                            HSSFRichTextString richTextString = cell.getRichStringCellValue();
//                            System.out.println("String value: " + richTextString.getString());
//                            break;
//                        }
//
//                        default: {
//                            // types other than String and Numeric.
//                            System.out.println("Type not supported.");
//                            break;
//                        }
//                    }
//                }
////                trans_list.add(transmittal);
////                doc_trans_list.add(new TransmittalDocument(documents, transmittal));
////                doc_list.add(documents);
//                databaseHelper.documents_REVDao.createOrUpdate(documents);
//                databaseHelper.transmittalDao.createOrUpdate(transmittal);
//                databaseHelper.transmittalDocument.createOrUpdate(new TransmittalDocument(documents, transmittal));
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        try {
////            databaseHelper.transmittalDao.insertList(trans_list);
////            databaseHelper.documentsDao.insertList(doc_list);
////            databaseHelper.transmittalDocument.insertList(doc_trans_list);
////        } catch (SQLException ex) {
////            Logger.getLogger(POIExcelReader.class.getName()).log(Level.SEVERE, null, ex);
////        }
//
//    }
//
//    @SuppressWarnings("unchecked")
//    public void displayFromExcel2(String xlsPath) {
//        InputStream inputStream = null;
//
//        try {
//            inputStream = new FileInputStream(xlsPath);
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found in the specified path.");
//            e.printStackTrace();
//        }
//
//        POIFSFileSystem fileSystem = null;
//
//        List<Documents_Rev> lds = new ArrayList<>();
//        try {
//            fileSystem = new POIFSFileSystem(inputStream);
//
//            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
//            HSSFSheet sheet = workBook.getSheetAt(0);
//            Iterator rows = sheet.rowIterator();
//            boolean b = true;
//
//            while (rows.hasNext()) {
//                b = false;
//
//                HSSFRow row = (HSSFRow) rows.next();
//                Documents_Rev documents = new Documents_Rev();
//
//// display row number in the console.
//                System.out.println("Row No.: " + row.getRowNum());
//
//// once get a row its time to iterate through cells.
//                Iterator cells = row.cellIterator();
//
//                while (cells.hasNext()) {
//                    HSSFCell cell = (HSSFCell) cells.next();
//
//                    System.out.println("Cell No.: " + cell.getCellNum());
//
//                    /*
//                     * Now we will get the cell type and display the values
//                     * accordingly.
//                     */
//                    switch (cell.getCellNum()) {
//                        case 0:
//                            b = true;
//                            try {
////                                System.err.println("ooo ==  " + cell.getRichStringCellValue());
//                                if (!cell.getRichStringCellValue().getString().equals("")) {
//                                    String[] doc_number_B = cell.getRichStringCellValue().getString().split("-");
//                                    if (doc_number_B.length < 5) {
//                                        b = false;
//                                    }
//                                    if (databaseHelper.documents_REVDao.getFirst("documentNo", cell.getRichStringCellValue().getString()) != null) {
//                                        b = false;
//                                    }
//                                    documents.setDocumentNo(cell.getRichStringCellValue().getString());
//                                    documents.setProjectNo(doc_number_B[0]);
//                                    documents.setPhaseCode(doc_number_B[1]);
//                                    documents.setAreaNo(doc_number_B[2]);
//                                    documents.setUnitNo(doc_number_B[3]);
//                                    if (doc_number_B[0].equals("4865")) {
//                                        documents.setDisciplineCode(doc_number_B[5]);
//                                        documents.setDocType(doc_number_B[4]);
//                                    } else {
//                                        documents.setDocType(doc_number_B[5]);
//                                        documents.setDisciplineCode(doc_number_B[4]);
//                                    }
//                                    documents.setSequential(doc_number_B[6]);
//                                } else {
//                                    System.err.println("nnn ==  ");
//                                    b = false;
//                                }
//                            } catch (Exception e) {
//                                System.err.println("nnn ==  ");
//                                b = false;
//                            }
//                            break;
//                        case 1:
//                            try {
//                                documents.setTitle(cell.getRichStringCellValue().getString());
//                            } catch (Exception e) {
//                            }
//                            break;
//                        case 2:
//                            try {
//                                documents.setClas(cell.getRichStringCellValue().getString());
//                            } catch (Exception e) {
//                            }
//                            break;
//                        case 3:
//                            try {
//                                documents.setRev(cell.getRichStringCellValue().getString());
//                            } catch (Exception e) {
//                            }
//                            break;
//                        case 4:
//                            try {
//                                documents.setPoi(cell.getRichStringCellValue().getString());
//                            } catch (Exception e) {
//                            }
//                            break;
//                    }
//                    switch (cell.getCellType()) {
//                        case HSSFCell.CELL_TYPE_NUMERIC: {
//                            // cell type numeric.
//                            System.out.println("Numeric value: " + cell.getNumericCellValue());
//                            break;
//                        }
//                        case HSSFCell.CELL_TYPE_BLANK: {
//                            System.out.println("Blank value");
//                        }
//                        case HSSFCell.CELL_TYPE_STRING: {
//                            // cell type string.
//                            HSSFRichTextString richTextString = cell.getRichStringCellValue();
//                            System.out.println("String value: " + richTextString.getString());
//                            break;
//                        }
//
//                        default: {
//                            // types other than String and Numeric.
//                            System.out.println("Type not supported.");
//                            break;
//                        }
//                    }
//                }
//                if (b) {
//                    lds.add(documents);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            databaseHelper.documents_REVDao.insertList(lds);
//        } catch (SQLException ex) {
//            Logger.getLogger(POIExcelReader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    
    
}
