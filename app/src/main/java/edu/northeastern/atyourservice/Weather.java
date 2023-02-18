package edu.northeastern.atyourservice;

public class Weather {
    private String temp;
    private String description;
    private String dateTime;

    public Weather(String temp, String description, String dateTime) {
        this.temp = temp;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

}
