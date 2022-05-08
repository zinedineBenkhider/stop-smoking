package com.stop.smoking.home.util;

import android.view.Menu;
import android.view.MenuItem;

import com.stop.smoking.home.presenter.model.DifferenceDateTime;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Utility {
    private static List<Integer> idMenuDaysToDaysMapping=new ArrayList<>();
    private static Map<String,String > deviseSymbolMapping=new HashMap<>();
    private static void initList(){
       if(idMenuDaysToDaysMapping.isEmpty()){
           idMenuDaysToDaysMapping.add(2);
           idMenuDaysToDaysMapping.add(3);
           idMenuDaysToDaysMapping.add(4);
           idMenuDaysToDaysMapping.add(5);
           idMenuDaysToDaysMapping.add(6);
           idMenuDaysToDaysMapping.add(7);
           idMenuDaysToDaysMapping.add(10);
           idMenuDaysToDaysMapping.add(14);
           idMenuDaysToDaysMapping.add(21);
           idMenuDaysToDaysMapping.add(30);
           idMenuDaysToDaysMapping.add(60);
           idMenuDaysToDaysMapping.add(90);
           idMenuDaysToDaysMapping.add(365);
           idMenuDaysToDaysMapping.add(365*5);
       }
    }

    private static void initDeviseSymbolMap(){
        if(deviseSymbolMapping.isEmpty()){
            deviseSymbolMapping.put("Dollar","$");
            deviseSymbolMapping.put("EUR","€");
            deviseSymbolMapping.put("GBP","£");
        }
    }

    public static Integer getNbDays(int id){
        initList();
        return idMenuDaysToDaysMapping.get(id);
    }

    public static String getUpperMenuItemFromUnsmokedDays(String[] items, int unsmokedDays){
        initList();
        String menuItem="";
        for (int i=0;i<items.length;i++){
            if(unsmokedDays<idMenuDaysToDaysMapping.get(i)){
                menuItem= items[i];
                break;
            }
        }
        return menuItem;
    }

    public static String getSymbolOfDevise(String devise){
        initDeviseSymbolMap();
        return deviseSymbolMapping.get(devise);
    }

    public static int[] parseDate(String dateStr){
        int[] res = new int[3];
        String[] date=dateStr.split("-");
        if(date.length==3){
            res[0]=Integer.parseInt(date[0]);
            res[1]=Integer.parseInt(date[1])-1;
            res[2]=Integer.parseInt(date[2]);
        }
        else{
            Calendar cldr = Calendar.getInstance();
            res[0]= cldr.get(Calendar.DAY_OF_MONTH);
            res[1]=cldr.get(Calendar.MONTH);
            res[2]=cldr.get(Calendar.YEAR);
        }
        return res;
    }

    public static DifferenceDateTime findDifference(String start_date, String end_date, boolean divideByFour, SimpleDateFormat dateTimeFormatter)
    {
        try {
            Date d1 = dateTimeFormatter.parse(start_date);
            Date d2 = dateTimeFormatter.parse(end_date);
            assert d2 != null;
            assert d1 != null;
            long difference_In_Time = d2.getTime() - d1.getTime();
            if(divideByFour){
                difference_In_Time=difference_In_Time/4;
            }
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Years = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            return new DifferenceDateTime(difference_In_Years,difference_In_Days,difference_In_Hours,difference_In_Minutes,difference_In_Seconds,difference_In_Time);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * return a time in right format
     * @param hours the hours to display
     * @param minutes the minutes to display
     * @return a string corresponding to the time given
     */
    public static String getFormatTime(int hours,int minutes){
        if(hours>=10 && minutes>=10){
            return hours+":"+minutes;
        }
        else if(hours>=10 && minutes<10){
            return hours+":0"+minutes;
        }
        else if(hours<10 && minutes>=10){
            return "0"+hours+":"+minutes;
        }
        else {
            return "0"+hours+":0"+minutes;
        }
    }

    public static DifferenceDateTime millisecondToDate(long milliseconds){
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        long years = (milliseconds / (1000L * 60 * 60 * 24 * 365));
        long days = (milliseconds / (1000 * 60 * 60 * 24)) % 365;
        DifferenceDateTime res=new DifferenceDateTime(years,days,hours,minutes,0,milliseconds);
        res.setValueToMonth();
        return new DifferenceDateTime(years,days,hours,minutes,0,milliseconds);
    }

    public static String formatFloat(float value){
        DecimalFormatSymbols formatSymbols=new DecimalFormatSymbols(Locale.ENGLISH);
        formatSymbols.setDecimalSeparator('.');
        formatSymbols.setGroupingSeparator(' ');
        DecimalFormat df=new DecimalFormat("#,###.##", formatSymbols);
        if(value==Math.round(value)){
            return df.format(value);
        }
        else{
            return df.format(value);
        }
    }


}
