package it.lscarpari.androidthings.thermometerappfirebase;

import android.os.AsyncTask;
import android.util.Log;

public class LedAsyncTask extends AsyncTask {

    private static final String TAG = LedAsyncTask.class.getSimpleName();

    private RainbowHatManager mRainbowHatManager;

    public LedAsyncTask(RainbowHatManager rainbowHatManager) {
        mRainbowHatManager = rainbowHatManager;
    }

    /**
     * Turns the red led on and turns it off one second later.
     * @param objects
     * @return null
     */
    @Override
    protected Void doInBackground(Object[] objects) {

        try {
            mRainbowHatManager.openLedRED();
            Thread.sleep(1000);
            mRainbowHatManager.closeLedRED();
        } catch (InterruptedException e) {
            Log.d(TAG, "Unable to make the thread sleep.", e);
        }

        return null;
    }

}
