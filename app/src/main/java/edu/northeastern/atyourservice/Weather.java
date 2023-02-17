package edu.northeastern.atyourservice;

public class Weather {
    private String temp;
    private String description;
    private String date;

    public Weather(String temp, String description, String date) {
        this.temp = temp;
        this.description = description;
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

}
