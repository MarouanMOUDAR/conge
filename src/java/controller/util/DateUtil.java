/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author
 */
public class DateUtil {
    
    public static Date addJourToDate(Date date, int nbrjour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, nbrjour);
        System.out.println("haa cal ==> " + Calendar.DATE);
        System.out.println("haa date fin ==> " + formateDate("dd/MM/yyyy", calendar.getTime()));
        System.out.println("haa date debut ==> " + formateDate("dd/MM/yyyy", date));
        System.out.println("haa  nbrjour ==> " + nbrjour);
        
        return (calendar.getTime());
    }
    
    public static String formateDate(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (date != null) {
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }
    
    public static Date parse(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static java.sql.Date convertFormUtilToSql(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }
    
}
