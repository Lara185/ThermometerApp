package it.lscarpari.androidthings.thermometerappfirebase;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class ButtonCLiveData extends LiveData<Boolean> {

    private static final String TAG = ButtonCLiveData.class.getName();

    private Button mButtonC;

    private final Button.OnButtonEventListener mOnButtonCEventListener = new Button.OnButtonEventListener() {
        @Override
        public void onButtonEvent(Button button, boolean pressed) {
            setValue(pressed);
        }
    };

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");

        if(mButtonC == null) {
            try {
                mButtonC = RainbowHat.openButtonC();
                mButtonC.setOnButtonEventListener(mOnButtonCEventListener);
            } catch (IOException e) {
                Log.e(TAG, "Unable to open button C.", e);
            }
        }
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");

        if(mButtonC != null) {
            try {
                mButtonC.setOnButtonEventListener(null);
                mButtonC.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close button C.", e);
            } finally {
                mButtonC = null;
            }
        }
    }

}
