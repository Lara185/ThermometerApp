package it.lscarpari.androidthings.thermometerappfirebase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Class that represents temperature.
 */
public class Temperature {

    // A string for date and time.
    private String datetime;

    // A float for temperature's value.
    private float value;

    public Temperature() {}

    public Temperature(float value) {
        this.datetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.value = value;
    }

    public Temperature(String datetime, float value) {
        this.datetime = datetime;
        this.value = value;
    }

    public String getDateTime() {
        return this.datetime;
    }

    public float getValue() {
        return this.value;
    }

    public void setDateTime(String datetime) {
        this.datetime = datetime;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
