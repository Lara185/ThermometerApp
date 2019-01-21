package it.lscarpari.androidthings.thermometerappfirebase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class TemperatureLiveData extends LiveData<Float> {

    private static final String TAG = TemperatureLiveData.class.getName();

    private Bmx280SensorDriver mEnvironmentalSensorDriver;
    private SensorManager mSensorManager;

    protected TemperatureLiveData(Application application) {
        mSensorManager = application.getSystemService(SensorManager.class);
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");

        if(mEnvironmentalSensorDriver == null) {
            try {
                mEnvironmentalSensorDriver = RainbowHat.createSensorDriver();
            } catch (IOException e) {
                Log.e(TAG, "Unable to open temperature sensor.", e);
            }
        }

        mSensorManager.registerDynamicSensorCallback(mDynamicSensorCallback);
        mEnvironmentalSensorDriver.registerTemperatureSensor();

        //Sensor mTemperature = mSensorManager.getDynamicSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).get(0);
        //mSensorManager.registerListener(mTemperatureSensorEventListener, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");

        mEnvironmentalSensorDriver.unregisterTemperatureSensor();
        mSensorManager.unregisterListener(mTemperatureSensorEventListener);
        mSensorManager.unregisterDynamicSensorCallback(mDynamicSensorCallback);

        if(mEnvironmentalSensorDriver != null) {
            try {
                mEnvironmentalSensorDriver.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close temperature sensor.", e);
            } finally {
                mEnvironmentalSensorDriver = null;
            }
        }
    }

    private final SensorManager.DynamicSensorCallback mDynamicSensorCallback = new SensorManager.DynamicSensorCallback() {
        @Override
        public void onDynamicSensorConnected(Sensor sensor) {
            if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
                mSensorManager.registerListener(mTemperatureSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    };

    private SensorEventListener mTemperatureSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.d(TAG, "onSensorChanged: ");

            setValue(event.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG, "onAccuracyChanged: " + accuracy);
        }
    };

}
