package edu.northeastern.atyourservice;

/**
 * The class that represents a weather item.
 *
 * @author Lin Han
 */
public class Weather {
    private String dateTime;
    private String temp;
    private String weatherCondition;

    /**
     * Constructor of the class.
     *
     * @param dateTime         the date and time
     * @param temp             the temperature
     * @param weatherCondition the weather condition
     */
    public Weather(String dateTime, String temp, String weatherCondition) {
        this.dateTime = dateTime;
        this.temp = temp;
        this.weatherCondition = weatherCondition;
    }

    /**
     * Gets the temperature.
     *
     * @return the temperature
     */
    public String getTemp() {
        return temp;
    }

    /**
     * Gets the weather condition.
     *
     * @return the weather condition
     */
    public String getWeatherCondition() {
        return weatherCondition;
    }

    /**
     * Gets the date and time.
     *
     * @return the date and time information
     */
    public String getDateTime() {
        return dateTime;
    }
}
