/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.register;

import com.almasb.common.encryption.AESEncryptor;
import java.io.File;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reza
 */
public class Register {

    String password = "rezagolab";

    public boolean showLoginStage = true;

    public int userID;

    public void writeEncrype(String name, String text) {
        try {
//            byte[] data = Files.readAllBytes(new File(name).toPath());
            byte[] data = text.getBytes();
            byte[] encrypted = AESEncryptor.encrypt(data,
                    password.toCharArray());
            Files.write(new File(name).toPath(),
                    encrypted);
        } catch (Exception ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readEncrypt(String fileName) {
        try {
            byte[] data = Files.readAllBytes(new File(fileName).toPath());
            byte[] encrypted = AESEncryptor.decrypt(data,
                    password.toCharArray());
//            System.out.println("data == " + new String(encrypted, "UTF-8"));
            return new String(encrypted, "UTF-8");
        } catch (Exception ex) {
//            System.err.println("khata dar zaman khandan file encrypt shode rokh dadeh ast");
            return null;
        }
    }

    public boolean checkRegister() {
        String line = readEncrypt("luoinch");
        if (line == null) {
            return false;
        }
        try {
            System.out.println("file = " + line);
            String year1 = line.substring(0, 4);
            String month1 = line.substring(4, 6);
            String day1 = line.substring(6, 8);
//            System.out.println("year1 = " + year1);
//            System.out.println("month1 = " + month1);
//            System.out.println("day1 = " + day1);
            String year2 = line.substring(8, 12);
            String month2 = line.substring(12, 14);
            String day2 = line.substring(14, 16);
//            System.out.println("year2 = " + year2);
//            System.out.println("month2 = " + month2);
//            System.out.println("day2 = " + day2);
            String yearRemember = line.substring(16, 20);
            String monthRemember = line.substring(20, 22);
            String dayRemember = line.substring(22, 24);

            userID = Integer.parseInt(line.substring(24));
//            System.out.println("userID == " + userID);
//            System.out.println("yearRemember == " + yearRemember);
//            System.out.println("monthRemember == " + monthRemember);
//            System.out.println("dayRemember == " + dayRemember);

            Date dateStart = new GregorianCalendar(Integer.parseInt(year1), Integer.parseInt(month1), Integer.parseInt(day1)).getTime();
            Date dateEnd = new GregorianCalendar(Integer.parseInt(year2), Integer.parseInt(month2), Integer.parseInt(day2)).getTime();
            Calendar dateNow = Calendar.getInstance();

//            System.out.println("dateStars = " + dateStart);
//            System.out.println("dateNow = " + dateNow);
            if (dateNow.getTime().after(dateEnd)) {
                System.err.println("system bayad gheire faaal shavad1");
                return false;
            }
            if (dateStart.after(dateEnd)) {
                System.err.println("system bayad gheire faaal shavad2");
                return false;
            }
            if (dateStart.after(dateNow.getTime())) {
                System.err.println("system bayad gheire faaal shavad3");
                return false;
            }

            year1 = Calendar.getInstance().get(Calendar.YEAR) + "";
            month1 = Calendar.getInstance().get(Calendar.MONTH) + "";
            month1 = month1.length() == 1 ? "0" + month1 : month1;
            day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "";
            day1 = day1.length() == 1 ? "0" + day1 : day1;

            if (Integer.parseInt(yearRemember) == dateNow.get(Calendar.YEAR) && Integer.parseInt(monthRemember) == dateNow.get(Calendar.MONTH) && Integer.parseInt(dayRemember) == dateNow.get(Calendar.DAY_OF_MONTH)) {
                showLoginStage = false;
            }
            writeEncrype("luoinch", year1 + month1 + day1 + year2 + month2 + day2 + yearRemember + monthRemember + dayRemember + userID);

        } catch (Exception e) {
            System.err.println("system bayad gheire faaal shavad4");
            return false;
        }
        return true;
    }

    public boolean checkLogin(boolean isRemembered) {
        String line = readEncrypt("luoinch");
        if (line == null) {
            return false;
        }
        try {
            String part1 = line.substring(0, 16);
            String yearRemember = line.substring(16, 20);
            String monthRemember = line.substring(20, 22);
            String dayRemember = line.substring(22, 24);

            if (!isRemembered) {
                yearRemember = "1111";
            } else {
                yearRemember = Calendar.getInstance().get(Calendar.YEAR) + "";
                monthRemember = Calendar.getInstance().get(Calendar.MONTH) + "";
                monthRemember = monthRemember.length() > 1 ? monthRemember : "0" + monthRemember;
                dayRemember = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "";
                dayRemember = dayRemember.length() > 1 ? dayRemember : "0" + dayRemember;
            }
            writeEncrype("luoinch", part1 + yearRemember + monthRemember + dayRemember + userID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean exitUser() {
        String line = readEncrypt("luoinch");
        if (line == null) {
            return false;
        }
        try {
            String part1 = line.substring(0, 16) + "11112233" + line.substring(24);

            writeEncrype("luoinch", part1);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
