/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author reza
 */
public class PersianCalendar extends com.ghasemkiani.util.icu.PersianCalendar {

    public PersianCalendar() {
        super(new Date());
    }

    private int getMount() {
        return this.get(Calendar.MONTH) + 1;
    }

    public String hour() {
        int temp = this.get(Calendar.HOUR_OF_DAY);
        return temp > 9 ? "" + temp : "0" + temp;
    }

    public String minute() {
        int temp = this.get(Calendar.MINUTE);
        return temp > 9 ? "" + temp : "0" + temp;

    }

    public String second() {
        int temp = this.get(Calendar.SECOND);
        return temp > 9 ? "" + temp : "0" + temp;
    }

    public String year() {
        return this.get(Calendar.YEAR) + "";
    }

    public String year2dig() {
        return year().substring(2);
    }

    public String month() {
        return this.getMount() + "";
    }

    public String day() {
        return this.get(Calendar.DAY_OF_MONTH) + "";
    }

    public String getNowMilladiDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
    }

    public String nameOfMonthEN() {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[Calendar.getInstance().get(Calendar.MONTH)];
    }

    public String nameOfMonthFA() {
        String[] monthNames = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
        return monthNames[getMount() - 1];
    }

    public String nameOFdayFa() {
        String[] dayNames = {"یک شنبه", "دو شنبه", "سه شنبه", "چهار شنبه", "پنج شنبه", "جمعه","شنبه"};
        return dayNames[this.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public String nameOFdayEn() {
        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};
        return dayNames[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];

    }
}
