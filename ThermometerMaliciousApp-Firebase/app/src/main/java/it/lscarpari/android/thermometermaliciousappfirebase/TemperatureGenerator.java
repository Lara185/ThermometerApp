package it.lscarpari.android.thermometermaliciousappfirebase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemperatureGenerator {

    private static Temperature temperature = new Temperature(
            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), 25
    );

    public static Temperature generate() {
        String datetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        float f = temperature.getValue() * (float)(1 + (Math.random() * 10 - 5) / 100);
        BigDecimal bd = new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP);
        float value = bd.floatValue();
        temperature.setDateTime(datetime);
        temperature.setValue(value);
        return new Temperature(datetime, value);
    }

    public static Temperature modify(Temperature temperature) {
        float value = temperature.getValue();
        float offset = 2f;
        temperature.setValue(value - offset);
        return temperature;
    }

}
