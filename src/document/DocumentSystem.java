package document;

import static dccletter.DCCLetter.databaseHelper;
import dccletter.utils.POIExcelReader;
import document.dataBase.tables.Definition;

/**
 *
 * @author reza
 */
public class DocumentSystem {

    public static void importFromExcel() {
        POIExcelReader poiExample = new POIExcelReader();
//        poiExample.displayFromExcel("d://test//ISO Table Query.xls");
//        poiExample.displayFromExcel2("d://test//mdl merg.xls");
    }

    public static void initDB() {
        if (!databaseHelper.definitionCodeDao.getAll().isEmpty()) {
            return;
        }

        { // Project No
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("4865", "", "Project No"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("TJRF", "", "Project No"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PG11", "", "Project No"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PG12", "", "Project No"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("TXP", "", "Project No"));
        }

        { // Phase Code
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("G", "General", "Phase Code"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("BE", "Basic Engineering", "Phase Code"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DE", "Detail Engineering", "Phase Code"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Procurement", "P", "Phase Code"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Construction", "C", "Phase Code"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DD", "Detail Design", "Phase Code"));
        }

        { // Area codes
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("10", "(Glycols , Acrylonitrile & Process Oil)", "Area codes"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("20", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)", "Area codes"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("30", "(α-Olefin)", "Area codes"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("40", "(Cooling Tower & Flare)", "Area codes"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("00", "(General)", "Area codes"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("50", "(Offsite)", "Area codes"));

            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Mono Ethylene Glycol", "01", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Diethylene Glycol", "02", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Triethylene Glycol", "03", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Acrylonitrile", "04", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Process Oil", "05", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Closed Drain", "40", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Process Metering (Inlet)", "", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Utility Metering (Inlet)", "", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Gas Station", "80", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Analyzer Room 1", "50", "(Glycols , Acrylonitrile & Process Oil)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Let-down Station", "88", "(Glycols , Acrylonitrile & Process Oil)"));

            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Butane", "06", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Propane", "07", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Ethylene", "08", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Methanol", "09", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Benzene", "10", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Process Metering (Outlet)", "", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Operator Bulding", "86", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Analyzer Room 2", "50", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Sub Station 1", "62", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Styrene", "14", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Acetic Acid", "11", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Closed Drain", "40", "(Methanol, Ethylene, Propane, Butane, Benzene, Styrene, Acetic Acid)"));

            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Xylene-para", "12", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Butene-1", "13", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Heavy Ends", "15", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Xylene-Ortho", "16", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Butadiene", "17", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Raffinate", "18", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Hexene-1", "19", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Process Metering (Inlet)", "", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Process Metering (Outlet)", "", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Xylene-para", "12", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Guard House 2", "90", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Analyzer Room 3", "50", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Sub Station 3", "62", "(α-Olefin)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Cooling Tower", "82", "(α-Olefin)"));

            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Flare", "", "(Cooling Tower & Flare)"));

            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("General", "00", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Control Room, Administrative, Laboratory", "53", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Warehouse", "56", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Play Yard", "58", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Fire Station", "60", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Parking", "68", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Truck Parking", "70", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Warehouse Yard", "72", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Master Point", "74", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Workshop", "76", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Maintenance Area", "78", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Sub Station 2", "62", "(General)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Guard House 1", "90", "(General)"));

            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("HP Steam", "20", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Steam Return", "20", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Service Water", "22", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Potable Water", "23", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Nitrogen", "29", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Instrument Air", "30", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Plant Air", "31", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("COC", "34", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("POC", "35", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("SN", "36", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Cooling Blowdown", "26", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Demin Water", "27", "(Offsite)"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("Natural Gas(Fuel)", "32", "(Offsite)"));
        }

        { // UnitCode
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("00", "General", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("01", "Mono Ethylene Glycol", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("02", "Diethylene Glycol", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("03", "Triethylene Glycol", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("04", "Acrylonitril", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("05", "Process Oil", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("06", "Butane", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("07", "Propane", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("08", "Ethylene", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("09", "Methanol", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("10", "Benzene", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("11", "Acetic Acid", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("12", "Xylene-para", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("13", "Butene-1", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("14", "Styrene", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("15", "Heavy Ends", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("16", "Xylene-Ortho", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("17", "Butadiene", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("18", "Raffinate", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("19", "Hexene-1", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("20", "Steam (all related equipment)", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("21", "Cond. Return", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("22", "Service Water", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("23", "Potable Water", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("24", "Cooling Water make up (RO water)", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("25", "Cooling Water", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("26", "Cooling Blowdown", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("27", "Demin Water", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("28", "Boiler Feed Water", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("29", "Nitrogen", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("30", "Instrument Air", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("31", "Plant Air", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("32", "Natural Gas(Fuel)", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("33", "Hydrocarbon Drain", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("34", "COC", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("35", "POC", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("36", "SN", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("37", "FW", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("38", "RW", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("39", "FL", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("40", "CLOSED DRAIN", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("41", "Waste Water", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("50", "Analyzer Room 1,2,3 & Metering", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("52", "Site Office", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("53", "Control Room", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("54", "Laboratory", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("56", "Warehouse", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("58", "Play Yard", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("60", "Fire Station", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("62", "Substation 1,2,3", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("68", "Parking", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("70", "Truck Parking", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("72", "Warehouse Yard", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("74", "Master Point", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("76", "Workshop", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("78", "Maintenance Area", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("80", "Gas Station", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("82", "Cooling Tower", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("84", "Loading Area", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("86", "Operator Building", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("88", "Let-down Station", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("90", "Guard House 1,2", "UnitCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("98", "TEG Drum Filling", "UnitCode"));
        }

        { // DocumentTyp
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DEP", "Detail Engineering Design Package", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DEC", "Design Criteria", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DIG", "Diagram", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DSC", "Description", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DSH", "Data Sheet", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DSP", "Specification", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PRO", "Procedure & Instruction", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("LST", "List", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("MNL", "Manual", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PFD", "Process Flow Diagram", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PID", "Piping And Instrument Diagram", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("RPT", "Report", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("UDD", "Utility Distribution Diagram", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("UFD", "Utility Flow Diagram", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("CSH", "Calculation Sheet", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("BOM", "Bill of material", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("SKT", "Sketch", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("REQ", "Requisition", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("CSD", "Standard Drawing", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("SLD", "Single Line Diagram", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PHI", "Philosophy", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("DWG", "Drawing", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("ITP", "Inspection And Test Procedure", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("TBE", "Technical Bid Evaluation", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("CBE", "Commercial Bid Evaluation", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("MRQ", "Material Requisition", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("RFQ", "Request For Quotation", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PBR", "Procurement Bid Report", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("VDS", "Vendor Selection", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("TRC", "Transaction Committee", "DocumentTyp"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("VDR", "Vendor Document Review", "DocumentTyp"));
        }

        { // DisciplineCode
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("GE", "General", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PR", "Process", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("CE", "Civil", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("ST", "Structure", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("BL", "Building", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("IN", "Instrument", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("FE", "Fix Equipment", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("EL", "Electrical", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("RE", "Rotary Equipment", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("HS", "HSE", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PI", "Piping", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("HV", "HVAC", "DisciplineCode"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("PJ", "Project", "DisciplineCode"));
        }

        { // Class
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("C1", "Requires Client Approval"
                    + " (Document deemed as Approved If no comment is received from Client in 14 calendar days)", "Class"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("C2", "Document is submitted for Client Review", "Class"));
        }

        { // Size
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("A0", "", "Size"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("A1", "", "Size"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("A2", "", "Size"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("A3", "", "Size"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("A4", "", "Size"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("A5", "", "Size"));
        }

        { // POI
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("IFC", "Issued For Comment – C1 Class Document", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("IFA", "Issued For Approval – C1 Class Document", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("IFR", "Issued For Review – C2 Class Document", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("IFDP", "Issued For Detail Engineering Design Package", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("APP", "Approved/ Reviewed", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("AN", "Approved as Noted, work may proceed", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("C", "Commented, will be Re-issued for approval/ Re-issued for review", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("AFQ", "Approved For inquiry", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("AFD", "Approved For Design", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("AFC", "Approved For Construction", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("R", "Rejected", "POI"));
            databaseHelper.definitionCodeDao.createOrUpdate(new Definition("IFI", "Issue For Information", "POI"));
        }

    }
}
