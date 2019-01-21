package it.lscarpari.androidthings.thermometerappfirebase;

import android.app.Application;
import android.util.Log;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

/**
 * Class that helps to open, update and close
 * the alphanumeric display and the red led.
 */
public class RainbowHatManager {

    private static final String TAG = RainbowHatManager.class.getName();

    private AlphanumericDisplay mAlphanumericDisplay;

    private Gpio mLedRED;

    private Gpio mLedBLUE;

    public RainbowHatManager(Application application) {

    }

    /**
     * Opens the alphanumeric display.
     */
    public void openAlphanumericDisplay() {
        Log.d(TAG, "openAlphanumericDisplay");

        if(mAlphanumericDisplay == null) {
            try {
                mAlphanumericDisplay = RainbowHat.openDisplay();
                mAlphanumericDisplay.clear();
                mAlphanumericDisplay.setEnabled(true);
            } catch (IOException e) {
                Log.e(TAG, "Unable to open alphanumeric display.", e);
            }
        }
    }

    /**
     * Shows a float value on the alphanumeric display.
     * @param value The float value to display.
     */
    public void updateAlphanumericDisplay(Float value) {
        Log.d(TAG, "updateAlphanumericDisplay");

        try {
            mAlphanumericDisplay.display(value);
        } catch (IOException e) {
            Log.e(TAG, "Unable to update alphanumeric display.", e);
        }
    }

    /**
     * Closes the alphanumeric display.
     */
    public void closeAlphanumericDisplay() {
        Log.d(TAG, "closeAlphanumericDisplay");

        if(mAlphanumericDisplay != null) {
            try {
                mAlphanumericDisplay.setEnabled(false);
                mAlphanumericDisplay.clear();
                mAlphanumericDisplay.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close alphanumeric display.", e);
            } finally {
                mAlphanumericDisplay = null;
            }
        }
    }

    /**
     * Checks if alphanumeric display is opened.
     * @return true if alphanumeric display is opened, false otherwise.
     */
    public boolean isAlphanumericDisplayOpened() {
        Log.d(TAG, "isAlphanumericDisplayOpened");

        return mAlphanumericDisplay != null;
    }

    /**
     * Opens the red led and sets its value to true.
     */
    public void openLedRED() {
        Log.d(TAG, "openLedRED");

        if(mLedRED == null) {
            try {
                mLedRED = RainbowHat.openLedRed();
                mLedRED.setValue(true);
            } catch (IOException e) {
                Log.e(TAG, "Unable to open led RED.", e);
            }
        }
    }

    /**
     * Sets the red led's value to false and closes the red led.
     */
    public void closeLedRED() {
        Log.d(TAG, "closeLedRED");

        if(mLedRED != null) {
            try {
                mLedRED.setValue(false);
                mLedRED.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close led RED.", e);
            } finally {
                mLedRED = null;
            }
        }
    }

    /**
     * Opens the blue led and sets its value to true.
     */
    public void openLedBLUE() {
        Log.d(TAG, "openLedBLUE");

        if(mLedBLUE == null) {
            try {
                mLedBLUE = RainbowHat.openLedBlue();
                mLedBLUE.setValue(true);
            } catch (IOException e) {
                Log.e(TAG, "Unable to open led BLUE.", e);
            }
        }
    }

    /**
     * Sets the blue led's value to false and closes the blue led.
     */
    public void closeLedBLUE() {
        Log.d(TAG, "closeLedBLUE");

        if(mLedBLUE != null) {
            try {
                mLedBLUE.setValue(false);
                mLedBLUE.close();
            } catch (IOException e) {
                Log.e(TAG, "Unable to close led BLUE.", e);
            } finally {
                mLedBLUE = null;
            }
        }
    }

}
