package it.lscarpari.android.thermometerlegitimateappfirebase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Temperature {

    private String datetime;

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
