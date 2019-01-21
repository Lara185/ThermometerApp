package it.lscarpari.androidthings.thermometerappfirebase;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class ButtonALiveData extends LiveData<Boolean> {

    private static final String TAG = ButtonALiveData.class.getName();

    private Button mButtonA;

    private final Button.OnButtonEventListener mOnButtonAEventListener = new Button.OnButtonEventListener() {
        @Override
        public void onButtonEvent(Button button, boolean pressed) {
            setValue(pressed);
        }
    };

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");

        if(mButtonA == null) {
            try {
                mButtonA = RainbowHat.openButtonA();
                mButtonA.setOnButtonEventListener(mOnButtonAEventListener);
            } catch (IOException e) {
                Log.e(TAG, "Unable to open button A.", e);
            }
        }
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");

        if(mButtonA != null) {
            try {
                mButtonA.setOnButtonEventListener(null);
                mButtonA.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close button A.", e);
            } finally {
                mButtonA = null;
            }
        }
    }

}
