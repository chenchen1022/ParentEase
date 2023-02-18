package edu.northeastern.atyourservice;

public class WeeklyItems {
    private String day;
    private String temp;
    private String desc;

    public WeeklyItems(String day, String temp, String desc) {
        this.day = day;
        this.temp = temp;
        this.desc = desc;
    }

    public String getDay() {
        return day;
    }

    public String getTemp() {
        return temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
