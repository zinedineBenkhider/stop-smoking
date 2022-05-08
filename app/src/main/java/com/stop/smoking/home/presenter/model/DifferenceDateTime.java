package com.stop.smoking.home.presenter.model;

public class DifferenceDateTime {
    private long years;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;
    private long months;
    private long differenceWithMilliseconds;
    public DifferenceDateTime(long years, long days, long hours, long minutes, long seconds, long differenceWithMilliseconds) {
        this.years = years;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.differenceWithMilliseconds = differenceWithMilliseconds;
    }

    public long getYears() {
        return years;
    }

    public void setYears(long years) {
        this.years = years;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getDifferenceWithMilliseconds() {
        return differenceWithMilliseconds;
    }

    public void setDifferenceWithMilliseconds(long differenceWithMilliseconds) {
        this.differenceWithMilliseconds = differenceWithMilliseconds;
    }

    @Override
    public String toString() {
        String res="";
        if(years!=0){
            res+=years+"Y ";
        }
        if(days!=0){
            res+=days+"D ";
        }
        if(hours!=0){
            res+=hours+"h ";
        }
        if(minutes!=0){
            res+=minutes+"m ";
        }
        res+=seconds+"s ";
        return res;
    }

    public void setValueToMonth(){
        double nbDayInMonthAverage=30.42;
        if(days!=0){
            months=(long)(days/nbDayInMonthAverage);
        }
        if(days%30>0){
            days=days%30;
        }
    }

    public String toStringBis() {
        String res="";
        double nbDayInMonthAverage=30.42;
        if(years!=0){
            res+=years+"Y ";
        }
        if(days!=0){
            res+=(int)(days/nbDayInMonthAverage)+"M ";
        }
        if(days%30>0){
            res+=days%30+"D ";
        }
        return res;
    }

    public String toStringForAcceptInReturn() {
        String res="";
        double nbDayInMonthAverage=30.42;
        int count=0;
        if(years!=0){
            res+=years+"Y ";
            count++;
        }
        int nbMonths=(int)(days/nbDayInMonthAverage);
        if(nbMonths>0){
            res+=nbMonths+"M ";
            count++;
        }
        if(days%30>0){
            res+=days%30+"D ";
            count++;
        }
        if(hours>0 && count<3){
            res+=hours+"H ";
            count++;
        }
        if(count<3){
            res+=minutes+"m ";
        }
        return res;
    }

    public long getMonths() {
        return months;
    }

    public void setMonths(long months) {
        this.months = months;
    }
}
